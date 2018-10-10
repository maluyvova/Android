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
    public val scrollHomePage = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_category_recycler"))
    private val categoryList = UiSelector().resourceId(appPackage + ":id/view_content_recycler_ll")
    private val textOfMovie = UiSelector().resourceId(appPackage + ":id/view_home_content_title_tv")
    private val titleOfMovie = UiSelector().resourceId(appPackage + ":id/view_home_content_iv")
    public val textOFCategory = UiSelector().resourceId(appPackage + ":id/view_content_recycler_category_title")
    private val featuredContainer = UiScrollable(UiSelector().resourceId(appPackage + ":id/bannerContainer"))
    private val featuredTitlesText = findObjectById(appPackage + ":id/banner_title")
    private val counterOfTitlesInFeaturedContainer = findObjectById(appPackage + ":id/numIndicator")
    private val sideCategoryMenu = uiDevice.findObject(UiSelector().className("android.widget.ImageButton"))
    private val treeDotsSetingsButton = uiDevice.findObject(UiSelector().description("More options"))
    private val containerOfTitlesSmaller = UiSelector().resourceId(appPackage + ":id/view_content_recycler")
    private val textOfTitleInContnueWatching = findObjectById(appPackage + ":id/view_home_content_continue_title_tv")
    private val playButtonForContinueWatching = findObjectById(appPackage + ":id/view_home_content_continue_play_btn")
    private val searchButton = "Search"
    private val searchField = appPackage + ":id/nav_app_bar_main_search"
    private val castButton = "Cast button. Disconnected"
    private val castMenu = findObjectById(appPackage + ":id/action_bar_root")

    init {
        if (!counterOfTitlesInFeaturedContainer.waitForExists(globalTimeout)) {
            for (i in 0..2) {
                scrollHomePage.scrollToBeginning(i)
                scrollHomePage.flingBackward()
            }
        }
        assertTrue("Counter for Featured titles is not displayed on HomeScreen", counterOfTitlesInFeaturedContainer.waitForExists(globalTimeout))
        assertTrue("Expected first List of All Objects is not displayed on HomeScreen", firstListOfAllObjects.waitForExists(moviesListTimeout))
        assertTrue("Expected titles is not displayed on HomeScreen", getTitleFromGrid().waitForExists(moviesListTimeout))
        assertTrue("SearchField is not displayed on HomeScreen", findElementByDescription(searchButton).waitForExists(globalTimeout))
        assertTrue("Settings button is not displayed on HomeScreen", treeDotsSetingsButton.waitForExists(globalTimeout))
        assertTrue("Side category button is not displayed on HomeScreen", sideCategoryMenu.waitForExists(globalTimeout))
    }

    protected fun getGrid(number: Int) =
            firstListOfAllObjects.getChildByInstance(categoryList, number) // it's object of all category moivies in homepage


    public fun clickAndSendTextToSearch(text: String): SearchScreen {
        findElementByDescription(searchButton).click()
        findObjectById(searchField).setText(text)
        return SearchScreen()
    }

    public fun clickOnCustButton(castDeviceName: String) {
        findElementByDescription(castButton).click()
        castMenu.waitForExists(globalTimeout)
        uiDevice.findObject(UiSelector().text(castDeviceName)).click()
    }

    public val textOfTitleInFeaturedCategor get() = featuredTitlesText.text

    public fun getCountOfMovies(i: Int): Int {
        return getGrid(i).getChild(containerOfTitlesSmaller).childCount
    }

    public fun clickOnTitleInFeaturedCateg(): GotIt {
        featuredTitlesText.click()
        return GotIt()
    }

    public val titleInContinueWatching get() = textOfTitleInContnueWatching.text

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
                scrollHomePage.scrollForward()
                i = 0
            }
            i++
            ii++
            if (ii > 125) {
                val rm = ""
                return
            }
        }
        scrollHomePage.scrollTextIntoView("$category")
    }

    fun scrollToTheEndAndClickOnSubCategory(): SubCategoryScreen {
        var number: Int = 0
        while (number != 18) {
            scrollHomePage.scrollToEnd(1)
            number++
        }
        for (j in 0..6) {
            val box = getGrid(j).getChild(textOFCategory)
            if (box.exists()) {
                if (box.text.equals("Special Interest")) {
                    box.click()
                    break
                }
            }
        }
        return SubCategoryScreen()
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
        featuredContainer.setAsHorizontalList().scrollToEnd(swipes)
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
                scrollHomePage.scrollForward()
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
                        scrollHomePage.setAsVerticalList().scrollToEnd(1)
                        getTextOfTitleWithIndex("$category")
                    }
                }
            }

        }
        return textOfMovies
    }

    public fun getTextOFMovie(numberOfView: Int) =
            getGrid(numberOfView).getChild(textOfMovie) //got a first element from the list of movies

    public fun getTextFromFeaturedTitlesCounter(): String {
        return counterOfTitlesInFeaturedContainer.text
    }

    public fun clickOnThreeDots() {
        getGrid(0).getChild(UiSelector().className("android.widget.ImageView")).click()
    }

    private fun getTitleFromGrid() =
            getGrid(0).getChild(titleOfMovie)

    public fun getTextOfCategory(number: Int) =
            getGrid(number).getChild(textOFCategory)

    public fun text(number: Int): Objects {
        val text = getTextOfCategory(number)
        return text(number)
    }


    public fun clickOnThreeDotsSetings(): SettingSmallWindowInRightCorner {
        treeDotsSetingsButton.click()
        return SettingSmallWindowInRightCorner()
    }


    public fun waitForDisapearCategoryText(text: String) {
        uiDevice.wait(Until.gone(By.text(text)), globalTimeout)
    }

    public fun waitForExistsCategoryText(text: String) {
        scrollHomePage.scrollTextIntoView(text)
        Assert.assertTrue("I added title to my queue, problem that queue container is not found on home page", uiDevice.wait(Until.findObject(By.text(text)), globalTimeout).text.equals(text))
    }

    public fun clickOnCategoryWithText(text: String, number: Int) {
        uiDevice.findObject(getTextOfCategory(number).selector.text(text)).click()
    }


    fun clickOnSidecategorButton(): SideCategoryMenuScreen {
        sideCategoryMenu.click()
        return SideCategoryMenuScreen()
    }


    public val textCategory = getTextOfCategory(0).text

    public val title get() = getTextOFMovie(0).text //get text title form the home page

    public fun clickOnTitleNoGotIt(): MovieDatailScreen {
        getTextOFMovie(0).click()
        return MovieDatailScreen()
    }

    public fun clickOnTitle(num: Int): GotIt {
        getTextOFMovie(num).click()
        return GotIt()
    }

    public fun clickOnElementByText(text: String) {
        uiDevice.findObject(UiSelector().text(text)).click()
    }

    public fun clickBack() {
        val pressBack = uiDevice.pressBack()
    }

    public fun longPress(): AddToQueue {
        getTitleFromGrid().dragTo(getTitleFromGrid(), 10)

        return AddToQueue()
    }

    public fun longPressToRemoveFromQueue(obj: UiObject): AddToQueue {
        obj.dragTo(obj, 10)
//        for (i in 1..4) {
//            val category = findElmentByidAnd3LevelsDeeper(firstListOfAllObjectsString, i, 0, 0, false).text
//            if (category.equals("Queue")) {
//                val titleFromQueue = findElmentByidAnd5LevelsDeeper(firstListOfAllObjectsString, i, 0, 0, 2, 0, false)
//                titleFromQueue.dragTo(titleFromQueue, 10)
        return AddToQueue()
    }


