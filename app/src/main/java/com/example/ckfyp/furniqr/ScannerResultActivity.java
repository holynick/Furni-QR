package com.example.ckfyp.furniqr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ScannerResultActivity extends AppCompatActivity {

    resultadapter adapter;
    ListView resultlist;
    TextView assembly;
    List<cartfurnitureitem> cart = ScannerActivity.cart;
    String Atype = ScannerActivity.Atype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_result);

        resultlist = (ListView) findViewById(R.id.resultlist);
        assembly = (TextView) findViewById(R.id.assembly);

        adapter = new resultadapter(ScannerResultActivity.this, cart);
        resultlist.setAdapter(adapter);
        if (Atype.equals("2")) {
            assembly.setText("Assembly Type: DIY");
        } else
            assembly.setText("Assembly Type: Assemble by staff");


    }

    @Override
    public void onBackPressed() {
        cart.clear();
        super.onBackPressed();
    }
}
