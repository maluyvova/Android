package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.GotIt
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import com.tubitv.tubitv.Screens.SerialsScreen
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/15/18.
 */
class SerialsTest:LaunchAppWithFacebook(){
    val textOfEpisodesForselectSerialWithFewSeasons = mutableListOf<String>()
    val episodesForScrollToTheSide=mutableListOf<String>()




    fun selectSerialWithFewSeasons(){
        val numbersOfTitles=MoviesByCategoryScreen().getCountOfTitles()
            val randomNumber= Random().nextInt(numbersOfTitles)
            val title =MoviesByCategoryScreen().gotkRandomTite(randomNumber) //randomNumber
            title.click()
            if(GotIt().gotitButton.waitForExists(globalTimeout))
                GotIt().clickOnGotIt()
        SerialsScreen().scrlbleScreen.scrollToEnd(2)
        val seasonPicker=SerialsScreen().seasonPickers
        val season2=SerialsScreen().sseason2
            if (seasonPicker.waitForExists(globalTimeout)){
                seasonPicker.click()
                if(season2.waitForExists(globalTimeout)){
                    uiDevice.pressBack()
                    val titleForFirstSeason=SerialsScreen().getTextOfEpisode()
                    textOfEpisodesForselectSerialWithFewSeasons.add(0,titleForFirstSeason)
                    seasonPicker.click()
                    season2.click()
                    val titleForSecondSeason=SerialsScreen().getTextOfEpisode()
                    textOfEpisodesForselectSerialWithFewSeasons.add(1,titleForSecondSeason)
                }
                else {
                    uiDevice.pressBack()
                    uiDevice.pressBack()
                    selectSerialWithFewSeasons()
                } }}

    fun scrollToTheSide(){
        var i =1;
        val numbersOfTitles=MoviesByCategoryScreen().getCountOfTitles()
        val randomNumber= Random().nextInt(numbersOfTitles)
        val title =MoviesByCategoryScreen().gotkRandomTite(randomNumber) //randomNumber
        title.click()
        if(GotIt().gotitButton.waitForExists(globalTimeout))
            GotIt().clickOnGotIt()
        SerialsScreen().scrlbleScreen.scrollToEnd(2)
        val seasonPicker=SerialsScreen().seasonPickers
        val season2=SerialsScreen().sseason2
        val textFoSeason=SerialsScreen().textofSeason
        if (seasonPicker.waitForExists(globalTimeout)){
            seasonPicker.click()
            if(season2.waitForExists(globalTimeout)){
                uiDevice.pressBack()
                episodesForScrollToTheSide.add(textFoSeason.text)
                val serialScreen=SerialsScreen()
               while(!textFoSeason.text.contains("Season 2")) {
                serialScreen.scrollEpisdoesList(i)
                i++}
                episodesForScrollToTheSide.add(textFoSeason.text)
            }
            else {
                uiDevice.pressBack()
                uiDevice.pressBack()
                scrollToTheSide()
            } }

    }



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
        homePage.waitForExistsCategoryText("Queue")
        val titletext= homeScreenWithQueue.textFromFirstTitleInQueue
        val gotit= homePage.clickOnTitle(0)
        val moviedatailScreen=gotit.clickOnGotIt()
        val homeScreen2=moviedatailScreen.clickOnRemoveFromQueue()
        homeScreen2.waitForDisapearCategoryText("Queue")
        val text=homeScreen2.getTextOfCategory(1).text
       Assert.assertNotEquals("The first text of category is Queue",text,"Queue")
    }

    @Test
    fun serialSelectNextEpisode(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory("Most Popular TV Shows")
        val serials =HomeScreen.Serials()
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val serialScreen=SerialsScreen()
        serialScreen.selectRundomSerialTitle()
        serialScreen.scrollScreen(4)
        serialScreen.scrollEpisdoesList(4)
        val firstTextOfNumber=serialScreen.getTextOfEpisode()
        serialScreen.clickOnPlayInTheButtonNextEpisode()
        Thread.sleep(45000)
        killApp()
        launchApp(appPackage,false)
        val tittle=homePage.titleInContinueWatching
        HomeScreen.HomeScreenWithContinueWatching().removeFromHistory()
        val history=HomeScreen.History().clickOnRemoveFromHisory()
        Assert.assertEquals("This test is selecting TVshow, then selects another episode,then starts playback,theb killing app,and then checking if correct episode in the History",firstTextOfNumber.substring(1,5),tittle.substring(1,5))
    }

    @Test
    fun selectSeasonOfSerials(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory("Most Popular TV Shows")
        val serials =HomeScreen.Serials()
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        selectSerialWithFewSeasons()
       Assert.assertNotEquals("This test is selecting season2 from drop down ",textOfEpisodesForselectSerialWithFewSeasons.get(0),textOfEpisodesForselectSerialWithFewSeasons.get(1))
    }
    @Test
    fun checkIfTheTextInSeasonPickerIsChangedAfterScrollingSerrialsToTheSide(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory("Most Popular TV Shows")
        val serials =HomeScreen.Serials()
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        scrollToTheSide()
        Assert.assertNotEquals(episodesForScrollToTheSide.get(0),episodesForScrollToTheSide.get(1))
    }

}



