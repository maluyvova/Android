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

    private val titleText = appPackage + ":id/textView_title"
    private val addToQueue = appPackage + ":id/imageView_add_from_queue"
    private val youMightAlsoLike = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_content_recycler_ll"))
    private val playButton = appPackage + ":id/imageView_play"
    private val scrollbleScreen = UiScrollable(UiSelector().resourceId(appPackage + ":id/empty_holder"))
    private val titleFromYouMightAlsoLike = appPackage + ":id/view_home_content_iv"
    private val shareWithButton = appPackage + ":id/imageView_share"
    private val captionIcon = appPackage + ":id/imageView_caption"
    private val huluIcon = appPackage + ":id/vaudTextView_present_hulu"
    private val categoryNameOnTopBar = appPackage + ":id/nav_app_bar_main_title"
    private val signInPopUp = appPackage + ":id/prompt_image_background"

    init {
        if (findElementById(signInPopUp, false).waitForExists(shortWaitTime)) {

        }
        Assert.assertTrue("Expected title text  is not displayed", findElementById(titleText, false).waitForExists(globalTimeout))
        Assert.assertTrue("Expected button add to queue is not displayed", findElementById(addToQueue, false).waitForExists(globalTimeout))
        Assert.assertTrue("Expected share button is not displayed", findElementById(shareWithButton, false).waitForExists(globalTimeout))
    }

    public val titleDatailScreen get() = findObjectById(titleText, false).text //get text from the datail page
    public val scrollableScreen = this.scrollbleScreen

    public val youMightaAlsoLike = this.youMightAlsoLike

    fun clickOnAddToQueue(): HomeScreen {
        findObjectById(addToQueue, false).click()
        uiDevice.pressBack()
        return HomeScreen()
    }

    fun waitUntillSelected() {
        while (findElementById(addToQueue, false).isSelected)
            break
    }

    fun dontSelectHuluTitle() {
        val i = 1;
        while (findElementById(huluIcon, false).exists()) {
            uiDevice.pressBack()
            HomeScreen().clickOnTitle(i)
            i + 1
        }
    }

    fun clickOnRemoveFromQueue(): HomeScreen {
        findObjectById(addToQueue, false).click()
        return HomeScreen()
    }

    fun checkIfStillOnThisPage(): Boolean {
        val s = findElementById(categoryNameOnTopBar, false).waitForExists(shortWaitTime)
        return s
    }

    fun simpleClickOnAddToQueue() {

        findObjectById(addToQueue, false).click()
    }

    fun simpleClickOnRemoveFromQueue(): HomeScreen {
        findObjectById(addToQueue, false).click()
        Thread.sleep(2000)
        uiDevice.pressBack()
        return HomeScreen()
    }

    fun clickOnPlay(): PlayBackScreen {
        findObjectById(playButton, false).click()
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
        findObjectById(shareWithButton, false).click()
        return ShareWithScreen()
    }

    fun selectTitleFromMightAlsoLike(): MovieDatailScreen {
        scrollbleScreen.setAsVerticalList().scrollToEnd(2)
        youMightAlsoLike.setAsHorizontalList().scrollToEnd(2)
        findObjectById(titleFromYouMightAlsoLike, false).click()
        return MovieDatailScreen()
    }

    fun checkIfCCIconpresent(): Boolean {
        return findElementById(captionIcon, false).waitForExists(globalTimeout)
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

    private val gotItButton = appPackage + ":id/got_it_button"
    public val gotitButton get() = this.findObjectById(gotItButton,false)

    fun clickOnGotIt(): MovieDatailScreen {
        findObjectById(gotItButton,false).click()
        return MovieDatailScreen()
    }

}









