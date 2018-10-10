package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/14/18.
 */
class SideCategoryTest : LaunchAppWithFacebook() {


    @Test
    fun selectSideCategory() {
        val homeScreen = HomeScreen()
        val sideCategory = homeScreen.clickOnSidecategorButton()
        val numberOfCategories = sideCategory.numberOftitles()
        print("Number of categories is $numberOfCategories")
        val randomNumber = Random().nextInt(numberOfCategories)
        print("randomly pick $randomNumber")
        val textOfRandomCategory = sideCategory.textOfRandomCategory(randomNumber)
        print("Text of Random number is $textOfRandomCategory")
        val randomCategory = sideCategory.selectRandomcategory(randomNumber)
        val movieDatailScreen = MoviesByCategoryScreen()
        val textOfCategory = movieDatailScreen.categoryText
        Assert.assertEquals("This text select random category from the categoryList and checking if text from the list equals to Text in category Screen", textOfRandomCategory.toLowerCase(), textOfCategory.toLowerCase())
    }

    @Test
    fun checkIfEmailIsIncluddedInHamburgerMenu() {
        val homeScreen = HomeScreen()
        val sideCategory = homeScreen.clickOnSidecategorButton()
        val email = sideCategory.getUserEmail()
        Assert.assertTrue(email.contains("@"))
    }

    @Test
    fun checkIfWeShowCorrectUserId() {
        LogInTest().SignOut()
        SignInTest().signInWithCorrectEmailAndPassword()
        LogInTest().SignOut()
        setUps()
        val homeScreen = HomeScreen()
        val sideCategory = homeScreen.clickOnSidecategorButton()
        val userName = sideCategory.getUserName().split(" ").get(0)
        val some = textFromFacebookButton
        Assert.assertTrue(textFromFacebookButton.contains(userName))

    }


}