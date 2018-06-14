package com.tubitv.tubitv

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import com.tubitv.tubitv.Screens.PlayBackScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/12/18.
 */
class DeepLinksTest:BaseTest() {

    val comandForDeepLink="am start -W -a android.intent.action.VIEW -d "
   val deepLinkForActionCategory="http://tubitv.com/category/action?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForSerial="https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForComedy="https://tubitv.com/category/comedy?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
  val deepLinkForMovie ="http://tubitv.com/movies/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
  val anotherDeepLinkForMovie="https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
  //val deepLinkForPlayback="tubitv://media-playback?contentId=294111\\&contentType=movie\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "



    fun ifNotRegistred(){
        launchApp(appPackage,false)
        if (uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_login_adapter_text_view")).waitForExists(globalTimeout)){
            SignInTest().signInWithCorrectEmailAndPassword() }
        killApp()

    }

   fun ifFirstTime(){
   if(uiDevice.findObject(UiSelector().resourceId("android:id/title")).waitForExists(globalTimeout)) {
     //  for(i in 1..2)
       uiDevice.findObject(UiSelector().textContains("Tubi TV Staging")).click()
     if ( uiDevice.findObject(UiSelector().resourceId("android:id/button_once")).exists())
       uiDevice.findObject(UiSelector().resourceId("android:id/button_once")).click()
       else  uiDevice.click(200,200)
   }
    else if( uiDevice.findObject(UiSelector().textContains("Tubi TV Staging")).exists()){
       uiDevice.findObject(UiSelector().textContains("Tubi TV Staging")).click()
       if (uiDevice.findObject(UiSelector().resourceId("android:id/button1")).waitForExists(globalTimeout)){
           uiDevice.findObject(UiSelector().resourceId("android:id/button1")).click()
       }
       else if ((uiDevice.findObject(UiSelector().text("OK")).exists())){
           uiDevice.findObject(UiSelector().text("OK")).click()}
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
    fun deepLinkForSerial(){
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink+deepLinkForSerial+ appPackage)
        ifFirstTime()
        Assert.assertEquals(MovieDatailScreen().titleDatailScreen,"Dog the Bounty Hunter")
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
    fun deepLinkForMovie(){
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink+ deepLinkForMovie + appPackage)
        ifFirstTime()
        Assert.assertEquals(MovieDatailScreen().titleDatailScreen,"Oldboy")
    }
    @Test
    fun deepLinkForVideo(){
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink+ anotherDeepLinkForMovie + appPackage)
        ifFirstTime()
        Assert.assertEquals(MovieDatailScreen().titleDatailScreen,"Oldboy")
    }
   /* @Test
    fun playbackDeepLink(){
        getInstrum()
        ifNotRegistred()
      uiDevice.executeShellCommand(comandForDeepLink+deepLinkForPlayback+ appPackage)
        ifFirstTime()
       val title= PlayBackScreen().waitForPlayBack()
        Assert.assertEquals(title,"Oldboy")
    }*/


}