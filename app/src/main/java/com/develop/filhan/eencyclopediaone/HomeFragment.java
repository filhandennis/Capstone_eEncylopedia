package com.develop.filhan.eencyclopediaone;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FavoriteHelper favHelper;
    private ImageView iconFav;
    private TextView lblUserName, lblUserProvince, lblUserFavNum;
    private RecyclerView rvRecommend, rvHits, rvFavorable;
    private LinearLayout blockNotLogin;
    private ScrollView blockLogin;

    private FirebaseAuth auth;

    public HomeFragment() {
        // Required empty public constructor
        auth=FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((HomeActivity) getActivity()).setActionBarTitle("Dashboard");

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

        AdapterRecyclerviewItemTwo dataAdapterR = new AdapterRecyclerviewItemTwo(new ItemController(getActivity()).randomData(4));
        AdapterRecyclerviewItemTwo dataAdapterH = new AdapterRecyclerviewItemTwo(new ItemController(getActivity()).randomData(3));
        AdapterRecyclerviewItemTwo dataAdapterF = new AdapterRecyclerviewItemTwo(new ItemController(getActivity()).randomData(3));
        rvRecommend.setAdapter(dataAdapterR);
        rvHits.setAdapter(dataAdapterH);
        rvFavorable.setAdapter(dataAdapterF);

        favHelper = new FavoriteHelper(getActivity());
        lblUserFavNum.setText(""+favHelper.size());
        if(favHelper.size()<1){lblUserFavNum.setText("Huh.."); iconFav.setImageResource(R.drawable.loving);}

        if(auth.getCurrentUser()!=null){blockLogin.setVisibility(View.VISIBLE); blockNotLogin.setVisibility(View.GONE);}
        else{blockLogin.setVisibility(View.GONE); blockNotLogin.setVisibility(View.VISIBLE);}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
