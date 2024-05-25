package com.example.tanlam.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView() {
    val currentDate = remember { LocalDate.now() }
    val firstDayOfMonth = currentDate.withDayOfMonth(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = YearMonth.from(currentDate).lengthOfMonth()

    Column(modifier = Modifier.run { padding(16.dp) }) {
        MonthHeader(currentDate)
        DaysOfWeekHeader()
        DaysGrid(firstDayOfWeek, daysInMonth)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(currentDate: LocalDate) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = currentDate.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()) + " " + currentDate.year,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DaysOfWeekHeader() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(modifier = Modifier.fillMaxWidth()) {
        for (day in daysOfWeek) {
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DaysGrid(firstDayOfWeek: Int, daysInMonth: Int) {
    val days = (1..daysInMonth).toList()
    var currentDay = 1 - firstDayOfWeek

    for (week in 0 until 6) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (day in 0 until 7) {
                if (currentDay > 0 && currentDay <= daysInMonth) {
                    Text(
                        text = currentDay.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "",
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                currentDay++
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun CalendarViewPreview() {
    MaterialTheme {
        CalendarView()
    }
}