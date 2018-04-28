package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SellerAddItemActivity extends AppCompatActivity {

    public final static String SELLER_REGISTRATION_ITEMID = "SELLER_REGISTRATION_ITEMI_ID";
    public final static String SELLER_REGISTRATION_ITEM = "SELLER_REGISTRATION_ITEM_NAME";

    private EditText txtNamaItem, txtAlamat, txtHargaMIN, txtHargaMAX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_item);

        txtNamaItem=(EditText)findViewById(R.id.txtItemSellerNama);
        txtAlamat=(EditText)findViewById(R.id.txtItemSellerAlamat);
        txtHargaMIN=(EditText)findViewById(R.id.txtItemSellerHargaMIN);
        txtHargaMAX=(EditText)findViewById(R.id.txtItemSellerHargaMAX);

        Intent ini = getIntent();
        setTitle("Add to "+ini.getStringExtra(SELLER_REGISTRATION_ITEM));
        txtNamaItem.setText(ini.getStringExtra(SELLER_REGISTRATION_ITEM));
    }
}
