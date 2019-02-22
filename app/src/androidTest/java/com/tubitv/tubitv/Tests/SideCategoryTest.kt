package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.LaunchScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/14/18.
 */
class SideCategoryTest : LaunchAppWithFacebook() {


    //@Test
    fun selectSideCategory() {
        val homeScreen = HomeScreen(true)
        val sideCategory = homeScreen.clickOnBrowseButton()
        val numberOfCategories = sideCategory.numberOftitles()
        print("Number of categories is $numberOfCategories")
        val randomNumber = Random().nextInt(numberOfCategories)
        print("randomly pick $randomNumber")
        val textOfRandomCategory = sideCategory.textOfRandomCategory(randomNumber)
        print("Text of Random number is $textOfRandomCategory")
        val randomCategory = sideCategory.selectRandomcategory(randomNumber)
        val movieDatailScreen = MoviesByCategoryScreen(textOfRandomCategory)
        val textOfCategory = movieDatailScreen.categoryText
        assertEquals("This text select random category from the categoryList and checking if text from the list equals to Text in category Screen", textOfRandomCategory.toLowerCase(), textOfCategory.toLowerCase())
    }

    //@Test
    fun checkIfEmailIsIncluddedInHamburgerMenu() {
        val homeScreen = HomeScreen(true)
        val titleInHomeScreen =homeScreen.longPress()
        titleInHomeScreen.clickAddToQueueAfterLongClickWithoutReturn()
        val sideCategory = homeScreen.clickOnBrowseButton()
        val email = sideCategory.getUserEmail()
        Assert.assertTrue(email.contains("@"))
    }

    //@Test
    fun checkIfWeShowCorrectUserId() {
        LogInTest().SignOut()
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.sendTextToEmailField("tubitv@tubitv.tubitv")
        signInScreen.sendTextToPasswordField("tubitv")
        signInScreen.clickOnSignInButton(false)
        uiDevice.pressBack()
        LogInTest().SignOut()
        setUps()
        val homeScreen = HomeScreen(true)
        val sideCategory = homeScreen.clickOnBrowseButton()
        val userName = sideCategory.getUserName().split(" ").get(0)
        val some = textFromFacebookButton
        assertThat(textFromFacebookButton, CoreMatchers.containsString(userName))

    }


}