//    open class HomeScreenWithContinueWatching() : BaseScreen() {
//        private val titleInContinueWatching = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_home_content_continue_iv"))
//
//        init {
//            Assert.assertTrue("Title is not added to 'History' after watcing 30 sec and click Back", titleInContinueWatching.waitForExists(moviesListTimeout))
//        }
//
//
//        public fun removeFromHistory() {
//            titleInContinueWatching.dragTo(titleInContinueWatching, 10)
//            uiDevice.findObject(UiSelector().resourceId("android:id/select_dialog_listview")).waitForExists(globalTimeout)
//        }
//    }


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
        private val addToQueueLongClick = uiDevice.findObject(UiSelector().resourceId("android:id/text1"))
        private val addToQueueLongClickUiSlector = UiSelector().resourceId("android:id/text1")
        private val smallWindow = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))
        private val facebookSignIn = findObjectById(appPackage + ":id/prompt_image_background")

        init {
            assertTrue("Expected small window with add to queue is not displayed", addToQueueLongClick.waitForExists(moviesListTimeout))

        }

        public fun clickAddToQueueAfterLongClickWithoutReturn() {
            addToQueueLongClick.click()
            if (facebookSignIn.exists()) {
                FacebookSignInForNonRegisterUser().signUpWithFacebookButton
                addToQueueLongClick.click()
            }
        }

        public fun clickRemoveFromHistory() {
            smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0).dragTo(smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0), 3)
            if (facebookSignIn.exists()) {
                FacebookSignInForNonRegisterUser().signUpWithFacebookButton
                smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0).dragTo(smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0), 3)
            }
        }


        public fun clickAddToQueueAfterLongClick(): QueueScreen {
            addToQueueLongClick.click()
            if (facebookSignIn.exists()) {
                FacebookSignInForNonRegisterUser().signUpWithFacebookButton
                addToQueueLongClick.click()
            }
            return QueueScreen()
        }

        inner class FacebookSignInForNonRegisterUser() {
            val headerText = findObjectById(appPackage + ":id/prompt_free_text")
            val textBody = findObjectById(appPackage + ":id/prompt_register_text")
            val signUpWithFacebookButton = uiDevice.findObject(UiSelector().text("Sign Up with Facebook"))
            val signInOrRegisterButton = uiDevice.findObject(UiSelector().text("Sign In or Register"))
            val closeButton = findObjectById(appPackage + ":id/prompt_image_close")

            init {
                assertTrue("Sign In or Register button is not present on facebook screen for when not register user wants add title to Queue", signInOrRegisterButton.waitForExists(globalTimeout))
                assertEquals("Header of text is not correspond requirements on facebook screen for when not register user wants add title to Queue", headerText.text, "Free TV, [free movies, ]free registration")
                assertEquals(textBody.text, "Register now to build your queue, continue")
                assertTrue("SignUp with Facebook button is not present on facebook screen for when not register user wants add title to Queue", signUpWithFacebookButton.exists())
                assertTrue("Close button button is not present on facebook screen for when not register user wants add title to Queue", closeButton.exists())
            }

            fun clickOnSignUpWithFacebook() {
                signUpWithFacebookButton.click()
            }

        }
    }


    class Serials(category: String) : BaseScreen() {
        private val titleWithSerial = uiDevice.findObject(UiSelector().text(category))

        public fun clickOnSerialCategory(): MoviesByCategoryScreen {
            titleWithSerial.click()
            return MoviesByCategoryScreen()
        }
    }

    class SettingSmallWindowInRightCorner() : BaseScreen() {
        private val boxWithSettingsItems = UiCollection(UiSelector().className("android.widget.ListView"))
        private val boxWithSettingsForInit = uiDevice.findObject(UiSelector().className("android.widget.FrameLayout"))
        private val SettingsAboutHelpCenter = UiSelector().resourceId(appPackage + ":id/title")

        init {
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", boxWithSettingsForInit.waitForExists(moviesListTimeout))

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
        private val removeFromHistory = uiDevice.findObject(UiSelector().text("Remove from history"))
        private val addToQueue = uiDevice.findObject(UiSelector().text("Add to queue"))

        init {
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", removeFromHistory.waitForExists(moviesListTimeout))
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", addToQueue.waitForExists(moviesListTimeout))

        }

        public fun clickOnRemoveFromHisory() {
            removeFromHistory.click()
        }
    }


}





