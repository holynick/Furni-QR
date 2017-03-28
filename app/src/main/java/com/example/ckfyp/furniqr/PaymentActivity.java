package com.example.ckfyp.furniqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {

    String idarray = "";
    String quantityarray = "";
    String amountarray = "";
    String url = "https://furnituredatabase.000webhostapp.com/furniqrregistercart.php";
    int randomcode;
    float amount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final List<cartfurnitureitem> cart = CartManager.getCart();
        TextView tvAmount = (TextView) findViewById(R.id.tvAmount);
        Button paybtn = (Button) findViewById(R.id.paybtn);


        for (int i = 0; i < cart.size(); i ++){
            amount = amount + cart.get(i).getAmount();
        }



        tvAmount.setText("Amount: RM " + Float.toString(amount));

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cart.size(); i ++){
                    idarray = idarray + cart.get(i).getId() + " , ";
                    quantityarray = quantityarray + cart.get(i).getQuantity() + " , ";
                    amountarray = amountarray + cart.get(i).getAmount() + " , ";
                }
                Random rnd =  new Random();
                randomcode = 1000 + rnd.nextInt(9000);
                final ProgressDialog loading = new ProgressDialog(PaymentActivity.this);
                loading.setTitle("Payment in process");
                loading.setMessage("Please wait...");
                loading.show();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            boolean success = object.getBoolean("success");
                            loading.dismiss();
                            if (success) {
                                cart.clear();
                                Intent intent = new Intent(PaymentActivity.this, AssemblyTypeActivity.class);
                                intent.putExtra("cartqrcode",randomcode);
                                PaymentActivity.this.startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(),"Payment Failed",Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idarray", idarray);
                        params.put("quantityarray", quantityarray);
                        params.put("amountarray", amountarray);
                        params.put("totalamount", amount + "");
                        params.put("cartqrcode", randomcode + "");
                        params.put("userid", LoginActivity.useridX);
                        params.put("username", LoginActivity.usernameX);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
                requestQueue.add(request);
            }
        });
    }
}
