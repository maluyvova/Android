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
 * Created by vburian on 4/2/18.
 */
class SubCategoryScreen : BaseScreen() {
    private val screen = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_grid_category_recycler"))
    private val boxWithTitles = UiCollection(UiSelector().resourceId(appPackage + ":id/fragment_category_recycler"))
    private val title = UiSelector().className("android.widget.LinearLayout")
    private val titleId = appPackage + ":id/view_category_content_iv"
    private val textOfTitle = appPackage + ":id/view_category_content_tv_title"

    init {
        Assert.assertTrue("Expected screen with subtitles is not displayed ", screen.waitForExists(globalTimeout))
    }


    public val textOFTitle get() = findObjectById(textOfTitle, false).text

    fun swipeScreen(number: Int) {
        screen.setAsHorizontalList().scrollToEnd(number)
    }

    fun longClickOnTitle(number: Int): AddToQueue {
        screen.getChildByInstance(title, number).dragTo(screen.getChildByInstance(title, number), 3)
        return AddToQueue(true)
    }

    fun countOfMovies(): Int {
        return boxWithTitles.getChildCount(UiSelector().resourceId(titleId))
    }

    fun clickOnTitle(number: Int) {
        screen.getChildByInstance(title, number).click()
    }

    fun clickOnTitleForQueue(number: Int): GotIt {
        screen.getChildByInstance(title, number).click()
        return GotIt()
    }

    fun removeAllTitles() {
        try {

            var i = 0
            if (countOfMovies() == 0) {
                return
            }
            while (countOfMovies() >= i) {
                longClickOnTitle(0)
                        .clickRemoveFromHistory()
                screen.getChildByInstance(title, 0).waitUntilGone(globalTimeout)
                i++
            }
        } catch (e: UiObjectNotFoundException) {
            TestException("Can't delete all titles from 'Continue watching'")
        }
    }

    fun verifyIfTitlesAddedToContinueWatching(vararg titles: String) {
        val results: ArrayList<Boolean> = ArrayList()
        var i = 0
        for (nameOfTitle in titles) {
            var i = 0
            while (countOfMovies() > i) {
                val title = screen.getChildByInstance(title, i).getChild(UiSelector().resourceId(textOfTitle)).text
                if (title.equals(nameOfTitle)) {
                    results.add(true)
                    break
                }
                i++
                if(countOfMovies()<i){
                    results.add(false)
                    break
                }

            }
        }
    }

    fun clickOnTitleForQueueNoGotIt(number: Int): MovieDatailScreen {
        screen.getChildByInstance(title, number).click()
        return MovieDatailScreen()
    }

    fun longClickOnTitle(nameOfMovie: String): AddToQueue {
        try {
            var countOfMovies = 0
            var i = 0
            while (countOfMovies() * 2 >= i) {
                val titleObj = screen.getChildByInstance(title, i).getChild(UiSelector().resourceId(textOfTitle))
                val text = titleObj.text
                if (text.contentEquals(nameOfMovie)) {
                    countOfMovies = countOfMovies()
                    titleObj.dragTo(titleObj, 2)
                    break
                }
                i++
            }
            if (i > countOfMovies * 2) {
                throw TestException("Can't find this movie: $nameOfMovie in 'Continue watching'")
            }
        } catch (e: UiObjectNotFoundException) {
            TestException("Can't find this movie: $nameOfMovie in 'Continue watching'")
        }
        return AddToQueue(true)
    }

    fun clickOnTitle(nameOfMovie: String): MovieDatailScreen {
        try {
            for (i in 0..countOfMovies()) {
                val titleObj = screen.getChildByInstance(title, i).getChild(UiSelector().resourceId(textOfTitle))
                val text = titleObj.text
                if (text.contentEquals(nameOfMovie)) {
                    titleObj.click()
                    break
                }
            }
        } catch (e: UiObjectNotFoundException) {
            TestException("Can't find this movie: $nameOfMovie in 'Continue watching'")
        }
        return MovieDatailScreen()
    }


}
