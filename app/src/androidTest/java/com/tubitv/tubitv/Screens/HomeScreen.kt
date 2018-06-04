package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert
import java.lang.reflect.AccessibleObject
import java.util.*

/**
 * Created by vburian on 2/19/18.
 */
open class HomeScreen:BaseScreen(){

    protected val firstListOfAllObjects = UiCollection(UiSelector().resourceId(appPackage + ":id/view_category_recycler"))
    public val scrollHomePage=UiScrollable(UiSelector().resourceId(appPackage+":id/view_category_recycler"))
    private val categoryList = UiSelector().resourceId(appPackage+":id/view_content_recycler_ll")
    private val textOfMovie = UiSelector().resourceId(appPackage+":id/view_home_content_title_tv")
    private val titleOfMovie =    UiSelector().resourceId(appPackage+":id/view_home_content_iv")
    public val textOFCategory =UiSelector().resourceId(appPackage+":id/view_content_recycler_category_title")
    private val featuredContainer=UiScrollable(UiSelector().resourceId(appPackage+":id/bannerContainer"))
    private val featuredTitlesText=UiDeviceID(appPackage+":id/banner_title")
    private val sideCategoryMenu=uiDevice.findObject(UiSelector().className("android.widget.ImageButton"))
    private val treeDotsSetingsButton=uiDevice.findObject(UiSelector().description("More options"))
    private val containerOfTitlesSmaller=UiSelector().resourceId(appPackage+":id/view_content_recycler")
    private val textOfTitleInContnueWatching=UiDeviceID(appPackage+":id/view_home_content_continue_title_tv")
    private val playButtonForContinueWatching=UiDeviceID(appPackage+":id/view_home_content_continue_play_btn")
    private val searchButton=uiDevice.findObject(UiSelector().description("Search"))
    private val searchField=UiDeviceID(appPackage+":id/nav_app_bar_main_search")
    private val custButton=uiDevice.findObject(UiSelector().description("Cast button. Disconnected"))
    private val castMenu=UiDeviceID(appPackage+":id/action_bar_root")
    init{

        Assert.assertTrue("Expected first List of All Objects is not displayed",firstListOfAllObjects.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected titles is not displayed",getTitleFromGrid().waitForExists(moviesListTimeout))
    }
    protected fun getGrid(number:Int) =
            firstListOfAllObjects.getChildByInstance(categoryList,number) // it's object of all category moivies in homepage


    public fun clickAndSendTextToSearch(text:String):SearchScreen{
      searchButton.click()
        searchField.setText(text)
        return SearchScreen()
    }
    public fun clickOnCustButton(castDeviceName:String){
     custButton.click()
        castMenu.waitForExists(globalTimeout)
        uiDevice.findObject(UiSelector().text(castDeviceName)).click()
    }
    public val textOfTitleInFeaturedCategor get() = featuredTitlesText.text

    public fun getCountOfMovies(i:Int):Int{
        return getGrid(i).getChild(containerOfTitlesSmaller).childCount
    }

    public fun clickOnTitleInFeaturedCateg():GotIt{
        featuredTitlesText.click()
        return GotIt()
    }
    public val titleInContinueWatching get()=textOfTitleInContnueWatching.text

    fun ScrollToSpecificCategory(s:String ){
        scrollHomePage.scrollTextIntoView("$s")
    }

    fun scrollToTheEndAndClickOnSubCategory():SubCategoryScreen{
        var number:Int=0
        while(number!=18){
            scrollHomePage.scrollToEnd(1)
            number++
        }
        for (j in 0..6){
           val box= getGrid(j).getChild(textOFCategory)
            if (box.exists()){
            if( box.text.equals("Special Interest")){
                box.click()
            break}
        }}
     return SubCategoryScreen()
    }

    fun horisontalScrollTitles(swipes:Int,category:String){
        for (i in 1..6){
            val box= getGrid(i).getChild(textOFCategory)
            if( box.text=="$category"){
                for(j in 1..6){
                getGrid(i).getChild(containerOfTitlesSmaller).swipeLeft(swipes)}
            break}
        }
    }
    fun scrollFeaturetTitles(swipes: Int){
        featuredContainer.setAsHorizontalList().scrollToEnd(swipes)
    }

    fun getText(category: String):String{
        var textOfMovies=""
        for (i in 0..6){
            if (getGrid(i).getChild(textOFCategory).exists()){
                val box= getGrid(i).getChild(textOFCategory).text
                if( box.equals("$category")){
                    if (getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).exists()){
                        textOfMovies= getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).text
                        break }
    }}}
        return textOfMovies}

    fun getTextOfTitleWithIndex(category: String):String{
        var textOfMovies=""
        for (i in 0..6){
            if (getGrid(i).getChild(textOFCategory).exists()){
            val box= getGrid(i).getChild(textOFCategory).text
            if( box.equals("$category")){
                if (getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).exists()){
                textOfMovies= getGrid(i).getChild(containerOfTitlesSmaller).getChild(textOfMovie).text
                    break }
                else {scrollHomePage.setAsVerticalList().scrollToEnd(1)
                  getTextOfTitleWithIndex("$category")
                  }}}

        }
    return textOfMovies}

    public fun getTextOFMovie(numberOfView:Int) =
            getGrid(numberOfView).getChild(textOfMovie) //got a first element from the list of movies


    public fun clickOnThreeDots(){
                getGrid(0).getChild(UiSelector().className("android.widget.ImageView")).click()
    }

    private fun getTitleFromGrid()=
            getGrid(0).getChild(titleOfMovie)

    public   fun getTextOfCategory(number:Int)=
            getGrid(number).getChild(textOFCategory)

