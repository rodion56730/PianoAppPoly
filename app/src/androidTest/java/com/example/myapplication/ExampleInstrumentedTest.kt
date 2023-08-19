package com.example.myapplication

import android.content.Context
import android.hardware.camera2.params.BlackLevelPattern
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var instrumentationContext: Context
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun creatingKey(){
        rule.setContent {  WhiteKeyItem(item = KeyModel(1,1.dp,1.dp))  }
        rule.onAllNodesWithContentDescription("Piano key").assertCountEquals(1)
    }

    @Test
    fun creatingBlackKey(){
        rule.setContent {  BlackKeyItem(item = KeyModel(1,1.dp,1.dp))  }
        rule.onAllNodesWithContentDescription("Piano key").assertCountEquals(1)
    }

    @Test
    fun recordStart(){
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        val microphoneManager = MicrophoneManager(instrumentationContext)
        microphoneManager.startRecording()
        assertTrue(microphoneManager.getIsRecording())
        microphoneManager.stopRecording()
        assertFalse(microphoneManager.getIsRecording())
    }

}