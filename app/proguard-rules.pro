-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

-keep class com.hex.app.license.** { *; }
-keep class com.hex.app.engine.** { *; }

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

-keep class androidx.compose.** { *; }
-keepattributes *Annotation*, InnerClasses, EnclosingMethod
-dontnote kotlinx.serialization.AnnotationsKt

-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }

-repackageclasses 'a'
-allowaccessmodification
-useuniqueclassmembernames
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute ''

-flattenpackagehierarchy 'com.hex.app'
-mergeinterfacesaggressively

-keep class com.hex.app.MainActivity { *; }
-keep class com.hex.app.HexApplication { *; }
-keepclassmembers class com.hex.app.license.LicenseManager {
    public static *** checkLicense(...);
    public static *** activateLicense(...);
}
