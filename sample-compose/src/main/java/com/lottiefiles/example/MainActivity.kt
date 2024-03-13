package com.lottiefiles.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.util.DotLottieEventListener
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.lottiefiles.example.ui.theme.ExampleTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    DefaultAnimationDemo()
                    AnimationWithReactiveProps()
//                    MarkerExample()
//                    ThemeExample()
                }
            }
        }
    }
}

@Composable
@Preview
fun DefaultAnimationDemo() {
    val dotLottieController = remember { DotLottieController() }
    val useFrameInterpolation = remember { mutableStateOf(true) }
    val loop = remember { mutableStateOf(true) }
    val speed = remember { mutableFloatStateOf(1f) }
    val segments = remember { mutableStateOf(1f..100f) }
    val currentFrame = remember { mutableFloatStateOf(0f) }
    val totalFrame = remember { mutableFloatStateOf(0f) }
    val dropdownExpand = remember { mutableStateOf(false) }
    val dropdownActive = remember { mutableStateOf("") }
    val hide = remember { mutableStateOf(false) }
    val events = object : DotLottieEventListener {
        override fun onLoad() {
            Log.i("DotLottie", "Loaded")
        }

        override fun onPause() {
            Log.i("DotLottie", "paused")
        }

        override fun onPlay() {
            Log.i("DotLottie", "Play")
        }

        override fun onStop() {
            Log.i("DotLottie", "Stop")
        }

        override fun onComplete() {
            Log.i("DotLottie", "Completed")
        }

        override fun onUnFreeze() {
            Log.i("DotLottie", "UnFreeze")
        }

        override fun onFrame(frame: Float) {
            currentFrame.value = dotLottieController.currentFrame
            totalFrame.value = dotLottieController.totalFrames
        }

        override fun onFreeze() {
            Log.i("DotLottie", "Freeze")
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (hide.value) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Removed", fontSize = 24.sp)
        } else {
            repeat(1) {
                Row {
                    repeat(1) { // Example: Repeat 3 times
                        DotLottieAnimation(
                            height = 600u,
                            width = 600u,
                            autoplay = true,
                            loop = true,
                            eventListeners = listOf(events),
//                                        source = DotLottieSource.Url("https://lottiefiles-mobile-templates.s3.amazonaws.com/ar-stickers/swag_sticker_piggy.lottie"),
                            source = DotLottieSource.Url("https://lottie.host/5525262b-4e57-4f0a-8103-cfdaa7c8969e/VCYIkooYX8.json"),
//                                        source = DotLottieSource.Url("https://lottie.host/294b684d-d6b4-4116-ab35-85ef566d4379/VkGHcqcMUI.lottie"),
//                                        source = DotLottieSource.Asset("swinging.json"),
                            modifier = Modifier.background(Color.LightGray),
                            controller = dotLottieController
                        )
                    }
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(modifier = Modifier.padding(2.dp)) {
                Button(onClick = {
                    hide.value = true
                }) {
                    Text(text = "Remove")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    hide.value = false
                }) {
                    Text(text = "Render")
                }
            }
            Row(modifier = Modifier.padding(2.dp)) {
                Text(text = "%.2f / %.2f ".format(currentFrame.value, totalFrame.value))
            }
            Row(modifier = Modifier.padding(2.dp)) {
                Button(onClick = {
                    dotLottieController.play()
                }) {
                    Text(text = "Play")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dotLottieController.pause()
                }) {
                    Text(text = "Pause")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dotLottieController.stop()
                }) {
                    Text(text = "Stop")
                }
            }
            Text(text = "Modes:", fontSize = 12.sp)
            Row(modifier = Modifier.padding(2.dp)) {
                Button(onClick = {
                    dotLottieController.setPlayMode(Mode.FORWARD)
                }) {
                    Text(text = ">")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dotLottieController.setPlayMode(Mode.REVERSE)
                }) {
                    Text(text = "<")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dotLottieController.setPlayMode(Mode.BOUNCE)
                }) {
                    Text(text = "<+>")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dotLottieController.setPlayMode(Mode.REVERSE_BOUNCE)
                }) {
                    Text(text = "<->")
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "useFrameInterpolation: ")
                Checkbox(checked = useFrameInterpolation.value, onCheckedChange = {
                    dotLottieController.setUseFrameInterpolation(it)
                    useFrameInterpolation.value = it
                })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "loop: ")
                Checkbox(checked = loop.value, onCheckedChange = {
                    dotLottieController.setLoop(it)
                    loop.value = it
                })
                Spacer(modifier = Modifier.width(8.dp))
            }
            Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Speed: ")
                Button(onClick = {
                    speed.value++
                    dotLottieController.setSpeed(speed.value)
                }) {
                    Text(text = "+")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${speed.value}")
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (speed.value > 1) {
                        speed.value--
                        dotLottieController.setSpeed(speed.value)
                    }
                }) {
                    Text(text = "-")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dotLottieController.setFrame(50f)
                }) {
                    Text(text = "Frame 50")
                }
            }
            Column(
                modifier = Modifier
                    .border(border = BorderStroke(1.dp, Color.LightGray))
                    .padding(10.dp, 4.dp)
            ) {
                Row {
                    RangeSlider(
                        value = segments.value,
                        onValueChange = { segments.value = it },
                        valueRange = 1f..100f
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${segments.value.start.roundToInt()} - ${segments.value.endInclusive.roundToInt()}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        dotLottieController.setSegments(
                            segments.value.start.roundToInt().toFloat(),
                            segments.value.endInclusive.roundToInt().toFloat()
                        )
                    }) {
                        Text(text = "Set Segment")
                    }
                }
            }
            Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    dotLottieController.resize(550u, 550u)
                }) {
                    Text(text = "Resize")
                }
                Button(onClick = {
                    dotLottieController.freeze()
                }) {
                    Text(text = "Freeze")
                }
                Row(
                    modifier = Modifier.padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        dotLottieController.unFreeze()
                    }) {
                        Text(text = "Unfreeze")
                    }
                }
            }
            Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { dropdownExpand.value = !dropdownExpand.value }) {
                    Text(text = "Animations")
                }
                DropdownMenu(expanded = dropdownExpand.value, onDismissRequest = {
                    dropdownExpand.value = false
                }) {
                    dotLottieController.manifest()?.animations?.forEach() {
                        DropdownMenuItem(text = { Text(text = it.id) }, onClick = {
                            dropdownActive.value = it.id
                            dropdownExpand.value = false
                            dotLottieController.loadAnimation(it.id)
                        })
                    }

                }
            }
        }
    }
}

