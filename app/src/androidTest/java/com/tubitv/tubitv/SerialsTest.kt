package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.GotIt
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.SerialsScreen
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/15/18.
 */
class SerialsTest:LaunchAppWithFacebook(){




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
        homePage.waitForExistsFirstCategoryText("Queue")
        val titletext= homeScreenWithQueue.textFromFirstTitleInQueue
        val gotit= homePage.clickOnTitle()
        val moviedatailScreen=gotit.clickOnGotIt()
        val homeScreen2=moviedatailScreen.clickOnRemoveFromQueue()
        homeScreen2.waitForExistsFirstCategoryText("Most Popular")
        val text=homeScreen2.getTextOfCategory().text
       Assert.assertNotEquals("The first text of category is Queue",text,"Queue")
    }

    @Test
    fun serialSelectNextEpisode(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory("Most Popular TV Shows")
        val serials =HomeScreen.Serials()
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val numbersOfTitles=moviesByCategoryScreen.getCountOfTitles()
        val randomNumber= Random().nextInt(numbersOfTitles)
        val title =moviesByCategoryScreen.gotkRandomTite(randomNumber)
        title.click()
        GotIt().clickOnGotIt()
        val serialScreen=SerialsScreen()
        serialScreen.scrollScreen(4)
        serialScreen.scrollEpisdoesList(4)
        val firstTextOfNumber=serialScreen.getTextOfEpisode()
        serialScreen.clickOnPlayInTheButtonNextEpisode()
        Thread.sleep(45000)
        killApp()
        launchApp()
        val tittle=homePage.titleInContinueWatching
        HomeScreen.HomeScreenWithContinueWatching().removeFromHistory()
        val history=HomeScreen.History().clickOnRemoveFromHisory()
        Assert.assertEquals("This test is selecting TVshow, then selects another episode,then starts playback,theb killing app,and then checking if correct episode in the History",firstTextOfNumber.substring(1,5),tittle.substring(1,5))
    }

}