package dev.nikhilkukreja.shoppingevents.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formateDate(
    mills: Long?,
    pattern: String = "EEE MMM dd yyyy"
): String? {
    if(mills == null) return null

    return SimpleDateFormat(
        pattern, Locale.getDefault()
    ).format(Date(mills))
}