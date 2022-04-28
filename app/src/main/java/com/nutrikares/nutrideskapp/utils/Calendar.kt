package com.nutrikares.nutrideskapp.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar

class Calendar {

    companion object {
        private object Days {
            const val MONDAY = 1
            const val TUESDAY = 2
            const val WEDNESDAY = 3
            const val THURSDAY = 4
            const val FRIDAY = 5
            const val SATURDAY = 6
            const val SUNDAY = 7
        }

        val dateIndex = {x: String -> when(x){
            "Lunes", "monday" -> Days.MONDAY
            "Martes", "tuesday" -> Days.TUESDAY
            "Miércoles", "wednesday" -> Days.WEDNESDAY
            "Jueves", "thursday" -> Days.THURSDAY
            "Viernes", "friday" -> Days.FRIDAY
            "Sábado", "saturday" -> Days.SATURDAY
            "Domingo", "sunday" -> Days.SUNDAY
            else -> 0
        }}

        private val date = {x: Int, lang: String -> when(lang){
            "es" -> {
                when(x){
                    Calendar.MONDAY -> "Lunes"
                    Calendar.TUESDAY -> "Martes"
                    Calendar.WEDNESDAY -> "Miércoles"
                    Calendar.THURSDAY -> "Jueves"
                    Calendar.FRIDAY -> "Viernes"
                    Calendar.SATURDAY -> "Sábado"
                    Calendar.SUNDAY -> "Domingo"
                    else -> "Undefined"
                }
            }
            "en" -> when(x){
                Calendar.MONDAY -> "monday"
                Calendar.TUESDAY -> "tuesday"
                Calendar.WEDNESDAY -> "wednesday"
                Calendar.THURSDAY -> "thursday"
                Calendar.FRIDAY -> "friday"
                Calendar.SATURDAY -> "saturday"
                Calendar.SUNDAY -> "sunday"
                else -> "Undefined"
            }
            else -> "Undefined"
        }
        }

        fun translate(date: String, language: String = "es"): String {
            return (when(language) {
                "es" -> {
                    when (date) {
                        "monday" -> "Lunes"
                        "tuesday" -> "Martes"
                        "wednesday" -> "Miércoles"
                        "thursday" -> "Jueves"
                        "friday" -> "Viernes"
                        "saturday" -> "Sábado"
                        "sunday" -> "Domingo"
                        else -> "Undefined"
                    }
                }
                "en" -> {
                    when (date) {
                        "Lunes" -> "monday"
                        "Martes" -> "tuesday"
                        "Miércoles" -> "wednesday"
                        "Jueves" -> "thursday"
                        "Viernes" -> "friday"
                        "Sábado" -> "saturday"
                        "Domingo" -> "sunday"
                        else -> "Undefined"
                    }
                }
                else -> "Undefined language"
            })
        }

        fun getDate(language: String = "es"): String {
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            return date(today, language)
        }

        fun getWeekStartEnd(): Pair<String, String>{
            val today = Calendar.getInstance()
            today.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            val formatter = SimpleDateFormat("d MMM", Locale.forLanguageTag("es-MX"))
            val pastSunday = formatter.format(today.time)
            today.add(Calendar.DATE, 7)
            val nextSunday = formatter.format(today.time)
            return Pair(pastSunday, nextSunday)

        }
    }

}