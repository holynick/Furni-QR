package com.example.ckfyp.furniqr;

import java.util.List;
import java.util.Vector;

/**
 * Created by Nick Yau on 3/18/2017.
 */

public class CartManager {

    public static List<cartfurnitureitem> cart;

    public static List<cartfurnitureitem> getCart() {
        if(cart == null) {
            cart = new Vector<cartfurnitureitem>();
        }

        return cart;
    }
}
