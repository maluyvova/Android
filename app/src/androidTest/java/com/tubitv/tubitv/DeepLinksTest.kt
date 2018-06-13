package com.tubitv.tubitv

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/12/18.
 */
class DeepLinksTest:BaseTest() {
   val comandForDeepLink="am start -W -a android.intent.action.VIEW -d"
   val deepLinkForActionCategory="http://tubitv.com/category/action?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForAnime="tubitv://media-browse?categoryId=1291\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForComedy="https://tubitv.com/category/comedy?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
  val some="http://tubitv.com/movies/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"




    fun ifNotRegistred(){
        launchApp(appPackage,false)
        if (uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_login_adapter_text_view")).waitForExists(globalTimeout)){
            SignInTest().signInWithCorrectEmailAndPassword() }
        killApp()
        Thread.sleep(2000)
    }

   fun ifFirstTime(){
   if(uiDevice.findObject(UiSelector().resourceId("android:id/title")).waitForExists(globalTimeout)){
       uiDevice.findObject(UiSelector().textContains("Tubi TV Staging")).click()
       uiDevice.findObject(UiSelector().resourceId("android:id/button_always")).click()}

    else if( uiDevice.findObject(UiSelector().textContains("Tubi TV Staging")).exists()){
       uiDevice.findObject(UiSelector().textContains("Tubi TV Staging")).click()
       if (uiDevice.findObject(UiSelector().resourceId("android:id/button1")).waitForExists(globalTimeout)){
           uiDevice.findObject(UiSelector().resourceId("android:id/button1")).click()
       }
   }
   }

    @Test
    fun deepLinkForAction(){
        getInstrum()
        ifNotRegistred()
       uiDevice.executeShellCommand(comandForDeepLink+deepLinkForActionCategory+ appPackage)
        ifFirstTime()
       Assert.assertEquals(MoviesByCategoryScreen().categoryText,"Action")
    }
    @Test
    fun deepLinkForAnime(){
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink+deepLinkForAnime+ appPackage)
        ifFirstTime()
        Assert.assertEquals(MoviesByCategoryScreen().categoryText,"Anime")
    }
    @Test
    fun deepLinkForComedy(){
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink+deepLinkForComedy+ appPackage)
        ifFirstTime()
        Assert.assertEquals(MoviesByCategoryScreen().categoryText,"Comedy")
    }
    @Test
    fun deepLink(){
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink+some+ appPackage)
        ifFirstTime()
        Assert.assertEquals(MovieDatailScreen().titleDatailScreen,"Oldboy")
    }

}