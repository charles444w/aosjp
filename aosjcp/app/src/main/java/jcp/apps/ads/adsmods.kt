package jcp.apps.ads


import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

// Initialize AdMob SDK
fun initializeAdMob(context: Context) {
    MobileAds.initialize(context) {}
}

// Composable to manage and display rewarded video ad
@Composable
fun RewardedVideoAd(
    adUnitId: String = "ca-app-pub-3940256099942544/5224354917", // Test Rewarded Ad Unit ID
    onRewardEarned: () -> Unit,
    onAdFailed: (String) -> Unit
) {
    val context = LocalContext.current
    var rewardedAd by remember { mutableStateOf<RewardedAd?>(null) }
    var adLoadStatus by remember { mutableStateOf("Not Loaded") }

    // Initialize AdMob when Composable is first composed
    LaunchedEffect(Unit) {
        initializeAdMob(context)
        loadRewardedAd(context, adUnitId, onAdLoaded = { ad ->
            rewardedAd = ad
            adLoadStatus = "Loaded"
        }, onAdFailed = { error ->
            adLoadStatus = "Failed: $error"
            onAdFailed(error)
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ad Status: $adLoadStatus")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                rewardedAd?.let { ad ->
                    ad.show(context as ComponentActivity) { rewardItem ->
                        // Reward earned, trigger callback
                        onRewardEarned()
                        adLoadStatus = "Reward Earned: ${rewardItem.type} ${rewardItem.amount}"
                        // Load a new ad after showing
                        loadRewardedAd(context, adUnitId, onAdLoaded = { newAd ->
                            rewardedAd = newAd
                            adLoadStatus = "Loaded"
                        }, onAdFailed = { error ->
                            adLoadStatus = "Failed: $error"
                            onAdFailed(error)
                        })
                    }
                } ?: run {
                    adLoadStatus = "Ad not loaded yet"
                    onAdFailed("Ad not loaded")
                }
            },
            enabled = rewardedAd != null
        ) {
            Text("Watch Rewarded Ad")
        }
    }
}

// Function to load a rewarded ad
private fun loadRewardedAd(
    context: Context,
    adUnitId: String,
    onAdLoaded: (RewardedAd) -> Unit,
    onAdFailed: (String) -> Unit
) {
    RewardedAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                onAdLoaded(ad)
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                onAdFailed(error.message)
            }
        }
    )
}