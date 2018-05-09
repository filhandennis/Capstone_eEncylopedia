package com.develop.filhan.eencyclopediaone;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //Class Helper
    private FavoriteHelper favHelper;
    //View Component
    private ImageView iconFav;
    private TextView lblUserName, lblUserProvince, lblUserFavNum;
    private RecyclerView rvRecommend, rvHits, rvFavorable;
    private LinearLayout blockNotLogin;
    private ScrollView blockLogin;
    private ProgressBar pbR, pbH, pbF;
    //Firebase Auth
    private FirebaseAuth auth;
    private UserModel userAuth;

    List<MenuFirebaseModel> menus;
    //Class Helper
    private MenuFirebaseHelper menuHelper;
    private ItemController cItemMenu;

    //Constructor Fragment
    public HomeFragment() {
        // Required empty public constructor
        auth=FirebaseAuth.getInstance();
        userAuth=new UserModel();
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((HomeActivity) getActivity()).setActionBarTitle("Dashboard");

        cItemMenu=new ItemController(getActivity());

        iconFav=(ImageView)v.findViewById(R.id.iconHomeUserFav);
        lblUserName=(TextView)v.findViewById(R.id.lblHomeUserName);
        lblUserProvince=(TextView)v.findViewById(R.id.lblHomeUserProvince);
        lblUserFavNum=(TextView)v.findViewById(R.id.lblHomeUserFavorite);
        rvRecommend=(RecyclerView)v.findViewById(R.id.lblHomeRecyclerR);
        rvHits=(RecyclerView)v.findViewById(R.id.lblHomeRecyclerH);
        rvFavorable=(RecyclerView)v.findViewById(R.id.lblHomeRecyclerF);

        blockNotLogin=(LinearLayout)v.findViewById(R.id.blockWithNoLogin);
        blockLogin=(ScrollView)v.findViewById(R.id.blockWithLogin);

        rvRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorable.setLayoutManager(new LinearLayoutManager(getActivity()));

        pbR=(ProgressBar)v.findViewById(R.id.pbHomeR);
        pbH=(ProgressBar)v.findViewById(R.id.pbHomeH);
        pbF=(ProgressBar)v.findViewById(R.id.pbHomeF);

        menuHelper=new MenuFirebaseHelper(getActivity());

        favHelper = new FavoriteHelper(getActivity());
        lblUserFavNum.setText(""+favHelper.size());
        if(favHelper.size()<1){lblUserFavNum.setText("Huh.."); iconFav.setImageResource(R.drawable.loving);}

        if(auth.getCurrentUser()!=null){
            blockLogin.setVisibility(View.VISIBLE); blockNotLogin.setVisibility(View.GONE);

            //Get User
            fillUser();

            //Get All Data
            menus = menuHelper.getAllMenus();

            //Wait Until 8 Seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Recomended
                    AdapterRecyclerviewItemTwo dataAdapterR = new AdapterRecyclerviewItemTwo(fillRecommended());
                    rvRecommend.setAdapter(dataAdapterR);
                    //Views
                    AdapterRecyclerviewItemTwo dataAdapterH = new AdapterRecyclerviewItemTwo(fillView());
                    rvHits.setAdapter(dataAdapterH);
                    //Favorable
                    AdapterRecyclerviewItemTwo dataAdapterF = new AdapterRecyclerviewItemTwo(fillFav());
                    rvFavorable.setAdapter(dataAdapterF);
                }
            }, 8000);

        }
        else{blockLogin.setVisibility(View.GONE); blockNotLogin.setVisibility(View.VISIBLE);}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cItemMenu=new ItemController(getActivity());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void fillUser(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){return;}
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference tbUser = FirebaseDatabase.getInstance().getReference("Users");
        (tbUser.child(user.getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel mUser = dataSnapshot.getValue(UserModel.class);
                userAuth=mUser;
                String name = mUser.getFullname();
                String province = mUser.getProvince();
                lblUserName.setText(name);
                lblUserProvince.setText(province);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadData(){

    }

    private ArrayList<MenuModel> fillRecommended(){
        ArrayList<MenuModel> list = new ArrayList<>();
        int min=0,max=210;
        for(MenuModel menu: cItemMenu.selectAll()){
            if(menu.getDaerah().equalsIgnoreCase(userAuth.getProvince())){
                int randomNumber =min + (int)(Math.random() * (max - min));
                if(randomNumber<=150) {
                    list.add(menu);
                }
                if(list.size()==3){break;}
            }
        }

        pbR.setVisibility(View.GONE);
        return list;
    }

    private ArrayList<MenuModel> fillView(){
        int limit = 3;
        menus = menuHelper.getAllMenus();
        if(menus.size()==0 || menus==null){return null;}
        Log.d("MENU:INIT:COUNT","Count :"+menus.size());
        //Sort DESC, 10->1
        Collections.sort(menus, new Comparator<MenuFirebaseModel>() {
            @Override
            public int compare(MenuFirebaseModel a, MenuFirebaseModel b) {
                return b.getViewsNum() - a.getViewsNum();
            }
        });
        ArrayList<MenuModel> list = new ArrayList<>();
        for(MenuFirebaseModel item : menus){
            MenuModel menu=cItemMenu.findItemById(item.getMenuId());
            menu.setViewsCount(item.getViewsNum());
            menu.setCommentsCount(item.getCommentsNum());
            menu.setFavoritesCount(item.getFavoritesNum());
            list.add(menu);
            if(list.size()==limit){break;}
        }
        Log.d("MENU:FILL:VIEW", "Count: "+list.size());
        pbH.setVisibility(View.GONE);
        return list;
    }

    private ArrayList<MenuModel> fillFav(){
        int limit = 3;
        menus = menuHelper.getAllMenus();
        if(menus.size()==0 || menus==null){return null;}
        Log.d("MENU:INIT:COUNT","Count :"+menus.size());
        //Sort DESC, 10->1
        Collections.sort(menus, new Comparator<MenuFirebaseModel>() {
            @Override
            public int compare(MenuFirebaseModel a, MenuFirebaseModel b) {
                return b.getFavoritesNum() - a.getFavoritesNum();
            }
        });
        ArrayList<MenuModel> list = new ArrayList<>();
        for(MenuFirebaseModel item : menus){
            MenuModel menu=cItemMenu.findItemById(item.getMenuId());
            menu.setViewsCount(item.getViewsNum());
            menu.setCommentsCount(item.getCommentsNum());
            menu.setFavoritesCount(item.getFavoritesNum());
            list.add(menu);
            if(list.size()==limit){break;}
        }
        Log.d("MENU:FILL:FAV", "Count: "+list.size());
        pbF.setVisibility(View.GONE);
        return list;
    }


}
