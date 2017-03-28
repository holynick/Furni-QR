package com.example.ckfyp.furniqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class AssemblyTypeActivity extends AppCompatActivity {

    Button btnDIY, btnAssembly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assembly_type);

        Intent intent = getIntent();
        final int cartqrcode = intent.getIntExtra("cartqrcode", -1);

        btnDIY = (Button) findViewById(R.id.btnDIY);
        btnAssembly = (Button) findViewById(R.id.btnAssembly);

        btnAssembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssemblyTypeActivity.this, QrCodeActivity.class);
                intent.putExtra("cartqrcode", cartqrcode);
                intent.putExtra("assemblytype", 1);
                AssemblyTypeActivity.this.startActivity(intent);
            }
        });

        btnDIY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssemblyTypeActivity.this, QrCodeActivity.class);
                intent.putExtra("cartqrcode", cartqrcode);
                intent.putExtra("assemblytype", 2);
                AssemblyTypeActivity.this.startActivity(intent);
            }
        });
    }
}
