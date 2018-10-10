package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.shortWaitTime
import junit.framework.Assert

/**
 * Created by vburian on 2/20/18.
 */
class MovieDatailScreen() : BaseScreen() {

    private val titleText = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/textView_title"))
    private val addToQueue = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/imageView_add_from_queue"))
    private val youMightAlsoLike = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_content_recycler_ll"))
    private val playButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/imageView_play"))
    private val scrollbleScreen = UiScrollable(UiSelector().resourceId(appPackage + ":id/empty_holder"))
    private val titleFromYouMightAlsoLike = findObjectById(appPackage + ":id/view_home_content_iv")
    private val shareWithButton = findObjectById(appPackage + ":id/imageView_share")
    private val captionicon = findObjectById(appPackage + ":id/imageView_caption")
    private val huluIcon = findObjectById(appPackage + ":id/vaudTextView_present_hulu")
    private val categoryNameOnTopBar = findObjectById(appPackage + ":id/nav_app_bar_main_title")
    private val signInPopUp = findObjectById(appPackage+":id/prompt_image_background")

            init {
                if(signInPopUp.waitForExists(shortWaitTime)){

                }
                Assert.assertTrue("Expected title text  is not displayed", titleText.waitForExists(globalTimeout))
                Assert.assertTrue("Expected button add to queue is not displayed", addToQueue.waitForExists(globalTimeout))
                Assert.assertTrue("Expected share button is not displayed", shareWithButton.waitForExists(globalTimeout))
            }

    public val titleDatailScreen get() = titleText.text //get text from the datail page
    public val scrollableScreen = this.scrollbleScreen

    public val youMightaAlsoLike = this.youMightAlsoLike

    fun clickOnAddToQueue(): HomeScreen {
        addToQueue.click()
        uiDevice.pressBack()
        return HomeScreen()
    }

    fun waitUntillSelected() {
        while (addToQueue.isSelected)
            break
    }

    fun dontSelectHuluTitle() {
        val i = 1;
        while (huluIcon.exists()) {
            uiDevice.pressBack()
            HomeScreen().clickOnTitle(i)
            i + 1
        }
    }

    fun clickOnRemoveFromQueue(): HomeScreen {
        addToQueue.click()
        return HomeScreen()
    }

    fun checkIfStillOnThisPage(): Boolean {
        val s = categoryNameOnTopBar.waitForExists(shortWaitTime)
        return s
    }

    fun simpleClickOnAddToQueue() {

        addToQueue.click()
    }

    fun simpleClickOnRemoveFromQueue(): HomeScreen {
        addToQueue.click()
        Thread.sleep(2000)
        uiDevice.pressBack()
        return HomeScreen()
    }

    fun clickOnPlay(): PlayBackScreen {
        playButton.click()
        return PlayBackScreen()
    }

    fun rightscrollScreenOnSide(rightScroll: Int) {
        var number: Int = 0
        while (rightScroll != number) {
            scrollbleScreen.setAsHorizontalList().scrollToEnd(1)
            number++
        }
    }

    fun leftscrllScreenOnSide(leftScroll: Int) {
        var number: Int = 0
        while (leftScroll != number) {
            scrollbleScreen.setAsHorizontalList().scrollToBeginning(1)
            number++
        }

    }

    fun clickOnShareButton(): ShareWithScreen {
        shareWithButton.click()
        return ShareWithScreen()
    }

    fun selectTitleFromMightAlsoLike(): MovieDatailScreen {
        scrollbleScreen.setAsVerticalList().scrollToEnd(2)
        youMightAlsoLike.setAsHorizontalList().scrollToEnd(2)
        titleFromYouMightAlsoLike.click()
        return MovieDatailScreen()
    }

    fun checkIfCCIconpresent(): Boolean {
        return captionicon.waitForExists(globalTimeout)
    }


}

class ShareWithScreen() : BaseScreen() {
    private val slide = UiScrollable(UiSelector().resourceId("android:id/sem_resolver_pagemode_page_list"))
    private val shareWithTitle = uiDevice.findObject(UiSelector().text("Share with"))
    private val facebookIcon = uiDevice.findObject(UiSelector().text("Facebook"))

    init {
        Assert.assertTrue(shareWithTitle.waitForExists(globalTimeout))
        //Assert.assertTrue(facebookIcon.waitForExists(globalTimeout))
    }

    fun clickOnFacebookShareIcon(): FacebookPageShareScreen {

        if (facebookIcon.waitForExists(globalTimeout)) {
            facebookIcon.click()
        } else {
            slide.setAsHorizontalList().scrollToEnd(1)
            facebookIcon.click()
        }
        return FacebookPageShareScreen()
    }
}

class FacebookPageShareScreen() : BaseScreen() {
    private val facebookPostButton = uiDevice.findObject(UiSelector().text("POST"))
    private val facebookPostButtonForTablets = uiDevice.findObject(UiSelector().text("Post"))
    private val facebookShareButton = uiDevice.findObject(UiSelector().text("SHARE"))

    fun clickOnFacebookPostButton(): MovieDatailScreen {
        if (facebookPostButton.waitForExists(globalTimeout)) {
            facebookPostButton.click()
        } else if (facebookPostButtonForTablets.exists()) {
            facebookPostButtonForTablets.click()
        } else
            facebookShareButton.click()

        return MovieDatailScreen()
    }
}

class GotIt() : BaseScreen() {

    private val gotItButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/got_it_button"))
    public val gotitButton get() = this.gotItButton

    fun clickOnGotIt(): MovieDatailScreen {
        gotItButton.click()
        return MovieDatailScreen()
    }

}









