package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert

import java.util.*
import android.support.test.uiautomator.UiSelector
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue


/**
 * Created by vburian on 2/19/18.
 */
open class HomeScreen : BaseScreen() {

    private val firstListOfAllObjectsString = appPackage + ":id/view_category_recycler"
    protected val firstListOfAllObjects = UiCollection(UiSelector().resourceId(firstListOfAllObjectsString))
    private val scrollHomePage = appPackage + ":id/view_category_recycler"
    private val categoryList = UiSelector().resourceId(appPackage + ":id/view_content_recycler_ll")
    private val textOfMovie = UiSelector().resourceId(appPackage + ":id/view_home_content_title_tv")
    private val titleOfMovie = UiSelector().resourceId(appPackage + ":id/view_home_content_iv")
    public val textOFCategory = UiSelector().resourceId(appPackage + ":id/view_content_recycler_category_title")
    private val featuredContainer = appPackage + ":id/bannerContainer"
    private val featuredTitlesText = appPackage + ":id/banner_title"
    private val counterOfTitlesInFeaturedContainer = appPackage + ":id/numIndicator"
    private val sideCategoryMenu = "android.widget.ImageButton"
    private val treeDotsSetingsButton = "More options"
    private val containerOfTitlesSmaller = UiSelector().resourceId(appPackage + ":id/view_content_recycler")
    private val textOfTitleInContnueWatching = appPackage + ":id/view_home_content_continue_title_tv"
    private val searchButton = "Search"
    private val searchField = appPackage + ":id/nav_app_bar_main_search"
    private val castButton = "Cast button. Disconnected"
    private val castMenu = appPackage + ":id/action_bar_root"

    init {
        if (!findElementById(counterOfTitlesInFeaturedContainer, false).waitForExists(globalTimeout)) {
            for (i in 0..2) {
                 //scrollableScreenById(scrollHomePage).setAsVerticalList().scrollToBeginning(i)
                scrollableScreenById(scrollHomePage).setAsVerticalList().scrollToBeginning(i)
                 scrollableScreenById(scrollHomePage).setAsVerticalList().flingBackward()
            }
        }
        assertTrue("Counter for Featured titles is not displayed on HomeScreen", findElementById(counterOfTitlesInFeaturedContainer, false).waitForExists(globalTimeout))
        assertTrue("Expected first List of All Objects is not displayed on HomeScreen", firstListOfAllObjects.waitForExists(moviesListTimeout))
        assertTrue("Expected titles is not displayed on HomeScreen", getTitleFromGrid().waitForExists(moviesListTimeout))
        assertTrue("SearchField is not displayed on HomeScreen", findElementByDescription(searchButton, false).waitForExists(globalTimeout))
        assertTrue("Settings button is not displayed on HomeScreen", findElementByDescription(treeDotsSetingsButton, false).waitForExists(globalTimeout))
        assertTrue("Side category button is not displayed on HomeScreen", findObjectByClass(sideCategoryMenu, false).waitForExists(globalTimeout))
    }

    protected fun getGrid(number: Int) =
            firstListOfAllObjects.getChildByInstance(categoryList, number) // it's object of all category moivies in homepage


    public fun clickAndSendTextToSearch(text: String): SearchScreen {
        findElementByDescription(searchButton, false).click()
        findObjectById(searchField, false).setText(text)
        return SearchScreen()
    }

    public fun clickOnCustButton(castDeviceName: String) {
        findElementByDescription(castButton, false).click()
        findObjectById(castMenu, true)
        findElementByText(castDeviceName, false).click()
    }

    public val textOfTitleInFeaturedCategor get() = findObjectById(featuredTitlesText, false).text

    public fun getCountOfMovies(i: Int): Int {
        return getGrid(i).getChild(containerOfTitlesSmaller).childCount
    }

    public fun clickOnTitleInFeaturedCategory(): GotIt {
        findObjectById(featuredTitlesText, false).click()
        return GotIt()
    }

    public val titleInContinueWatching get() = findObjectById(textOfTitleInContnueWatching, false).text

