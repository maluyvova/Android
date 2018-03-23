package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Test

/**
 * Created by vburian on 3/23/18.
 */
class LogInTest:BaseTest(){

  @Test
    fun logIn(){
     val homepage=HomeScreen()
      val smallWindowWithSetings=homepage.clickOnThreeDotsSetings()
      val settingsScreen=smallWindowWithSetings.clickOnSettings()
      val launchScreen=settingsScreen.clickOnSignOut()
      val signInScreen=launchScreen.clickOnSignIn()
      signInScreen.sendTextToEmailField("tubitv@tubitv.tubitv")
      signInScreen.sendTextToPasswordField("tubitv")
      signInScreen.clickOnSignInButton()

  }







}