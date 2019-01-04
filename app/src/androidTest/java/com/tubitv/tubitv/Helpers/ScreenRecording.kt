package com.tubitv.tubitv.Helpers

import android.os.Environment
import com.tubitv.tubitv.Screens.BaseScreen
import java.io.File

/**
 * Created by vburian on 12/27/18.
 */
class ScreenRecording: BaseScreen() {

    private val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "app_screenRecord")

    fun statrtRecording() {
        if (!this.dir.exists()) {
            if (!this.dir.mkdirs()) {
                throw TestException("Can't create folder for screenshoots on device: $deviceName path: ${dir.path}")
            }
        }
        //uiDevice.executeShellCommand("screenrecord --time-limit 200000")
        val path = "screenrecord /sdcard/Pictures/app_screenRecord/video1.mp4 ; /sdcard/Pictures/app_screenRecord/video2.mp4 ; /sdcard/Pictures/app_screenRecord/video3.mp4 ; /sdcard/Pictures/app_screenRecord/video14.mp4"
        uiDevice.executeShellCommand(path)
    }
}