package jcp.apps.jptask

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*

import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield
import androidx.compose.material3.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.rememberScrollState


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

import androidx.compose.ui.unit.sp

@Composable
fun MarqueeTextView(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    speed: Float = 50f, // Pixels per second
    isPlaying: Boolean = true
) {
    // 初始化 TextPaint
    val textPaint = remember(textStyle) {
        TextPaint().apply {
            isAntiAlias = true
            textSize = textStyle.fontSize.value * 4f // 根據密度調整
            color = textStyle.color.hashCode()
        }
    }

    // 計算文本寬度
    val textWidth = remember(text) { textPaint.measureText(text) }
    val offset = remember { Animatable(0f) } // 初始偏移量為 0，文本從左側開始
    var isFirstScroll by remember { mutableStateOf(true) } // 標記是否為第一次滾動
    var canvasWidth by remember { mutableFloatStateOf(0f) } // 存儲畫布寬度

    // 動畫控制
    LaunchedEffect(isPlaying, canvasWidth, textWidth) {
        if (isPlaying && textWidth > 0 && canvasWidth > 0) {
            if (isFirstScroll) {
                // 第一次滾動：從 0 到 -textWidth
                offset.animateTo(
                    targetValue = -textWidth,
                    animationSpec = tween(
                        durationMillis = ((textWidth / speed) * 1000).toInt(),
                        easing = LinearEasing
                    )
                )
                isFirstScroll = false // 標記第一次滾動完成
                offset.snapTo(0f) // 重置為後續循環起始點
            }
            // 後續循環：從 0 到 -(canvasWidth + textWidth)
            offset.animateTo(
                targetValue = -(canvasWidth + textWidth),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (((canvasWidth + textWidth) / speed) * 1000).toInt(),
                        easing = LinearEasing
                    )
                )
            )
        } else {
            offset.stop() // 暫停時停止動畫
        }
    }

    // 繪製 Canvas
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)
            .onSizeChanged { canvasWidth = it.width.toFloat() } // 獲取畫布寬度
    ) {
        drawIntoCanvas { canvas ->
            val x = if (isFirstScroll) offset.value else offset.value + canvasWidth // 主文本的 x 坐標
            // 繪製主文本
            canvas.nativeCanvas.drawText(
                text,
                x,
                size.height / 2 + textPaint.textSize / 3,
                textPaint
            )

            // 僅當主文本完全離開畫布（x + textWidth <= 0）時，繪製第二份文本
            if (x + textWidth <= 0) {
                val secondX = x + textWidth + canvasWidth // 第二份文本從右側開始
                canvas.nativeCanvas.drawText(
                    text,
                    secondX,
                    size.height / 2 + textPaint.textSize / 3,
                    textPaint
                )
            }
        }
    }
}

@Composable
fun MarqueeTextViewExample() {
    var isPlaying by remember { mutableStateOf(true) }

    Column {
        MarqueeTextView(
            text = "這是一個從右向左循環滾動的跑馬燈文本！",
            modifier = Modifier.fillMaxWidth(),
            isPlaying = isPlaying
        )
        Button(onClick = { isPlaying = !isPlaying }) {
            Text(if (isPlaying) "暫停" else "繼續")
        }
    }
}
/*
@Composable
fun MarqueeTextView(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    speed: Float = 50f, // Pixels per second
    isPlaying: Boolean = true
) {
    // 初始化 TextPaint
    val textPaint = remember(textStyle) {
        TextPaint().apply {
            isAntiAlias = true
            textSize = textStyle.fontSize.value * 4f // 根據密度調整
            color = textStyle.color.hashCode()
        }
    }

    // 計算文本寬度
    val textWidth = remember(text) { textPaint.measureText(text) }
    val offset = remember { Animatable(0f) } // 初始偏移量為 0，文本從左側開始
    var isFirstScroll by remember { mutableStateOf(true) } // 標記是否為第一次滾動
    var canvasWidth by remember { mutableFloatStateOf(0f) } // 存儲畫布寬度

    // 動畫控制
    LaunchedEffect(isPlaying, canvasWidth, textWidth) {
        if (isPlaying && textWidth > 0 && canvasWidth > 0) {
            if (isFirstScroll) {
                // 第一次滾動：從 0 到 -textWidth
                offset.animateTo(
                    targetValue = -textWidth,
                    animationSpec = tween(
                        durationMillis = ((textWidth / speed) * 1000).toInt(),
                        easing = LinearEasing
                    )
                )
                isFirstScroll = false // 標記第一次滾動完成
                offset.snapTo(canvasWidth - textWidth) // 重置為右側開始
            }
            // 後續循環：從 canvasWidth - textWidth 到 -textWidth
            offset.animateTo(
                targetValue = -textWidth,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (((canvasWidth + textWidth) / speed) * 1000).toInt(),
                        easing = LinearEasing
                    )
                )
            )
        } else {
            offset.stop() // 暫停時停止動畫
        }
    }

    // 繪製 Canvas
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)
            .onSizeChanged { canvasWidth = it.width.toFloat() } // 獲取畫布寬度
    ) {
        drawIntoCanvas { canvas ->
            val x = if (isFirstScroll) offset.value else offset.value + canvasWidth // 主文本的 x 坐標
            // 繪製主文本
            canvas.nativeCanvas.drawText(
                text,
                x,
                size.height / 2 + textPaint.textSize / 3,
                textPaint
            )

            // 僅當主文本完全離開畫布（x + textWidth <= 0）時，繪製第二份文本
            if (x + textWidth <= 0) {
                val secondX = x + textWidth + canvasWidth // 第二份文本從右側開始
                canvas.nativeCanvas.drawText(
                    text,
                    secondX,
                    size.height / 2 + textPaint.textSize / 3,
                    textPaint
                )
            }
        }
    }
}

@Composable
fun MarqueeTextViewExample() {
    var isPlaying by remember { mutableStateOf(true) }

    Column {
        MarqueeTextView(
            text = "這是一個從右向左循環滾動的跑馬燈文本！",
            modifier = Modifier.fillMaxWidth(),
            isPlaying = isPlaying
        )
        Button(onClick = { isPlaying = !isPlaying }) {
            Text(if (isPlaying) "暫停" else "繼續")
        }
    }
}
*/


