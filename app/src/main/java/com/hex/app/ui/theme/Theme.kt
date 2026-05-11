package com.hex.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    // ═══ ألوان أساسية ═══
    primary = HexColors.accent,                    // #FF0000
    onPrimary = Color.White,                       // نص على الأحمر
    primaryContainer = HexColors.accentDark,       // #B30000
    onPrimaryContainer = Color.White,
    
    // ═══ ألوان ثانوية ═══
    secondary = HexColors.accentDark,              // #B30000
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF330000),        // أحمر داكن جداً
    onSecondaryContainer = Color.White,
    
    // ═══ ألوان الخلفيات ═══
    background = HexColors.bgPrimary,              // #0A0A0B
    onBackground = HexColors.textPrimary,          // أبيض
    
    // ═══ ألوان الأسطح ═══
    surface = HexColors.surface,                   // #1A1A1C
    onSurface = HexColors.textPrimary,
    surfaceVariant = Color(0xFF1F0000),            // أسود محمر
    onSurfaceVariant = HexColors.textSecondary,
    
    // ═══ ألوان الخطأ ═══
    error = HexColors.danger,                      // #FF0000
    onError = Color.White,
    errorContainer = Color(0xFF330000),
    onErrorContainer = Color.White,
    
    // ═══ حدود وخطوط ═══
    outline = HexColors.border,                    // #2A0000
    outlineVariant = Color(0xFF1A0000),
    
    // ═══ ألوان معكوسة ═══
    inverseSurface = Color(0xFFE6E0D8),
    inverseOnSurface = Color.Black,
    inversePrimary = HexColors.accentDark,
    
    // ═══ سطوع ═══
    surfaceTint = HexColors.accent,
    scrim = Color.Black.copy(alpha = 0.6f)
)

@Composable
fun HexTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}