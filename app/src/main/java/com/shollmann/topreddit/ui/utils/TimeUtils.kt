package com.shollmann.topreddit.ui.utils

import android.content.Context
import com.shollmann.topreddit.R

object TimeUtils {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS


    fun getTimeAgo(time: Long, context: Context): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> context.resources.getString(R.string.time_just_now)
            diff < 2 * MINUTE_MILLIS -> context.resources.getString(R.string.time_one_min_ago)
            diff < 50 * MINUTE_MILLIS -> String.format(
                context.resources.getString(R.string.time_minutes_ago),
                (diff / MINUTE_MILLIS).toString()
            )
            diff < 90 * MINUTE_MILLIS -> context.resources.getString(R.string.time_one_hour_ago)
            diff < 24 * HOUR_MILLIS -> String.format(
                context.resources.getString(R.string.time_hours_ago),
                (diff / HOUR_MILLIS).toString()
            )
            diff < 48 * HOUR_MILLIS -> context.resources.getString(R.string.time_one_day_ago)
            else -> String.format(
                context.resources.getString(R.string.time_days_ago),
                (diff / DAY_MILLIS).toString()
            )
        }
    }

    fun a(): Int{
        return 1+1
    }

}
