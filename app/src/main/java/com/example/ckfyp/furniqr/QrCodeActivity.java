package com.example.ckfyp.furniqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QrCodeActivity extends AppCompatActivity {

    ImageView qrcodeimg;
    Button backtomenu;
    String url = "https://furnituredatabase.000webhostapp.com/furniqrregisterqrcode.php";
    List<String> qrdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        try{
            qrdata = (List<String>) storage.read(QrCodeActivity.this, "qrdata");
        }catch (IOException e) {
            qrdata = new ArrayList<>();
        }catch (ClassNotFoundException e) {
            qrdata = new ArrayList<>();
        }

        Intent intent = getIntent();
        final int cartqrcode = intent.getIntExtra("cartqrcode", -1);
        final int assemblytype = intent.getIntExtra("assemblytype", -1);

        final String qrcode = Integer.toString(cartqrcode) + Integer.toString(assemblytype);

        qrdata.add(qrcode);

        qrcodeimg = (ImageView) findViewById(R.id.qrcodeimg);
        backtomenu = (Button) findViewById(R.id.backtomenu);

        try {
            storage.write(QrCodeActivity.this, "qrdata", qrdata);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(qrcode, BarcodeFormat.QR_CODE,200,200);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        qrcodeimg.setImageBitmap(bitmap);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");
                    if (success) {
                      Toast.makeText(getBaseContext(),"Qr code added to database", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(),"Qr code failed to add into the database.",Toast.LENGTH_SHORT);
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
                params.put("qrcode", qrcode);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(QrCodeActivity.this);
        requestQueue.add(request);

        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QrCodeActivity.this, MainMenuActivity.class);
                QrCodeActivity.this.startActivity(intent);
            }
        });
    }
}
