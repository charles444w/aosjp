package jcp.apps.game


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.random.Random
@Composable
fun FlappyBirdGame() {
    // Game state
    var birdY by remember { mutableStateOf(500f) } // Bird's Y position
    var birdVelocity by remember { mutableStateOf(0f) } // Bird's vertical velocity
    var pipes by remember { mutableStateOf(listOf<Pipe>()) } // List of pipes
    var score by remember { mutableStateOf(0) } // Player score
    var gameOver by remember { mutableStateOf(false) } // Game over flag

    // Animation for game loop
    val infiniteTransition = rememberInfiniteTransition()
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(16, easing = LinearEasing) // ~60 FPS
        )
    )

    // Game dimensions
    val screenWidth = 800f
    val screenHeight = 1200f
    val birdSize = 50f
    val pipeWidth = 100f
    val gapHeight = 300f

    // Update game state
    LaunchedEffect(time) {
        if (!gameOver) {
            // Update bird
            birdVelocity += 0.5f // Gravity
            birdY += birdVelocity

            // Update pipes
            pipes = pipes.map { it.copy(x = it.x - 5f) } // Move pipes left
                .filter { it.x > -pipeWidth } // Remove off-screen pipes

            // Spawn new pipe every 100 frames
            if (pipes.isEmpty() || pipes.last().x < screenWidth - 400f) {
                val gapY = Random.nextFloat() * (screenHeight - gapHeight - 200f) + 100f
                pipes = pipes + Pipe(screenWidth, gapY)
            }

            // Check collisions
            val birdRect = Rect(birdY, birdY + birdSize, 50f, 50f + birdSize)
            pipes.forEach { pipe ->
                val topPipeRect = Rect(0f, pipe.x + pipeWidth, pipe.gapY, pipe.x)
                val bottomPipeRect = Rect(pipe.gapY + gapHeight, pipe.x + pipeWidth, screenHeight, pipe.x)
                if (birdRect.overlaps(topPipeRect) || birdRect.overlaps(bottomPipeRect)) {
                    gameOver = true
                }
            }

            // Check if bird is out of bounds
            if (birdY < 0 || birdY > screenHeight - birdSize) {
                gameOver = true
            }

            // Update score
            pipes.forEach { pipe ->
                if (!pipe.scored && pipe.x < 50f) {
                    score += 1
                    pipe.scored = true
                }
            }
        }
    }

    // Handle tap to jump
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures {
                    if (!gameOver) {
                        birdVelocity = -15f // Jump
                    } else {
                        // Reset game
                        birdY = 500f
                        birdVelocity = 0f
                        pipes = emptyList()
                        score = 0
                        gameOver = false
                    }
                }
            }
    ) {
        // Draw bird
        drawCircle(
            color = Color.Yellow,
            radius = birdSize / 2,
            center = Offset(50f, birdY)
        )

        // Draw pipes
        pipes.forEach { pipe ->
            // Top pipe
            drawRect(
                color = Color.Green,
                topLeft = Offset(pipe.x, 0f),
                size = Size(pipeWidth, pipe.gapY)
            )
            // Bottom pipe
            drawRect(
                color = Color.Green,
                topLeft = Offset(pipe.x, pipe.gapY + gapHeight),
                size = Size(pipeWidth, screenHeight - pipe.gapY - gapHeight)
            )
        }

        // Draw score
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Score: $score",
                screenWidth / 2,
                100f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 50f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Draw game over
        if (gameOver) {
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "Game Over! Tap to Restart",
                    screenWidth / 2,
                    screenHeight / 2,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.RED
                        textSize = 60f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

// Data class for pipes
data class Pipe(
    val x: Float,
    val gapY: Float,
    var scored: Boolean = false
)

// Simple Rect class for collision detection
data class Rect(val top: Float, val right: Float, val bottom: Float, val left: Float) {
    fun overlaps(other: Rect): Boolean {
        return left < other.right && right > other.left && top < other.bottom && bottom > other.top
    }
}


