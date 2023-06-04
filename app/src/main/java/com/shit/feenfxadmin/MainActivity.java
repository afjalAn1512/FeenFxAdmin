package com.shit.feenfxadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shit.feenfxadmin.spalsh.LogInActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextView userName, name,userCount,with_count,dep_count,buy_count,sell_count,with_reqCount,dep_reqCount;
    private CardView add_timeSlot,add_Trading,add_wallet,payment,withdraw,deposit,user_list,log_out;

    Dialog popup;
    private ImageView image;

    String uid;

    FirebaseStorage storage;
    StorageReference storageReference;

    private Uri filePath;
    private String imageLink = "";
    byte[] Data;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.textView2);
        name = findViewById(R.id.textView7);
        userCount = findViewById(R.id.textView4);
        add_timeSlot = findViewById(R.id.add_timeSlot);
        add_Trading = findViewById(R.id.add_Trading);
        add_wallet = findViewById(R.id.add_wallet);
        payment = findViewById(R.id.payment);
        with_count = findViewById(R.id.te4xetView4);
        withdraw = findViewById(R.id.withdraw);
        deposit = findViewById(R.id.deposit);
        dep_count = findViewById(R.id.textV6iew4);
        user_list = findViewById(R.id.user_list);
        log_out = findViewById(R.id.log_out);
        buy_count = findViewById(R.id.textView10);
        sell_count = findViewById(R.id.textVf6diew4);
        dep_reqCount = findViewById(R.id.textView100);
        with_reqCount = findViewById(R.id.textVf6diew40);

        popup = new Dialog(this);


        UUID uuid = UUID.randomUUID();
        uid = uuid.toString();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        FirebaseFirestore.getInstance().collection("Admin").whereEqualTo("user_id", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(value)) {
                            String nameA = Objects.requireNonNull(documentSnapshot.get("fullName")).toString();
                            String email = Objects.requireNonNull(documentSnapshot.get("email")).toString();
                            userName.setText(nameA);
                            name.setText(email);
                        }
                    }
                });
        FirebaseFirestore.getInstance().collection("userDetails")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count++;
                        }
                        userCount.setText(String.valueOf(count));
                    }
                });


        FirebaseFirestore.getInstance().collection("Withdraw")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count_w = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count_w++;
                        }
                        with_count.setText(String.valueOf(count_w));
                    }
                });

        FirebaseFirestore.getInstance().collection("Withdraw")
                .whereEqualTo("status","Pending")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count_t = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count_t++;
                        }
                        with_reqCount.setText(String.valueOf(count_t));
                    }
                });


        FirebaseFirestore.getInstance().collection("Trade")
                .whereEqualTo("trade","buy")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count_b = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count_b++;
                        }
                        buy_count.setText(String.valueOf(count_b));
                    }
                });

        FirebaseFirestore.getInstance().collection("Trade")
                .whereEqualTo("trade","sell")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count_s = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count_s++;
                        }
                        sell_count.setText(String.valueOf(count_s));
                    }
                });


        FirebaseFirestore.getInstance().collection("Deposit")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count_w = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count_w++;
                        }
                        dep_count.setText(String.valueOf(count_w));
                    }
                });


        FirebaseFirestore.getInstance().collection("Deposit")
                .whereEqualTo("status","Pending")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count_r = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count_r++;
                        }
                        dep_reqCount.setText(String.valueOf(count_r));
                    }
                });


        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WithdrawActivity.class));
                finish();
            }
        });

        user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserListActivity.class));
                finish();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
                finish();
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DepositActivity.class));
                finish();
            }
        });


        add_timeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TimeSlotActivity.class));
                finish();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PaymentRequestActivity.class));
                finish();
            }
        });

        add_Trading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TradingActivity.class));
                finish();
            }
        });

        add_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup.setContentView(R.layout.popup_add_wallet);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextInputEditText wallet_add = popup.findViewById(R.id.description);
                TextView pro = popup.findViewById(R.id.textView8);
                image = popup.findViewById(R.id.imageView3);
                CardView add = popup.findViewById(R.id.button);
                popup.show();

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String Description = Objects.requireNonNull(wallet_add.getText()).toString().trim();

                        if (TextUtils.isEmpty(Description)){
                            wallet_add.setError("Enter Package Description");
                            wallet_add.requestFocus();
                        }else if (filePath == null) {
                            pro.setText("Qrcode Photo Select");
                            pro.setTextColor(Color.RED);
                        }else {

                            HashMap<String,Object> map = new HashMap<>();
                            map.put("wallet_address",Description);
                            map.put("timestamp", FieldValue.serverTimestamp());
                            map.put("wallet_id", uid);

                            FirebaseFirestore.getInstance().collection("Wallet").document("Address")
                                    .set(map)
                                    .addOnCompleteListener(task -> {
                                        uploadImage(uid);
                                    });
                        }
                    }
                });

            }
        });

    }


    private void uploadImage(String uid) {

        if (filePath != null) {
            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] data = baos.toByteArray();
                //uploading the image
                UploadTask uploadTask = ref.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageLink = uri.toString();
                                Log.d("imageLink", "onSuccess: " + imageLink);
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                DocumentReference docRef = FirebaseFirestore.getInstance().collection("Wallet").document("Address");

                                Map<String, Object> userMap = new HashMap<>();
                                final String id = docRef.getId();

                                userMap.put("imageUrl", imageLink);
                                userMap.put("timestamp", FieldValue.serverTimestamp());

                                docRef.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        popup.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        });

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }

        }

    }


    private void selectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }


    // Override onActivityResult method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                Data = baos.toByteArray();
                Glide.with(MainActivity.this).load(bitmap).centerCrop().into(image);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}