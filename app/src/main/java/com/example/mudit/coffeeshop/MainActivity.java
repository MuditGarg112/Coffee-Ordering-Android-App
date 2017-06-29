/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */

package com.example.mudit.coffeeshop;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mudit.coffeeshop.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private static int quantity = 0;
    private static String[] variant = {"Espresso", "Macchiato", "Ristretto", "Long Black", "Cafe Latte", "Cappuccino", "Piccolo Latte", "Mocha", "Affogato", "Flat White"};
    private static float[] price_list = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    private static int variant_number = 0;
    private static int size = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity = 0;
        variant_number = -1;
        size = -1;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increase(View view) {
        if (quantity < 10)
            display(++quantity);
        else {
            quantity = 0;
            display(quantity);
        }
    }

    public void decrease(View view) {
        if (quantity > 0)
            display(--quantity);
        else {
            quantity = 10;
            display(quantity);
        }
    }

    public void previous(View view) {
        if (variant_number > 0) {
            display_type(--variant_number);
        } else {
            variant_number = 9;
            display_type(variant_number);
        }

    }

    public void next(View view) {
        if (variant_number < 9) {
            display_type(++variant_number);
        } else {
            variant_number = 0;
            display_type(variant_number);
        }

    }

    private void display_type(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.variant);
        quantityTextView.setText(variant[variant_number]);

        TextView small = (TextView) findViewById(R.id.small_price);
        small.setText("Small: " + price_list[number]);

        TextView medium = (TextView) findViewById(R.id.medium_price);
        medium.setText("Medium: " + (price_list[number] * 1.5));

        TextView large = (TextView) findViewById(R.id.large_price);
        large.setText("Large: " + (price_list[number] * 2));
    }


    public void PlaceOrder(View view) {
        finalise(quantity);
    }


    private void finalise(int number) {
        TextView f = (TextView) findViewById(R.id.last);
        TextView c = (TextView) findViewById(R.id.cost);
        int cream_price = 0;
        int chocolate_price = 0;
        float final_price = 0;
        if (variant_number != -1 && quantity > 0 && size != -1) {
            if (size == 0) {
                final_price = quantity * price_list[variant_number];
            } else if (size == 1) {
                final_price = (float) (quantity * price_list[variant_number] * 1.5);
            } else
                final_price = (float) (quantity * price_list[variant_number] * 2.0);

            CheckBox whipped_cream = (CheckBox) findViewById(R.id.Whipped_cream_check_box);
            boolean cream = whipped_cream.isChecked();
            if (cream == true) {
                cream_price = 10;
            }
            CheckBox chocolate_topping = (CheckBox) findViewById(R.id.chocolate_topping_check_box);
            boolean topping = chocolate_topping.isChecked();
            if (topping == true) {
                chocolate_price = 15;
            }

            final_price += chocolate_price + cream_price;
            String glass;

            if(size==0)
                glass = "Small";
            else if(size==1)
                glass = "Medium";
            else
                glass = "Large";

            String order_summary = "Quantity: "+quantity+"\nVariant: "+variant[variant_number]+"\nWhipped cream: "+cream+"\nChocolate topping: "+topping+"\nSize: "+glass;
            order_summary+="\nFinal Price : "+final_price;

            c.setText(order_summary);

            String s = "Order registered for " + number + " " + variant[variant_number];
            String string ="";
            EditText name = (EditText) findViewById(R.id.Enter_name);
            String NAME = name.getText().toString();

            string+="\n\nName: "+NAME;
            String subject = "Coffee Order from "+ NAME;

            EditText num = (EditText) findViewById(R.id.Enter_number);
            String NUMBER = num.getText().toString();

            string+="\nNumber: "+NUMBER;

            EditText address = (EditText) findViewById(R.id.Enter_address);
            String ADDRESS = address.getText().toString();

            string+="\nAddress: "+ADDRESS;

            if (NAME.length()==0) {
                f.setText("Enter Your Name");
            }

            else if (NUMBER.length()!=10) {
                f.setText("Invalid Number");
            }

            else if (ADDRESS.length()==0) {
                f.setText("Enter Your Address");
            }

            else {
                f.setText(s);
                String body = order_summary + "\n\nfrom:" + string;
                Intent intent;
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gargmudit11@gmail.com", "mudit16057@iiitd.ac.in"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }


        } else if (quantity == 0) {
            c.setText("Please select the number of coffee");
            f.setText("Place Your Order");
        } else if (variant_number == -1) {
            c.setText("Please select a variant");
            f.setText("Place Your Order");
        } else if (size == -1) {
            c.setText("Please select the size");
            f.setText("Place Your Order");
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.number);
        quantityTextView.setText("" + number);
    }

    public void setprice_small(View view) {
        size = 0;
        cost(size);
        Button b = (Button) findViewById(R.id.small);
        b.setSoundEffectsEnabled(true);
    }

    public void setprice_medium(View view) {
        size = 1;
        cost(size);
    }

    public void setprice_large(View view) {
        size = 2;
        cost(size);
    }

    private void cost(int size) {
        TextView final_cost = (TextView) findViewById((R.id.cost));
        if (quantity > 0 && variant_number != -1) {
            int cream_price = 0;
            int chocolate_price = 0;
            float final_price = 0;
            if (variant_number != -1 && quantity > 0 && size != -1) {
                if (size == 0) {
                    final_price = quantity * price_list[variant_number];
                } else if (size == 1) {
                    final_price = (float) (quantity * price_list[variant_number] * 1.5);
                } else
                    final_price = (float) (quantity * price_list[variant_number] * 2.0);

                CheckBox whipped_cream = (CheckBox) findViewById(R.id.Whipped_cream_check_box);
                boolean cream = whipped_cream.isChecked();
                if (cream == true) {
                    cream_price = 10;
                }
                CheckBox chocolate_topping = (CheckBox) findViewById(R.id.chocolate_topping_check_box);
                boolean topping = chocolate_topping.isChecked();
                if (topping == true) {
                    chocolate_price = 15;
                }

                final_price += chocolate_price + cream_price;
                String glass;

                if (size == 0)
                    glass = "Small";
                else if (size == 1)
                    glass = "Medium";
                else
                    glass = "Large";

                String order_summary = "Quantity: " + quantity + "\nVariant: " + variant[variant_number] + "\nWhipped cream: " + cream + "\nChocolate topping: " + topping + "\nSize: " + glass;
                order_summary += "\nFinal Price : " + final_price;

                final_cost.setText(order_summary);
            }
        }else if (quantity == 0)
                final_cost.setText("Please select the number of coffee");

            else if (variant_number == -1) {
                final_cost.setText("Please select a type ");
            }
        }


}