@Composable
@Preview
fun AnimationWithReactiveProps() {
    val loop = remember { mutableStateOf(true) }
    val autoplay = remember { mutableStateOf(true) }
    val currentFrame = remember { mutableFloatStateOf(0f) }
    val speed = remember { mutableFloatStateOf(1f) }
    val useFrameInterpolation = remember { mutableStateOf(true) }
    val width = remember { mutableStateOf(500u) }
    val height = remember { mutableStateOf(500u) }
    val source =
        remember { mutableStateOf("https://lottie.host/5525262b-4e57-4f0a-8103-cfdaa7c8969e/VCYIkooYX8.json") }
    val segments = remember { mutableStateOf<Pair<Float, Float>?>(null) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            DotLottieAnimation(
                width = width.value,
                height = height.value,
                source = DotLottieSource.Url(source.value),
                autoplay = autoplay.value,
                useFrameInterpolation = useFrameInterpolation.value,
                loop = loop.value,
                speed = speed.floatValue,
                segments = segments.value,
                eventListeners = listOf(object : DotLottieEventListener {
                    override fun onFrame(frame: Float) {
                        currentFrame.floatValue = frame
                    }
                })
            )
        }
        Row {
            Text(text = "Frame: ${currentFrame.floatValue}")
        }
        Row {
            Column {
                Button(onClick = {
                    loop.value = !loop.value
                }) {
                    Text(text = "Loop  = ${loop.value}")
                }
                Button(onClick = {
                    speed.floatValue -= if (speed.floatValue > 0.1f) 0.1f else 0f
                }) {
                    Text(text = "-Speed = %.2f".format(speed.floatValue))
                }
                Button(onClick = { speed.floatValue += 0.1f }) {
                    Text(text = "+Speed = %.2f".format(speed.floatValue))
                }
                Button(onClick = {
                    autoplay.value = !autoplay.value
                }) {
                    Text(text = "AutoPlay = ${autoplay.value}")
                }

                Button(onClick = {
                    useFrameInterpolation.value = !useFrameInterpolation.value
                }) {
                    Text(text = "useFrameInterpolation = ${useFrameInterpolation.value}")
                }

                Button(onClick = {
                    width.value += 50u
                    height.value += 50u
                }) {
                    Text(text = "Resize")
                }

                Button(onClick = {
                    if (source.value == "https://lottie.host/294b684d-d6b4-4116-ab35-85ef566d4379/VkGHcqcMUI.lottie") {
                        source.value =
                            "https://lottie.host/5525262b-4e57-4f0a-8103-cfdaa7c8969e/VCYIkooYX8.json"
                    } else {
                        source.value =
                            "https://lottie.host/294b684d-d6b4-4116-ab35-85ef566d4379/VkGHcqcMUI.lottie"
                    }
                }) {
                    Text(text = "Swap Animation")
                }

                Button(onClick = {
                    if (segments.value != null) {
                        segments.value = null
                    } else {
                        segments.value = 10f to 50f
                    }
                }) {
                    Text(text = if (segments.value != null) "Reset Segments" else "Set Segments 10 to 50")
                }
            }
        }
    }
}

@Composable
fun MarkerExample() {
    val marker = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            DotLottieAnimation(
                width = 500u,
                height = 500u,
                source = DotLottieSource.Asset("markers.json"),
                autoplay = true,
                marker = marker.value,
                loop = true,
            )
        }
        Row {
            Column {
                Button(onClick = {
                    marker.value = "bird"
                }) {
                    Text(text = "Play `bird`")
                }
            }
        }
    }
}

@Composable
fun ThemeExample() {
    val theme = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            DotLottieAnimation(
                width = 500u,
                height = 500u,
                source = DotLottieSource.Asset("theming_example.lottie"),
                autoplay = true,
                themeId = theme.value,
                loop = true,
            )
        }
        Row {
            Column {
                Button(onClick = {
                    theme.value = "theme"
                }) {
                    Text(text = "Load `theme`")
                }
            }
        }
    }
}
