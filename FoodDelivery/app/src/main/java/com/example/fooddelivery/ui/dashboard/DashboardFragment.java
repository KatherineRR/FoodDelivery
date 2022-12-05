package com.example.fooddelivery.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.adapter.DishAdapter;
import com.example.fooddelivery.adapter.FavoriteAdapter;
import com.example.fooddelivery.gestion.DishGestion;
import com.example.fooddelivery.model.Dish;

import java.util.List;

public class DashboardFragment extends Fragment {

    RecyclerView allMenuRecyclerView;

    FavoriteAdapter dishAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        List<Dish> list = DishGestion.getDishes();

        allMenuRecyclerView = root.findViewById(R.id.favorites_recycler);
        dishAdapter = new FavoriteAdapter(getContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(dishAdapter);
        dishAdapter.notifyDataSetChanged();


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        dishAdapter.refresh();
        dishAdapter.notifyDataSetChanged();
    }

}