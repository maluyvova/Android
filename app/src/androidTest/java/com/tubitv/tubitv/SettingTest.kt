package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 5/30/18.
 */
class SettingTest:LaunchAppWithFacebook() {

    @Test
    fun lockInLandscapeMode(){
        val homePage= HomeScreen()
        var portraitModeContOfMovies=homePage.getCountOfMovies(1)
        if (portraitModeContOfMovies>6){
            uiDevice.setOrientationLeft()
            portraitModeContOfMovies=homePage.getCountOfMovies(1)
        }
        val smallSettingsScreen=homePage.clickOnThreeDotsSetings()
        val settingsScreen=smallSettingsScreen.clickOnSettings()
        val homeScreen=settingsScreen.clickOnLockInLandscapeMode() //click on Landscape mode return user to homePage
        val landscapeModeCountOfMovies=homeScreen.getCountOfMovies(0)
        Assert.assertNotEquals("This test rotate device to landscape mode and checking if amount of movies for landscape mode different than portrait mode",portraitModeContOfMovies,landscapeModeCountOfMovies)

    }

    @Test
    fun uncheckLandscapeMode(){
        val homePage= HomeScreen()
        var portraitModeContOfMovies=homePage.getCountOfMovies(1)
        if (portraitModeContOfMovies>6){
            uiDevice.setOrientationLeft()
            portraitModeContOfMovies=homePage.getCountOfMovies(1)
        }
        lockInLandscapeMode()
        val smallSettingsScreen=homePage.clickOnThreeDotsSetings()
        val settingsScreen=smallSettingsScreen.clickOnSettings()
        settingsScreen.simpleClickOnLockInLandscapeMode()
        val portraitModeCountOfmoviesAfterRotationBack=homePage.getCountOfMovies(0)
        Assert.assertEquals("This test rotates device to landscape mode and then rotates device back to portrait mode checking if amount of movies after rotation back is the same",portraitModeContOfMovies,portraitModeCountOfmoviesAfterRotationBack)
    }
    @Test
    fun checkLandscapeModeAfterKillingApp(){
        lockInLandscapeMode()
        val homePage= HomeScreen()
        val beforeKill=homePage.getCountOfMovies(0)
        killApp()
        launchApp(appPackage,false)
        val afterKill=homePage.getCountOfMovies(0)
        Assert.assertEquals(beforeKill,afterKill)
    }
    @Test
    fun privacy(){
        val originalText="Escape the claws of subscription fees! Tubi TV, the free Internet TV network," +
                " is working on your behalf to unlock Hollywood so entertainment is free, without the burden of credit cards. Check out the largest collection of premium and unique movies and TV shows. For free, forever, since advertisers pay so you never have to.\n" +
                "\n" +
                " Updated weekly; find out more at tubitv.com.\n" +
                "\n"
        val homePage= HomeScreen()
       val settingMenu= homePage.clickOnThreeDotsSetings()
       val privatePolicyScreen= settingMenu.clickOnAbout()
       val text= privatePolicyScreen.textOfPriVatePolicy
       Assert.assertEquals(originalText,text)
    }







}