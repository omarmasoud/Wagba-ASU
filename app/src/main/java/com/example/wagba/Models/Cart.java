package com.example.wagba.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//Singleton
public class Cart {
    private static Cart cart;
    public static String Tag="CartTag";
    private ArrayList<MenuItem> Items;
    private String currentRestaurant;
    private Date date;
    private Order currentorder;
    private long  amount;
    private String DeliveryLocation;

    public String getDeliveryLocation() {
        return DeliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        DeliveryLocation = deliveryLocation;
    }

    public long getAmount() {
        long totalprice=0;
        for (int i = 0; i <this.Items.size() ; i++) {
            totalprice+=this.Items.get(i).getPrice()*this.Items.get(i).getCount();
        }
        this.amount=totalprice;
        return this.amount;
    }


    private SimpleDateFormat formatter;
    SharedPreferences preferences;
    String cartExportDate;

    SharedPreferences.Editor editor;
    private Cart(){
        this.formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Items=new ArrayList<>();
        this.DeliveryLocation="3";

        Cart.cart=this;//setting the static object to this once instantiated
    }
    public static Cart getCart(){
        return
                Cart.cart==null?
                        new Cart():Cart.cart;
    }
    public void addToCart(MenuItem menuItem){
        this.Items.add(menuItem);
    }
    public ArrayList<MenuItem> getItems(){
        return this.Items;
    }
    public void removeItem(int i){
        this.Items.remove(i);
    }
    private void SetItems(ArrayList<MenuItem> items){
        this.Items=Items;
    }
    public void storeData(Context context)
    {
        this.preferences=context.getSharedPreferences(Cart.Tag, Context.MODE_PRIVATE);
        editor=preferences.edit();
        Gson gson=new Gson();
        String jsonItems=gson.toJson(this.getItems());
        editor.putString("items", jsonItems);
        editor.apply();
    }
    public void loadData(Context context)
    {
        this.preferences=context.getSharedPreferences(Cart.Tag, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        ArrayList<MenuItem> menuItems = null;
        String jsonItems=preferences.getString("items", null);
        Type type = new TypeToken<ArrayList<MenuItem>>() {}.getType();
        if(jsonItems!=null)
        {
            menuItems= gson.fromJson(jsonItems, type);

        }
        this.Items=menuItems;


    }

    public Order getCurrentorder() {
        return currentorder;
    }

    public Order ExportCart(){
        if(this.getItems().size()==0)
            return null;
        Order order;
        long itemcount= this.Items.size();
        long totalprice=0;
        for (int i = 0; i <this.Items.size() ; i++) {
            totalprice+=this.Items.get(i).getPrice()*this.Items.get(i).getCount();
        }
        Log.d("totalprice", Long.toString(totalprice));
        this.date= Calendar.getInstance().getTime();
        this.amount=totalprice;
        cartExportDate=formatter.format(this.date);
        order=new Order(itemcount, cartExportDate, "pending", totalprice);
        order.setDeliveryLocation(this.getDeliveryLocation());
        this.Items=new ArrayList<>();
        this.currentorder=order;

    return order;
    }
    public void removeItem(MenuItem item){
        this.Items.remove(item);
    }
    public String getCartExportDate(){
        return cartExportDate;
    }
}
