package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 4/4/18.
 */
class SearchScreen : BaseScreen() {
    private val boxWithTitles = UiCollection(UiSelector().resourceId(appPackage + ":id/recycler_view"))
    private val titleInSearchScreen = UiSelector().resourceId(appPackage + ":id/poster")

    init {
        Assert.assertTrue("Expected Category name in TOP is not displayed", boxWithTitles.waitForExists(globalTimeout))
    }


    fun clickOnTitleByInstatnce(number: Int): GotIt {
        boxWithTitles.getChildByInstance(titleInSearchScreen, number).click()
        return GotIt()
    }
}