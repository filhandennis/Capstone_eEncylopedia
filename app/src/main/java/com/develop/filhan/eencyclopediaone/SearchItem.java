package com.develop.filhan.eencyclopediaone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchItem extends AppCompatActivity {

    private Button btnCari;
    private EditText txtCari;
    private ListView listCari;
    private TextView lblStatusCari;

    private ArrayList<MenuModel> datas;
    private ArrayAdapter dataAdapter;

    private ItemController itemController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchitem);
        setTitle("Pencarian");

        btnCari=(Button)findViewById(R.id.btnCari);
        txtCari=(EditText) findViewById(R.id.txtCari);
        listCari=(ListView)findViewById(R.id.listCari);
        lblStatusCari=(TextView)findViewById(R.id.lblStatusCari);

        itemController=new ItemController(this);
        datas = new ArrayList<>();

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cari();
            }
        });
    }

    public void cari(){
        String nama = txtCari.getText().toString();
        ArrayList<MenuModel> cari = itemController.findItemByNama(nama);
        if(cari==null){return;}
        ArrayList<String> pencarian = new ArrayList<>();
        for(MenuModel item: cari){
            pencarian.add(item.getJudul());
        }
        lblStatusCari.setText("Ditemukan "+itemController.countItems(cari));
        this.datas=cari;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pencarian);
        listCari.setAdapter(dataAdapter);
    }
}
