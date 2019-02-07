package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Networking.ServerManager
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.LaunchScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import com.tubitv.tubitv.SimpleLaunchApp
import junit.framework.Assert
import org.junit.Test

/**
 * Created by vburian on 6/4/18.
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UiPlussAPITests : SimpleLaunchApp() {


    @Test
    fun addTitlesToQueueAndVerifyWithUi() {
        ServerManager().addToQueueTest()
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.sendTextToEmailField("testingnetwork@gmail.com")
        signInScreen.sendTextToPasswordField("tubitv")
        signInScreen.clickOnSignInButton(true)
        SimpleLaunchApp().castings()
        val homescreen = HomeScreen(true)
        homescreen.waitForExistsCategoryText("Queue")
        if (homescreen.textCategory().equals("Queue")) {

            homescreen.clickOnThreeDots()
            val countOftitles = MoviesByCategoryScreen("Queue").getCountOfTitles()
            Assert.assertTrue(countOftitles > 6)
        } else throw TestException("Queue didn't show up")
    }


    @Test
    fun deleteFromQueue() {
        val serverManager = ServerManager()
        serverManager.deleteFromQueueTest()
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.sendTextToEmailField("testingnetwork@gmail.com")
        signInScreen.sendTextToPasswordField("tubitv")
        signInScreen.clickOnSignInButton(true)
        SimpleLaunchApp().castings()
        val homescreen = HomeScreen(true)
        homescreen.waitForDisapearCategoryText("Queue")
        val textOfCategory = homescreen.textCategory()
        Assert.assertEquals("Queue category still on home page after remove from queue with API", textOfCategory.toLowerCase(), "most popular")
    }


}





