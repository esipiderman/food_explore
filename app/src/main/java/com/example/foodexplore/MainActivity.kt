package com.example.foodexplore

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexplore.databinding.*
import com.example.foodexplore.room.Food
import com.example.foodexplore.room.FoodDao
import com.example.foodexplore.room.MyDatabase

class MainActivity : AppCompatActivity(), MyAdapter.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var foodDao: FoodDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodDao = MyDatabase.getDatabase(this).foodDao

        firstRun()

        showAllData()

        binding.btnAdd.setOnClickListener {
            addFood()
        }

        binding.inputTextMain.addTextChangedListener { searchItem ->

            searchOnDatabase(searchItem.toString())
        }

        binding.btnRemoveAll.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogDeleteAllBinding = DialogDeleteAllBinding.inflate(layoutInflater)

            dialog.setView(dialogDeleteAllBinding.root)
            dialog.setCancelable(true)
            dialog.show()

            dialogDeleteAllBinding.dialogDeleteAllDelete.setOnClickListener {
                dialog.dismiss()
            }

            dialogDeleteAllBinding.dialogDeleteAllSure.setOnClickListener {

                dialog.dismiss()
                foodDao.deleteAllFoods()
                showAllData()

            }
        }
    }

    private fun searchOnDatabase(searchItem: String) {
        if (searchItem.isNotEmpty()){

            val filteredList = foodDao.searchFoods(searchItem)

            myAdapter.setData( ArrayList(filteredList) )

        }else{
            val data = foodDao.getAllFoods()
            myAdapter.setData(ArrayList(data))
        }
    }

    private fun addFood() {
        val dialog = AlertDialog.Builder(this).create()

        val dialogBinding = DialogAddItemBinding.inflate(layoutInflater)

        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.dialogBtnDone.setOnClickListener {

            if (
                dialogBinding.edtName.length() > 0 &&
                dialogBinding.edtCity.length() > 0 &&
                dialogBinding.edtPrice.length() > 0 &&
                dialogBinding.edtDistance.length() > 0
            ) {
                val txtName = dialogBinding.edtName.text.toString()
                val txtCity = dialogBinding.edtCity.text.toString()
                val txtPrice = dialogBinding.edtPrice.text.toString()
                val txtDistance = dialogBinding.edtDistance.text.toString()
                val txtNumberRating: Int = (1..150).random()
                val txtRating: Float = ((10..50).random().toFloat()) / 10f

                val random = (1..12).random()
                val urlPic = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food$random.jpg"

                val newFood = Food(
                    txtSubject = txtName,
                    txtDistance = txtDistance,
                    txtPrice = txtPrice,
                    txtCity = txtCity,
                    imageUrl = urlPic,
                    numOfRating = txtNumberRating,
                    rating = txtRating
                )

                myAdapter.addFood(newFood)
                foodDao.insertFood(newFood)

                binding.recyclerMain.scrollToPosition(myAdapter.itemCount-1)
                dialog.dismiss()

            } else {
                Toast.makeText(this, "complete all the information", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firstRun(){
        val sharedPreferences = getSharedPreferences("foodExplore", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("first_run", true)){

            val foodList = listOf<Food>(
                Food(
                    txtSubject = "Hamburger",
                    txtDistance = "15",
                    txtPrice = "3",
                    txtCity = "Isfahan, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                    numOfRating = 20,
                    rating = 4.5f
                ),
                Food(
                    txtSubject = "Grilled fish",
                    txtDistance = "20",
                    txtPrice = "2.1",
                    txtCity = "Tehran, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                    numOfRating = 10,
                    rating = 4f
                ),
                Food(
                    txtSubject = "Lasania",
                    txtDistance = "40",
                    txtPrice = "1.4",
                    txtCity = "Isfahan, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                    numOfRating = 30,
                    rating = 2f
                ),
                Food(
                    txtSubject = "pizza",
                    txtDistance = "10",
                    txtPrice = "2.5",
                    txtCity = "Zahedan, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                    numOfRating = 80,
                    rating = 1.5f
                ),
                Food(
                    txtSubject = "Sushi",
                    txtDistance = "20",
                    txtPrice = "3.2",
                    txtCity = "Mashhad, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                    numOfRating = 200,
                    rating = 3f
                ),
                Food(
                    txtSubject = "Roasted Fish",
                    txtDistance = "40",
                    txtPrice = "3.7",
                    txtCity = "Jolfa, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                    numOfRating = 50,
                    rating = 3.5f
                ),
                Food(
                    txtSubject = "Fried chicken",
                    txtDistance = "70",
                    txtPrice = "3.5",
                    txtCity = "NewYork, USA",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                    numOfRating = 70,
                    rating = 2.5f
                ),
                Food(
                    txtSubject = "Vegetable salad",
                    txtDistance = "12",
                    txtPrice = "3.6",
                    txtCity = "Berlin, Germany",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                    numOfRating = 40,
                    rating = 4.5f
                ),
                Food(
                    txtSubject = "Grilled chicken",
                    txtDistance = "10",
                    txtPrice = "3.7",
                    txtCity = "Beijing, China",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                    numOfRating = 15,
                    rating = 5f
                ),
                Food(
                    txtSubject = "Baryooni",
                    txtDistance = "16",
                    txtPrice = "10",
                    txtCity = "Ilam, Iran",
                    imageUrl =  "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                    numOfRating = 28,
                    rating = 4.5f
                ),
                Food(
                    txtSubject = "Ghorme Sabzi",
                    txtDistance = "11.5",
                    txtPrice = "7.5",
                    txtCity = "Karaj, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                    numOfRating = 27,
                    rating = 5f
                ),
                Food(
                    txtSubject = "Rice",
                    txtDistance = "12.5",
                    txtPrice =  "2.4",
                    txtCity = "Shiraz, Iran",
                    imageUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                    numOfRating = 35,
                    rating = 2.5f
                ),
            )

            foodDao.insertAllFoods(foodList)

            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }
    }

    private fun showAllData(){

        val foodData = foodDao.getAllFoods()

        //------------SET ADAPTER------------------
        myAdapter = MyAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager =  LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onFoodClicked(food: Food, pos: Int) {

        val dialog = AlertDialog.Builder(this).create()

        val dialogUpdateBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(dialogUpdateBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogUpdateBinding.edtName.setText(food.txtSubject)
        dialogUpdateBinding.edtCity.setText(food.txtCity)
        dialogUpdateBinding.edtDistance.setText(food.txtDistance)
        dialogUpdateBinding.edtPrice.setText(food.txtPrice)

        dialogUpdateBinding.dialogUpdateBtnDone.setOnClickListener {

            if (
                dialogUpdateBinding.edtName.length() > 0 &&
                dialogUpdateBinding.edtCity.length() > 0 &&
                dialogUpdateBinding.edtPrice.length() > 0 &&
                dialogUpdateBinding.edtDistance.length() > 0
            ) {
                val txtName = dialogUpdateBinding.edtName.text.toString()
                val txtCity = dialogUpdateBinding.edtCity.text.toString()
                val txtPrice = dialogUpdateBinding.edtPrice.text.toString()
                val txtDistance = dialogUpdateBinding.edtDistance.text.toString()

                val newFood = Food(
                    id = food.id,
                    txtSubject = txtName,
                    txtDistance = txtDistance,
                    txtPrice = txtPrice,
                    txtCity = txtCity,
                    imageUrl = food.imageUrl,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )

                myAdapter.updateFood(newFood, pos)
                foodDao.updateFood(newFood)

                binding.recyclerMain.scrollToPosition(pos)
                dialog.dismiss()

            } else {
                Toast.makeText(this, "complete all the information", Toast.LENGTH_LONG).show()
            }


        }

        dialogUpdateBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onFoodLongClicked(food: Food, pos: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)

        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogDeleteDelete.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogDeleteSure.setOnClickListener {

            dialog.dismiss()
            myAdapter.removeFood(food, pos)
            foodDao.deleteFood(food)

        }

    }
}