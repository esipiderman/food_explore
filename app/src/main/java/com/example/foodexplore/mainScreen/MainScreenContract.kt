package com.example.foodexplore.mainScreen

import com.example.foodexplore.model.Food

interface MainScreenContract {

    interface Presenter {
        fun firstRun()
        fun onAttach(view: View)
        fun onDetach()
        fun onUpdateFood(food: Food, pos:Int)
        fun onDeleteFood(food: Food, pos: Int)
        fun onAddFoodClicked(newFood: Food)
        fun onDeleteAllClicked()
        fun userSearching(filter: String)
    }

    interface View {
        fun showAllFoods(data:List<Food>)
        fun refreshFoods(data:List<Food>)
        fun addNewFood(food: Food)
        fun updateFood(oldFood: Food, pos: Int)
        fun removeFood(oldFood: Food, pos: Int)
    }
}