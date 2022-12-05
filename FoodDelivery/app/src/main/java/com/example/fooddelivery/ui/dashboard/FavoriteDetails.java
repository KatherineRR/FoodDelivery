package com.example.fooddelivery.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.gestion.DishGestion;
import com.example.fooddelivery.model.Dish;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FavoriteDetails extends AppCompatActivity {

    ImageView imagen;
    TextView tvName, tvPrice, tvRating;
    RatingBar ratingBar;
    Button btFavorites;

    String name, price, rating, deliveryTime, calories;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        deliveryTime = intent.getStringExtra("deliveryTime");
        calories = intent.getStringExtra("calories");

        tvName = findViewById(R.id.tvNameF);
        tvPrice = findViewById(R.id.tvPriceF);
        tvRating = findViewById(R.id.tvRatingF);
        ratingBar = findViewById(R.id.ratingBarF);
        imagen = findViewById(R.id.imagenF);
        btFavorites = findViewById(R.id.btDeleteFavorites);

        tvName.setText(name);
        tvPrice.setText("$ "+price);
        tvRating.setText(rating);
        ratingBar.setRating(Float.parseFloat(rating));

        storageReference = FirebaseStorage
                .getInstance().getReference().child( name + ".jpg");
        File localFile=null;
        try {
            localFile = File.createTempFile("images","jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File finalLocalFile = localFile;
        storageReference.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        String archivo = finalLocalFile.getAbsolutePath();
                        imagen.setImageBitmap(BitmapFactory.decodeFile(archivo));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getContext(), "Error Descargando", Toast.LENGTH_SHORT).show();
            }
        });

        btFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DishGestion.delete(name)){
                    Toast.makeText(getApplicationContext(), "Dish removed successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}