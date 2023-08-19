package com.example.myapplication

import android.content.Context
import android.media.SoundPool
import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp


private val builder = SoundPool.Builder()
private var soundPool: SoundPool = builder.build()
private var soundPool2: SoundPool = builder.build()
private var soundPool3: SoundPool = builder.build()
private var soundPool4: SoundPool = builder.build()
private var soundPool5: SoundPool = builder.build()
private var soundPool6: SoundPool = builder.build()
private var soundPool7: SoundPool = builder.build()
private var soundPool8: SoundPool = builder.build()
private var soundPool9: SoundPool = builder.build()
private var soundPool10: SoundPool = builder.build()
private var soundPool11: SoundPool = builder.build()
private var soundPool12: SoundPool = builder.build()
private var soundPool13: SoundPool = builder.build()
private var soundPool14: SoundPool = builder.build()
private var soundPool15: SoundPool = builder.build()
private var soundPool16: SoundPool = builder.build()
private var soundPool17: SoundPool = builder.build()
private var soundPool18: SoundPool = builder.build()
private var soundPool19: SoundPool = builder.build()
private var soundPool20: SoundPool = builder.build()
private var soundPool21: SoundPool = builder.build()
private var soundPool22: SoundPool = builder.build()
private var soundPool23: SoundPool = builder.build()
private var soundPool24: SoundPool = builder.build()
var listAll = mutableMapOf<Int, KeyModel>()
var listWhite = mutableMapOf<Int, KeyModel>()




@Composable
fun WhiteKeyItem(item: KeyModel) {
    val context = LocalContext.current
    loadSound(context, item)
    loadShortSound(context, item)
    listWhite[item.id] = item
    listAll[item.id] = item
    var soundStarted = false
    val timer = object : CountDownTimer(220, 10) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished < 65) {
                playSound(item.id, item.songId)
                soundStarted = true
                cancel()
            }
        }

        override fun onFinish() {}
    }

    var offsetX = 0.dp
    var currentId = item.id
    Box(modifier = Modifier
        .semantics { contentDescription = "Piano key" }
        .background(Color.White)
        .height(item.whKeySizeHeight)
        .width(item.whKeySizeWidth)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    timer.start()
                },
                onTap = {
                    timer.cancel()
                    if (!soundStarted) {
                        playSound(item.id, item.shortSoundId)
                    }
                    soundStarted = false
                }
            )
        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {currentId = item.id}
            ) { change, _ ->
                offsetX += change.position.x.dp - change.previousPosition.x.dp
                if(change.position.y >= 461) {
                    if (offsetX >= (item.whKeySizeWidth + 20.dp)) {
                        if (currentId < 24) {
                            currentId++
                            listWhite[currentId]?.let { playSound(currentId, it.songId) }
                            offsetX = 0.dp
                        }
                    }
                    if (offsetX <= -(item.whKeySizeWidth + 20.dp)) {
                        if (currentId > 0) {
                            currentId--
                            listWhite[currentId]?.let { playSound(currentId, it.songId) }
                            offsetX = 0.dp
                        }
                    }
                }else{
                    if (offsetX >= (item.whKeySizeWidth + 20.dp)) {
                        if (currentId < 24) {
                            currentId++
                            listAll[currentId]?.let { playSound(currentId, it.songId) }
                            offsetX = 0.dp
                        }
                    }
                    if (offsetX <= -(item.whKeySizeWidth + 20.dp)) {
                        if (currentId > 0) {
                            currentId--
                            listAll[currentId]?.let { playSound(currentId, it.songId) }
                            offsetX = 0.dp
                        }
                    }
                }
            }
        }
    )
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun BlackKeyItem(item: KeyModel) {
    val context = LocalContext.current
    loadSound(context, item)
    loadShortSound(context, item)
    listAll[item.id] = item
    var currentId = item.id
    var offsetX = 0.dp
    var soundStarted = false
    val timer = object : CountDownTimer(200, 10) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished < 65) {
                playSound(item.id, item.songId)
                soundStarted = true
                cancel()
            }
        }

        override fun onFinish() {}
    }

    Box(modifier = Modifier
        .semantics { contentDescription = "Piano key" }
        .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
        .background(Color.Black)
        .height(item.whKeySizeHeight / 3 * 2)
        .width(item.whKeySizeWidth / 3 * 2)
        .pointerInput(Unit) {

            detectTapGestures(
                onPress = {
                    timer.start()

                },
                onTap = {
                    timer.cancel()
                    if (!soundStarted) {
                        playSound(item.id, item.shortSoundId)
                    }
                    soundStarted = false
                }
            )

        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {currentId = item.id}
            ){change, _ ->
                offsetX += change.position.x.dp - change.previousPosition.x.dp
                if (offsetX >= (item.whKeySizeWidth + 20.dp)) {
                    if (currentId < 24) {
                        currentId++
                        listAll[currentId]?.let { playSound(currentId, it.songId) }
                        offsetX = 0.dp
                    }
                }
                if (offsetX <= -(item.whKeySizeWidth + 20.dp)) {
                    if (currentId > 0) {
                        currentId--
                        listAll[currentId]?.let { playSound(currentId, it.songId) }
                        offsetX = 0.dp
                    }
                }
            }
        }
    )
}

