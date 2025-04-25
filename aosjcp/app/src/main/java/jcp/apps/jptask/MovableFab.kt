package jcp.apps.jptask

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun MovableFab() {
    // 存储 FAB 的位置（x, y 坐标）
    var fabOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    // 存储屏幕尺寸
    var screenSize by remember { mutableStateOf(IntSize.Zero) }
    // 获取当前密度以转换 dp 到像素
    val density = LocalDensity.current
    // FAB 尺寸（56dp）转换为像素
    val fabSizePx = with(density) { 56.dp.toPx() }

    // 当屏幕尺寸可用时，将 FAB 初始化到右下角
    LaunchedEffect(screenSize) {
        if (screenSize != IntSize.Zero) {
            fabOffset = Offset(
                x = screenSize.width - fabSizePx, // 右下角，考虑 FAB 尺寸
                y = screenSize.height - fabSizePx
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize() // 填充整个屏幕
            .onGloballyPositioned { coordinates ->
                screenSize = coordinates.size // 获取屏幕尺寸
            }
    ) {
        // 可移动的 Floating Action Button
        FloatingActionButton(
            onClick = { /* 点击事件处理，例如添加新项目 */ },
            modifier = Modifier
                .align(Alignment.TopStart) // 从左上角开始，偏移由 offset 控制
                .offset {
                    // 使用 fabOffset 设置 FAB 位置
                    androidx.compose.ui.unit.IntOffset(
                        fabOffset.x.toInt(),
                        fabOffset.y.toInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        // 根据拖动距离更新 FAB 位置
                        fabOffset = Offset(
                            x = max(0f, min(fabOffset.x + dragAmount.x, screenSize.width - fabSizePx)),
                            y = max(0f, min(fabOffset.y + dragAmount.y, screenSize.height - fabSizePx))
                        )
                    }
                }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "添加")
        }
    }
}