package jcp.apps

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import jcp.apps.t.TransactionDataModel
import jcp.apps.ui.theme.AosjcpTheme
import java.text.NumberFormat
import java.util.Locale


class MainActivity : ComponentActivity() {
    var number: Int = 100000000
    var number2: Int = 100000
    var str: String = NumberFormat.getNumberInstance(Locale.US).format(number) //str = 1,000,000,000
    var str2: String = NumberFormat.getNumberInstance(Locale.US).format(number2) //str = 1,000,000,000
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
    fun dp2px(context: Context, dpVal: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpVal * scale+0.5F).toInt()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

         Log.d("charles_log","s1:${str} , s2:${str2}")
        //  val dataStore: DataStore<Preferences> = baseContext.createDataStore(name = "my_preferences")

        setContent {

            AosjcpTheme {
                //  val dataStore: DataStore<Preferences> = .createDataStore(name = "my_preferences")
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val shouldShowDialog = remember { mutableStateOf(false) } // 1
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        shouldShowDialog,
                        context = this
                    )
                }
            }
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {

}

@Composable
fun TopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.White)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "简体中文",
            modifier = Modifier
                .size(40.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Home")
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "简体中文",
            modifier = Modifier
                .size(40.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SettingScreen() {
    var items = remember {
        mutableStateListOf(
            SettingModel(selected = true, lang = "简体中文"),
            SettingModel(lang = "繁體中文")
        )
    }
    var currentLanguage by remember { mutableStateOf<String>("简体中文") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        //TopBar()
        ItemView(TransactionDataModel(1))
        /*items.forEachIndexed { index, settingModel ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        currentLanguage = items[index].lang

                        items
                            .toList()
                            .mapIndexed { newIndec, settingModel ->
                                items = items.apply {
                                    this[newIndec] =
                                        this[newIndec].copy(selected = (currentLanguage == this[newIndec].lang))
                                }
                            }
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = items[index].lang
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "简体中文",
                    modifier = Modifier
                        .size(40.dp)
                        .alpha(if (items[index].selected) 1f else 0f),
                    contentScale = ContentScale.Crop
                )
            }
        }*/

    }
}

@Composable
fun LazyVerticalGridDemo() {

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(100) {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                ) {
                    Text(
                        text = "$it",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        })
}

@Composable
fun ItemView(data: TransactionDataModel) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_calendar),
            contentDescription = "",
            modifier = Modifier
                .size(20.dp),
            contentScale = ContentScale.Crop
        )
        Text(modifier = Modifier.padding(start = 15.dp), text = "${data.transactionData}")

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.icon_right_arrow),
            contentDescription = "简体中文",
            modifier = Modifier
                .size(20.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ItemDetailsView(data: TransactionDataModel) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .padding(20.dp)
    ) {
        Column {
            Text(modifier = Modifier, text = "${data.transactionData}")
            Text(modifier = Modifier, text = "${data.description}")
            Text(modifier = Modifier, text = "${data.category}")
            Text(modifier = Modifier, text = "${data.debit}")
            Text(modifier = Modifier, text = "${data.credit}")
        }

        /*   Image(
               painter = painterResource(id = R.drawable.ic_launcher_foreground),
               contentDescription = "简体中文",
               modifier = Modifier
                   .size(40.dp),
               contentScale = ContentScale.Crop
           )*/
        Spacer(modifier = Modifier.weight(1f))
        Text(modifier = Modifier.align(Alignment.CenterVertically), text = "")
        Spacer(modifier = Modifier.weight(1f))
        /*  Image(
              painter = painterResource(id = R.drawable.ic_launcher_foreground),
              contentDescription = "简体中文",
              modifier = Modifier
                  .size(40.dp),
              contentScale = ContentScale.Crop
          )*/
    }
}

@Composable
fun ListConfig() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(5.dp)
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(15.dp)
                    .height(15.dp)
            )
            Text(text = "Bookmark", Modifier.padding(start = 3.dp))
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(15.dp)
                    .height(15.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(15.dp)
                    .height(15.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
        }

    }

}

@Composable
fun placeHolderOrDrawableRes(
    urlOrDrawableRes: String,
    @DrawableRes placeholder: Int = R.drawable.ic_launcher_background
): Painter {
    val drawableRes = urlOrDrawableRes.toIntOrNull()
    return when {
        drawableRes != null -> painterResource(id = drawableRes)
        else -> painterResource(id = placeholder)
    }
}

@Composable
fun customListView(context: Context) {
    // on below line we are creating and
    // initializing our array list
    val courseList: MutableList<VideoModel> = mutableListOf(
        VideoModel(
            "android",
            mutableListOf(
                VideoModel.Details("titlean1", desc = "descan1"),
                VideoModel.Details("titlean2", desc = "descan2")
            )
        ),
        VideoModel(
            "ios",
            mutableListOf(
                VideoModel.Details("titleios1", desc = "descios1"),
                VideoModel.Details("titleios2", desc = "descios2")
            )
        ),
    )

    LazyColumn {
        itemsIndexed(courseList[0].videoDetails) { index, item ->
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(0.dp)
                )
            )
            {

                Column(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        // on below line inside row we are adding spacer
                        Spacer(modifier = Modifier.width(5.dp))
                        AsyncImage(
                            placeholder = placeHolderOrDrawableRes(item.smallUrlImage),
                            model = item.smallUrlImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        // on below line we are adding spacer between image and a text
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = item.title,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        modifier = Modifier
                            .padding(8.dp), text = item.desc, fontSize = 16.sp, color = Color.Black
                    )
                }

            }
        }
    }
}

val urlVideo =
    "https://firebasestorage.googleapis.com/v0/b/aosfbai.appspot.com/o/Product%20Demo%20Video%20_%20SaaS%20Explainer%20Video%20_%20Infinity.mp4?alt=media&token=93a94173-0e3b-43fa-8bf6-d65567636146"

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView() {
    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build()

    // Create a MediaSource
    val mediaSource =
        remember(urlVideo) {
            MediaItem.fromUri(urlVideo)
        }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set your desired height
    )
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    shouldShowDialog: MutableState<Boolean>,
    context: Context
) {
    //setting screen
    var items =
        listOf(SettingModel(selected = true, lang = "简体中文"), SettingModel(lang = "繁體中文"))
    SettingScreen()

    //main screen
    /*    Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Column {
                ListConfig()
                customListView(context)
            }

            //LazyVerticalGridDemo()
            *//*    Column() {
                Button(
                    onClick = { shouldShowDialog.value = true },
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(text = "Show Dialog")
                }
                ExoPlayerView()
            }*//*
    }

    if (shouldShowDialog.value) {
        ShowDialogNoNetworking(shouldShowDialog = shouldShowDialog)
    }*/
}

@Composable
fun ShowDialogNoNetworking(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AosjcpTheme {
        // Greeting("Android")
    }
}