package com.drtahir.studentkit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("My Application", appName)
  }

  @Test
  fun `test image enhancer`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val enhancerOk = com.drtahir.studentkit.data.ImageEnhancer.initInterpreter(context)
    println("ImageEnhancer initInterpreter: $enhancerOk")
    
    val bitmap = android.graphics.Bitmap.createBitmap(64, 64, android.graphics.Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    
    val enhanced = com.drtahir.studentkit.data.ImageEnhancer.enhanceImage(context, bitmap) { }
    println("Enhanced size: ${enhanced.width}x${enhanced.height}")
    val samplePixel = enhanced.getPixel(enhanced.width / 2, enhanced.height / 2)
    val r = (samplePixel shr 16) and 0xFF
    val g = (samplePixel shr 8) and 0xFF
    val b = samplePixel and 0xFF
    println("Enhanced center pixel: R=$r, G=$g, B=$b, hex=${Integer.toHexString(samplePixel)}")
    org.junit.Assert.assertTrue("Pixel should not be black", r > 50 && g > 50 && b > 50)
  }
}
