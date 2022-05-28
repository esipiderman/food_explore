package com.example.foodexplore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexplore.databinding.ActivityMainBinding
import com.example.foodexplore.databinding.DialogAddItemBinding
import com.example.foodexplore.databinding.DialogDeleteItemBinding
import com.example.foodexplore.databinding.DialogUpdateItemBinding

class MainActivity : AppCompatActivity(), MyAdapter.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter
    private val foodList = arrayListOf<Food>(
        Food(
            "Hamburger",
            "15",
            "3",
            "Isfahan, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
            20,
            4.5f
        ),
        Food(
            "Grilled fish",
            "20",
            "2.1",
            "Tehran, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
            10,
            4f
        ),
        Food(
            "Lasania",
            "40",
            "1.4",
            "Isfahan, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
            30,
            2f
        ),
        Food(
            "pizza",
            "10",
            "2.5",
            "Zahedan, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
            80,
            1.5f
        ),
        Food(
            "Sushi",
            "20",
            "3.2",
            "Mashhad, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
            200,
            3f
        ),
        Food(
            "Roasted Fish",
            "40",
            "3.7",
            "Jolfa, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
            50,
            3.5f
        ),
        Food(
            "Fried chicken",
            "70",
            "3.5",
            "NewYork, USA",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
            70,
            2.5f
        ),
        Food(
            "Vegetable salad",
            "12",
            "3.6",
            "Berlin, Germany",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
            40,
            4.5f
        ),
        Food(
            "Grilled chicken",
            "10",
            "3.7",
            "Beijing, China",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
            15,
            5f
        ),
        Food(
            "Baryooni",
            "16",
            "10",
            "Ilam, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
            28,
            4.5f
        ),
        Food(
            "Ghorme Sabzi",
            "11.5",
            "7.5",
            "Karaj, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
            27,
            5f
        ),
        Food(
            "Rice",
            "12.5",
            "2.4",
            "Shiraz, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
            35,
            2.5f
        ),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //------------SET ADAPTER------------------
        myAdapter = MyAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.recyclerMain.adapter = myAdapter

        binding.recyclerMain.layoutManager =  LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAdd.setOnClickListener {
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


                    val random = (0..11).random()
                    val urlPic = foodList[random].imageUrl

                    val newFood = Food(
                        txtName,
                        txtDistance,
                        txtPrice,
                        txtCity,
                        urlPic,
                        txtNumberRating,
                        txtRating
                    )

                    myAdapter.addFood(newFood)
                    foodList.add(0, newFood)

                    binding.recyclerMain.scrollToPosition(0)
                    dialog.dismiss()

                } else {
                    Toast.makeText(this, "complete all the information", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.inputTextMain.addTextChangedListener { searchItem ->
            if (searchItem!!.isNotEmpty()){
                val copyList = foodList.clone() as ArrayList<Food>

                val filteredList = copyList.filter {
                    it.txtSubject.contains(searchItem)
                }

                myAdapter.setData( ArrayList(filteredList) )

            }else{
                myAdapter.setData(foodList.clone() as ArrayList<Food>)
            }
        }
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
                    txtName,
                    txtDistance,
                    txtPrice,
                    txtCity,
                    food.imageUrl,
                    food.numOfRating,
                    food.rating
                )

                myAdapter.updateFood(newFood, pos)
                foodList[pos] = newFood

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
            foodList.remove(food)

        }

    }
}