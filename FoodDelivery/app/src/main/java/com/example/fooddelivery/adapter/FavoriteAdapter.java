package com.example.fooddelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.gestion.DishGestion;
import com.example.fooddelivery.ui.dashboard.FavoriteDetails;
import com.example.fooddelivery.R;
import com.example.fooddelivery.model.Dish;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.DishViewHolder> {

    Context context;
    List<Dish> allmenuList;
    StorageReference storageReference;

    public FavoriteAdapter(Context context, List<Dish> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
    }

    public FavoriteAdapter() {
        refresh();
    }
    public void refresh() {
        allmenuList= DishGestion.getDishes();
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_dish, parent, false);

        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, final int position) {

        holder.tvDishName.setText(allmenuList.get(position).getName());
        holder.tvDishCalories.setText(allmenuList.get(position).getCalories());
        holder.tvDishRating.setText(allmenuList.get(position).getRating());
        holder.tvDishTime.setText(allmenuList.get(position).getDeliveryTime());
        holder.tvDishPrice.setText("₡ " + allmenuList.get(position).getPrice());

        storageReference = FirebaseStorage
                .getInstance().getReference().child( allmenuList.get(position).getName() + ".jpg");
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
                        holder.ImageDish.setImageBitmap(BitmapFactory.decodeFile(archivo));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getContext(), "Error Descargando", Toast.LENGTH_SHORT).show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FavoriteDetails.class);
                i.putExtra("name", allmenuList.get(position).getName());
                i.putExtra("price", allmenuList.get(position).getPrice());
                i.putExtra("rating", allmenuList.get(position).getRating());
                i.putExtra("deliveryTime", allmenuList.get(position).getDeliveryTime());
                i.putExtra("note", allmenuList.get(position).getCalories());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allmenuList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder{

        TextView tvDishName, tvDishCalories, tvDishRating, tvDishTime, tvDishPrice;
        ImageView ImageDish;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvDishCalories = itemView.findViewById(R.id.tvDishCalories);
            tvDishRating = itemView.findViewById(R.id.tvDishRating);
            tvDishTime = itemView.findViewById(R.id.tvDishTime);
            tvDishPrice = itemView.findViewById(R.id.tvDishPrice);
            ImageDish = itemView.findViewById(R.id.ImageDish);
        }
    }

}
