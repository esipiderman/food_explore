package com.example.foodexplore.mainScreen

import com.example.foodexplore.model.Food
import com.example.foodexplore.model.FoodDao

class MainScreenPresenter(
    private val foodDao: FoodDao
) : MainScreenContract.Presenter {
    var mainView : MainScreenContract.View? = null

    override fun firstRun() {
        val firstRunFoodList = listOf<Food>(
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
        foodDao.insertAllFoods(firstRunFoodList)

    }

    override fun onAttach(view: MainScreenContract.View) {
        mainView = view

        mainView!!.showAllFoods(foodDao.getAllFoods())
    }

    override fun onDetach() {
        mainView = null
    }

    override fun onUpdateFood(food: Food, pos: Int) {
        foodDao.updateFood(food)
        mainView!!.updateFood(food, pos)
    }

    override fun onDeleteFood(food: Food, pos: Int) {
        foodDao.deleteFood(food)
        mainView!!.removeFood(food, pos)
    }

    override fun onAddFoodClicked(newFood: Food) {
        foodDao.insertFood(newFood)
        mainView!!.addNewFood(newFood)
    }

    override fun onDeleteAllClicked() {
        foodDao.deleteAllFoods()
        mainView!!.refreshFoods(foodDao.getAllFoods())
    }

    override fun userSearching(filter: String) {
        if (filter.isNotEmpty()){
            //show filtered list
            mainView!!.refreshFoods(foodDao.searchFoods(filter))
        }else{
            //show all foods
            mainView!!.refreshFoods(foodDao.getAllFoods())
        }
    }
}