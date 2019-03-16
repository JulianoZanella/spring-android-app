package com.julianozanella.springandroidapp.domain

import java.util.ArrayList

class Cart(val items: MutableList<CartItem> = ArrayList()){
    override fun toString(): String {
        return "Cart(items=$items)"
    }
}