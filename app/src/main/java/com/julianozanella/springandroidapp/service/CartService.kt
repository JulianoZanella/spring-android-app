package com.julianozanella.springandroidapp.service

import android.content.Context
import android.util.Log
import com.julianozanella.springandroidapp.domain.Cart
import com.julianozanella.springandroidapp.domain.CartItem
import com.julianozanella.springandroidapp.domain.Produto
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.extensions.saveSharedPreferences

class CartService(private val context: Context) {

    fun createOrClearCart(): Cart {
        val cart = Cart()
        context.saveSharedPreferences(KEY.CART, cart)
        return cart
    }

    fun getCart(): Cart {
        var cart: Cart? = context.getSharedPreference(KEY.CART, Cart::class.java) as Cart?
        if (cart == null) {
            cart = createOrClearCart()
        }
        return cart
    }

    fun addProduct(obj: Produto): Cart {
        val cart = getCart()
        val position: Int = cart.items.indexOfFirst {
            it.produto.id == obj.id
        }
        if (position == -1) {
            cart.items.add(CartItem(1, obj))
        } else {
            val cartItem = cart.items[position]
            cart.items[position].quantidade++
        }
        Log.d("Cart", cart.toString())
        context.saveSharedPreferences(KEY.CART, cart)
        return cart
    }

    fun removeProduct(obj: Produto): Cart {
        val cart = getCart()
        val position: Int = cart.items.indexOfFirst {
            it.produto.id == obj.id
        }
        if (position != -1) {
            cart.items.removeAt(position)
        }
        context.saveSharedPreferences(KEY.CART, cart)
        return cart
    }

    fun increaseQuantity(obj: Produto): Cart {
        return addProduct(obj)
    }

    fun decreaseQuantity(obj: Produto): Cart {
        var cart = getCart()
        val position: Int = cart.items.indexOfFirst {
            it.produto.id == obj.id
        }
        if (position != -1) {
            val qtd = cart.items[position].quantidade--
            if(qtd <1){
                cart = removeProduct(obj)
            }
        }
        context.saveSharedPreferences(KEY.CART, cart)
        return cart
    }

    fun getTotal(): Double {
        val cart = getCart()
        var sum = 0.0
        cart.items.forEach {
            sum += it.quantidade * it.produto.preco
        }
        return sum
    }
}