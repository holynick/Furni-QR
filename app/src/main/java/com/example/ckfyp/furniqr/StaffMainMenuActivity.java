package com.example.ckfyp.furniqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StaffMainMenuActivity extends AppCompatActivity {

    Button scanner, viewtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main_menu);

        scanner = (Button) findViewById(R.id.scanner);
        viewtask = (Button) findViewById(R.id.viewtask);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMainMenuActivity.this, ScannerActivity.class);
                StaffMainMenuActivity.this.startActivity(intent);
            }
        });

        viewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMainMenuActivity.this, TaskActivity.class);
                StaffMainMenuActivity.this.startActivity(intent);
            }
        });
    }
}
