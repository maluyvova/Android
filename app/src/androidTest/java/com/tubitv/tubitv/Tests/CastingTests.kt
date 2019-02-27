package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen

/**
 * Created by vburian on 5/14/18.
 */
class CastingTests : LaunchAppWithFacebook() {

    // @Test
    fun castingPlaybackBar() {
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnCustButton("SHIELD2")
        val titleInHomeScreen = homeScreen.getTextOfTitle()
        val gotitPage = homeScreen.clickOnTitle(0)
        val datailPage = gotitPage.clickOnGotIt()
        datailPage.clickOnPlay()
    }
    //In Casting Screen finish add all elements and finish with casting tests


}