package com.hex.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hex.app.ui.theme.HexColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    LaunchedEffect(Unit) {
        delay(400)
        visible = true
        delay(800)
        showText = true
        delay(1200)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            HexColors.accent.copy(alpha = glowAlpha * 0.3f),
                            Color.Transparent
                        )
                    )
                )
                .blur(40.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "⬡",
                color = HexColors.accent,
                fontSize = 80.sp,
                fontFamily = FontFamily.Default
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "HEX",
                color = HexColors.accent,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 6.sp,
                modifier = Modifier
                    .alpha(if (showText) 1f else 0f)
                    .scale(if (showText) 1f else 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "RED PHANTOM",
                color = HexColors.textMuted,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp,
                modifier = Modifier.alpha(if (showText) 1f else 0f)
            )
        }
    }
}
