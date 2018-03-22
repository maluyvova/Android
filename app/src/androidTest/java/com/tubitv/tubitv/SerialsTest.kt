package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/15/18.
 */
class SerialsTest:BaseTest(){




    @Test
    fun longClickOnSerial(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory("Most Popular TV Shows")
        val serials =HomeScreen.Serials()
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val numbersOfTitles=moviesByCategoryScreen.getCountOfTitles()
        val randomNumber= Random().nextInt(numbersOfTitles)
        val title=moviesByCategoryScreen.gotkRandomTite(randomNumber)
        title.dragTo(title,3)
        val queScreen=HomeScreen.AddToQueue()
        queScreen.clickAddToQueueAfterLongClickWithoutReturn()
        uiDevice.pressBack()

        val homeScreenWithQueue=HomeScreen.QueueScreen()
        val titletext= homeScreenWithQueue.textFromFirstTitleInQueue
        Thread.sleep(2000)
        val gotit= homePage.clickOnTitle()
        val moviedatailScreen=gotit.clickOnGotIt()
        val homeScreen2=moviedatailScreen.clickOnRemoveFromQueue()
        homeScreen2.waitForExistsFirstCategoryText("Most Popular")
        val text=homeScreen2.getTextOfCategory().text
       Assert.assertNotEquals("The first text of category is Queue",text,"Queue")
    }

}