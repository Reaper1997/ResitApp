package com.example.steven.resitapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ClothesCategory extends AppCompatActivity {
private RecyclerView ShoppingcartListView;
private DatabaseReference database;
private FirebaseRecyclerAdapter<Cart,CartHolder>mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_category);
      database= FirebaseDatabase.getInstance().getReference().child("Shopping_Cart");
     ShoppingcartListView=(RecyclerView)findViewById(R.id.shoppingcartlist);
     ShoppingcartListView.setHasFixedSize(true);
     ShoppingcartListView.setLayoutManager(new LinearLayoutManager(ClothesCategory.this));





    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirebase=new FirebaseRecyclerAdapter<Cart, CartHolder>(
                Cart.class,
                R.layout.cart_row,
                CartHolder.class,
                database
        ) {
            @Override
            protected void populateViewHolder(CartHolder viewHolder, Cart model, final int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setUid(model.getUid());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(ClothesCategory.this, "You liked this post", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(ClothesCategory.this);
                        builder.setMessage("Do you want to add this item to your shopping cart").setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog,int which){
                                       int selectedItems=position;
                                        mFirebase.getRef(selectedItems).removeValue();
                                        mFirebase.notifyItemRemoved(selectedItems);
                                        ShoppingcartListView.invalidate();
                                        onStart();
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener(){
                                 @Override
                                    public void onClick(DialogInterface dialog,int which){
                                     dialog.cancel();
                                 }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                    }
                });
            }
        };
ShoppingcartListView.setAdapter(mFirebase);
    }

    public static class CartHolder extends RecyclerView.ViewHolder{

        View mView;

        public CartHolder(View itemView) {
            super(itemView);

            mView=itemView;
        }
        public void setTitle(String Title){
            TextView postTitle=(TextView)mView.findViewById(R.id.postTitle);
            postTitle.setText(Title);
        }
        public void setPrice(String Price){
            TextView postPrice=(TextView)mView.findViewById(R.id.postPrice);
            postPrice.setText(Price);
        }
        public void setDescription(String Description){
            TextView postDescription=(TextView)mView.findViewById(R.id.postDescription);
            postDescription.setText(Description);
        }
        public void setImage(Context cntxt, String Image){
            ImageView postImage=(ImageView)mView.findViewById(R.id.postImage);
            Picasso.with(cntxt).load(Image).into(postImage);
        }

        public void setUid(String Uid){
            TextView postUid=(TextView)mView.findViewById(R.id.postUid);
            postUid.setText(Uid);
        }

    }


}
