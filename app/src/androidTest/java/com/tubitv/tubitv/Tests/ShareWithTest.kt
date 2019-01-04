package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Test

/**
 * Created by vburian on 5/14/18.
 */
class ShareWithTest : LaunchAppWithFacebook() {


    @Test
    fun movieShareWithFacebook() {
        val homePage = HomeScreen(true)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val shareScreen = movieDatailScreen.clickOnShareButton()
        val facebookPage = shareScreen.clickOnFacebookShareIcon()
        facebookPage.clickOnFacebookPostButton()
    }


}