package jcp.apps.base

import android.widget.Toolbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AppToolBar() {
    Box(modifier = Modifier
        .height(41.dp)
        .fillMaxWidth()
        .background(color = Color.Green)
        .padding(5.dp)) {
        Row(
            modifier = Modifier
                .height(41.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(modifier = Modifier.size(25.dp, 25.dp),imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            Spacer(modifier = Modifier.weight(1f))
            Icon(modifier = Modifier.size(25.dp, 25.dp),imageVector = Icons.Filled.Home, contentDescription = null)

        }
       Text(
            modifier = Modifier.fillMaxSize().padding(start = 30.dp, end = 30.dp)
                .background(Color.LightGray).wrapContentHeight(align = Alignment.CenterVertically),

            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = "Home Page"
        )
    }

}