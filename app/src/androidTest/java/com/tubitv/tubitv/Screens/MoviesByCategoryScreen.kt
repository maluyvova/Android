package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.shortWaitTime
import junit.framework.Assert
import junit.framework.Assert.assertTrue
import java.util.*

/**
 * Created by vburian on 2/23/18.
 */
class MoviesByCategoryScreen : BaseScreen() {
    private val boxOfTitles = UiCollection(UiSelector().resourceId(appPackage + ":id/view_grid_category_recycler"))
    private val categoryName = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/nav_app_bar_main_title"))
    private val title = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_category_content_iv"))
    private val title2 = UiSelector().resourceId(appPackage + ":id/view_category_content_iv")
    private val textOfTitle = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_category_content_tv_title"))
    private val screen = appPackage + ":id/view_category_recycler"
    public val categoryText get() = categoryName.text
    public val titleText get() = textOfTitle.text


    init {
        if (!categoryName.waitForExists(globalTimeout)) {
            scrollableScreenById(screen).scrollBackward()
        }
        if (!categoryName.waitForExists(shortWaitTime)) {
            scrollableScreenById(screen).scrollForward()
        }
        assertTrue("Expected Category name in TOP is not displayed", categoryName.waitForExists(globalTimeout))

    }

    public fun clickOnTitle(): GotIt {
        title.click()
        return GotIt()
    }

    public fun clickOnTitleNoGotIt(): MovieDatailScreen {
        title.click()
        return MovieDatailScreen()
    }

    public fun getCountOfTitles(): Int {
        val numbersOfTitles = boxOfTitles.getChildCount()
        return numbersOfTitles
    }


    public fun gotkRandomTite(random: Int): UiObject {
        val randomtitle = boxOfTitles.getChildByInstance(title2, random)
        return randomtitle
    }


}