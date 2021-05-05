package com.example.desenrolaai

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.example.desenrolaai.model.Product

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {
    var data = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data[position]
        holder.linearLayout.findViewById<TextView>(R.id.product_name).text = product.name
        holder.linearLayout.findViewById<TextView>(R.id.product_categories).text = product.categories.toString()
        holder.linearLayout.findViewById<TextView>(R.id.product_desciption).text = product.description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.product_view, parent, false) as LinearLayout
        return ProductViewHolder(view)
    }
}

class ProductListener(val clickListener: (product: Product) -> Unit){
    fun onClick(product: Product) = clickListener(product)
}