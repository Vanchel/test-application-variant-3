package com.bignerdranch.android.testapplicationvariant3.adapters

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bignerdranch.android.testapplicationvariant3.R

@BindingAdapter("price")
fun TextView.price(price: Double?) {
    text = resources.getString(R.string.price_format, price ?: 0.0)
}

@BindingAdapter("priceEx")
fun TextView.priceExplicit(price: Double?) {
    text = resources.getString(R.string.price_format_explicit, price ?: 0.0)
}

@BindingAdapter("weightEx")
fun TextView.weightExplicit(weight: Double?) {
    text = resources.getString(R.string.weight_format_explicit, weight ?: 0.0)
}

@BindingAdapter("inCart")
fun TextView.inCart(isInCart: Boolean) {
    text = resources.getString(if (isInCart) R.string.in_cart else R.string.to_cart)
}

@BindingAdapter("htmlDescription")
fun TextView.htmlDescription(htmlString: String?) {
    text = HtmlCompat.fromHtml(htmlString ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
}