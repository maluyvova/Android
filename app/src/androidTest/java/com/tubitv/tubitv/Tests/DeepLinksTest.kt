package com.tubitv.tubitv.Tests

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.*
import com.tubitv.tubitv.Enomus.Categories
import com.tubitv.tubitv.Enomus.DeepLinks
import com.tubitv.tubitv.Enomus.TypeOfContent
import com.tubitv.tubitv.Screens.*
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/12/18.
 */
class DeepLinksTest : BaseTest() {
    //val ForDrama = "tubitv://media-browse?categoryId=1291\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val ForMovieOldBoy = "http://tubitv.com/movies/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val LinkForMovieOldBoy = "https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val MovieIgor = "https://link.tubi.tv/t1XXhN28RR"
    val SerialDeadLikeMe = "https://link.tubi.tv/kCXwWs68RR"
    //val TheHollow = "http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val OldBoyUTM = "https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val WildAtHeartUTMSerial = "http://tubitv.com/tv-shows/203?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val McLeodsDaughtersUTMSerial = "https://link.tubi.tv/vInFMDv8hT"
    //val McLeodsDaughters = "http://tubitv.com/series/337?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val DogTheBountyHunterUTMSErial = "https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val deeplinkToCategoryUTMAnime = " tubitv://media-browse?categoryId=anime\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val deeplinkToCategoryUTMAnime2 = "https://tubitv.com/category/anime?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToPlayBackUTM = "tubitv://media-playback?contentId=294111\\&contentType=video\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "
    //val anotherDeeplinkWithUTM = "http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val deeplinkForMovieWomanThouArtLoosed = "http://tubitv.com/movies/456403?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val deepLinkForPlayback="tubitv://media-playback?contentId=294111\\&contentType=movie\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "


    fun ifNotRegistred() {
        launchApp(appPackage, false)
        if (uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_login_adapter_text_view")).waitForExists(globalTimeout)) {
            SignInTest().signInWithCorrectEmailAndPassword()
        }
        killApp()

    }

    fun ifFirstTime() {
        var app = "Dev Tubi TV"
        if (appPackage.equals("com.tubitv.fire") || appPackage.equals("com.tubitv")) {
            app = "Tubi TV"
        }
        if (uiDevice.findObject(UiSelector().resourceId("android:id/title")).waitForExists(shortWaitTime)) {
            uiDevice.findObject(UiSelector().textContains(app)).click()
            if (uiDevice.findObject(UiSelector().resourceId("android:id/button_once")).exists())
                uiDevice.findObject(UiSelector().resourceId("android:id/button_once")).click()
            else uiDevice.click(200, 200)
        } else if (uiDevice.findObject(UiSelector().textContains(app)).exists()) {
            uiDevice.findObject(UiSelector().textContains(app)).click()
            if (uiDevice.findObject(UiSelector().textContains(app)).exists()) {
                uiDevice.findObject(UiSelector().textContains(app)).click()
                return
            }
            if (uiDevice.findObject(UiSelector().resourceId("android:id/button1")).waitForExists(shortWaitTime)) {
                uiDevice.findObject(UiSelector().resourceId("android:id/button1")).click()
            }
        }
    }

    @Test
    fun deepLinkForAction() {
        getInstrum()
        clearAppData()
        // ifNotRegistred()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.ACTION_CATEGORY.value + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen("Action").categoryText, "Action")
    }

    @Test
    fun deepLinkForSerial() {
        val expectedTitleOfSerial = "Dog the Bounty Hunter"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_SERIAL.value + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected serial $expectedTitleOfSerial didn't show up probably expired  ", MovieDatailScreen().titleName, "$expectedTitleOfSerial")
    }

    @Test
    fun deepLinkForComedy() {
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_COMEDY.value + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category: ${Categories.COMEDY.value} didn't show up after deep link", MoviesByCategoryScreen("Comedy").categoryText, Categories.COMEDY.value)
    }

    //@Test
    fun deepLinkForDrama() {
        val expectedCategory = "Drama"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value +DeepLinks.FOR_DRAMA.value + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category: $expectedCategory didn't show up after deep link", MoviesByCategoryScreen("Drama").categoryText, "$expectedCategory")
    }

