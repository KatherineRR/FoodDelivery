package com.example.fooddelivery.ui.home;

import android.os.AsyncTask;
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
import com.example.fooddelivery.model.Dish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView allMenuRecyclerView;

    DishAdapter dishAdapter;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        new GetDishes().execute();

        return root;
    }

    private StringBuilder stringBuilder = new StringBuilder();
    final String urlBase = "https://api.edamam.com/search?q=chiken&app_id=61edb0a3&app_key=55f29445d458a346a33571d89486481a&from=10&to=20";

    private  class GetDishes extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            final String url = urlBase;
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                int code = connection.getResponseCode();

                if (code == HttpURLConnection.HTTP_OK){
                    stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(),
                                    "utf-8"));

                    String linea = "";
                    while ((linea = bufferedReader.readLine()) != null){
                        stringBuilder.append(linea+"\n");
                    }
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result){
            DecimalFormat formato1 = new DecimalFormat("#.00");
            ArrayList<Dish> list = new ArrayList<Dish>();
            try {
                //JSONArray jsonArray = new JSONArray(result);
                //JSONObject jsonObject = jsonArray.getJSONObject(0);
                //JSONArray menu = jsonObject.getJSONArray("allmenu");

                /**for (int i = 0; i < menu.length(); i++) {
                    JSONObject jsonobject = menu.getJSONObject(i);

                    Dish dish = new Dish();
                    dish.setName(jsonobject.getString("name"));
                    dish.setRating(jsonobject.getString("rating"));
                    dish.setDeliveryTime(jsonobject.getString("deliveryTime"));
                    dish.setPrice(jsonobject.getString("price"));
                    dish.setCalories(jsonobject.getString("calories"));
                    list.add(dish);
                }**/

                JSONObject jsonObject = new JSONObject(result);
                JSONArray menu = jsonObject.getJSONArray("hits");

                for (int i = 0; i < menu.length(); i++) {
                    JSONObject jsonObject1 = menu.getJSONObject(i).getJSONObject("recipe");

                    Dish dish = new Dish();
                    dish.setName(jsonObject1.getString("label"));
                    dish.setRating(jsonObject1.getString("yield"));
                    dish.setDeliveryTime(jsonObject1.getString("totalTime") + " minutes");
                    dish.setPrice(formato1.format(Float.parseFloat(jsonObject1.getString("totalWeight"))));
                    dish.setCalories(formato1.format(Float.parseFloat(jsonObject1.getString("calories"))));
                    list.add(dish);
                }


                allMenuRecyclerView = root.findViewById(R.id.favorites_recycler);
                dishAdapter = new DishAdapter(getContext(), list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                allMenuRecyclerView.setLayoutManager(layoutManager);
                allMenuRecyclerView.setAdapter(dishAdapter);
                dishAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}