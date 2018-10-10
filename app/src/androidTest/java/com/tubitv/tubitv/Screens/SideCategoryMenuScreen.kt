package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/14/18.
 */
class SideCategoryMenuScreen : BaseScreen() {
    private val boxWithListOfCategories = UiSelector().resourceId(appPackage + ":id/design_navigation_view")
    private val wrapperBoxWithListOfCategories = UiCollection(boxWithListOfCategories)
    private val scrollableScreen = UiScrollable(boxWithListOfCategories)
    private val category = UiSelector().resourceId(appPackage + ":id/design_menu_item_text")
    private val userAcountPicture = findObjectById(appPackage + ":id/nav_menu_header_user_avatar")
    private val userAcountName = findObjectById(appPackage + ":id/nav_menu_header_user_name")
    private val userEmail = findObjectById(appPackage + ":id/nav_menu_header_user_email_address")

    init {
        scrollableScreen.flingToBeginning(2)
        Assert.assertTrue("Expected Category name in TOP is not displayed", wrapperBoxWithListOfCategories.waitForExists(globalTimeout))
        Assert.assertTrue("Expected picture of user account is not displayed", userAcountPicture.waitForExists(globalTimeout))
        Assert.assertTrue("Expected name of user account is not displayed", userAcountName.waitForExists(globalTimeout))
    }

    fun numberOftitles(): Int {
        return wrapperBoxWithListOfCategories.getChildCount(category)

    }


    fun selectRandomcategory(index: Int) {
        wrapperBoxWithListOfCategories.getChildByInstance(category, index).click()

    }

    fun textOfRandomCategory(index: Int): String {
        val text = wrapperBoxWithListOfCategories.getChildByInstance(category, index).text
        return text
    }

    fun scrollToSpecificCategory(category: String): SubCategoryScreen {
        try {
            scrollableScreen.scrollTextIntoView(category)
            uiDevice.findObject(UiSelector().text(category)).click()
        } catch (e: UiObjectNotFoundException) {
            throw TestException("This category: $category not found on side menu")
        }
        return SubCategoryScreen()
    }

    fun scrollToSpecificCategoryWithoutReturn(category: String) {
        try {
            scrollableScreen.scrollTextIntoView(category)
            uiDevice.findObject(UiSelector().text(category)).click()
        } catch (e: UiObjectNotFoundException) {
            throw   TestException("This $category not found on side menu")
        }
    }

    fun getUserEmail(): String {
        return userEmail.text
    }

    fun getUserName(): String {
        return userAcountName.text
    }


}