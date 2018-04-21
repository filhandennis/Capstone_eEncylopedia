package com.develop.filhan.eencyclopediaone;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private ProgressBar pbSignup;
    private Button btnSignup;
    private EditText txtNama, txtTTL, txtProvinsi, txtDN, txtEmail, txtPassword;
    private AutoCompleteTextView txtAutoCompleteProvinsi;
    private Calendar myCalendar = Calendar.getInstance();
    private ProvinceController cProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Form Sign Up");

        pbSignup=(ProgressBar)findViewById(R.id.pbSignup);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        txtNama=(EditText)findViewById(R.id.txtSignupNama);
        txtTTL=(EditText)findViewById(R.id.txtSignupTglLahir);
        txtProvinsi=(EditText)findViewById(R.id.txtSignupProvinsi);
        txtAutoCompleteProvinsi = (AutoCompleteTextView) findViewById(R.id.actSignupProvinsi);
        txtDN=(EditText)findViewById(R.id.txtSignupUsername);
        txtEmail=(EditText)findViewById(R.id.txtSignupEmail);
        txtPassword=(EditText)findViewById(R.id.txtSignupPassword);

        cProvince= new ProvinceController(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });

        pickADate();
        pickAProvince();

    }

    private void userRegister(){
        pbSignup.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Register Act", Toast.LENGTH_SHORT).show();
    }

    // DatePicker
    private void pickADate(){
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtTTL.setText(sdf.format(myCalendar.getTime()));
            }

        };
        txtTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void pickAProvince(){
        ArrayList<ProvinceModel> lProvince = cProvince.selectAll();
        ArrayList<String> lProvinceName = new ArrayList<>();
        for(ProvinceModel sProvinceName: lProvince){
            lProvinceName.add(sProvinceName.getNama());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, lProvinceName);
        txtAutoCompleteProvinsi.setThreshold(1);//will start working from first character
        txtAutoCompleteProvinsi.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        txtAutoCompleteProvinsi.getDropDownAnchor();
    }
}
