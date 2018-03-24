package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ASUS on 24/03/2018.
 */

public class ItemController {
    Gson gson;
    Context context;

    public ItemController(Context context){
        gson=new Gson();
        this.context = context;
    }

    public ArrayList<MenuModel> selectAll(){
        ArrayList<MenuModel> list = new ArrayList<>();

        InputStream inputStream = context.getResources().openRawResource(R.raw.pariwisata);
        JSONObject json = createJSONFromFile(R.raw.pariwisata);
        try {
            JSONArray provinsi = json.getJSONArray("provinsi");
            for(int a=0;a<provinsi.length();a++){
                JSONObject p = provinsi.getJSONObject(a);
                String daerah = p.getString("nama");
                JSONArray makanan = p.getJSONArray("makanan_khas");
                for(int b=0;b<makanan.length();b++){
                    JSONObject m = makanan.getJSONObject(b);
                    String nama = m.getString("nama");
                    String deskripsi = m.getString("deskripsi");

                    //Log.d("ItemController->Item","a:"+a+",b:"+b+",Nama:"+nama);

                    list.add(new MenuModel(nama, daerah, deskripsi, ""));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public MenuModel select(int pos){
        return this.selectAll().get(pos);
    }

    public ArrayList<MenuModel> getItemByDaerah(String nama){
        ArrayList<MenuModel> list = new ArrayList<>();
        for(MenuModel item: selectAll()){
            if(item.getDaerah().equals(nama)){
                list.add(item);
            }
        }
        return list;
    }

    public ArrayList<MenuModel> findItemByNama(String nama){
        ArrayList<MenuModel> list = new ArrayList<>();
        if(nama.length()<3){return null;}
        //Log.d("FINDING","x:"+nama);
        for(MenuModel item: selectAll()){
            //Log.d("SCANNING "+item.getJudul(),""+item.getJudul().contains(nama));
            if(item.getJudul().toLowerCase().contains(nama.toLowerCase())){
                list.add(item);
                //Log.d("FINDED","x:"+item.getJudul());
            }
        }
        return list;
    }

    public int countItems(ArrayList<MenuModel> list){
        return list.size();
    }

    private JSONObject createJSONFromFile(int fileID) {

        JSONObject result = null;

        try {
            // Read file into string builder
            InputStream inputStream = context.getResources().openRawResource(fileID);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            for (String line = null; (line = reader.readLine()) != null ; ) {
                builder.append(line).append("\n");
            }

            // Parse into JSONObject
            String resultStr = builder.toString();
            JSONTokener tokener = new JSONTokener(resultStr);
            result = new JSONObject(tokener);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
