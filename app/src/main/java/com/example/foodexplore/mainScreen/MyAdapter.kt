package com.example.foodexplore.mainScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodexplore.databinding.ItemFoodBinding
import com.example.foodexplore.model.Food
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MyAdapter(val data : ArrayList<Food>, private val foodEvents: FoodEvents) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int){

            binding.itmTxtSubject.text = data[position].txtSubject
            binding.itmTxtCity.text = data[position].txtCity
            binding.itmTxtPrice.text = "$" + data[position].txtPrice + " vip"
            binding.itmTxtDistance.text = data[position].txtDistance  + " miles from you"
            binding.itmRating.rating = data[position].rating
            binding.itmTxtNumRating.text = "(" + data[position].numOfRating.toString() + " Rating)"

            Glide.with(binding.root.context)
                .load(data[position].imageUrl)
                .transform(RoundedCornersTransformation(16,4))
                .into(binding.itemImg)


            itemView.setOnClickListener{

                foodEvents.onFoodClicked(data[adapterPosition], adapterPosition)

            }

            itemView.setOnLongClickListener {

                foodEvents.onFoodLongClicked(data[adapterPosition] , adapterPosition)

                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addFood(newFood : Food){

        data.add(0,newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood : Food, pos : Int){

        data.remove(oldFood)
        notifyItemRemoved(pos)

    }

    fun updateFood(food: Food, pos: Int){

        data[pos] = food
        notifyItemChanged(pos)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list :ArrayList<Food>){

        data.clear()
        data.addAll(list)

        notifyDataSetChanged()
    }

    interface FoodEvents {

        fun onFoodClicked(food: Food, pos: Int)
        fun onFoodLongClicked(food : Food, pos : Int)

    }

}