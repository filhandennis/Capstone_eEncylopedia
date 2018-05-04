package com.develop.filhan.eencyclopediaone;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 04/05/2018.
 */

public class MenuFirebaseHelper {
    private FirebaseDatabase fdb;
    private DatabaseReference tbMenu;
    private FirebaseAuth auth;

    private Context context;

    private List<MenuFirebaseModel> menus;

    public MenuFirebaseHelper(Context context) {
        this.fdb = FirebaseDatabase.getInstance();
        this.tbMenu = fdb.getReference("Menus");
        this.auth = FirebaseAuth.getInstance();
        this.menus = new ArrayList<>();
        this.context = context;
    }

    public MenuFirebaseHelper(FirebaseDatabase fdb, DatabaseReference tbMenu, FirebaseAuth auth) {
        this.fdb = FirebaseDatabase.getInstance();
        this.tbMenu = fdb.getReference("Menus");
        this.auth = FirebaseAuth.getInstance();
    }

    private String getTimestamp(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()).toString();
    }

    public boolean checkUserLogin(){
        return FirebaseAuth.getInstance().getCurrentUser()!=null ? true:false;
    }

    public void addView(String menuId, String userUUID){
        String ts = getTimestamp();
        //Check Menu
        //Add new View
        DatabaseReference tbView = tbMenu.child(menuId).child("Views");
        String key = tbView.push().getKey();
        tbView.child(key).setValue(new ViewModel(Integer.parseInt(menuId), userUUID, ts));
        Log.d("MENU::VIEW","REF -- "+key);
    }

    public void addComment(String menuId, String userUUID, String text){
        // Get Date and Time (Timestamp)
        String ts = getTimestamp();
        //Firebase Ref.
        DatabaseReference tbComment = tbMenu.child(menuId).child("Comments");
        String key = tbComment.push().getKey();
        tbComment.child(key).setValue(new CommentModel(Integer.parseInt(menuId), userUUID, ts, text));
        Log.d("MENU::COMMENT","REF -- "+key);
    }

    public void addLike(String menuId, String userUUID){
        // Get Date and Time (Timestamp)
        String ts = getTimestamp();
        //Firebase Ref.
        DatabaseReference tbLike = tbMenu.child(menuId).child("Likes");
        tbLike.child(userUUID).setValue(new FavModel(Integer.parseInt(menuId),userUUID,ts));
        Log.d("MENU::LIKE","REF -- "+userUUID);
    }
    public void removeLike(String menuId, String userUUID){
        DatabaseReference tbLike = tbMenu.child(menuId).child("Likes");
        tbLike.child(userUUID).removeValue();
        Log.d("MENU:DELETE:LIKE","REF -- "+userUUID);
    }

    public void addSeller(String menuId, String userUUID){

    }

    public List<MenuFirebaseModel> getAllMenus(){
        //final ProgressDialog dload = new ProgressDialog(context);
        //dload.show();
        //if(FirebaseDatabase.getInstance().getReference().child("Menus").){return null;}
        tbMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //dload.dismiss();
                List<MenuFirebaseModel> menuss = new ArrayList<>();
                for(DataSnapshot menu: dataSnapshot.getChildren()) {
                    //Log.d("MENU:ITEM:DATA",""+menu.toString());
                    String id = menu.getKey();
                    //Populate Views
                    List<ViewModel> views = new ArrayList<>();
                    if(menu.hasChild("Views")){views = getViews(id);}
                    //Populate Comments
                    List<CommentModel> comments = new ArrayList<>();
                    if(menu.hasChild("Comments")){comments = getComments(id);}
                    //Populate Favs
                    List<FavModel> favs = new ArrayList<>();
                    if(menu.hasChild("Likes")){favs = getLikes(id);}
                    //Create Item
                    MenuFirebaseModel imenu = new MenuFirebaseModel(Integer.parseInt(id),"",views,comments,favs);
                    //Add to List
                    menuss.add(imenu);
                }
                Log.d("MENU:COUNT:MENUS1","Count :"+menuss.size());
                menus=menuss;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MENU:ERROR:MENUS","Error :"+databaseError.getMessage().toString());
            }
        });
        Log.d("MENU:COUNT:MENUS","Count :"+menus.size());
        return menus;
    }

    public int countMenu(){
        return getAllMenus().size();
    }

    public List<ViewModel> getViews(String menuId){
        final List<ViewModel> views = new ArrayList<>();
        tbMenu.child(menuId).child("Views").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ps : dataSnapshot.getChildren()){
                    ViewModel view = ps.getValue(ViewModel.class);
                    views.add(view);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MENU:ERROR:VIEWS","Error :"+databaseError.getMessage().toString());
            }
        });
        return views;
    }
    public List<FavModel> getLikes(String menuId){
        final List<FavModel> favs = new ArrayList<>();
        tbMenu.child(menuId).child("Likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ps : dataSnapshot.getChildren()){
                    FavModel fav = ps.getValue(FavModel.class);
                    favs.add(fav);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MENU:ERROR:LIKES","Error :"+databaseError.getMessage().toString());
            }
        });
        return favs;
    }

    public List<CommentModel> getComments(String menuId){
        final List<CommentModel> comments = new ArrayList<>();
        tbMenu.child(menuId).child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ps : dataSnapshot.getChildren()){
                    CommentModel comment = ps.getValue(CommentModel.class);
                    comments.add(comment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MENU:ERROR:COMMENTS","Error :"+databaseError.getMessage().toString());
            }
        });
        return comments;
    }

    private boolean checkExist(DatabaseReference ref){
        return true;
    }
}
