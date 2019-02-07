package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat

/**
 * Created by vburian on 4/4/18.
 */
class SearchScreen : BaseScreen() {
    private val searchField = appPackage + ":id/search_input_box"
    private val treandingSearch = appPackage + ":id/search_item_text_view"
    private val parentOfProposition = appPackage + ":id/root_layout"
    private val childOfProposition = appPackage + ":id/search_item_text_view"
    private val clearButton = appPackage + ":id/history_clear_text_view"


    init {
        if (!findElementById(clearButton, false).exists()) {
            try {
                assertThat("Expected Category name in TOP is not displayed", findElementById(searchField, true).text, equalTo("Find movies, TV shows & more"))
            } catch (e: UiObjectNotFoundException) {
                throw TestExceptionWithError("Can't find search field", e)
            }

            try {
                assertThat("Trending searches is not present on search screen ", findElementById(treandingSearch, true).text, equalTo("TRENDING SEARCHES"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find Trending search", e)
            }
        }
        verifyPropositions()

    }


    fun verifyPropositions() {
        var prevTitleText = ""
        var nextTitleText = ""
        for (i in 0..4) {
            try {
                prevTitleText = nextTitleText
                nextTitleText = getTextFromProposition(i)
                assertThat("Looks like we show 2 same title proposition on Search screen: they are: $prevTitleText and $nextTitleText", nextTitleText, not(prevTitleText))
            } catch (e: UiObjectNotFoundException) {
                throw    TestExceptionWithError("Can't find Propositions on Search screen", e)
            }
        }
    }

    fun getTextFromProposition(position: Int): String {
        try {
            return findObjectByIdAndIndex(parentOfProposition, position, false).getChild(UiSelector().resourceId(childOfProposition)).text
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Propositions on Search screen", e)
        }
    }

    fun getTextFromRecentSearch(): String {
        return getTextFromProposition(1)
    }

    fun clickOnProposition(position: Int): TitlesForSearchScreen {
        val suggestionText = getTextFromProposition(position)
        try {
            findObjectByIdAndIndex(parentOfProposition, position, false).getChild(UiSelector().resourceId(childOfProposition)).click()
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Propositions on Search screen on this position: $position", e)
        }
        return TitlesForSearchScreen(suggestionText)
    }

    fun provideTextToSearch(title: String): TitlesForSearchScreen {
        try {
            //findElementById(searchField, false).click()
            findElementById(searchField, false).setText(title)
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find search field", e)
        }
        return TitlesForSearchScreen(title)
    }



    fun clickOnRecentSearches(): TitlesForSearchScreen {
        var searched = ""
        try {
            val recentSearches = findObjectByIdAndIndex(parentOfProposition, 0, false).getChild(UiSelector().resourceId(childOfProposition)).text
            if (recentSearches != "RECENT SEARCHES") {
                throw TestException("Can't find 'Recent Searches' on search screen, but it should be there")
            }
            searched = getTextFromProposition(1)
            findObjectByIdAndIndex(parentOfProposition, 1, false).getChild(UiSelector().resourceId(childOfProposition)).click()
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find 'Recent Searches'", e)
        }
        return TitlesForSearchScreen(searched)
    }

    fun clickOnClear() {
        try {
            findObjectById(clearButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find 'Clear' button", e)
        }

    }

    fun verifyHowManyElementsBetweenRecentAndTrending(): Int {
        var i = 1
        var ii = 0
        try {
            val recentSearches = findObjectByIdAndIndex(parentOfProposition, 0, false).getChild(UiSelector().resourceId(childOfProposition)).text
            if (recentSearches != "RECENT SEARCHES") {
                throw TestException("Can't find 'Recent Searches' on search screen, but it should be there")
            }
            while (findObjectByIdAndIndex(parentOfProposition, i, false).getChild(UiSelector().resourceId(childOfProposition)).text != "TRENDING SEARCHES") {
                val sm = findObjectByIdAndIndex(parentOfProposition, i, false).getChild(UiSelector().resourceId(childOfProposition)).text
                i++
                ii++
                if (ii > 15) {
                    throw TestException("Can't find trendind seachres")
                }
            }
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Something wrong on Search screen", e)
        }
        return i - 1
    }


//    fun clickOnTitleByInstatnce(number: Int): GotIt {
//        boxWithTitles.getChildByInstance(titleInSearchScreen, number).click()
//        return GotIt()
//    }
}