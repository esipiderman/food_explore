package com.example.foodexplore.mainScreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexplore.databinding.*
import com.example.foodexplore.model.Food
import com.example.foodexplore.model.FoodDao
import com.example.foodexplore.model.MyDatabase
import com.example.foodexplore.utils.URL_PIC
import com.example.foodexplore.utils.showToast

class MainActivity : AppCompatActivity(), MyAdapter.FoodEvents, MainScreenContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MyAdapter
    private lateinit var presenter: MainScreenContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainScreenPresenter(MyDatabase.getDatabase(this).foodDao)

        val sharedPreferences = getSharedPreferences("foodExplore", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_run", true)){
            presenter.firstRun()
            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }

        presenter.onAttach(this)

        binding.btnAdd.setOnClickListener {
            addFood()
        }

        binding.inputTextMain.addTextChangedListener { searchItem ->
            presenter.userSearching(searchItem.toString())
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
                presenter.onDeleteAllClicked()
                dialog.dismiss()

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
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
                val urlPic = "$URL_PIC$random.jpg"

                val newFood = Food(
                    txtSubject = txtName,
                    txtDistance = txtDistance,
                    txtPrice = txtPrice,
                    txtCity = txtCity,
                    imageUrl = urlPic,
                    numOfRating = txtNumberRating,
                    rating = txtRating
                )

                presenter.onAddFoodClicked(newFood)
                dialog.dismiss()

            } else {
                showToast("complete all the information")
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
                    id = food.id,
                    txtSubject = txtName,
                    txtDistance = txtDistance,
                    txtPrice = txtPrice,
                    txtCity = txtCity,
                    imageUrl = food.imageUrl,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )

                presenter.onUpdateFood(newFood, pos)
                dialog.dismiss()

            } else {
                showToast("complete all the information")
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
            presenter.onDeleteFood(food, pos)
            dialog.dismiss()
        }

    }

    override fun showAllFoods(data: List<Food>) {
        mainAdapter = MyAdapter(ArrayList(data), this)
        binding.recyclerMain.adapter = mainAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
    override fun refreshFoods(data: List<Food>) {
        mainAdapter.setData(ArrayList(data))
    }
    override fun addNewFood(food: Food) {
        mainAdapter.addFood(food)
        binding.recyclerMain.scrollToPosition(0)
    }
    override fun updateFood(oldFood: Food, pos: Int) {
        mainAdapter.updateFood(oldFood, pos)
        binding.recyclerMain.scrollToPosition(pos)
    }
    override fun removeFood(oldFood: Food, pos: Int) {
        mainAdapter.removeFood(oldFood, pos)
    }
}