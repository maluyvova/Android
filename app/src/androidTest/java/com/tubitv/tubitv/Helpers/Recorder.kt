//package com.tubitv.tubitv.Helpers
//
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.media.MediaRecorder
//import android.support.v4.view.ViewCompat.getRotation
//import android.os.Environment.getExternalStorageDirectory
//
//
//
//
//
///**
// * Created by vburian on 1/18/19.
// */
//import android.media.projection.MediaProjection;
//import android.media.projection.MediaProjectionManager;
//import android.content.Context.MEDIA_PROJECTION_SERVICE
//import android.os.Bundle
//import android.util.DisplayMetrics
//import android.media.MediaMuxer
//import android.view.Display
//import android.support.v4.view.ViewCompat.getDisplay
//import android.content.Context.DISPLAY_SERVICE
//import android.hardware.display.DisplayManager
//import java.io.IOException
//import android.content.Intent
//import android.util.Log
//
//
//class Recorder {
//    private fun startScreenRecord(intent: Intent) {
//            if (sMuxer == null) {
//               val mMediaProjectionManager = getSystemService(
//                        android.content.Context.MEDIA_PROJECTION_SERVICE);
//                // get MediaProjection
//                val projection = MediaProjectionManager().getMediaProjection(resultCode, intent)
//                if (projection != null) {
//                    val metrics = getResources().getDisplayMetrics()
//                    val density = metrics.densityDpi
//
//                    try {
//                       val sMuxer = MediaMuxerWrapper(".mp4") // if you record audio only, ".m4a" is also OK.
//                        if (true) {
//                            // for screen capturing
//                            MediaScreenEncoder(sMuxer, mMediaEncoderListener,
//                                    projection, metrics.widthPixels, metrics.heightPixels, density)
//                        }
//                        if (true) {
//                            // for audio capturing
//                            MediaAudioEncoder(sMuxer, mMediaEncoderListener)
//                        }
//                        sMuxer.prepare()
//                        sMuxer.startRecording()
//                    } catch (e: IOException) {
//                        Log.e(TAG, "startScreenRecord:", e)
//                    }
//
//                }
//            }
//
//    }
//
//
//    }