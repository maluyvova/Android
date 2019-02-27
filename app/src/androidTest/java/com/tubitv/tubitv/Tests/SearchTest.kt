package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.Categories
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.TitlesForSearchScreen
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by vburian on 4/4/18.
 */
class SearchTest : LaunchAppWithFacebook() {

    val textWhatTestIsLookingFor = "zombie"
    @Test
    fun verifyIfTitleAfterSearchContainText() {
        val homeScreen = HomeScreen(true)
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = homeScreen.clickOnSearch()
                .provideTextToSearch(textWhatTestIsLookingFor)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        assertTrue("Title name is not contain text which test has provided provided in search field", textOfTitle.contains(textWhatTestIsLookingFor))
    }


    @Test
    fun slideTitleOnTheSide() {
        val homeScreen = HomeScreen(true)
        val gotIt = homeScreen.clickOnSearch()
                .provideTextToSearch(textWhatTestIsLookingFor)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val titleText = titleDatailScreen.titleName
        titleDatailScreen.rightscrollScreenOnSide(3)
        val titleTextAfterSlides = titleDatailScreen.titleName
        titleDatailScreen.leftscrllScreenOnSide(3)
        val titleorig = titleDatailScreen.titleName
        assertEquals("Scroll on the side doesn't wrok", titleText, titleorig)
        assertNotEquals("Scroll on the side doesn't work", titleText, titleTextAfterSlides)
    }

