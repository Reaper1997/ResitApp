package com.example.steven.resitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LoginProfile extends AppCompatActivity {
private ImageButton image;
private EditText title;
private EditText price;
private EditText description;
private Button ViewClothes;
private Button sell;
private Uri ImageUri=null;
private StorageReference storage;
private DatabaseReference database;
private ProgressDialog progressBar;

    private static final int GALLERY_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profile);


        ViewClothes=(Button)findViewById(R.id.btnclothescategory);
        image=(ImageButton)findViewById(R.id.ImageButton);
        title=(EditText)findViewById(R.id.edittextTitle);
        price=(EditText)findViewById(R.id.edittextPrice);
        description=(EditText)findViewById(R.id.edittextDescription);
        sell=(Button)findViewById(R.id.btnsellitem);
        progressBar=new ProgressDialog(this);
        storage= FirebaseStorage.getInstance().getReference();
        database= FirebaseDatabase.getInstance().getReference().child("Shopping_Cart");


        ViewClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clothescategory=new Intent(LoginProfile.this,ClothesCategory.class);
                startActivity(clothescategory);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryview=new Intent(Intent.ACTION_GET_CONTENT);
                galleryview.setType("image/*");
                startActivityForResult(galleryview,GALLERY_REQUEST);
            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {

             sellItem();
    }
});
    }



    private void sellItem(){
        progressBar.setMessage("Uploading Item...");

        final String text_title=title.getText().toString().trim();
        final String text_price=price.getText().toString().trim();
        final String text_description=description.getText().toString().trim();

        if(!TextUtils.isEmpty(text_title)&& !TextUtils.isEmpty(text_price) && !TextUtils.isEmpty(text_description) && ImageUri!=null){
            progressBar.show();
            StorageReference path=storage.child("Shopping_Cart_Images").child(ImageUri.getLastPathSegment());

            path.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadurl=taskSnapshot.getDownloadUrl();
                    DatabaseReference post=database.push();

                    post.child("Title").setValue(text_title);
                    post.child("Price").setValue(text_price);
                    post.child("Description").setValue(text_description);
                    post.child("Image").setValue(downloadurl.toString());
                    post.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                    progressBar.dismiss();
                    Toast.makeText(LoginProfile.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){

             ImageUri=data.getData();

            image.setImageURI(ImageUri);
        }

    }

}
