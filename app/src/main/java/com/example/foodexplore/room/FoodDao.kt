package com.example.foodexplore.room

import androidx.room.*

@Dao
interface FoodDao {

    @Update
    fun updateFood(food: Food)

    @Insert
    fun insertFood(food: Food)

    @Insert
    fun insertAllFoods(data: List<Food>)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM table_food")
    fun deleteAllFoods()

    @Query("SELECT * FROM table_food")
    fun getAllFoods(): List<Food>

    @Query("SELECT * FROM table_food WHERE txtSubject LIKE '%' || :searcher || '%'")
    fun searchFoods(searcher: String): List<Food>

}