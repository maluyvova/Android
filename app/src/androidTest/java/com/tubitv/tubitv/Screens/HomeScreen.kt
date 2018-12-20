package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import junit.framework.Assert

import java.util.*
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.*
import com.tubitv.tubitv.Enomus.DirectionOfScrolling
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Helpers.TextExceptionWithError
import junit.framework.Assert.assertTrue


/**
 * Created by vburian on 2/19/18.
 */
open class HomeScreen(checkForObject: Boolean) : BaseScreen() {

    private val firstListOfAllObjectsString = appPackage + ":id/view_category_recycler"
    val firstListOfAllObjects = UiCollection(UiSelector().resourceId(firstListOfAllObjectsString))
    private val scrollHomePage = appPackage + ":id/view_category_recycler"
    private val categoryList = UiSelector().resourceId(appPackage + ":id/view_content_recycler_ll")
    val textOfMovie = UiSelector().resourceId(appPackage + ":id/view_home_content_title_tv")
    private val titleOfMovie = UiSelector().resourceId(appPackage + ":id/view_home_content_iv")
    public val textOFCategory = UiSelector().resourceId(appPackage + ":id/view_content_recycler_category_title") //change it
    val textOfCategoryId = appPackage + ":id/view_content_recycler_category_title"
    private val featuredContainer = appPackage + ":id/bannerContainer"
    private val featuredTitlesText = appPackage + ":id/banner_title"
    private val counterOfTitlesInFeaturedContainer = appPackage + ":id/numIndicator"
    private val sideCategoryMenu = "Open navigation drawer"
    private val treeDotsSetingsButton = "More options"
    private val containerOfTitlesSmaller = UiSelector().resourceId(appPackage + ":id/view_content_recycler")
    private val textOfTitleInContnueWatching = appPackage + ":id/view_home_content_continue_title_tv"
    private val searchButton = "Search"
    private val searchField = appPackage + ":id/nav_app_bar_main_search"
    private val castButton = "Cast button. Disconnected"
    private val castMenu = appPackage + ":id/action_bar_root"
    private val smallWindowForLongPress = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))
    private var intForScrolling = 0;


    init {
        if (checkForObject) {
            if (!findElementById(counterOfTitlesInFeaturedContainer, false).waitForExists(mediumWaitTime)) {
                for (i in 0..2) {
                    scrollableScreenById(scrollHomePage).setAsVerticalList().scrollToBeginning(i)
                    scrollableScreenById(scrollHomePage).setAsVerticalList().flingBackward()
                }
            }
            if (uiDevice.findObject(UiSelector().resourceId(counterOfTitlesInFeaturedContainer)).exists()) {
                assertTrue("Counter for Featured titles is not displayed on HomeScreen", findElementById(counterOfTitlesInFeaturedContainer, false).waitForExists(globalTimeout))
                assertTrue("Expected first List of All Objects is not displayed on HomeScreen", firstListOfAllObjects.waitForExists(moviesListTimeout))
                assertTrue("Expected titles is not displayed on HomeScreen", getTitleFromGrid().waitForExists(moviesListTimeout))
                assertTrue("SearchField is not displayed on HomeScreen", findElementByDescription(searchButton, false).waitForExists(globalTimeout))
                assertTrue("Settings button is not displayed on HomeScreen", findElementByDescription(treeDotsSetingsButton, false).waitForExists(globalTimeout))
                assertTrue("Side category button is not displayed on HomeScreen", findByContentDesc(sideCategoryMenu, false).waitForExists(globalTimeout))
            }
        }
    }

    protected fun getGrid(number: Int) =
            firstListOfAllObjects.getChildByInstance(categoryList, number) // it's object of all category moivies in homepage


    public fun clickAndSendTextToSearch(text: String): SearchScreen {
        try {
            findElementByDescription(searchButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find search button", e)
        }
        try {
            findObjectById(searchField, false).setText(text)
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find search field", e)
        }

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

    fun scrollToSpecificCategory(category: String, directionOfScrolling: DirectionOfScrolling): String {
        if (intForScrolling > 3) {
            return category
        }
        var box = ""
        var i = 0
        var ii = 0
        while (i < 10) {
            if (getGrid(i).getChild(textOFCategory).waitForExists(if (i < 3) globalTimeout else 5)) {
                box = getGrid(i).getChild(textOFCategory).text
                if (box.contains("$category")) {
                    if (getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).exists()) {
                        break
                    }
                }
            } else if (i > getChildCount(firstListOfAllObjectsString, textOfCategoryId)) {
                if (directionOfScrolling.equals(DirectionOfScrolling.DOWN))
                    scrollableScreenById(scrollHomePage).scrollForward()
                else
                    scrollableScreenById(scrollHomePage).scrollBackward()
                i = 0
            }
            i++
            ii++
            if (ii > 70) {
                TestException("Can't catch $category on home screen while scrolling down")
            }
        }
        try {
            findElementByText(box, false).click()
        } catch (e: UiObjectNotFoundException) {
            intForScrolling++
            scrollToSpecificCategory(category, DirectionOfScrolling.UP)
//            scrollableScreenById(scrollHomePage).scrollTextIntoView(box)
//            findElementByText(box, false).click()
        }
        return box
    }

    fun scrolDownLittleBit() {
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
        findByContentDesc(sideCategoryMenu, false).click()
        return SideCategoryMenuScreen()
    }


    fun textCategory(): String {
        return getTextOfCategory(0).text
    }

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
        return AddToQueue(true)
    }

    public fun longPressWithoutReturn() {
        getTitleFromGrid().dragTo(getTitleFromGrid(), 10)
    }

    fun dismissCasting() {
        getTitleFromGrid().dragTo(getTitleFromGrid(), 10)
        if (smallWindowForLongPress.waitForExists(shortWaitTime)) {
            uiDevice.pressBack()
        }
    }
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
    var homescreen = HomeScreen(true)
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


