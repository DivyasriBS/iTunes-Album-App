package com.divyasri.itunes.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import android.widget.TextView
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * @author Divyasri B S
 */
class Utility {
    companion object {

        fun isPastDate(dateInMillis: Long?): Boolean {
            val c = Calendar.getInstance()
            c.timeInMillis = dateInMillis!!
            return c.time.before(Date())
        }

        fun convertDpToPx(dp: Int): Int {
            return (dp * Resources.getSystem()
                .displayMetrics.density).toInt()
        }

        fun formatDate(
            date: String?, inPattern: String?,
            outPattern: String?
        ): String? {
            val returnValue: String?
            val format =
                SimpleDateFormat(inPattern, Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            returnValue = try {
                val tempDate = format.parse(date)
                val outFormat = SimpleDateFormat(outPattern)
                outFormat.format(tempDate)
            } catch (e: Exception) {
                date
            }
            return returnValue
        }

        fun formatDate(date: String?, inPattern: String?): Date? {
            val returnValue: Date? = null
            val format = SimpleDateFormat(inPattern)
            try {
                val tempDate = format.parse(date)
                //            SimpleDateFormat outFormat = new SimpleDateFormat(outPattern);
//            returnValue = outFormat.format(tempDate);
            } catch (e: ParseException) {
            }
            return returnValue
        }

        fun formatDate(timeInMillis: Long, pattern: String?): String {
            var returnValue: String
            //        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(timeInMillis);
            val date = Date(timeInMillis)
            val format = SimpleDateFormat(pattern)
            return format.format(date)
        }

        fun formatDateAsLong(date: String?, pattern: String?): Long {
            val format = SimpleDateFormat(pattern)
            format.timeZone = TimeZone.getDefault()
            val tempDate: Date?
            tempDate = try {
                format.parse(date)
            } catch (e: ParseException) {
                Date()
            }
            val c = Calendar.getInstance()
            c.time = tempDate
            return c.timeInMillis
        }

        fun splitDate(startDate: String?, pattern: String?): IntArray {
            val dateArr = IntArray(3)
            val format = SimpleDateFormat(pattern)
            val c = Calendar.getInstance()
            try {
                c.time = format.parse(startDate)
                dateArr[0] = c[Calendar.DATE]
                dateArr[1] = c[Calendar.MONTH]
                dateArr[2] = c[Calendar.YEAR]
            } catch (e: ParseException) {
                // Should not normally occur;
            }
            return dateArr
        }

        fun convertTo12HourFormat(input: String?): String {
            val _24HourSDF = SimpleDateFormat("HH:mm")
            val _12HourSDF = SimpleDateFormat("hh:mm a")
            var _24HourDt: Date? = null
            try {
                _24HourDt = if (!isNullOrEmpty(input)) {
                    _24HourSDF.parse(input)
                } else {
                    return " "
                }
            } catch (e: ParseException) {
                e.message
            }
            println(_24HourDt)
            println(_12HourSDF.format(_24HourDt))
            return _12HourSDF.format(_24HourDt)
        }


        fun isNullOrEmpty(s: EditText?): Boolean {
            return s?.getText() == null || s.getText().toString().trim()
                .equals(AppConstants.STR_BLANK);
        }

        fun isNullOrEmpty(s: TextView?): Boolean {
            return s == null || s.text == null || (s.text.toString()
                .trim ().equals(AppConstants.STR_BLANK))
        }

        fun isNullOrEmpty(s: String?): Boolean {
            return s == null || s.trim { it <= ' ' } == AppConstants.STR_BLANK || s.equals(
                AppConstants.STR_NULL,
                ignoreCase = true
            )
        }

        fun isValidDate(date: String?, inPattern: String?): Boolean {
            try {
                val temp = SimpleDateFormat(inPattern).parse(date)
                if (temp.before(Date())) {
                    return false
                }
            } catch (e: ParseException) {
                return false
            }
            return true
        }







    }
}