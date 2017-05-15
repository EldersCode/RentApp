package com.tourism.hesham.rentapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class advertises extends AppCompatActivity {
private de.hdodenhof.circleimageview.CircleImageView flats;
    //////////////
    private Button retriveImages;
    private EditText user;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

//private DatabaseReference firebaseDatabase;

    private EditText chompersNo;
    private EditText hint;
    private EditText area;
    private Button submit;
    private StorageReference storageReference;
    private static final int GALARY_INTENT=2;
    private int i=0;
    private Profile profile ;
    //////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertises);
         profile = Profile.getCurrentProfile();
        profile.getName();
        Toast.makeText(getApplicationContext(), "Welcome "+ profile.getName() + " :)", Toast.LENGTH_LONG).show();

        flats=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.flats);

        flats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = LayoutInflater.from(advertises.this).inflate(R.layout.flats,null);
                imageView1 = (ImageView) view.findViewById(R.id.img1);
                imageView2 = (ImageView) view.findViewById(R.id.img2);
                imageView3 = (ImageView) view.findViewById(R.id.img3);

                storageReference= FirebaseStorage.getInstance().getReference();


                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,GALARY_INTENT);
                        i=1;

                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");

//
                        startActivityForResult(intent,GALARY_INTENT);
                        i=2;

                    }
                });
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,GALARY_INTENT);
                        i=3;
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(advertises.this);
                builder.setView(view);
builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
submitData();
    }
});
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
                ////////////////
            }});

    }
//////////

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode== GALARY_INTENT && resultCode==RESULT_OK ) {
//                //to show images on image view we set after selecting image to upload
        final Uri uri = data.getData();
        final ProgressDialog progressDialog = new ProgressDialog(advertises.this);
        progressDialog.show();
        switch (i) {
            case 1:
                imageView1.setImageURI(uri);

                break;
            case 2:
                imageView2.setImageURI(uri);
                break;
            case 3:
                imageView3.setImageURI(uri);
                break;
        }
            profile = Profile.getCurrentProfile();
            StorageReference filepath = storageReference.child("flats").child("profileid"+profile.getId()+i);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
progressDialog.cancel();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("eeeee", String.valueOf(e));
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

                }
            });

    }
//        //after selecting flat image send it to storage database

}
private void submitData(){






}
    @Override
    public void onBackPressed() {
startActivity(new Intent(getApplicationContext(),MapsActivity.class));

    }
}