class AddToQueue(checkForObjects: Boolean) : BaseScreen() {
    private val addToQueueLongClick = "android:id/text1"
    private val addToQueueLongClickUiSlector = UiSelector().resourceId("android:id/text1")
    private val smallWindow = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))


    init {
        if (checkForObjects)
            assertTrue("Expected small window with add to queue is not displayed", findElementById(addToQueueLongClick, false).waitForExists(moviesListTimeout))

    }

    public fun clickAddToQueueAfterLongClickWithoutReturn(): Boolean {
        val facebook = false
        findObjectById(addToQueueLongClick, false).click()
        if (findElementById(facebookSignIn, false).exists()) {
            FacebookSignInForNonRegisterUser().clickOnSignUpWithFacebook()
            return facebook
        }
        return true
    }

    public fun clickRemoveFromHistory() {
        smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0).dragTo(smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0), 3)
        if (findElementById(facebookSignIn, false).exists()) {
            FacebookSignInForNonRegisterUser().clickOnSignUpWithFacebook()
            smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0).dragTo(smallWindow.getChildByInstance(addToQueueLongClickUiSlector, 0), 3)
        }
    }


    public fun clickAddToQueueAfterLongClick(): QueueScreen {
        findObjectById(addToQueueLongClick, false).click()
        if (findElementById(facebookSignIn, false).exists()) {
            FacebookSignInForNonRegisterUser().clickOnSignUpWithFacebook()
            findObjectById(addToQueueLongClick, false).click()
        }
        return QueueScreen()
    }

    fun clickAddToQueueAfterLongClickForSignIn(): FacebookSignInForNonRegisterUser {
        findObjectById(addToQueueLongClick, false).click()
        return FacebookSignInForNonRegisterUser()
    }

    inner class FacebookSignInForNonRegisterUser() {
        val continueFacebook = uiDevice.findObject(UiSelector().resourceId("u_0_9"))
        val continueFacebookClass = uiDevice.findObject(UiSelector().className("android.widget.Button"))
        private val headerText = appPackage + ":id/prompt_free_text"
        private val textBody = appPackage + ":id/prompt_register_text"
        private val signUpWithFacebookButton = "Sign Up with Facebook"
        private val signInOrRegisterButton = "Sign In or Register"
        private val closeButton = appPackage + ":id/prompt_image_close"

        init {
            assertTrue("Sign In or Register button is not present on facebook screen for when not register user wants add title to Queue", findElementByText(signInOrRegisterButton, false).waitForExists(globalTimeout))
            //assertEquals("Header of text is not correspond requirements on facebook screen for when not register user wants add title to Queue", findObjectById(headerText, false).text, "Free TV,"+"\n"+"free movies, "+"\n"+"free registration")
            // assertEquals(findObjectById(textBody, false).text, "Register now to build your queue, continue")
            assertTrue("SignUp with Facebook button is not present on facebook screen for when not register user wants add title to Queue", findElementByText(signUpWithFacebookButton, false).exists())
            //  assertTrue("Close button button is not present on facebook screen for when not register user wants add title to Queue", findElementById(closeButton, false).exists())
        }

        fun clickOnSignUpWithFacebook() {
            findElementByText(signUpWithFacebookButton, false).click()
            if (continueFacebook.waitForExists(globalTimeout)) {
                continueFacebook.click()
            }
            if (continueFacebookClass.waitForExists(globalTimeout)) {
                continueFacebookClass.click()
            }
        }

        fun clickOnSignIn() {
            try {
                findElementByText(signInOrRegisterButton, false).click()
            } catch (e: UiObjectNotFoundException) {
                TestException("Sign In or Register button doesn't exist on 'Sign in' screen after add to queue for not register user")
            }
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

    fun clickOnHelpCenter(): HelpCenterWebView {
        boxWithSettingsItems.getChildByInstance(SettingsAboutHelpCenter, 2).click()
        return HelpCenterWebView()
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







