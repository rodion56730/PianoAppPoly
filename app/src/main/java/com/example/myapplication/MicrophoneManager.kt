package com.example.myapplication

import android.content.Context
import android.content.Context.STORAGE_SERVICE
import android.media.MediaRecorder
import android.os.Build
import android.os.storage.StorageManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MicrophoneManager constructor(private val context: Context)  {
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var outFile: DataOutputStream
    private var isRecording = false

    fun getIsRecording(): Boolean {
        return isRecording
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun startRecording() {
        try {
            if(!isRecording) {
                val storageManager = context.getSystemService(STORAGE_SERVICE) as StorageManager
                val storageVolume = storageManager.storageVolumes[0]
                val finalFile = File(
                    storageVolume.directory!!.path +
                            "/Download/Piano${
                                LocalDateTime.now().format(
                                    DateTimeFormatter.ofPattern("MM.dd.yyy hh.mm.ss a")
                                )
                            }.wav"
                )
                val os: OutputStream
                os = FileOutputStream(finalFile)
                val bos = BufferedOutputStream(os)
                outFile = DataOutputStream(bos)
                mediaRecorder = MediaRecorder(context)
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS)
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                mediaRecorder.setOutputFile(finalFile)
                mediaRecorder.prepare()
                mediaRecorder.start()
                isRecording = true
                println("Record started")
            }else{
                Toast.makeText(context,"Запись уже начата",Toast.LENGTH_LONG).show()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }catch (e: RuntimeException){
          Toast.makeText(context,"Нет нужных разрешений",Toast.LENGTH_LONG).show()
        }
    }

     fun stopRecording() {
        if (isRecording) {
            mediaRecorder.stop()
            mediaRecorder.release()
            outFile.flush()
            outFile.close()
            isRecording = false
        }else {
            Toast.makeText(context,"Запись не начата", Toast.LENGTH_LONG).show()
        }
    }
}