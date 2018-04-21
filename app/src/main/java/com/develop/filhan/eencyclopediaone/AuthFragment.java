package com.develop.filhan.eencyclopediaone;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    private LinearLayout blockLogin, blockNotLogin;
    private ProgressBar pbSignin;
    private Button btnLogin;
    private EditText txtEmail, txtPassword;
    private TextView lblNama, lblTTL, lblProvinsi, lblDN, lblEmail, btnRegister;
    private ImageView imgDP;

    private boolean userIsLogin;

    public AuthFragment() {
        // Required empty public constructor
        userIsLogin=false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(userIsLogin!=true){menu.clear(); return;}
        menu.clear();
        inflater.inflate(R.menu.menu_auth, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_auth_logout:
                userLogout();
                break;
            case R.id.menu_auth_edit: break;
            case R.id.menu_auth_becomeseler: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).setActionBarTitle("Profile");

        // Inisialisasi Komponen View
        blockNotLogin=(LinearLayout)v.findViewById(R.id.blockSigninNotLogin);
        blockLogin=(LinearLayout)v.findViewById(R.id.blockSigninLogin);
        // USER NOT LOGIN
        txtEmail=(EditText)v.findViewById(R.id.txtSigninEmail);
        txtPassword=(EditText)v.findViewById(R.id.txtSigninPassword);
        btnLogin=(Button)v.findViewById(R.id.btnSignin);
        btnRegister=(TextView)v.findViewById(R.id.btnSigninRegister);
        pbSignin=(ProgressBar)v.findViewById(R.id.pbSignin);

        // USER LOGIN
        lblNama=(TextView)v.findViewById(R.id.lblAuthNama);
        lblTTL=(TextView)v.findViewById(R.id.lblAuthTTL);
        lblProvinsi=(TextView)v.findViewById(R.id.lblAuthProvinsi);
        lblDN=(TextView)v.findViewById(R.id.lblAuthDN);
        lblEmail=(TextView)v.findViewById(R.id.lblAuthEmail);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    private void userLogin(){
        Toast.makeText(getActivity(), "User Login Action", Toast.LENGTH_SHORT).show();
        pbSignin.setVisibility(View.VISIBLE);
        userIsLogin=true;
        blockLogin.setVisibility(View.VISIBLE);
        blockNotLogin.setVisibility(View.GONE);
    }

    private void userLogout(){
        userIsLogin=false;
        // TODO NEXT LOGOUT
        // Hidden-Show Field
        blockLogin.setVisibility(View.GONE);
        blockNotLogin.setVisibility(View.VISIBLE);
    }

    private void userRegister(){
        getActivity().startActivity(new Intent(getActivity(), SignupActivity.class));
    }

}
