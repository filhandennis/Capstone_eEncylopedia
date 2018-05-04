package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SellerAddItemActivity extends AppCompatActivity {

    public final static String SELLER_REGISTRATION_ITEMID = "SELLER_REGISTRATION_ITEMI_ID";
    public final static String SELLER_REGISTRATION_ITEM = "SELLER_REGISTRATION_ITEM_NAME";

    private EditText txtNamaItem, txtAlamat, txtHargaMIN, txtHargaMAX;
    private Button btnAddSeller;

    //Ref
    private String menuId;

    //Firebase Object
    FirebaseAuth auth;

    //Helper Class
    private SellerFirebaseHelper cSeller;
    private MenuFirebaseHelper cMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_item);

        txtNamaItem=(EditText)findViewById(R.id.txtItemSellerNama);
        txtAlamat=(EditText)findViewById(R.id.txtItemSellerAlamat);
        txtHargaMIN=(EditText)findViewById(R.id.txtItemSellerHargaMIN);
        txtHargaMAX=(EditText)findViewById(R.id.txtItemSellerHargaMAX);

        btnAddSeller=(Button)findViewById(R.id.btnItemSellerAdd);

        Intent ini = getIntent();
        setTitle("Add to "+ini.getStringExtra(SELLER_REGISTRATION_ITEM));
        txtNamaItem.setText(ini.getStringExtra(SELLER_REGISTRATION_ITEM));

        //Get Item ID
        menuId=ini.getStringExtra(SELLER_REGISTRATION_ITEMID);

        //Helper Refer
        cMenu=new MenuFirebaseHelper(this);
        //Firebase Object
        auth=FirebaseAuth.getInstance();

        //SharedPreferences Object
        final SharedPreferences prefSeller=getApplicationContext().getSharedPreferences("Seller",0);

        btnAddSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaItem = txtNamaItem.getText().toString();
                String alamat=txtAlamat.getText().toString();
                if(alamat.trim().length()<1){alamat=prefSeller.getString("Address","");}
                int minPrice = Integer.parseInt(txtHargaMIN.getText().toString());
                int maxPrice = Integer.parseInt(txtHargaMAX.getText().toString());
                if(namaItem.length()<3 || minPrice==0){return;}
                cMenu.addMenuSeller(menuId,auth.getCurrentUser().getUid(),namaItem,alamat,"",minPrice,maxPrice);
                Toast.makeText(SellerAddItemActivity.this, "Registered to Item "+menuId, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
