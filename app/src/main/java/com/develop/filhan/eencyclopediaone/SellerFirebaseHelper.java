package com.develop.filhan.eencyclopediaone;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 05/05/2018.
 */

public class SellerFirebaseHelper {
    private FirebaseDatabase fdb;
    private DatabaseReference tbSeler;

    //GetOne Seller
    private SellerModel seller;

    public SellerFirebaseHelper() {
        this.fdb=FirebaseDatabase.getInstance();
        this.tbSeler=fdb.getReference("Sellers");

        seller=new SellerModel();
    }

    public void addNewSeller(String userId, String nama, String address, String latlon){
        String statusNew = "REQUEST";
        //REQUEST->APPROVED->VALID
        DatabaseReference refKey = tbSeler.child(userId);
        SellerModel seller = new SellerModel(userId,nama,address,statusNew);
        seller.setLatlon(latlon);
        refKey.setValue(seller);

        //Future Delete (Admin Feature)
        approveSeller(userId);

        Log.d("SELLER::ADD","Key -- "+userId);
    }

    public void deleteSeller(String userId){
        tbSeler.child(userId).removeValue();
        fdb.getReference("Users").child(userId).child("role").setValue("Watcher");
        Log.d("SELLER::DELETE","Key -- "+userId);
    }

    public SellerModel getSeller(String userId){
        (tbSeler.child(userId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SellerModel sellerx=dataSnapshot.getValue(SellerModel.class);

                seller=sellerx;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("SELLER:ERROR:GET","Error -- "+databaseError.getMessage().toString());
            }
        });
        return seller;
    }

    public void approveSeller(String sellerId){
        tbSeler.child(sellerId).child("status").setValue("APPROVED");
        fdb.getReference("Users").child(sellerId).child("role").setValue("Seller");
        Log.d("SELLER::APPROVE","Key -- "+sellerId);
    }

    public void validSeller(String sellerId){
        tbSeler.child(sellerId).child("status").setValue("VALID");
        Log.d("SELLER::VALIDATE","Key -- "+sellerId);
    }

//    public void addNewItemSeller(String userId, String namaItem, String address, String latlon, int minPrice, int maxPrice){}
}
