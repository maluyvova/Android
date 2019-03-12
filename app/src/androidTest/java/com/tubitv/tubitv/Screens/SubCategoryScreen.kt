package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

/**
 * Created by vburian on 4/2/18.
 */
class SubCategoryScreen(categoryName: String) : BaseScreen() {
    private val clickedCategory = categoryName
    private val screen = UiScrollable(UiSelector().resourceId(appPackage + ":id/container_videos_recycler_view"))
    private val boxWithTitles = UiCollection(UiSelector().resourceId(appPackage + ":id/container_videos_recycler_view"))
    private val title = UiSelector().className("android.widget.LinearLayout")
    private val titleImageId = appPackage + ":id/video_poster_image_view"
    private val textOfTitle = appPackage + ":id/video_title_text_view"
    private val categoryNameId = appPackage + ":id/titlebar_title_text_view"
    private val backButton = appPackage + ":id/titlebar_back_image_view"

    init {
        assertThat("back button doesn't exist on Sub Category screen", findObjectById(backButton, true).exists(), equalTo(true))
        assertThat("Looks like wrong category is displayed", waitUntilTextMeetsConditions(findObjectById(categoryNameId, true), clickedCategory).text, equalTo(clickedCategory))
    }


    val textOFTitle get() = findObjectById(textOfTitle, false).text

    fun textOfAllTitles(): MutableList<String> {
        val titles = mutableListOf<String>()
        var i = 0
        while (countOfMovies() > i) {
            if (!screen.getChildByInstance(title, i).getChild(UiSelector().resourceId(textOfTitle)).exists()) {
                screen.flingBackward()
            }
            if (!screen.getChildByInstance(title, i).getChild(UiSelector().resourceId(textOfTitle)).exists()) {
                screen.flingForward()
            }
            titles.add(screen.getChildByInstance(title, i).getChild(UiSelector().resourceId(textOfTitle)).text)
            i++
            if (i > 30) {
                throw TestException("Can't get text of all titles")
            }
        }
        return titles
    }

    fun swipeScreen(number: Int) {
        screen.setAsHorizontalList().scrollToEnd(number)
    }

    fun longClickOnTitle(number: Int): AddToQueue {
        screen.getChildByInstance(title, number).dragTo(screen.getChildByInstance(title, number), 3)
        return AddToQueue(true)
    }

    fun countOfMovies(): Int {
        return boxWithTitles.getChildCount(UiSelector().resourceId(titleImageId))
    }

    fun waitUntilTextMeetsConditions(obj: UiObject, text: String): UiObject {
        var i = 0
        val some = obj.text
        while (!obj.text.equals(text) && i < 200) {
            val r = "sfsdf"
            i++
        }
        return obj
    }

    fun clickOnTitle(number: Int) {
        screen.getChildByInstance(title, number).click()
    }

    fun clickOnTitleForQueue(number: Int): GotIt {
        screen.getChildByInstance(title, number).click()
        return GotIt()
    }

    fun removeAllTitlesFromHistory() {
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
            throw   TestException("Can't delete all titles from 'Continue watching'")
        }
    }

    fun removeAllTitlesFromQueue() {
        try {

            var i = 0
            if (countOfMovies() == 0) {
                return
            }
            while (countOfMovies() >= i) {
                longClickOnTitle(0)
                        .clickAddToQueueAfterLongClickWithoutReturn()
                screen.getChildByInstance(title, 0).waitUntilGone(globalTimeout)
                i++
            }
        } catch (e: UiObjectNotFoundException) {
            throw   TestException("Can't delete all titles from 'Continue watching'")
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
                if (countOfMovies() < i) {
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
            throw   TestException("Can't find this movie: $nameOfMovie in 'Continue watching'")
        }
        return AddToQueue(false)
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
            throw     TestException("Can't find this movie: $nameOfMovie in 'Continue watching'")
        }
        return MovieDatailScreen()
    }


}
