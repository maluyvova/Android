package com.tubitv.tubitv.Helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.junit.Test
import java.io.File

/**
 * Created by vburian on 4/18/18.
 */
class ImageKotlin {

    //@JvmStatic
   // @Test
    fun main() {

        run {
           // var imgA: Bitmap? = null
            //var imgB: Bitmap? = null

            val fileA = File("/Users/vburian/Desktop/1.jpg")
            val fileB = File("/Users/vburian/Desktop/2.jpg")

            // imgA = ImageIO.read(fileA);
            //imgB = ImageIO.read(fileB);
           var imgA = BitmapFactory.decodeFile("/Users/vburian/Desktop/1.jpg")
            var imgB = BitmapFactory.decodeFile("/Users/vburian/Desktop/2.jpg")

            val width1 = imgA!!.width
            val width2 = imgB!!.width
            val height1 = imgA.height
            val height2 = imgB.height

            if (width1 != width2 || height1 != height2)
                println("Error: Images dimensions" + " mismatch")
            else {
                var difference: Long = 0
                for (y in 0 until height1) {
                    for (x in 0 until width1) {
                        val rgbA = imgA.getPixel(x, y)
                        val rgbB = imgB.getPixel(x, y)
                        val redA = rgbA shr 16 and 0xff
                        val greenA = rgbA shr 8 and 0xff
                        val blueA = rgbA and 0xff
                        val redB = rgbB shr 16 and 0xff
                        val greenB = rgbB shr 8 and 0xff
                        val blueB = rgbB and 0xff
                        difference += Math.abs(redA - redB).toLong()
                        difference += Math.abs(greenA - greenB).toLong()
                        difference += Math.abs(blueA - blueB).toLong()
                    }
                }

                // Total number of red pixels = width * height
                // Total number of blue pixels = width * height
                // Total number of green pixels = width * height
                // So total number of pixels = width * height * 3
                val total_pixels = (width1 * height1 * 3).toDouble()

                // Normalizing the value of different pixels
                // for accuracy(average pixels per color
                // component)
                val avg_different_pixels = difference / total_pixels

                // There are 255 values of pixels in total
                val percentage = avg_different_pixels / 255 * 100

                println("Difference Percentage-->" + percentage)
            }
        }
    }
}