    fun ScrollToSpecificCategory(category: String) {
        var i = 0
        var ii = 0
        while (i < 10) {
            if (getGrid(i).getChild(textOFCategory).waitForExists(if (i < 3) globalTimeout else 5)) {
                val box = getGrid(i).getChild(textOFCategory).text
                if (box.equals("$category")) {
                    if (getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).exists()) {
                        break
                    }
                }
            } else if (i > 6) {
                scrollableScreenById(scrollHomePage).scrollForward()
                i = 0
            }
            i++
            ii++
            if (ii > 125) {
                val rm = ""
                return
            }
        }
        scrollableScreenById(scrollHomePage).scrollTextIntoView("$category")
    }

    fun scrolDownLittleBit(){
        scrollableScreenById(scrollHomePage).setAsVerticalList().scrollToEnd(1)
    }

    fun horisontalScrollTitles(swipes: Int, category: String) {
        for (i in 1..6) {
            val box = getGrid(i).getChild(textOFCategory)
            if (box.text == "$category") {
                for (j in 1..6) {
                    getGrid(i).getChild(containerOfTitlesSmaller).swipeLeft(swipes)
                }
                break
            }
        }
    }

    fun scrollFeaturetTitles(swipes: Int) {
        scrollableScreenById(featuredContainer).setAsHorizontalList().scrollToEnd(swipes)
    }

    fun getText(category: String): String {
        var textOfMovies = ""
        var i = 0
        while (i < 10) {
            if (getGrid(i).getChild(textOFCategory).waitForExists(if (i < 3) globalTimeout else 5)) {
                val box = getGrid(i).getChild(textOFCategory).text
                if (box.equals("$category")) {
                    if (getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).exists()) {
                        textOfMovies = getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).text
                        break
                    }
                }
            } else if (i > 6) {
                scrollableScreenById(scrollHomePage).scrollForward()
                i = 0
            }
            i++
        }
        return textOfMovies
    }

    fun getTextOfTitleWithIndex(category: String): String {
        var textOfMovies = ""
        for (i in 0..6) {
            if (getGrid(i).getChild(textOFCategory).exists()) {
                val box = getGrid(i).getChild(textOFCategory).text
                if (box.equals("$category")) {
                    if (getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).exists()) {
                        textOfMovies = getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).text
                        break
                    } else {
                        scrollableScreenById(scrollHomePage).setAsVerticalList().scrollToEnd(1)
                        getTextOfTitleWithIndex("$category")
                    }
                }
            }

        }
        return textOfMovies
    }

    fun getTextOFMovie(numberOfView: Int) =
            getGrid(numberOfView).getChild(textOfMovie) //got a first element from the list of movies

    fun getTextFromFeaturedTitlesCounter(): String {
        return findElementById(counterOfTitlesInFeaturedContainer, false).text
    }

    fun clickOnThreeDots() {
        getGrid(0).getChild(UiSelector().className("android.widget.ImageView")).click()
    }

    private fun getTitleFromGrid() =
            getGrid(0).getChild(titleOfMovie)

    fun getTextOfCategory(number: Int) =
            getGrid(number).getChild(textOFCategory)

    fun text(number: Int): Objects {
        val text = getTextOfCategory(number)
        return text(number)
    }


    public fun clickOnThreeDotsSetings(): SettingSmallWindowInRightCorner {
        findElementByDescription(treeDotsSetingsButton, false).click()
        return SettingSmallWindowInRightCorner()
    }


    public fun waitForDisapearCategoryText(text: String) {
        uiDevice.wait(Until.gone(By.text(text)), globalTimeout)
    }

    public fun waitForExistsCategoryText(text: String) {     //change it
        scrollableScreenById(scrollHomePage).scrollTextIntoView(text)
        Assert.assertTrue("I added title to my queue, problem that queue container is not found on home page", uiDevice.wait(Until.findObject(By.text(text)), globalTimeout).text.equals(text))
    }


    fun clickOnSidecategorButton(): SideCategoryMenuScreen {
        findObjectByClass(sideCategoryMenu, false).click()
        return SideCategoryMenuScreen()
    }


    public val textCategory = getTextOfCategory(0).text

    public val title get() = getTextOFMovie(0).text //get text title form the home page

    public fun clickOnTitle(num: Int): GotIt {
        getTextOFMovie(num).click()
        return GotIt()
    }

    public fun clickOnElementByText(text: String) {
        uiDevice.findObject(UiSelector().text(text)).click()
    }

    public fun longPress(): AddToQueue {
        getTitleFromGrid().dragTo(getTitleFromGrid(), 10)

        return AddToQueue()
    }


    class RemoveFormHistoryScreen() : BaseScreen() {
        private val boxWithRemoveFromHistory = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))
        private val removeFromHistory = UiSelector().resourceId("android:id/text1")

        init {
            Assert.assertTrue("Title is not added to 'History' after watcing 30 sec and click Back", boxWithRemoveFromHistory.waitForExists(moviesListTimeout))
        }

        protected fun getBottonRemoveFromHistory() =
                boxWithRemoveFromHistory.getChildByInstance(removeFromHistory, 0)


        public fun clickOnRemoveFromHistory() {
            getBottonRemoveFromHistory().click()


        }

    }


    class QueueScreen() : BaseScreen() {
        var homescreen = HomeScreen()
        private val firstListOfAllObjectsString = appPackage + ":id/view_category_recycler"
        private val textOFCategory = appPackage + ":id/view_content_recycler_category_title"
        private val classs = "android.widget.LinearLayout"
        private val queueList = UiSelector().resourceId(appPackage + ":id/view_content_recycler_ll")
        private val textOfMovie = appPackage + ":id/view_home_content_title_tv"


        init {
            Assert.assertTrue("Expected queue is not displayed", getQueuFromGrid().waitForExists(globalTimeout))
        }

        protected fun getGrid(number: Int) =
                homescreen.firstListOfAllObjects.getChildByInstance(queueList, number) // it's object of all category moivies in

        private fun getQueuFromGrid() =
                getGrid(0).getChild(homescreen.textOfMovie)

        private fun getFirstTitleInQueu() =
                getGrid(0).getChild(homescreen.textOfMovie)


        fun textFromFirstTitleInQueue(): Pair<String, UiObject> {
            var firstObject = UiObject(UiSelector())
            var textFromFirstTitle = ""
            try {
                for (i in 1..4) {
                    val textOfCategory = getGrid(i).getChild(UiSelector().resourceId(textOFCategory)).text
                    if (textOfCategory.equals("Queue")) {
                        firstObject = getGrid(i).getChild(UiSelector().resourceId(textOfMovie))
                        textFromFirstTitle = firstObject.text
                        break
                    }
                }
            } catch (e: UiObjectNotFoundException) {

            }
            return Pair(textFromFirstTitle, firstObject)
        }
    }


    class AddToQueue() : BaseScreen() {
        private val addToQueueLongClick = "android:id/text1"
        private val addToQueueLongClickUiSlector = UiSelector().resourceId("android:id/text1")
        private val smallWindow = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))
        private val facebookSignIn = appPackage + ":id/prompt_image_background"

        init {
            assertTrue("Expected small window with add to queue is not displayed", findElementById(addToQueueLongClick, false).waitForExists(moviesListTimeout))

        }

        public fun clickAddToQueueAfterLongClickWithoutReturn() {
            findObjectById(addToQueueLongClick, false).click()
            if (findElementById(facebookSignIn, false).exists()) {
                FacebookSignInForNonRegisterUser().signUpWithFacebookButton
                findObjectById(addToQueueLongClick, false).click()
            }
        }

        public fun clickRemoveFromHistory() {
            smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0).dragTo(smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0), 3)
            if (findElementById(facebookSignIn, false).exists()) {
                FacebookSignInForNonRegisterUser().signUpWithFacebookButton
                smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0).dragTo(smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0), 3)
            }
        }


        public fun clickAddToQueueAfterLongClick(): QueueScreen {
            findObjectById(addToQueueLongClick, false).click()
            if (findElementById(facebookSignIn, false).exists()) {
                FacebookSignInForNonRegisterUser().signUpWithFacebookButton
                findObjectById(addToQueueLongClick, false).click()
            }
            return QueueScreen()
        }

        inner class FacebookSignInForNonRegisterUser() {
            val headerText = appPackage + ":id/prompt_free_text"
            val textBody = appPackage + ":id/prompt_register_text"
            val signUpWithFacebookButton = "Sign Up with Facebook"
            val signInOrRegisterButton = "Sign In or Register"
            val closeButton = appPackage + ":id/prompt_image_close"

            init {
                assertTrue("Sign In or Register button is not present on facebook screen for when not register user wants add title to Queue", findElementByText(signInOrRegisterButton, false).waitForExists(globalTimeout))
                assertEquals("Header of text is not correspond requirements on facebook screen for when not register user wants add title to Queue", findObjectById(headerText, false).text, "Free TV, [free movies, ]free registration")
                assertEquals(findObjectById(textBody, false).text, "Register now to build your queue, continue")
                assertTrue("SignUp with Facebook button is not present on facebook screen for when not register user wants add title to Queue", findElementByText(signUpWithFacebookButton, false).exists())
                assertTrue("Close button button is not present on facebook screen for when not register user wants add title to Queue", findElementById(closeButton, false).exists())
            }

            fun clickOnSignUpWithFacebook() {
                findElementByText(signUpWithFacebookButton, false).click()
            }

        }
    }


    class Serials(category: String) : BaseScreen() {
        private val titleWithSerial = findElementByText(category, false)

        public fun clickOnSerialCategory(): MoviesByCategoryScreen {
            titleWithSerial.click()
            return MoviesByCategoryScreen()
        }
    }

    class SettingSmallWindowInRightCorner() : BaseScreen() {
        private val boxWithSettingsItems = UiCollection(UiSelector().className("android.widget.ListView"))
        private val boxWithSettingsForInit = "android.widget.FrameLayout"
        private val SettingsAboutHelpCenter = UiSelector().resourceId(appPackage + ":id/title")

        init {
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", findObjectByClass(boxWithSettingsForInit, false).waitForExists(moviesListTimeout))

        }

        fun clickOnSettings(): SettingsScreen {
            boxWithSettingsItems.getChildByInstance(SettingsAboutHelpCenter, 0).click()
            return SettingsScreen()
        }

        fun clickOnAbout(): PrivatePolicyScreen {
            boxWithSettingsItems.getChildByInstance(SettingsAboutHelpCenter, 1).click()
            return PrivatePolicyScreen()
        }

        fun clickOnHelpCenter(): SettingsScreen {
            boxWithSettingsItems.getChildByInstance(SettingsAboutHelpCenter, 2).click()
            return SettingsScreen()
        }
    }

    class History() : BaseScreen() {
        private val removeFromHistory = "Remove from history"
        private val addToQueue = uiDevice.findObject(UiSelector().text("Add to queue"))

        init {
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", findElementById(removeFromHistory, false).waitForExists(moviesListTimeout))
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", addToQueue.waitForExists(moviesListTimeout))

        }

        public fun clickOnRemoveFromHisory() {
            findElementByText(removeFromHistory, false).click()
        }
    }


}





