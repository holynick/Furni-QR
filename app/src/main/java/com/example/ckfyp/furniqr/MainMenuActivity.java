package com.example.ckfyp.furniqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final CardView showcatalog = (CardView) findViewById(R.id.showcatalog);
        final CardView qrcode = (CardView) findViewById(R.id.qrcode);
        final CardView hotitem = (CardView) findViewById(R.id.hotitem);
        final CardView special = (CardView) findViewById(R.id.special);


        showcatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CatalogActivity.class);
                MainMenuActivity.this.startActivity(intent);
            }
        });

        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CatalogActivity.class);
                intent.putExtra("special", "blackfriday");
                MainMenuActivity.this.startActivity(intent);
            }
        });

        hotitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CatalogActivity.class);
                intent.putExtra("hotitem", "hot");
                MainMenuActivity.this.startActivity(intent);
            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, QrCodeListActivity.class);
                MainMenuActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cartmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_cart:
                Intent intent = new Intent(MainMenuActivity.this, CartActivity.class);
                MainMenuActivity.this.startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }
}
