package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ASUS on 22/04/2018.
 */

public class ProvinceController {

    Context context;

    public ProvinceController(Context context){
        this.context = context;
    }

    public ArrayList<ProvinceModel> selectAll(){
        ArrayList<ProvinceModel> list = new ArrayList<>();
        JSONObject json = createJSONFromFile(R.raw.provinsi);
        try {
            JSONObject provinsi = json.getJSONObject("provinsi");
            Iterator keys = provinsi.keys();
            while (keys.hasNext()){
                String currentDynamicKey = (String)keys.next();
                //Log.d("PROVINCE::KEY","k: "+currentDynamicKey);
                JSONObject currentDynamicValue = provinsi.getJSONObject(currentDynamicKey);
                String nama=currentDynamicValue.getString("nama");
                String d=currentDynamicValue.getString("d");
                list.add(new ProvinceModel(Integer.parseInt(currentDynamicKey), nama, d));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("PROVINCE::COUNT","num: "+list.size());
        return list;
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
