package com.tubitv.tubitv.Helpers

import android.graphics.BitmapFactory
import android.os.Environment
import com.tubitv.tubitv.Enomus.ScreensForComparing
import com.tubitv.tubitv.Screens.BaseScreen
import java.io.File
import java.lang.Math.abs
import java.lang.Thread.sleep

/**
 * Created by vburian on 12/21/18.
 */
class ScreenComparing(titleName: String, whichScreen: ScreensForComparing) : BaseScreen() {

    private val whichScreen = whichScreen
    private var folderForTitle = File("")
    private var folderForScreenShoots = File("")
    private var folderForScreenRecords = File("")
    //    private val dir  = File("/storage/sdcard/Pictures/app_screenshots")
    private val titleName = titleName.replace("\\s".toRegex(), "")   //name of movie that in testing
    private var secondScreenShoot: File = File("")
    private var firstScreenShoot: File = File("")
    //private val blackScreen = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + File.separator + "BlackScreen" + ".jpg")
    private var folderIsCreated = false



    fun getFolderForRecords():String{
        return folderForTitle.name
    }

    fun getDifferencePercent(time: String): Double {
        val firstScreenShoot = createFolderForScreenShoots(time).first
//        if (whichScreen.equals(ScreensForComparing.BLACK_SCREEN)) {
//            secondScreen = blackScreen
//        } else {
        val secondScreenShoot = createFolderForScreenShoots(time).second   //it can be second screen or black
        //   }
        val img1 = BitmapFactory.decodeFile(firstScreenShoot.path)
        val img2 = BitmapFactory.decodeFile(secondScreenShoot.path)
        val width2 = img2.width
        val height2 = img2.height
        val width = img1.width
        val height = img1.height
        if (width != width2 || height != height2) {
            val f = "(%d,%d) vs. (%d,%d)".format(width, height, width2, height2)
            throw IllegalArgumentException("Images must have the same dimensions: $f") as Throwable
        }
        var diff = 0L
        for (y in 0 until height) {
            for (x in 0 until width) {
                diff += pixelDiff(img1.getPixel(x, y), img2.getPixel(x, y))
            }
        }
        val maxDiff = 3L * 255 * width * height
        return 100.0 * diff / maxDiff
    }

    private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
        val r1 = (rgb1 shr 16) and 0xff
        val g1 = (rgb1 shr 8) and 0xff
        val b1 = rgb1 and 0xff
        val r2 = (rgb2 shr 16) and 0xff
        val g2 = (rgb2 shr 8) and 0xff
        val b2 = rgb2 and 0xff
        return abs(r1 - r2) + abs(g1 - g2) + abs(b1 - b2)
    }

    fun wakeUpScreen() {
        uiDevice.swipe(385, 317, 1500, 483, 2)
    }

    fun assignFolderForTitle() {
        var dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                this.titleName)
        var i = 0
        while (dir.exists()) {
            dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    this.titleName + i)
            i++
        }
        folderForTitle = dir
        folderForScreenShoots = File(folderForTitle.path + File.separator + "app_screenshots")
        folderForScreenRecords = File(folderForTitle.path + File.separator + "app_screenRecords")
        folderIsCreated = true

    }

    private fun createFolderForScreenShoots(time: String): Pair<File, File> {
        if (this.folderIsCreated) {
            secondScreenShoot = this.firstScreenShoot
            this.firstScreenShoot.delete()
            if (this.firstScreenShoot.exists()) {
                throw TestException("First ScreenSoot is not deleted ${this.firstScreenShoot.path}")
            }
        }
        if (!this.folderIsCreated) {
            assignFolderForTitle()
        }
        if (!this.folderForTitle.exists()) {
            if (!this.folderForTitle.mkdirs()) {
                throw TestException("Can't create folder for screenshoots on device: $deviceName path: ${folderForScreenShoots.path}")
            }
        }
        if (!this.folderForScreenShoots.exists()) {
            if (!this.folderForScreenShoots.mkdirs()) {
                throw TestException("Can't create folder for screenshoots on device: $deviceName path: ${folderForScreenShoots.path}")
            }
        }
        if (!this.folderForScreenRecords.exists()) {
            if (!this.folderForScreenRecords.mkdirs()) {
                throw TestException("Can't create folder for screenshoots on device: $deviceName path: ${folderForScreenRecords.path}")
            }
        }
        this.firstScreenShoot = File(this.folderForScreenShoots.path + File.separator + "FirstScreenShotOf${this.titleName}" + "${time.replace("\\s".toRegex(), "")}" + ".jpg")
        val justForRetrun = File("")
        if (!firstScreenShoot.exists()) {
            sleep(1000)
            wakeUpScreen()
            uiDevice.takeScreenshot(firstScreenShoot)
        }
        this.secondScreenShoot = File(this.folderForScreenShoots.path + File.separator + "SecondScreenShotOf${this.titleName}" + "${time.replace("\\s".toRegex(), "")}" + ".jpg")
        sleep(8000)
        wakeUpScreen()
        uiDevice.takeScreenshot(this.secondScreenShoot)
        return Pair(firstScreenShoot, this.secondScreenShoot)
    }

    fun deleteFolderForTitle() {
        if (folderIsCreated) {
          folderForTitle.deleteRecursively()
        }
        if (folderForTitle.exists()) {
            throw TestException("Can't delete folder with screenshots")

        }
    }

}