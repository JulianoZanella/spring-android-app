package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.julianozanella.springandroidapp.domain.Cart
import com.julianozanella.springandroidapp.domain.Produto
import com.julianozanella.springandroidapp.service.CartService

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cart = MutableLiveData<Cart>()
    private var service: CartService? = null

    fun setService(cartService: CartService) {
        service = cartService
    }

    fun getCart(): LiveData<Cart> {
        cart.value = service?.getCart()
        return cart
    }

    fun clearCart() {
        cart.value = service?.createOrClearCart()
    }

    fun addProduct(obj: Produto) {
        cart.value = service?.addProduct(obj)
    }

    fun removeProduct(obj: Produto) {
        cart.value = service?.removeProduct(obj)
    }

    fun increaseQuantity(obj: Produto) {
        cart.value = service?.increaseQuantity(obj)
    }

    fun decreaseQuantity(obj: Produto) {
        cart.value = service?.decreaseQuantity(obj)
    }

    fun getTotal(): LiveData<Double> {
        val data = MutableLiveData<Double>()
        cart.observeForever {
            data.value = service?.getTotal()
        }
        return data
    }

}