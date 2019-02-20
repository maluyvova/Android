package com.tubitv.tubitv.Tests

import android.support.test.uiautomator.UiObjectNotFoundException
import com.tubitv.tubitv.Enomus.Categories
import com.tubitv.tubitv.Enomus.DirectionOfScrolling
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/15/18.
 */
class SerialsTest : LaunchAppWithFacebook() {
    val textOfEpisodesForselectSerialWithFewSeasons = mutableListOf<String>()
    val episodesForScrollToTheSide = mutableListOf<String>()


    fun selectSerialWithFewSeasons() {
        val numbersOfTitles = MoviesByCategoryScreen(Categories.TV_COMEDIES.value).getCountOfTitles()
        val randomNumber = Random().nextInt(numbersOfTitles)
        val title = MoviesByCategoryScreen(Categories.TV_COMEDIES.value).gotkRandomTite(randomNumber) //randomNumber
        title.click()
        if (GotIt().gotitButton.waitForExists(globalTimeout))
            GotIt().clickOnGotIt()
        SerialsScreen().scrlbleScreen.scrollToEnd(2)
        val seasonPicker = SerialsScreen().seasonPickers
        val season2 = SerialsScreen().sseason2
        if (seasonPicker.waitForExists(globalTimeout)) {
            seasonPicker.click()
            if (season2.waitForExists(globalTimeout)) {
                uiDevice.pressBack()
                val titleForFirstSeason = SerialsScreen().getTextOfEpisode()
                textOfEpisodesForselectSerialWithFewSeasons.add(0, titleForFirstSeason)
                seasonPicker.click()
                season2.click()
                val titleForSecondSeason = SerialsScreen().getTextOfEpisode()
                textOfEpisodesForselectSerialWithFewSeasons.add(1, titleForSecondSeason)
            } else {
                uiDevice.pressBack()
                uiDevice.pressBack()
                selectSerialWithFewSeasons()
            }
        }
    }

    fun scrollEpisodesToTheSide(category: String) {
        var i = 1;
        val numbersOfTitles = MoviesByCategoryScreen(category).getCountOfTitles()
        val randomNumber = Random().nextInt(numbersOfTitles)
        val title = MoviesByCategoryScreen(category).gotkRandomTite(randomNumber) //randomNumber
        title.click()
        if (GotIt().gotitButton.waitForExists(globalTimeout))
            GotIt().clickOnGotIt()
        SerialsScreen().scrlbleScreen.scrollToEnd(2)
        val seasonPicker = SerialsScreen().seasonPickers
        val season2 = SerialsScreen().sseason2
        val textFoSeason = SerialsScreen().textofSeason
        if (seasonPicker.waitForExists(globalTimeout)) {
            seasonPicker.click()
            if (season2.waitForExists(globalTimeout)) {
                uiDevice.pressBack()
                episodesForScrollToTheSide.add(textFoSeason.text)
                val serialScreen = SerialsScreen()
                while (!textFoSeason.text.contains("Season 2")) {
                    serialScreen.scrollEpisdoesList(i)
                    i++
                }
                episodesForScrollToTheSide.add(textFoSeason.text)
            } else {
                uiDevice.pressBack()
                uiDevice.pressBack()
                scrollEpisodesToTheSide(category)
            }
        }

    }


    @Test
    fun longClickOnSerial() {
        var mark = true
        val homePage = HomeScreen(true)
        homePage.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        val sideCategory = homePage.clickOnBrowseButton()
        sideCategory.scrollToSpecificCategory(Categories.TV_COMEDIES.value)
        val title = MoviesByCategoryScreen(Categories.TV_COMEDIES.value).gotkRandomTite(Random().nextInt(5))
        title.dragTo(title, 3)
        val queScreen = AddToQueue(true)
        queScreen.clickAddToQueueAfterLongClickWithoutReturn()
        BaseScreen().navigateBackToHomeScreen()
        val sideCategory2 = homePage.clickOnBrowseButton()
        val subCategoryScreen = sideCategory2.scrollToSpecificCategory(Categories.QUEUE.value)
        val gotit = subCategoryScreen.clickOnTitleForQueue(0)
        val moviedatailScreen = gotit.clickOnGotIt()
        moviedatailScreen.simpleClickOnAddToQueue()
        BaseScreen().navigateBackToHomeScreen()
        val sideCategory3 = homePage.clickOnBrowseButton()
        try {
            sideCategory3.scrollToSpecificCategory(Categories.QUEUE.value)
        } catch (e: TestException) {
            junit.framework.Assert.assertEquals("Queue category still on home page after,click add and then remove from queue", "most popular", "most popular")
            mark = false
        }
        if (mark) {
            assertEquals("Title still in Queue but test should remove it", 1, 2)
        }
    }

