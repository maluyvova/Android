package com.tubitv.tubitv

import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Networking.Mockt
import com.tubitv.tubitv.Screens.FacebookAppScreen
import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 5/14/18.
 */
class ShareWithTest:LaunchAppWithFacebook() {




    @Test
    fun movieShareWithFacebook(){
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle()
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val shareScreen=movieDatailScreen.clickOnShareButton()
        val facebookPage=shareScreen.clickOnFacebookShareIcon()
        facebookPage.clickOnFacebookPostButton()
    }


}