fun playSound(id: Int, songId: Int) {
    when (id) {
        1 -> soundPool.play(songId, 1f, 1f, 1, 0, 1f)
        2 -> soundPool2.play(songId, 1f, 1f, 1, 0, 1f)
        3 -> soundPool3.play(songId, 1f, 1f, 1, 0, 1f)
        4 -> soundPool4.play(songId, 1f, 1f, 1, 0, 1f)
        5 -> soundPool5.play(songId, 1f, 1f, 1, 0, 1f)
        6 -> soundPool6.play(songId, 1f, 1f, 1, 0, 1f)
        7 -> soundPool7.play(songId, 1f, 1f, 1, 0, 1f)
        8 -> soundPool8.play(songId, 1f, 1f, 1, 0, 1f)
        9 -> soundPool9.play(songId, 1f, 1f, 1, 0, 1f)
        10 -> soundPool10.play(songId, 1f, 1f, 1, 0, 1f)
        11 -> soundPool11.play(songId, 1f, 1f, 1, 0, 1f)
        12 -> soundPool12.play(songId, 1f, 1f, 1, 0, 1f)
        13 -> soundPool13.play(songId, 1f, 1f, 1, 0, 1f)
        14 -> soundPool14.play(songId, 1f, 1f, 1, 0, 1f)
        15 -> soundPool15.play(songId, 1f, 1f, 1, 0, 1f)
        16 -> soundPool16.play(songId, 1f, 1f, 1, 0, 1f)
        17 -> soundPool17.play(songId, 1f, 1f, 1, 0, 1f)
        18 -> soundPool18.play(songId, 1f, 1f, 1, 0, 1f)
        19 -> soundPool19.play(songId, 1f, 1f, 1, 0, 1f)
        20 -> soundPool20.play(songId, 1f, 1f, 1, 0, 1f)
        21 -> soundPool21.play(songId, 1f, 1f, 1, 0, 1f)
        22 -> soundPool22.play(songId, 1f, 1f, 1, 0, 1f)
        23 -> soundPool23.play(songId, 1f, 1f, 1, 0, 1f)
        24 -> soundPool24.play(songId, 1f, 1f, 1, 0, 1f)
    }
}