    @Test
    fun serialSelectNextEpisode() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        sideCategory.scrollToSpecificCategory(Categories.TV_COMEDIES.value)
        val serialScreen = SerialsScreen()
        serialScreen.selectRundomSerialTitle(Categories.TV_COMEDIES.value)
        serialScreen.scrollScreen(4)
        serialScreen.scrollEpisdoesList(4)
        val firstTextOfNumber = serialScreen.getTextOfEpisode()
        serialScreen.clickOnPlayInTheButtonNextEpisode()
        Thread.sleep(45000)
        killApp()
        launchApp(appPackage, false)
        val homePage2 = HomeScreen(true)
        val sideCategory2 = homePage2.clickOnBrowseButton()
        val subCategoryScreen = sideCategory2.scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
        subCategoryScreen.clickOnTitleForQueueNoGotIt(0)
        val serialScreen2 = SerialsScreen()
        val title = serialScreen2.getNumberOfEpisode()
        uiDevice.pressBack()
        val smallWindow = subCategoryScreen.longClickOnTitle(0)
        smallWindow.clickRemoveFromHistory()
        Assert.assertEquals("This test is selecting TVshow, then selects another episode,then starts playback,theb killing app,and then checking if correct episode in the History", firstTextOfNumber.substring(1, 5), title.substring(1, 5))
    }

    @Test
    fun selectSeasonOfSerials() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCategoryScreen = sideCategory.scrollToSpecificCategory(Categories.TV_COMEDIES.value)
        selectSerialWithFewSeasons()
        Assert.assertNotEquals("This test is selecting season2 from drop down ", textOfEpisodesForselectSerialWithFewSeasons.get(0), textOfEpisodesForselectSerialWithFewSeasons.get(1))
    }

    @Test
    fun checkIfTheTextInSeasonPickerIsChangedAfterScrollingSerrialsToTheSide() {
        val homePage = HomeScreen(true)
        homePage.scrollToSpecificCategory(Categories.TV_COMEDIES.value, DirectionOfScrolling.DOWN)
        val serials = Serials(Categories.TV_COMEDIES.value)
        val moviesByCategoryScreen = serials.clickOnSerialCategory()
        scrollEpisodesToTheSide(Categories.TV_COMEDIES.value)
        Assert.assertNotEquals(episodesForScrollToTheSide.get(0), episodesForScrollToTheSide.get(1))
    }

    //@Test
    fun selectPlaybackNavigateBackToHome5TimesSerials() {
        var firsTime = true
        fun selectSerial() {
            val homePage = HomeScreen(true)
            val sideCategoryScreen = homePage.clickOnBrowseButton()
            val subcategory = sideCategoryScreen.scrollToSpecificCategory(Categories.TV_COMEDIES.value)
            subcategory.clickOnTitle(0)
            if (firsTime) {
                GotIt().clickOnGotIt()
            }
            val playBack = MovieDatailScreen().clickOnPlay()
            playBack.waitUntilAdsfinishes()
            BaseScreen().navigateBackToHomeScreen()
            firsTime = false
        }
        for (i in 1..5) {
            selectSerial()
        }
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(0)
                .clickRemoveFromHistory()

    }

    @Test
    fun playSerialNavigateBack5TimesForGuest() {

        val homeScreen = HomeScreen(true)
        val gotItScreen = homeScreen
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.REALITY_TV.value)
                .clickOnTitleForQueue(0)

        val serialDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = serialDatailScreen.clickOnPlay()
        for (i in 1..5) {
            uiDevice.pressBack()
            serialDatailScreen.clickOnPlay()
                    .waitUntilAdsfinishes()
        }
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitles()
    }

    @Test
    fun playSerialNavigateBack5TimesForRegisterUser() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        playSerialNavigateBack5TimesForGuest()
    }

    @Test
    fun clickOnPauseAndBackButtonSerial() {
        val homePage = HomeScreen(true)
        val sideCategoryScreen = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.REALITY_TV.value)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
                .clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
                .clickPlay()
                .clickOnNativeBackForSerial()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitles()
    }

    @Test
    fun clickOnBackButtonWhileSerialIsPlaying() {
        val homePage = HomeScreen(true)
        val sideCategoryScreen = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.TV_COMEDIES.value)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
                .clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
                .clickOnNativeBackForSerial()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitles()
    }


}



