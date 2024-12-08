package com.example.carouseleffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.carouseleffect.ui.theme.CarouselEffectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarouselEffectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimateCarousel(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

val items = listOf(
    CarousalItem(0, R.drawable.image1, R.string.image1),
    CarousalItem(1, R.drawable.image3, R.string.image3),
    CarousalItem(2, R.drawable.image4, R.string.image4),
    CarousalItem(3, R.drawable.image5, R.string.image5)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimateCarousel(modifier: Modifier = Modifier) {

    val animatedScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            animatedScale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
            )

            animatedScale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
            )

        }
    }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState {
            items.size
        },
        modifier = modifier,
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        minSmallItemWidth = 40.dp,
        maxSmallItemWidth = 56.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { index ->
        Image(
            painter = painterResource(id = items[index].imageResourceId),
            modifier = Modifier
                .height(205.dp)
                .maskClip(shape = MaterialTheme.shapes.extraLarge)
                .graphicsLayer(
                    scaleX = animatedScale.value,
                    scaleY = animatedScale.value
                ),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = items[index].contentDescription)
        )
    }

//    HorizontalUncontainedCarousel(
//        state = rememberCarouselState {
//            items.size
//        },
//        modifier = modifier,
//        itemSpacing = 8.dp,
//        itemWidth = 186.dp,
//        contentPadding = PaddingValues(horizontal = 16.dp)
//    ) { index ->
//        Image(
//            painter = painterResource(id = items[index].imageResourceId),
//            modifier = Modifier
//                .height(205.dp)
//                .maskClip(shape = MaterialTheme.shapes.extraLarge)
//                .graphicsLayer(
//                    scaleX = animatedScale.value,
//                    scaleY = animatedScale.value
//                ),
//            contentScale = ContentScale.Crop,
//            contentDescription = stringResource(id = items[index].contentDescription)
//        )
//    }
}

data class CarousalItem(
    val id: Int,
    @DrawableRes val imageResourceId: Int,
    @StringRes val contentDescription: Int
)