package com.example.ckfyp.furniqr;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    static TextView totalamount;
    ListView cartlist;
    cartadapter adapter;

    final public static List<cartfurnitureitem> cart = CartManager.getCart();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartlist = (ListView) findViewById(R.id.cartlist);
        final Button checkout = (Button) findViewById(R.id.checkout);
        final Button remeovecart = (Button) findViewById(R.id.removecart);
        totalamount = (TextView) findViewById(R.id.totalamount);

        getcart();

        remeovecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i =  cart.size()-1; i >= 0; i--){
                    if (cart.get(i).selected == true)
                        cart.remove(cart.get(i));
                }
                finish();
                startActivity(getIntent());
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Checkout?");
                builder.setMessage("Do you want to check out with the selected item? (Unselected item will be removed)");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i =  cart.size()-1; i >= 0; i--){
                            if (cart.get(i).selected == false)
                                cart.remove(cart.get(i));
                        }
                        if (cart.isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("Error");
                            builder.setMessage("The cart is empty");
                            builder.setNegativeButton("Ok", null);

                        }else {
                            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                            CartActivity.this.startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        for (int i = 0; i < cart.size(); i++){
            cart.get(i).selected = false;
        }

        gettotalamount();

    }

    public void getcart(){
        cartlist.setAdapter(null);
        adapter = new cartadapter(CartActivity.this,cart, true);
        cartlist.setAdapter(adapter);

    }

    public static void gettotalamount(){
        float amount = 0;
        for (int i = 0; i < cart.size(); i++){
            if (cart.get(i).selected == true)
            amount = amount + cart.get(i).getAmount();
        }
        totalamount.setText(Float.toString(amount));
    }
}