public fun text(number: Int):Objects{
    val text=getTextOfCategory(number)
    return text(number)
}



    public fun clickOnThreeDotsSetings():SettingSmallWindowInRightCorner{
        treeDotsSetingsButton.click()
        return SettingSmallWindowInRightCorner()
    }



    public fun waitForDisapearCategoryText(text:String){
         uiDevice.wait(Until.gone(By.text(text)), globalTimeout)
    }

    public fun waitForExistsCategoryText(text: String){
        uiDevice.wait(Until.findObject(By.text(text)), globalTimeout)
    }

    public fun clickOnCategoryWithText(text:String,number: Int){
      uiDevice.findObject(getTextOfCategory(number).selector.text(text)).click()
    }


    fun clickOnSidecategorButton():SideCategoryMenuScreen{
        sideCategoryMenu.click()
        return SideCategoryMenuScreen()
    }


    public val textCategory=getTextOfCategory(0).text

    public val title get() = getTextOFMovie(0).text //get text title form the home page

    public fun clickOnTitleNoGotIt():MovieDatailScreen{
        getTextOFMovie(0).click()
        return MovieDatailScreen()
    }


    public fun clickOnTitle():GotIt{
        getTextOFMovie(0).click()
        return GotIt()
    }
    public fun clickBack(){
        val pressBack = uiDevice.pressBack()
    }

    public fun longPress():AddToQueue{
        getTitleFromGrid().dragTo(getTitleFromGrid(),10)

return AddToQueue()
    }

    public fun longPressToRemoveFromQueue():AddToQueue{
        getTitleFromGrid().dragTo(getTitleFromGrid(),10)
        return AddToQueue()
    }

   open class HomeScreenWithContinueWatching():BaseScreen(){
       private val titleInContinueWatching = uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_home_content_continue_iv"))

        init{
            Assert.assertTrue("Title is not added to 'History' after watcing 30 sec and click Back",titleInContinueWatching.waitForExists(moviesListTimeout))
        }


        public fun removeFromHistory(){
            titleInContinueWatching.dragTo(titleInContinueWatching,10)
            uiDevice.findObject(UiSelector().resourceId("android:id/select_dialog_listview")).waitForExists(globalTimeout)
        }
    }



    class RemoveFormHistoryScreen():BaseScreen(){
        private val boxWithRemoveFromHistory = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))
        private val removeFromHistory=UiSelector().resourceId("android:id/text1")
        init{
            Assert.assertTrue("Title is not added to 'History' after watcing 30 sec and click Back",boxWithRemoveFromHistory.waitForExists(moviesListTimeout))
        }
        protected fun getBottonRemoveFromHistory()=
            boxWithRemoveFromHistory.getChildByInstance(removeFromHistory,0)


        public fun clickOnRemoveFromHistory(){
            getBottonRemoveFromHistory().click()


        }

    }


    class QueueScreen():BaseScreen(){
        var homescreen=HomeScreen()
        private val queueList=UiSelector().resourceId(appPackage+":id/view_content_recycler_ll")
        private val titlesInQueue=UiSelector().resourceId(appPackage+":id/view_home_content_iv")
       init{Assert.assertTrue("Expected queue is not displayed",getQueuFromGrid().waitForExists(globalTimeout))}
        protected fun getGrid(number:Int) =
                homescreen.firstListOfAllObjects.getChildByInstance(queueList,number) // it's object of all category moivies in

        private fun getQueuFromGrid()=
                getGrid(0).getChild(homescreen.textOfMovie)

        private fun getFirstTitleInQueu()=
            getGrid(0).getChild(homescreen.textOfMovie)

        public val textFromFirstTitleInQueue get()=getQueuFromGrid().text



    }


     class AddToQueue():BaseScreen(){
         private val addToQueueLongClick=uiDevice.findObject(UiSelector().resourceId("android:id/text1"))
     init{
         Assert.assertTrue("Expected small window with add to queue is not displayed",addToQueueLongClick.waitForExists(moviesListTimeout))

     }

         public fun clickAddToQueueAfterLongClickWithoutReturn(){
             addToQueueLongClick.click()
         }


         public fun clickAddToQueueAfterLongClick():QueueScreen{
             addToQueueLongClick.click()
             return QueueScreen()
         }
     }

    class Serials():BaseScreen(){
        private val titleWithSerial=uiDevice.findObject(UiSelector().text("Most Popular TV Shows"))

        public  fun clickOnSerialCategory():MoviesByCategoryScreen{
            titleWithSerial.click()
            return MoviesByCategoryScreen()
        }
    }

    class SettingSmallWindowInRightCorner():BaseScreen(){
       private val boxWithSettingsItems=UiCollection(UiSelector().className("android.widget.ListView"))
        private val boxWithSettingsForInit=uiDevice.findObject(UiSelector().className("android.widget.FrameLayout"))
         private val SettingsAboutHelpCenter=UiSelector().resourceId(appPackage+":id/title")
    init {
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", boxWithSettingsForInit.waitForExists (moviesListTimeout))

    }

        fun clickOnSettings():SettingsScreen{
            boxWithSettingsItems.getChildByInstance(SettingsAboutHelpCenter,0).click()
            return SettingsScreen()
        }
    }
    class History():BaseScreen(){
        private val removeFromHistory=uiDevice.findObject(UiSelector().text("Remove from history"))
        private val addToQueue=uiDevice.findObject(UiSelector().text("Add to queue"))
        init {
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", removeFromHistory.waitForExists (moviesListTimeout))
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", addToQueue.waitForExists (moviesListTimeout))

        }

        public fun clickOnRemoveFromHisory(){
            removeFromHistory.click()
        }
    }



    }