    @Test
    fun clickOnFirstSuggestion() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val textOfProposition = searchScreen.getTextFromProposition(1).toLowerCase()
        val gotIt = searchScreen.clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        assertTrue("Title name is not contain text which test has provided provided in search field, text of suggestion: $textOfProposition text from title details page: $textOfTitle", textOfTitle.contains(textOfProposition))
    }

    @Test
    fun clickOnSecondSuggestion() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val textOfProposition = searchScreen.getTextFromProposition(2).toLowerCase()
        val gotIt = searchScreen.clickOnProposition(2)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        assertTrue("Title name is not contain text which test has provided provided in search field, text of suggestion: $textOfProposition text from title details page: $textOfTitle", textOfTitle.contains(textOfProposition))
    }

    @Test
    fun clickOnThirdSuggestion() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val textOfProposition = searchScreen.getTextFromProposition(3).toLowerCase()
        val gotIt = searchScreen.clickOnProposition(3)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        assertTrue("Title name is not contain text which test has provided provided in search field, text of suggestion: $textOfProposition text from title details page: $textOfTitle", textOfTitle.contains(textOfProposition))
    }

    @Test
    fun clickOnFirstSuggestionAndNavigateBackCheckIfSearchedViewIsSaved() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val textOfProposition = searchScreen.getTextFromProposition(1)
        val gotIt = searchScreen.clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName
        titleDatailScreen.clickOnBackButton()
        TitlesForSearchScreen(textOfProposition).clickOnTitle(0)
                .clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
                .clickOnNativeBackForMovie()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(textOfTitle)
                .clickRemoveFromHistory()
        assertTrue("Title name is not contain text which test has provided provided in search field, text of suggestion: $textOfProposition text from title details page: $textOfTitle", textOfTitle.toLowerCase().contains(textOfProposition.toLowerCase()))
    }


    @Test
    fun clickOnSuggestionAndCheckIfThatAddedToRecentSearches() {
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnSearch()
                .clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnSearch()
                .clickOnRecentSearches()
    }

    @Test
    fun clickOnClearAndCheckThatRecentSearchesDisappear() {
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnSearch()
                .clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
        BaseScreen().navigateBackToHomeScreen()
        val searchScreen = homeScreen.clickOnSearch()
        searchScreen.clickOnClear()
        searchScreen.getTextFromProposition(0)
        assertNotEquals("The 'Recent searches' still on screen, but I clicked to clear it", searchScreen.getTextFromProposition(0), "RECENT SEARCHES")
        assertEquals("First element have to be 'Trending searches'", searchScreen.getTextFromProposition(0), "TRENDING SEARCHES")
    }

    @Test
    fun clickOnClearAndCheckThatRecentSearchesDisappearMovieWasProvidedToSearch() {
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnSearch()
                .provideTextToSearch("shark")
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
        BaseScreen().navigateBackToHomeScreen()
        val searchScreen = homeScreen.clickOnSearch()
        searchScreen.clickOnClear()
        searchScreen.getTextFromProposition(0)
        assertNotEquals("The 'Recent searches' still on screen, but I clicked to clear it", searchScreen.getTextFromProposition(0), "RECENT SEARCHES")
        assertEquals("First element have to be 'Trending searches'", searchScreen.getTextFromProposition(0), "TRENDING SEARCHES")
    }

    @Test
    fun checkThatWeShowJust3PriviosSearches() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val gotIt = searchScreen.clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
        for (i in 4..8 step 2) {
            BaseScreen().navigateBackToHomeScreen()
            homeScreen.clickOnSearch()
                    .clickOnProposition(i)
        }
        uiDevice.pressBack()
        var elements = searchScreen.verifyHowManyElementsBetweenRecentAndTrending()
        assertTrue("We save just 3 recent search from user, but this test found that we show $elements", elements == 3)
    }

    @Test
    fun checkIfWeShowRecentUserSearch() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val gotIt = searchScreen.clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
        for (i in 4..8 step 2) {
            BaseScreen().navigateBackToHomeScreen()
            homeScreen.clickOnSearch()
                    .clickOnProposition(i)
        }
        uiDevice.pressBack()
        val mostRecentSearch = searchScreen.provideTextToSearch("Hello")
                .clickOnCloseClearButton()
                .getTextFromProposition(1)
        assertEquals("Looks like search screen doesn't save recent user search", mostRecentSearch, "Hello")
    }

    @Test
    fun provide2CharToSearchField() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        searchScreen.provideTextToSearch("LM")
                .clickOnCloseClearButton()
        val mostRecentSearch = searchScreen.getTextFromRecentSearch()
        assertEquals("Looks like search screen doesn't save recent user search, test provided 2 char to search field", mostRecentSearch, "LM")
    }

    @Test
    fun getMessageForNoResults() {
        val searchedString = "LMRSGS"
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val textNoResults = searchScreen.provideTextToSearch(searchedString)
                .getMessageWhenNoResultsReturned()
        uiDevice.pressBack()
        val mostRecentSearch = searchScreen.getTextFromRecentSearch()
        assertEquals("Looks like search screen doesn't save resent user search of $searchedString", mostRecentSearch, searchedString)
        assertEquals("If nothing was returned we show message, but message doesn't correspond requirements", textNoResults.trim(), "We could't find any results for\n" +
                "\"LMRSGS\"\n" +
                " please try again.")

    }

    @Test
    fun provideLongStringToSearchField() {
        val searchedString = "LMRSGJKHJKHJKHKLJMMKMKLLLMMMMMMNNNMNNNLKLJJKHJKGGGGS"
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val textNoResults = searchScreen.provideTextToSearch(searchedString)
                .getMessageWhenNoResultsReturned()
        uiDevice.pressBack()
        val mostRecentSearch = searchScreen.getTextFromRecentSearch()
        assertEquals("Looks like search screen doesn't save resent user search of $searchedString", mostRecentSearch, searchedString)
        assertEquals("If nothing was returned we show message, but message doesn't correspond requirements", textNoResults.trim(), "We could't find any results for\n" +
                "\"$searchedString\"\n" +
                " please try again.")
    }

    @Test
    fun searchForHuluTitles() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
        val huluTitle = searchScreen.provideTextToSearch("777")
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
                .checkIfThisHuluTitle()
        assertTrue("I provided hulu title in search but I can't find it, maybe title already expired ", huluTitle)
    }

    @Test
    fun checkSearchScreenDoesntSaveInstanceAfterClear() {
        val homeScreen = HomeScreen(true)
        val searchScreen = homeScreen.clickOnSearch()
                .clickOnProposition(1)
                .clickOnCloseClearButton()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSearch()
                .clickOnClear()         //if this button doesn't exists, that means instance was saved, but test cliked on clear
    }

    @Test
    fun signInAndThenMakeSearch() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        homeScreen.clickOnSearch()
                .clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
                .clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(0)
                .clickRemoveFromHistory()
    }

    @Test
    fun naviageToSearchFromBrowseMenu() {
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnBrowseButton()
                .clickOnSearch()
                .clickOnProposition(3)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
                .clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(0)
                .clickRemoveFromHistory()
    }

    @Test
    fun navigateToSearchFromAccountMenu() {
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnAccountButton()
                .clickOnSearch()
                .clickOnProposition(1)
                .clickOnFirstTitleFirstTime()
                .clickOnGotIt()
                .clickOnPlay()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(0)
                .clickRemoveFromHistory()
    }


}