package com.develop.filhan.eencyclopediaone;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Class Fragment untuk Halaman Login, Register dan User Information.
 */
public class AuthFragment extends Fragment {

    //Komponen View
    private LinearLayout blockLogin, blockNotLogin;
    private ProgressBar pbSignin;
    private Button btnLogin;
    private EditText txtEmail, txtPassword;
    private TextView lblNama, lblTTL, lblProvinsi, lblDN, lblEmail, lblRole, btnRegister;
    private ImageView imgDP;

    //Firebase Object
    private FirebaseAuth auth;
    private FirebaseDatabase fdb;
    private DatabaseReference tbUser;

    //Shared Preferences
    private SharedPreferences prefUser, prefSeller;
    private SharedPreferences.Editor prefEdit, prefEditSeller;

    private boolean userIsLogin;

    //Constructor
    public AuthFragment() {
        //Initiate Firebase
        auth=FirebaseAuth.getInstance();
        fdb=FirebaseDatabase.getInstance();
        tbUser=fdb.getReference("Users");
        //Credential
        userIsLogin=checkUserLogin();
    }

    //Untuk menambahkan/membuat menu pada Action Bar yang diambil dari menu.xml
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(userIsLogin!=true){menu.clear(); return;}
        menu.clear();
        inflater.inflate(R.menu.menu_auth, menu);
    }

    //Aksi jika item menu dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Mengambil Item ID dari item yang dipilih di Menu
        switch (item.getItemId()){
            //Aksi untuk Logout
            case R.id.menu_auth_logout:
                userActLogout();
                break;
            //Aksi untuk Edit Profile(Kosong)
            case R.id.menu_auth_edit: break;
            //Aksi untuk Menjadi Seller
            case R.id.menu_auth_becomeseler:
                //Pengecekan Role Watcher dengan menggunakan SharedPreference, untuk mencegah Role selain watcher
                //mendaftarkan kembali
                if(!(prefUser.getString("ROLE","Watcher").equalsIgnoreCase("Watcher"))){
                    Toast.makeText(getActivity(), "You're Already be A Seller", Toast.LENGTH_SHORT).show();
                    return true;
                }
                startActivity(new Intent(getActivity(), SellerRegistrationActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method yang dijalankan jika Layout View telah dibuat/pack
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setHasOptionsMenu(true); //Atur untuk memberitahukan bahwafraggment ini mempunyai menu
        ((HomeActivity) getActivity()).setActionBarTitle("Profile"); //Setting nama action bar

        //User Section
        prefUser=getActivity().getSharedPreferences("User",0);
        prefEdit= prefUser.edit();
        //Seller Section
        prefSeller=getActivity().getSharedPreferences("Seller",0);
        prefEditSeller= prefSeller.edit();

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
        lblRole=(TextView)v.findViewById(R.id.lblAuthRole);

        //Button Action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userActLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });

        //Check Login atau Tidak
        if(checkUserLogin()==true){
            userLogin();
        }else{
            userLogout();
        }
    }

    //Membuat Layout di Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    //Melakukan Cek User Logindengan FirebaseAuth
    private boolean checkUserLogin(){
        return auth.getCurrentUser()!=null;
    }

    //Aksi untuk User yang login
    //Aksi ini digunakan untuk melakukan cek data User yang akan login
    private void userActLogin(){
        //Get Value
        String email=txtEmail.getText().toString();
        String pass=txtPassword.getText().toString();

        //Login Validation
        if(email.trim().length()<1 || pass.trim().length()<1){return;}

        //Display Loading
        pbSignin.setVisibility(View.VISIBLE);
        //FirebaseAuth by Email&Password
        auth.signInWithEmailAndPassword(email,pass)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Hidden Loading
                pbSignin.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Welcome !", Toast.LENGTH_SHORT).show();
                    userIsLogin=true;
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(),HomeActivity.class));
                }else{
                    Toast.makeText(getActivity(), "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Method ini digunakan untuk mengambil informasi akan user yang sedang login
    private void userLogin(){
        FirebaseUser curruser = auth.getCurrentUser();
        final String UserId = curruser.getUid();

        //Firebase Reference berdasarkan ID User Login
        (tbUser.child(UserId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Make FirebaseDatabase / DatabaseReference Object to Model Class
                UserModel iUser = dataSnapshot.getValue(UserModel.class);
                //Get Information from Model
                lblNama.setText(iUser.getFullname());
                lblTTL.setText(iUser.getTtl());
                lblProvinsi.setText(iUser.getProvince());
                lblEmail.setText(iUser.getEmail());
                lblDN.setText(iUser.getDisplayname());
                lblRole.setText(iUser.getRole());
                //Put and Save SharedPreference about User Information
                prefEdit.putString("UUID",""+UserId);
                prefEdit.putString("EMAIL",""+iUser.getEmail());
                prefEdit.putString("ROLE",""+iUser.getRole());
                prefEdit.putString("PROVINCE",""+iUser.getProvince());
                prefEdit.commit();

                //If Role Seller
                //Check if user have a role seller
                //Jika role user seller maka akan Dilakukan pengambilan informasi tentang sellernya
                if(iUser.getRole().equalsIgnoreCase("Seller")){
                    (FirebaseDatabase.getInstance().getReference("Sellers").child(UserId))
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    SellerModel seller = dataSnapshot.getValue(SellerModel.class);
                                    prefEditSeller.putString("Address", seller.getAddress());
                                    prefEditSeller.putString("LatLon", seller.getLatlon());
                                    prefEditSeller.putString("Status", seller.getStatus());
                                    prefEditSeller.commit();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Display Block User Information
        blockLogin.setVisibility(View.VISIBLE);
        //Hidden Form Login
        blockNotLogin.setVisibility(View.GONE);
    }

    //Aksi yang dilakukan jika User Hendak Logout / Mengklik tombol Logout
    private void userActLogout(){
        //AlertDialog
        AlertDialog.Builder aConfirm = new AlertDialog.Builder(getActivity());
        aConfirm.setTitle("Confirm Sign-out");
        aConfirm.setMessage("Sign-out?");
        //Jika tombol "Continue" ditekan
        aConfirm.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userLogout();
                Toast.makeText(getActivity(), "See you!", Toast.LENGTH_SHORT).show();
            }
        });
        //Jika tombol "Cancel" ditekan
        aConfirm.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        aConfirm.show();
    }
    //Method yang digunakan untuk melakukan Logout pada Aplikasi, hal ini termasuk penghapusan
    //Informasi Logout FirebaseAuth dan SharedPreferences
    private void userLogout(){
        userIsLogin=false;
        // TODO NEXT LOGOUT
        //Firebase Logout
        auth.signOut();
        //Clear SharedPreferences
        prefEdit.clear();
        prefEdit.commit();
        // Hidden-Show Field
        blockLogin.setVisibility(View.GONE);
        blockNotLogin.setVisibility(View.VISIBLE);
    }

    //Pendaftar User Baru
    private void userRegister(){
        getActivity().startActivity(new Intent(getActivity(), SignupActivity.class));
    }

}