    @Test
    fun deepLinkForMovieOldBoy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_MOVIE_OLDBOY.value + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link ", MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deepLinkForVideoOldBoy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.MOVIE_OLDBOY_UTM.value + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link ", MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deepLinkForMovieIgor() {
        val expectedMovie = "Igor"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_MOVIE_IGOR.value)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }


    @Test
    fun deepLinkForMovieWomanThouArtLoosed() {
        val expectedMovie = "Woman Thou Art Loosed"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_MOVIE_WOMAN_THOU_AER_LOOSED_UTM.value)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deeplinkWithUTMtheHollow() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.MOVIE_THE_HOLLOW.value)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deeplinkWithUTMOldboy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.MOVIE_OLDBOY_UTM.value)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deeplinkWithUTMWildAtHeartSerial() {
        val expectedMovie = "Wild at Heart"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.WILD_AT_HEART_UTM_SERIAL.value)
        ifFirstTime()
        HomeScreen(true)
    }


    @Test
    fun deeplinkWithUTMMcLeodsDaughtersSerial() {
        val expectedMovie = "McLeod's Daughters"
        getInstrum()
        clearAppData()
        launchApp(appPackage, false)
        killApp()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.SERIAL_MC_LEODS_DAUGHTERS_UTM.value)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheBegining()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deeplinkWithMcLeodsDaughtersSerial() {
        val expectedMovie = "McLeod's Daughters"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.SERIAL_MC_LEODS_DAUGHTERS.value)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :" + "DeepLinks.REQUIREMENTS.value", MovieDatailScreen().titleName, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheBegining()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deeplinkWithUTMDogTheBountyHunterSerial() {
        val expectedMovie = "Dog the Bounty Hunter"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.SERIAL_DOG_THE_BOUNTY_HUNTER_UTM.value)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :" + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheEnd()
                .seekToTheBegining()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }


    @Test
    fun deepLinkForAnimeUTM() {
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_CATEGORY_ANIME_UTM.value + appPackage)
        ifFirstTime()
        val category = MoviesByCategoryScreen("Anime").categoryText
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen("Anime").categoryText, "Anime")
    }

    @Test
    fun deepLinkForAnime2UTM() {
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_CATEGORY_ANIME_UTM2.value + appPackage)
        ifFirstTime()
        val category = MoviesByCategoryScreen("Anime").categoryText
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen("Anime").categoryText, "Anime")
    }

    @Test
    fun deepLinkUMLTheHollow() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.ANOTHER_DEEPLINK_WITH_UTM.value)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deepLinkUMLTheHollowRegisterUser() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        clearAppData()
        launchApp(appPackage, false)
        val homeScreen = HomeScreen(true)
        homeScreen.dismissCasting()
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        killApp()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.ANOTHER_DEEPLINK_WITH_UTM.value)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deepLinkUMWomanThouArtLoosed() {
        val expectedMovie = "Woman Thou Art Loosed"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value +DeepLinks.FOR_MOVIE_WOMAN_THOU_ART_LOOSED.value)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements : " + DeepLinks.REQUIREMENTS.value, MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }

    @Test
    fun deepLinkUMWomanThouArtLoosedForRegisterUser() {
        val expectedMovie = "Woman Thou Art Loosed"
        getInstrum()
        killApp()
        clearAppData()
        launchApp(appPackage, false)
        val homeScreen = HomeScreen(true)
        homeScreen.dismissCasting()
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        killApp()
        uiDevice.executeShellCommand(DeepLinks.COMAND_FOR_DEEPLINK.value + DeepLinks.FOR_MOVIE_WOMAN_THOU_ART_LOOSED.value)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :${DeepLinks.REQUIREMENTS.value}", MovieDatailScreen().titleName, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .removeAllTitlesFromHistory()
    }


    /* @Test
     fun playbackDeepLink(){
         getInstrum()
         ifNotRegistred()
       uiDevice.executeShellCommand(comandForDeepLink+deepLinkForPlayback+ appPackage)
         ifFirstTime()
        val title= PlayBackScreen().waitForPlayBack()
         Assert.assertEquals(title,"Oldboy")
     }*/


}