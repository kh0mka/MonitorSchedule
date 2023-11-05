#
# For more details, see http://developer.android.com/guide/developing/tools/proguard.html
#
-keepattributes Signature
-keepattributes Annotation
-keep class sun.misc.Unsafe { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class com.khomenok.monitorschedule.domain.models.* { *; }
-keep class com.khomenok.monitorschedule.data.db.entities.* { *; }
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type


