package com.example.fooddelivery.gestion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fooddelivery.data.AdminDB;
import com.example.fooddelivery.model.Dish;

import java.util.ArrayList;

public class DishGestion {

    private static AdminDB data=null;
    private static SQLiteDatabase conexion;

    public static void init(AdminDB data) {
        DishGestion.data=data;
    }

    public static boolean insert(Dish dish) {
        long registro = -1;
        if (dish!=null) {
            ContentValues info = new ContentValues();
            info.put("name",dish.getName());
            info.put("rating",dish.getRating());
            info.put("deliveryTime",dish.getDeliveryTime());
            info.put("price",dish.getPrice());
            info.put("calories",dish.getCalories());
            conexion=data.getWritableDatabase();
            registro=conexion.insert("dishes",null,info);
            conexion.close();
        }
        return registro!=-1;
    }

    public static boolean delete(String name) {
        conexion = data.getWritableDatabase();
        long registro = conexion.delete("dishes","name=?",new String[]{""+name+""});
        conexion.close();
        return registro ==1;
    }

    public static ArrayList<Dish> getDishes() {
        ArrayList<Dish> lista=new ArrayList<>();

        conexion=data.getReadableDatabase();
        Cursor datos = conexion.rawQuery("select * from dishes",null);

        while (datos.moveToNext()) {
            lista.add(new Dish(datos.getString(0),
                    datos.getString(1),
                    datos.getString(2),
                    datos.getString(3),
                    datos.getString(4)));
        }
        conexion.close();
        return lista;
    }

    public static Dish busca(String name) {
        Dish dish=null;

        conexion=data.getReadableDatabase();
        Cursor datos = conexion.rawQuery("select * from dishes where name=?",
                new String[]{""+name+""});
        //Cursor datos2 = conexion.rawQuery("select * from estudiante where id="+id,null);

        if (datos.moveToFirst()) {  //Si se puede ir al primer registro... significa que hay registros...
            dish= new Dish(datos.getString(0),
                    datos.getString(1),
                    datos.getString(2),
                    datos.getString(3),
                    datos.getString(4));
        }
        conexion.close();
        return dish;
    }
}
