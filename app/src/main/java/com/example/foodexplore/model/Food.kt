package com.example.foodexplore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_food")
data class Food(

    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    val txtSubject : String,
    val txtDistance : String,
    val txtPrice : String,
    val txtCity : String,
    val imageUrl : String,
    val numOfRating : Int,
    val rating : Float
)
