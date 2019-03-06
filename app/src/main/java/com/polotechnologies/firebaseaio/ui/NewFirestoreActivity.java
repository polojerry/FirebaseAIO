package com.polotechnologies.firebaseaio.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.polotechnologies.firebaseaio.R;
import com.polotechnologies.firebaseaio.data_models.FirestoreItems;
import com.polotechnologies.firebaseaio.data_models.RealtimeItems;

public class NewFirestoreActivity extends AppCompatActivity {

    //Integer variable for requesting image
    static final int REQUEST_IMAGE_GET = 1;

    //Database Reference
    DatabaseReference mDatabaseReference;

    //Firabase Authentication
    FirebaseAuth mAuth;

    //Storage Reference
    StorageReference mStorageReference;

    // Bitmap and Uri for User Selected Image
    Bitmap thumbnail;
    Uri fullPhotoUri;

    //String to hold the uploaded image Url
    String uploadedImageUrl;

    //Widgets to be used
    TextInputEditText mImageName;
    ImageView mSelectedImage;
    Button mUploadImage;

    ProgressBar realTimeProgressBar;

    FirebaseFirestore fireStoreDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_firestore);

        mImageName = findViewById(R.id.newFirestoreImageName);
        mSelectedImage = findViewById(R.id.newFirestoreImage);
        mUploadImage = findViewById(R.id.btnUploadFirestore);
        realTimeProgressBar = findViewById(R.id.firestoreProgressBar);

        mAuth = FirebaseAuth.getInstance();
        fireStoreDb = FirebaseFirestore.getInstance();

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUploadImage.setEnabled(false);
                realTimeProgressBar.setVisibility(View.VISIBLE);
                uploadImage();
            }
        });
    }



    /*
        Method used to select image from Gallery
     */
    public void selectImage() {
        checkPermission();

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    /*
         Method to check for permission to read External Storage
     */
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    /*
       Overriding method to get the selected method using the REQUEST In
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {

            thumbnail = data.getParcelableExtra("data");
            fullPhotoUri = data.getData();

            // Do work with photo saved at fullPhotoUri
            Glide.with(getApplicationContext())
                    .load(fullPhotoUri)
                    .into(mSelectedImage);

        }
    }

    /*
        Method to upload image to firebase storage and getting the download url
     */
    public void uploadImage() {

        FirebaseUser mCurrentUser = mAuth.getCurrentUser();
        String userId = mCurrentUser.getUid();
        String name = mImageName.getText().toString();

        final StorageReference storageReference = mStorageReference.child("fireStoreImages/" + userId + "/" + name + ".png");
        storageReference.putFile(fullPhotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String downlaodUrl = uri.toString();
                        uploadedImageUrl = downlaodUrl;

                        uploadImageUrl(downlaodUrl);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewFirestoreActivity.this, "Failed to get Download Link" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                realTimeProgressBar.setVisibility(View.GONE);
                Toast.makeText(NewFirestoreActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImageUrl(String downloadUrl) {
        String imageName = mImageName.getText().toString().trim();

        FirestoreItems firestoreItems = new FirestoreItems(imageName,downloadUrl);


        CollectionReference collectionReference = fireStoreDb.collection("fireStoreImages");
        collectionReference.add(firestoreItems).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                realTimeProgressBar.setVisibility(View.GONE);
                Toast.makeText(NewFirestoreActivity.this, "Success Uploaded Image", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                realTimeProgressBar.setVisibility(View.GONE);
                Toast.makeText(NewFirestoreActivity.this, "Failed to Upload Image URL", Toast.LENGTH_SHORT).show();
            }
        });
        

    }


}
