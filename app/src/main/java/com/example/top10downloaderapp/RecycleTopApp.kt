package com.example.top10downloaderapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.top10downloaderapp.databinding.CardCellBinding
import com.squareup.picasso.Picasso

class RecycleTopApp(var listTop : List<TopApp> , var context : Context) : RecyclerView.Adapter<RecycleTopApp.ItemHolder>() {
    class ItemHolder(val binding: CardCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val list = listTop
        holder.binding.apply {
            tvTitle.text = "${position+1}- ${list[position].title}"
            Picasso.with(context)
                .load(list[position].image)
                .into(imageView2)
        }
    }

    override fun getItemCount(): Int = listTop.size

    fun update(newList : List<TopApp>){
        listTop = newList
        notifyDataSetChanged()
    }
}