fun loadShortSound(context: Context, item: KeyModel) {

    when (item.id) {
        1 -> item.shortSoundId = soundPool.load(context, R.raw.key01short, 1)
        3 -> item.shortSoundId = soundPool3.load(context, R.raw.key03short, 1)
        5 -> item.shortSoundId = soundPool5.load(context, R.raw.key05short, 1)
        6 -> item.shortSoundId = soundPool6.load(context, R.raw.key06short, 1)
        8 -> item.shortSoundId = soundPool8.load(context, R.raw.key08short, 1)
        10 -> item.shortSoundId = soundPool10.load(context, R.raw.key10short, 1)
        12 -> item.shortSoundId = soundPool12.load(context, R.raw.key12short, 1)
        13 -> item.shortSoundId = soundPool13.load(context, R.raw.key13short, 1)
        15 -> item.shortSoundId = soundPool15.load(context, R.raw.key15short, 1)
        17 -> item.shortSoundId = soundPool17.load(context, R.raw.key17short, 1)
        18 -> item.shortSoundId = soundPool18.load(context, R.raw.key18short, 1)
        20 -> item.shortSoundId = soundPool20.load(context, R.raw.key20short, 1)
        22 -> item.shortSoundId = soundPool22.load(context, R.raw.key22short, 1)
        24 -> item.shortSoundId = soundPool24.load(context, R.raw.key24short, 1)
        2 -> item.shortSoundId = soundPool2.load(context, R.raw.key02short, 1)
        4 -> item.shortSoundId = soundPool4.load(context, R.raw.key04short, 1)
        7 -> item.shortSoundId = soundPool7.load(context, R.raw.key07short, 1)
        9 -> item.shortSoundId = soundPool9.load(context, R.raw.key09short, 1)
        11 -> item.shortSoundId = soundPool11.load(context, R.raw.key11short, 1)
        14 -> item.shortSoundId = soundPool14.load(context, R.raw.key14short, 1)
        16 -> item.shortSoundId = soundPool16.load(context, R.raw.key16short, 1)
        19 -> item.shortSoundId = soundPool19.load(context, R.raw.key19short, 1)
        21 -> item.shortSoundId = soundPool21.load(context, R.raw.key21short, 1)
        23 -> item.shortSoundId = soundPool23.load(context, R.raw.key23short, 1)
    }
}

fun loadSound(context: Context, item: KeyModel) {
    when (item.id) {
        1 -> item.songId = soundPool.load(context, R.raw.key01, 1)
        3 -> item.songId = soundPool3.load(context, R.raw.key03, 1)
        5 -> item.songId = soundPool5.load(context, R.raw.key05, 1)
        6 -> item.songId = soundPool6.load(context, R.raw.key06, 1)
        8 -> item.songId = soundPool8.load(context, R.raw.key08, 1)
        10 -> item.songId = soundPool10.load(context, R.raw.key10, 1)
        12 -> item.songId = soundPool12.load(context, R.raw.key12, 1)
        13 -> item.songId = soundPool13.load(context, R.raw.key13, 1)
        15 -> item.songId = soundPool15.load(context, R.raw.key15, 1)
        17 -> item.songId = soundPool17.load(context, R.raw.key17, 1)
        18 -> item.songId = soundPool18.load(context, R.raw.key18, 1)
        20 -> item.songId = soundPool20.load(context, R.raw.key20, 1)
        22 -> item.songId = soundPool22.load(context, R.raw.key22, 1)
        24 -> item.songId = soundPool24.load(context, R.raw.key24, 1)
        2 -> item.songId = soundPool2.load(context, R.raw.key02, 1)
        4 -> item.songId = soundPool4.load(context, R.raw.key04, 1)
        7 -> item.songId = soundPool7.load(context, R.raw.key07, 1)
        9 -> item.songId = soundPool9.load(context, R.raw.key09, 1)
        11 -> item.songId = soundPool11.load(context, R.raw.key11, 1)
        14 -> item.songId = soundPool14.load(context, R.raw.key14, 1)
        16 -> item.songId = soundPool16.load(context, R.raw.key16, 1)
        19 -> item.songId = soundPool19.load(context, R.raw.key19, 1)
        21 -> item.songId = soundPool21.load(context, R.raw.key21, 1)
        23 -> item.songId = soundPool23.load(context, R.raw.key23, 1)
    }
}


