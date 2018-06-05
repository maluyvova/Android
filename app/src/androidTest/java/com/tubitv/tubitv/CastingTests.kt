package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Test

/**
 * Created by vburian on 5/14/18.
 */
class CastingTests:LaunchAppWithFacebook() {

   // @Test
    fun castingPlaybackBar(){
    val homeScreen=HomeScreen()
        homeScreen.clickOnCustButton("SHIELD2")
        val titleInHomeScreen = homeScreen.title
        val gotitPage = homeScreen.clickOnTitle(0)
        val datailPage = gotitPage.clickOnGotIt()
        datailPage.clickOnPlay()
    }
    //In Casting Screen finish add all elements and finish with casting tests


}