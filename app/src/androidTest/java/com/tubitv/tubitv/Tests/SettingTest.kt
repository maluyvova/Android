package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Helpers.NativeCamera
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.SendMessageView
import com.tubitv.tubitv.deviceName
import junit.framework.Assert.assertFalse
import org.junit.Assert
import org.junit.Test
import java.lang.Thread.sleep

/**
 * Created by vburian on 5/30/18.
 */
class SettingTest : LaunchAppWithFacebook() {
    private val longText = "This is automation test with long message,PABLO NERUDA:\n" +
            "Tonight I Can Write \n" +
            "Tonight I can write the saddest lines. \n" +
            "Write, for example, \"The night is starry \n" +
            "and the stars are blue and shiver in the distance.\" \n" +
            "The night wind revolves in the sky and sings. \n" +
            "Tonight I can write the saddest lines. \n" +
            "I loved her, and sometimes she loved me too. \n" +
            "Through nights like this one I held her in my arms. \n" +
            "I kissed her again and again under the endless sky. \n" +
            "She loved me, sometimes I loved her too. \n" +
            "How could one not have loved her great still eyes. \n" +
            "Tonight I can write the saddest lines. \n" +
            "To think that I do not have her. To feel that I have lost her. \n" +
            "To hear the immense night, still more immense without her. \n" +
            "And the verse falls to the soul like dew to the pasture. \n" +
            "What does it matter that my love could not keep her. \n" +
            "The night is starry and she is not with me. \n" +
            "This is all. In the distance someone is singing. In the distance. \n" +
            "My soul is not satisfied that it has lost her. \n" +
            "My sight tries to find her as though to bring her closer. \n" +
            "My heart looks for her, and she is not with me. \n" +
            "The same night whitening the same trees. \n" +
            "We, of that time, are no longer the same. \n" +
            "I no longer love her, that's certain, but how I loved her. \n" +
            "My voice tried to find the wind to touch her hearing.   "

    @Test
    fun lockInLandscapeMode() {
        val homePage = HomeScreen(true)
        homePage.scrolDownLittleBit()
        var portraitModeContOfMovies = homePage.getCountOfMovies(1)
        if (portraitModeContOfMovies > 6) {
            uiDevice.setOrientationLeft()
            portraitModeContOfMovies = homePage.getCountOfMovies(1)
        }

        val settingsScreen = homePage.clickOnAccountButton()
        settingsScreen.clickOnLockInLandscapeMode()
        uiDevice.pressBack()
        val homeScreen = HomeScreen(true)
        val landscapeModeCountOfMovies = homeScreen.getCountOfMovies(1)
        Assert.assertNotEquals("This test rotates device to landscape mode and checking if amount of movies for landscape mode different than portrait mode", portraitModeContOfMovies, landscapeModeCountOfMovies)
    }

    @Test
    fun uncheckLandscapeMode() {
        val homePage = HomeScreen(true)
        var portraitModeContOfMovies = homePage.getCountOfMovies(1)
        if (portraitModeContOfMovies > 6) {
            uiDevice.setOrientationLeft()
            portraitModeContOfMovies = homePage.getCountOfMovies(1)
        }
        lockInLandscapeMode()
        val settingsScreen = homePage.clickOnAccountButton()
        settingsScreen.clickOnLockInLandscapeMode()
        uiDevice.pressBack()
        val portraitModeCountOfmoviesAfterRotationBack = homePage.getCountOfMovies(0)
        Assert.assertEquals("This test rotates device to landscape mode and then rotates device back to portrait mode checking if amount of movies after rotation back is the same", portraitModeContOfMovies, portraitModeCountOfmoviesAfterRotationBack)
    }

//    @Test
//    fun checkLandscapeModeAfterKillingApp() {
//        lockInLandscapeMode()
//        val homePage = HomeScreen(true)
//        val beforeKill = homePage.getCountOfMovies(0)
//        killApp()
//        launchApp(appPackage, false)
//        val afterKill = homePage.getCountOfMovies(0)
//        Assert.assertEquals(beforeKill, afterKill)
//    }

    @Test
    fun privacy() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        settingMenu.clickOnAbout()
                .clickOnPrivacyPolicy()
    }

    @Test
    fun termOfUse() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        settingMenu.clickOnAbout()
                .clickOnTermsOfUse()
                .scrollToTheButton()
    }

    @Test
    fun checkATextOnSupportWebView() {
        val homePage = HomeScreen(true)
        val accountScreen = homePage.clickOnAccountButton()
        val helpCenterScreen = accountScreen.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        helpCenterScreen.clcikOnFirstLinkOnGeneral() //check inside method it's trying to find elements by Text, so if text doesn't match it will trow exception
    }

    @Test
    fun navigateBackToHomeScreenFromSendMesageScreen() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        val helpCenterScreen = settingMenu.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        val messageNotSent=helpCenterScreen.clickOnSendMessage()
                .provideMessageToDescription("Hello Tubi,Android automation tests, don't pay attention")
                .clickOnBackButton()
        messageNotSent as SendMessageView.MessageNotSentDialog
        messageNotSent.clickOnDelete()
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true)
    }

    @Test
    fun sendLongTextToHelpCenter() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        val helpCenterScreen = settingMenu.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        helpCenterScreen.clickOnSendMessage()
                .provideMessageToDescription(longText)
                .clickOnSendButton()
                .checkIfMessageWasSent(false)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true)
    }

    @Test
    fun sendJustOneCharToHelpCenter() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        val helpCenterScreen = settingMenu.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        helpCenterScreen.clickOnSendMessage()
                .provideMessageToDescription("a")
                .clickOnSendButton()
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true)
    }


    @Test
    fun provideMessageAndClearItToHelpCenter() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        val helpCenterScreen = settingMenu.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        val isButtonDisabled = helpCenterScreen.clickOnSendMessage()
                .provideMessageToDescription("Hello just want to type something")
                .clearMessageFromDescription()
                .checkIfSendButtonDisabled()
        assertFalse("Send button still enabled even when text field is cleared", isButtonDisabled)
    }

    @Test
    fun sendMessageWithAtachedPhotoFromCamera() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        val helpCenterScreen = settingMenu.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        helpCenterScreen.clickOnSendMessage()
                .provideMessageToDescription("Hello just want to type something, Test automation adnroid")
                .clickOnAttachButton()
                .addFile()
    SendMessageView().clickOnSendButton()
            .checkIfMessageWasSent(true)
    }

    @Test
    fun sendMessageWithDeletedPhotoFromCamera() {
        val homePage = HomeScreen(true)
        val settingMenu = homePage.clickOnAccountButton()
        val helpCenterScreen = settingMenu.clickOnHelpCenter()
        helpCenterScreen.clickOnIcon(1)
        helpCenterScreen.clickOnSendMessage()
                .provideMessageToDescription("Hello just want to type something, Test automation adnroid")
                .clickOnAttachButton()

        val attachmentStillExists = NativeCamera().makeAPhoto(deviceName)
                .clickOnDeletePhoto()
                .checkIfAttachmentDeleted()

        assertFalse("I clicked on delete attachment, but attachment still exists ", attachmentStillExists)

    }


}