package com.tourism.hesham.rentapp;





import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class flats extends AppCompatActivity {
    private Uri uri1;
//        private Button retriveImages;
//        private EditText user;

        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView imageView3;
private DatabaseReference firebaseDatabase;
    private AlertDialog.Builder builder;

//private DatabaseReference firebaseDatabase;
private FirebaseDatabase database;
        private EditText chompersNo;
        private EditText hint;
        private EditText area;
        private View view;
    private Button locateFlat ;
     private StorageReference storageReference;
        private static final int GALARY_INTENT=2;
        private int i=0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.flats);
            view = LayoutInflater.from(flats.this).inflate(R.layout.activity_locate_on_map,null,false);

            builder = new AlertDialog.Builder(flats.this);
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (view != null) {

            ViewGroup parentViewGroup = (ViewGroup) view.getParent();

            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
        dialog.dismiss();
                }
            });
            builder.setView(view);
            locateFlat=(Button) findViewById(R.id.locateFlat);
            locateFlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
        database = FirebaseDatabase.getInstance();
                    DatabaseReference countries = database.getReference("countries");
                    DatabaseReference countryName = database.getReference("egypt");
                    DatabaseReference city = database.getReference("alex");
countryName.child("alex").child("flats");


//                    myRef.setValue("Hello, World!");
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
            });

            /////////////
//            LayoutInflater inflater = (this).getLayoutInflater();
//            View dialogLayout = inflater.inflate(R.layout.flats,
//                    null);

//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//            builder.setView(dialogLayout);
//
//            android.app.AlertDialog dialog = builder.create();
//            dialog.show();
            ////////////////

            ////////////////
            storageReference= FirebaseStorage.getInstance().getReference();

//            retriveImages.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onRetriveData(user.getText().toString(), imageView1, 1);
//                    onRetriveData(user.getText().toString(), imageView2, 2);
//                    onRetriveData(user.getText().toString(), imageView3, 3);
//                }
//            });
            imageView1 = (ImageView) findViewById(R.id.img1);
            imageView2 = (ImageView) findViewById(R.id.img2);
            imageView3 = (ImageView) findViewById(R.id.img3);

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


        }
        //retrive users flat data
//        private void onRetriveData(String userName, final ImageView img,int x){
//            StorageReference pathReference = storageReference.child("flats").child(userName+x);
//
//
//            pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Glide
//                            .with(getApplicationContext())
//                            .load(uri)
//                            .centerCrop()
//                            .crossFade()
//                            .into(img);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();            }
//            });
//            submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {


//                    dialog.getWindow().setSoftInputMode(
//                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                    dialog.setView(dialogLayout, 0, 0, 0, 0);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.setCancelable(true);
//                    WindowManager.LayoutParams wlmp = dialog.getWindow()
//                            .getAttributes();
//                    wlmp.gravity = Gravity.BOTTOM;


//        Button btnCamera = (Button) dialogLayout.findViewById(R.id.button_Camera);
//        Button btnGallery = (Button) dialogLayout.findViewById(R.id.button_Gallery);
//        Button btnDismiss = (Button) dialogLayout.findViewById(R.id.btnCancelCamera);



//                }
//            });
//submit.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        firebaseDatabase=FirebaseDatabase.getInstance().getReference();
//firebaseDatabase.child("area").child(area.getText().toString());
//firebaseDatabase.child("chompersNo").child(chompersNo.getText().toString());
//firebaseDatabase.child("hint discribtion").child(hint.getText().toString());
//    }
//});
       // }
///////////////////////////////////////////////sending images to server
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode== GALARY_INTENT && resultCode==RESULT_OK ){
                //after selecting flat image send it to storage database
                final Uri uri =data.getData();
                StorageReference filepath =storageReference.child("flats").child(String.valueOf(i));
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
                        //to show images on image view we set after selecting image to upload
                        switch (i){
                            case 1 :
                                imageView1.setImageURI(uri);

                                break;
                            case 2:
                                imageView2.setImageURI(uri);
                                break;
                            case 3:
                                imageView3.setImageURI(uri);
                                break;
                        }


                    }
                });

            }
        }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }

    }
