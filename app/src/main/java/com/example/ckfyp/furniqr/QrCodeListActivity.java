package com.example.ckfyp.furniqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QrCodeListActivity extends AppCompatActivity {

    List<String> qrdata;
    ListView qrcodelist;
    qrcodeadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_list);

        try{
            qrdata = (List<String>) storage.read(QrCodeListActivity.this, "qrdata");
        }catch (IOException e) {
            qrdata = new ArrayList<>();
        }catch (ClassNotFoundException e) {
            qrdata = new ArrayList<>();
        }

        qrcodelist = (ListView) findViewById(R.id.qrcodelist);
        adapter = new qrcodeadapter(this, qrdata);
        qrcodelist.setAdapter(adapter);

        qrcodelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qrcode = qrdata.get(position);
                Intent intent = new Intent(QrCodeListActivity.this, QrCodeDetailsActivity.class);
                intent.putExtra("qrcode", qrcode);
                QrCodeListActivity.this.startActivity(intent);
            }
        });

    }
}
