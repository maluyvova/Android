package com.tubitv.tubitv.Screens

import android.app.UiAutomation
import android.support.test.uiautomator.*
import com.tubitv.tubitv.Enomus.Categories
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert
import junit.framework.Assert.assertTrue

/**
 * Created by vburian on 3/14/18.
 */
class BrowseMenuScreen : BaseScreen() {
    private var rowScreen = false
    private val boxWithListOfCategories = appPackage + ":id/content_recycler_view"
    private val wrapperBoxWithListOfCategories = UiCollection(UiSelector().resourceId(boxWithListOfCategories))
    private val scrollableScreen = UiScrollable(UiSelector().resourceId(boxWithListOfCategories))
    private val category = UiSelector().resourceId(appPackage + ":id/design_menu_item_text")
    private val userAcountPicture = appPackage + ":id/nav_menu_header_user_avatar"
    private val userAcountName = appPackage + ":id/nav_menu_header_user_name"
    private val userEmail = appPackage + ":id/nav_menu_header_user_email_address"
    private val threeDots = appPackage + ":id/more_content_image_view"
    private val nameOfBigContainer = appPackage + ":id/group_title_text_view"
    private val containerForSecondScreen = appPackage + ":id/container_content_recycler_view"
    private val smallContainerForSecondScreen = appPackage + ":id/item_root_view"
    private val categoryScreenForSecondScreen = appPackage + ":id/container_title_text_view"
    private var stopLoop = 0

    init {
        if (findElementById(threeDots, true).exists()) {
            assertTrue("Name of big container doesn't exists,e.g-> 'For you', 'Genres' ", findElementById(nameOfBigContainer, true).exists())

        } else {
            rowScreen = true
            scrollableScreen.flingToBeginning(3)
        }
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

        if (rowScreen) {
            return SubCategoryScreen(clickOnSpecificCategoryRowScreen(category))
        } else {
            when {
                category == Categories.CONTINUE_WATCHING.value -> {
                    return SubCategoryScreen(clickOnContinueWatchingContainerScreen(category))
                }
                category == Categories.QUEUE.value -> {
                }

//            if (category == Categories.CONTINUE_WATCHING.value) {
//                return SubCategoryScreen(clickOnContinueWatchingContainerScreen(category))
//            }
            //return SubCategoryScreen(clickOnSpecificCategoryContainerScreen(category))
            }
        }
        return return SubCategoryScreen(clickOnSpecificCategoryContainerScreen(category))
    }

    fun clickOnSpecificCategoryRowScreen(category: String): String {
        var foundCategory = UiObject(UiSelector())
        var foundCategoryName = ""
        var i = 0
        var ii = 0
        try {
            while (i in 0..UiCollection(UiSelector().resourceId(boxWithListOfCategories)).childCount) {
                if (findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false).exists()) {
                    foundCategoryName = findElmentByidAnd2LevelsDeeper(boxWithListOfCategories, i, 0, false).text
                    if (foundCategoryName.equals(category)) {
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
            return foundCategoryName
        } catch (e: UiObjectNotFoundException) {
            throw   TestException("This $category not found on side menu")
        }
    }


    fun clickOnSpecificCategoryContainerScreen(category: String): String {
        var foundCategory = UiObject(UiSelector())
        var threeDots = UiObject(UiSelector())
        var bigContainer = ""
        var textOfFoundedCategory = ""
        var x = 0
        if (category == Categories.QUEUE.value) {
            while (bigContainer != "For You") {
                if (uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(nameOfBigContainer)).exists()) {
                    bigContainer = uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(nameOfBigContainer)).text
                    threeDots = uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(this.threeDots))
                    x++
                }
            }
        } else while (bigContainer != "Genres") {
            if (uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(nameOfBigContainer)).exists()) {
                bigContainer = uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(nameOfBigContainer)).text
                threeDots = uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(this.threeDots))
                x++
            }
        }
        threeDots.click()
        var i = 0
        try {
            while (i in 0..UiCollection(UiSelector().resourceId(containerForSecondScreen)).childCount) {
                if (findObjectByIdAndIndex(smallContainerForSecondScreen, i, false).exists()) {
                    foundCategory = findObjectByIdAndIndex(smallContainerForSecondScreen, i, false)
                            .getChild(UiSelector().resourceId(categoryScreenForSecondScreen))
                    textOfFoundedCategory = foundCategory.text
                    if (foundCategory.text == category) {
                        findObjectByIdAndIndex(smallContainerForSecondScreen, i, false).click()
                        break
                    }
                } else {
                    UiScrollable(UiSelector().resourceId(containerForSecondScreen)).setAsVerticalList().scrollForward()
                    i = 0
                }
                i++
            }
        } catch (e: UiObjectNotFoundException) {
            if (stopLoop == 0) {
                UiScrollable(UiSelector().resourceId(containerForSecondScreen)).setAsVerticalList().scrollForward()
                clickOnSpecificCategoryContainerScreen(category)
                stopLoop++
            } else {
                throw   TestException("This $category not found on new browse screen ")
            }
        }

        return textOfFoundedCategory
    }


    fun clickOnContinueWatchingContainerScreen(category: String): String {
        var foundCategory = UiObject(UiSelector())
        var threeDots = UiObject(UiSelector())
        var bigContainer = UiObject(UiSelector())
        var bigContainerText = ""
        var textOfFoundedCategory = ""
        var x = 0
        var i = 0
        while (bigContainerText != "For You") {
            if (x > 40) {
                throw TestException("can't find this $category on Container screen")
            }
            if (uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(nameOfBigContainer)).exists()) {
                bigContainer = uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x))
                bigContainerText = bigContainer.getChild(UiSelector().resourceId(nameOfBigContainer)).text
                if (bigContainerText.equals("For You")) {
                    while (i in 0..bigContainer.childCount) {
                        val childs = bigContainer.childCount
                        val textOne = bigContainer.getChild(UiSelector().resourceId(categoryScreenForSecondScreen)).text
                        if (bigContainer.getChild(UiSelector().resourceId(categoryScreenForSecondScreen)).text.equals(category)) {
                            bigContainer.getChild(UiSelector().resourceId(categoryScreenForSecondScreen)).click()
                            return category
                        }
                        i++
                    }
                }
                uiDevice.findObject(UiSelector().resourceId(boxWithListOfCategories)).getChild(UiSelector().index(x)).getChild(UiSelector().resourceId(categoryScreenForSecondScreen))
                x++
            }
        }
        return ""
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

    public fun clickOnSearch(): SearchScreen {
        try {
            findElementParentIdChildIndex(tabs, true, 2, 0).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find search button", e)
        }
        return SearchScreen()
    }


}