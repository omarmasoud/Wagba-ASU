package Models;

import java.util.ArrayList;
//Singleton
public class Cart {
    private static Cart cart;
    private ArrayList<MenuItem> Items;
    private String currentRestaurant;
    private Cart(){
        Items=new ArrayList<>();
        Cart.cart=this;//setting the static object to this once instantiated
    }
    public static Cart getCart(){
        return
                Cart.cart==null?
                        new Cart():Cart.cart;
    }
}
