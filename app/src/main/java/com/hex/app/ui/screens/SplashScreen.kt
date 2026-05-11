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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hex.app.R
import com.hex.app.ui.theme.HexColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }
    
    // حركة نبض للتوهج الأحمر
    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
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
        // توهج أحمر في الخلفية
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
            // الأيقونة
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(if (visible) 1f else 0f)
                    .scale(if (visible) 1f else 0.6f),
                contentAlignment = Alignment.Center
            ) {
                // أيقونة Android
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.logo_splash),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // اسم التطبيق
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
            
            // شعار
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