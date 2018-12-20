package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/14/18.
 */
class SideCategoryMenuScreen : BaseScreen() {
    private val boxWithListOfCategories = appPackage + ":id/design_navigation_view"
    private val wrapperBoxWithListOfCategories = UiCollection(UiSelector().resourceId(boxWithListOfCategories))
    private val scrollableScreen = UiScrollable(UiSelector().resourceId(boxWithListOfCategories))
    private val category = UiSelector().resourceId(appPackage + ":id/design_menu_item_text")
    private val userAcountPicture = appPackage + ":id/nav_menu_header_user_avatar"
    private val userAcountName = appPackage + ":id/nav_menu_header_user_name"
    private val userEmail = appPackage + ":id/nav_menu_header_user_email_address"

    init {
        scrollableScreen.flingToBeginning(3)
        Assert.assertTrue("Expected Category name in TOP is not displayed", wrapperBoxWithListOfCategories.waitForExists(globalTimeout))
        Assert.assertTrue("Expected picture of user account is not displayed", findElementById(userAcountPicture, false).waitForExists(globalTimeout))
        Assert.assertTrue("Expected name of user account is not displayed", findElementById(userAcountName, false).waitForExists(globalTimeout))
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
        var foundCategory = UiObject(UiSelector())
        var i = 0
        var ii = 0
        try {
            while (i in 0..UiCollection(UiSelector().resourceId(boxWithListOfCategories)).childCount) {
                if (findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false).exists()) {
                    val name = findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false).text
                    if (name.equals(category)) {
                        foundCategory = findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false)
                        foundCategory.click()
                        break
                    }
                } else if (ii > 60) {
                    throw TestException("This $category not found on side menu")
                } else {
                    UiScrollable(UiSelector().resourceId(boxWithListOfCategories)).setAsVerticalList().scrollForward()
                    i = 0
                }
                ii++
                i++
            }

            return SubCategoryScreen()
        } catch (e: UiObjectNotFoundException) {
            throw   TestException("This $category not found on side menu")
        }
//        try {
//            scrollableScreen.scrollTextIntoView(category)
//            uiDevice.findObject(UiSelector().text(category)).click()
//        } catch (e: UiObjectNotFoundException) {
//            throw TestException("This category: $category not found on side menu")
//        }
//        return SubCategoryScreen()
    }

    fun scrollToSpecificCategoryWithoutReturn(category: String): UiObject {
        var foundCategory = UiObject(UiSelector())
        var i = 0
        var ii = 0
        try {
            while (i in 0..UiCollection(UiSelector().resourceId(boxWithListOfCategories)).childCount) {
                if (findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false).exists()) {
                    val name = findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false).text
                    if (name.equals(category)) {
                        foundCategory = findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false)
                        break
                    }
                } else if (ii > 60) {
                    throw TestException("This $category not found on side menu")
                } else {
                    UiScrollable(UiSelector().resourceId(boxWithListOfCategories)).setAsVerticalList().scrollForward()
                    i = 0
                }
                ii++
                i++
            }

            return foundCategory
        } catch (e: UiObjectNotFoundException) {
            throw   TestException("This $category not found on side menu")
        }
    }

    fun getUserEmail(): String {
        return findElementById(userEmail, false).text
    }

    fun getUserName(): String {
        return findElementById(userAcountName, false).text
    }


}