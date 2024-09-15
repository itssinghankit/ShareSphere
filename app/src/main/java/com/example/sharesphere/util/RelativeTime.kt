import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
fun getRelativeTimeSpan(dateString: String): String {
    val then = Instant.parse(dateString)
    val now = Instant.now()

    val seconds = ChronoUnit.SECONDS.between(then, now)
    val minutes = ChronoUnit.MINUTES.between(then, now)
    val hours = ChronoUnit.HOURS.between(then, now)
    val days = ChronoUnit.DAYS.between(then, now)

    return when {
        abs(seconds) < 60 -> "just now"
        abs(minutes) < 60 -> "${abs(minutes)}m"
        abs(hours) < 24 -> "${abs(hours)}h"
        abs(days) < 365 -> "${abs(days) }d"
        else -> "${abs(days) / 365}y"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTimeRelative(dateString: String): String {
    val instant = Instant.parse(dateString)
    val zone = ZoneId.systemDefault()
    val localDate = instant.atZone(zone).toLocalDate()
    val now = LocalDate.now(zone)

    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        .withZone(zone)
        .withLocale(Locale.US)

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(zone)
        .withLocale(Locale.US)

    val time = timeFormatter.format(instant)

    return when {
        localDate == now -> time
        localDate == now.minusDays(1) -> "Yesterday"
        localDate.year == now.year -> dateFormatter.format(instant)
        else -> dateFormatter.format(instant)
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun main(){
//    val date ="2024-07-28T16:01:35.549Z"
//    println(getRelativeTimeSpan(date))
//    println(formatDateTimeRelative(date))
//}