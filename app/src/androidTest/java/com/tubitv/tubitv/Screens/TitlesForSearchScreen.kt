package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

/**
 * Created by vburian on 1/31/19.
 */
class TitlesForSearchScreen(searchedText: String) : BaseScreen() {

    private val searchField = appPackage + ":id/search_input_box"
    private val clearButton = appPackage + ":id/clear_button"
    private val titlesContainer = appPackage + ":id/search_result_recycler_view"
    private val clearCloseButtonInSeachField = appPackage + ":id/clear_button"
    private val classNameOfTitle = "android.widget.LinearLayout"
    private val noResultsText = appPackage + ":id/no_results_text_view"

    init {
        try {
            assertThat("Searched text is not shown in search field", findElementById(searchField, true).text, equalTo(searchedText))
        } catch (e: UiObjectNotFoundException) {
            throw   TestExceptionWithError("Can't find search field", e)
        }
        try {
            assertThat("Clear button is not present in search field", findElementById(clearButton, true).exists(), equalTo(true))
        } catch (e: UiObjectNotFoundException) {
            throw  TestExceptionWithError("Can't find Clear button in search field", e)
        }
    }


    fun getTitle(index: Int): UiObject {

        var title = UiObject(UiSelector())
        try {
            title = findElementByIdParentChildByClassName(titlesContainer, classNameOfTitle, true, index)
        } catch (e: UiObjectNotFoundException) {
            throw  TestExceptionWithError("Can't find title on Search screen", e)
        }
        return title

    }

    fun getTextOfTitle(index: Int): String {
        return getTitle(index).text
    }

    fun clickOnFirstTitleFirstTime(): GotIt {
        getTitle(0).click()
        return GotIt()
    }

    fun clickOnTitle(position: Int): MovieDatailScreen {
        try {
            getTitle(position).click()
        } catch (e: UiObjectNotFoundException) {
            throw  TestExceptionWithError("Can't find title on Search screen by this position: $position", e)
        }
        return MovieDatailScreen()
    }

    fun clickOnCloseClearButton(): SearchScreen {
        try {
            findObjectById(clearCloseButtonInSeachField, false).click()
        } catch (e: UiObjectNotFoundException) {
            throw  TestExceptionWithError("Can't find close/clear button in search field", e)
        }
        return SearchScreen()

    }

    fun getMessageWhenNoResultsReturned(): String {
        try {
            return findObjectById(noResultsText, true).text
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Text with 'No results'", e)
        }
    }


}