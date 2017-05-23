package com.tourism.hesham.rentapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

public class flats extends AppCompatActivity {
    private Uri uri1;
    //        private Button retriveImages;
//        private EditText user;
    private Profile profile;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    // layout inestances

    private EditText priceEditText;
    private EditText ApartmentAreaEditText;
    private EditText noOfBedRoomsEditText;
    private EditText noOfBathRoomsEditText;
    private Switch parkingLotsSwitch;
    private Switch LivingRoomSwitch;
    private Switch KitchenSwitch;
    private Switch coolingSystemSwitch;
    private Switch NegotiablePriceSwitch;

    //
    private AlertDialog.Builder builder;
    private LinearLayout petsLayout;
    private Switch petsSwitch;
    //private DatabaseReference firebaseDatabase;
    private FirebaseDatabase database;
    private View view;
    //////////// tack images
    private Button locateFlat;
    private StorageReference storageReference;
    private static final int GALARY_INTENT = 2;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flats);
//declearing inistances
        petsLayout = (LinearLayout) findViewById(R.id.switchOn_pets);
        petsSwitch = (Switch) findViewById(R.id.petSwitch);
        priceEditText = (EditText) findViewById(R.id.Price);
        ApartmentAreaEditText = (EditText) findViewById(R.id.area);
        noOfBedRoomsEditText = (EditText) findViewById(R.id.bedrooms);
        noOfBathRoomsEditText = (EditText) findViewById(R.id.bathrooms);

        parkingLotsSwitch=(Switch)findViewById(R.id.ParkingSwitch);
        LivingRoomSwitch=(Switch)findViewById(R.id.livingRoomSwitch);
        KitchenSwitch=(Switch)findViewById(R.id.kitchenSwitch);
        coolingSystemSwitch=(Switch)findViewById(R.id.coolingSystemSwitch);
        NegotiablePriceSwitch=(Switch)findViewById(R.id.negotiablePriceSwitch);


        petsLayout.setVisibility(View.GONE);

        petsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    petsLayout.setVisibility(View.VISIBLE);
                } else {
                    petsLayout.setVisibility(View.GONE);

                }

            }
        });

        profile = Profile.getCurrentProfile();
        profile.getId();
        view = LayoutInflater.from(flats.this).inflate(R.layout.activity_locate_on_map, null, false);

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
        locateFlat = (Button) findViewById(R.id.locateFlat);
        locateFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();

                ///check if there was a previuos flat or not

                startActivity(new Intent(getApplicationContext(), LocateOnMap.class));

                DatabaseReference users = database.getReference("users");
                DatabaseReference houses = database.getReference("houses");
                DatabaseReference regions = database.getReference("regions");

                users.child("userId/" + "houses/" + "owened/" + "houseId/").setValue("");

                houses.child("houseId/" + "bedRoomsNo/").setValue(noOfBedRoomsEditText.getText().toString());
                houses.child("houseId/" + "bathNo/").setValue(noOfBathRoomsEditText.getText().toString());
                houses.child("houseId/" + "price/").setValue(priceEditText.getText().toString());
                houses.child("houseId/" + "parking/").setValue(String.valueOf(parkingLotsSwitch.isChecked()));
                houses.child("houseId/" + "negotiablePrice/").setValue(String.valueOf(NegotiablePriceSwitch.isChecked()));
                houses.child("houseId/" + "livingRoom/").setValue(String.valueOf(LivingRoomSwitch.isChecked()));
                houses.child("houseId/" + "pets/").setValue("boolean");
                houses.child("houseId/" + "kitchen/").setValue(String.valueOf(KitchenSwitch.isChecked()));
                houses.child("houseId/" + "coolingSystem/").setValue(String.valueOf(coolingSystemSwitch.isChecked()));
                houses.child("houseId/" + "area/").setValue(ApartmentAreaEditText.getText().toString());
                houses.child("houseId/" + "houseIdNo/" + "location/").setValue("");

                regions.child("contry/" + "city/" + "houseId").setValue("location");

//                AlertDialog dialog = builder.create();
//
//                dialog.show();
            }
        });
        ////////////////
        storageReference = FirebaseStorage.getInstance().getReference();

//            retriveImages.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onRetriveData(user.getText().toString(), imageView1, 1);
//                    onRetriveData(user.getText().toString(), imageView2, 2);
//                    onRetriveData(user.getText().toString(), imageView3, 3);
//                }
//            });
//        imageView1 = (ImageView) findViewById(R.id.img1);
//        imageView2 = (ImageView) findViewById(R.id.img2);
//        imageView3 = (ImageView) findViewById(R.id.img3);

//        imageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALARY_INTENT);
//                i = 1;
//            }
//        });
//        imageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALARY_INTENT);
//                i = 2;
//            }
//        });
//        imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALARY_INTENT);
//                i = 3;
//            }
//        });


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
        if (requestCode == GALARY_INTENT && resultCode == RESULT_OK) {
            //after selecting flat image send it to storage database
            final Uri uri = data.getData();
            StorageReference filepath = storageReference.child("egypt/" + "alex/" + "flats/" + "flatId").child(String.valueOf(i));
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    //to show images on image view we set after selecting image to upload
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


                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));

    }

}
