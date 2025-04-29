package jcp.apps.game


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhackAMoleGame()
        }
    }
}

@Composable
fun WhackAMoleGame() {
    var showInstructions by remember { mutableStateOf(true) }
    var isChinese by remember { mutableStateOf(true) } // 語言切換：true 為中文，false 為英文

    if (showInstructions) {
        InstructionScreen(
            isChinese = isChinese,
            onLanguageToggle = { isChinese = !isChinese },
            onStartGame = { showInstructions = false }
        )
    } else {
        GameScreen()
    }
}

@Composable
fun InstructionScreen(
    isChinese: Boolean,
    onLanguageToggle: () -> Unit,
    onStartGame: () -> Unit
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFC1CC), Color(0xFFB2FEFA)) // 粉紅到淺藍漸變
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 標題
        Text(
            text = if (isChinese) "打地鼠遊戲說明" else "Whack-a-Mole Game Instructions",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6B4EFF), // 鮮紫色
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 說明文字
        Text(
            text = if (isChinese) """
                歡迎體驗打地鼠遊戲！
                - 遊戲包含一個 3x3 的網格，地鼠會隨機出現。
                - 點擊出現的地鼠即可得分。
                - 遊戲持續 60 秒，結束後顯示最終分數。
                - 快來挑戰你的反應速度吧！
            """.trimIndent() else """
                Welcome to Whack-a-Mole!
                - The game features a 3x3 grid where moles appear randomly.
                - Tap the moles to score points.
                - The game lasts for 60 seconds, and your final score is shown at the end.
                - Test your reflexes and have fun!
            """.trimIndent(),
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 語言切換按鈕
        Button(
            onClick = onLanguageToggle,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA1A1)) // 粉紅按鈕
        ) {
            Text(
                text = if (isChinese) "Switch to English" else "切換到中文",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        // 開始遊戲按鈕
        Button(
            onClick = onStartGame,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF)), // 亮藍按鈕
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = if (isChinese) "開始遊戲" else "Start Game",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun GameScreen() {
    var score by remember { mutableStateOf(0) }
    var gameTime by remember { mutableStateOf(60) }
    var isGameRunning by remember { mutableStateOf(false) }
    val moleStates = remember { List(9) { mutableStateOf(false) } }
    var gameOver by remember { mutableStateOf(false) }

    // 計時器
    LaunchedEffect(isGameRunning) {
        if (isGameRunning) {
            while (gameTime > 0 && isGameRunning) {
                delay(1000L)
                gameTime -= 1
            }
            if (gameTime <= 0) {
                isGameRunning = false
                gameOver = true
            }
        }
    }

    // 地鼠隨機出現
    LaunchedEffect(isGameRunning) {
        if (isGameRunning) {
            while (isGameRunning) {
                val randomIndex = Random.nextInt(9)
                moleStates[randomIndex].value = true
                delay(800L)
                moleStates[randomIndex].value = false
                delay(200L)
            }
        }
    }

    val gradientBackground = Brush.linearGradient(
        colors = listOf(Color(0xFFFFE1F0), Color(0xFFCCFFCC)) // 粉紅到淺綠漸變
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 分數和時間
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "分數: $score",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF1493) // 鮮粉色
            )
            Text(
                text = "時間: $gameTime",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00CED1) // 青色
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3x3 網格
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            for (row in 0 until 3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0 until 3) {
                        val index = row * 3 + col
                        Mole(
                            isVisible = moleStates[index].value,
                            onMoleClicked = {
                                if (moleStates[index].value) {
                                    score += 1
                                    moleStates[index].value = false
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 遊戲結束或開始按鈕
        if (gameOver) {
            Text(
                text = "遊戲結束！最終分數: $score",
                fontSize = 24.sp,
                color = Color(0xFFFF4500), // 鮮橙色
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    score = 0
                    gameTime = 60
                    gameOver = false
                    isGameRunning = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)), // 亮粉色
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("重新開始", fontSize = 18.sp, color = Color.White)
            }
        } else {
            Button(
                onClick = {
                    if (!isGameRunning) {
                        score = 0
                        gameTime = 60
                        isGameRunning = true
                    }
                },
                enabled = !isGameRunning,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B68EE)), // 亮紫色
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("開始遊戲", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun Mole(isVisible: Boolean, onMoleClicked: () -> Unit) {
    val moleColors = listOf(
        Color(0xFFFF69B4), // 亮粉色
        Color(0xFF00FA9A), // 亮綠色
        Color(0xFFFFD700), // 亮黃色
        Color(0xFF1E90FF)  // 亮藍色
    )
    val randomColor = remember { moleColors.random() }

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color(0xFFE6E6FA), shape = CircleShape) // 淡紫色背景
            .clip(CircleShape)
            .clickable { onMoleClicked() },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(randomColor, shape = CircleShape)
            )
        }
    }
}