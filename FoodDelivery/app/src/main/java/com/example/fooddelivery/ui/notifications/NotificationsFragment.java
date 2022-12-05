package com.example.fooddelivery.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddelivery.R;
import com.example.fooddelivery.model.Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {

    Button btCall, btEmail;
    TextView tvResName, tvDescripcion, tvEmail, tvPhone;

    FirebaseDatabase database;
    DatabaseReference myRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        btCall = root.findViewById(R.id.btCall);
        btEmail = root.findViewById(R.id.btEmail);
        tvResName = root.findViewById(R.id.tvResName);
        tvDescripcion = root.findViewById(R.id.tvDescripcion);
        tvEmail = root.findViewById(R.id.tvEmail);
        tvPhone = root.findViewById(R.id.tvPhone);
        database=FirebaseDatabase.getInstance();
        myRef = database.getReference().child("info");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    Info info = bookSnapshot.getValue(Info.class);
                    tvResName.setText(info.getName());
                    tvDescripcion.setText(info.getDescription());
                    tvEmail.setText(info.getEmail());
                    tvPhone.setText(info.getPhone().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefono = tvPhone.getText().toString();
                Uri uri = Uri.parse("tel:" + telefono);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        return root;
    }

    private void enviar() {
        String para=tvEmail.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {para});
        startActivity(intent);
    }

}