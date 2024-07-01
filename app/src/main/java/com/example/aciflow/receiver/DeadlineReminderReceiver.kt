package com.example.aciflow.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.aciflow.R
import java.util.concurrent.TimeUnit

class DeadlineReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "reminder_channel"

        val channel = NotificationChannel(
            channelId,
            "Deadline Reminder",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val builder = context.let {
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.logo_app)
                .setContentTitle("Erinnerung")
                .setContentText("Ihre Deadline ist morgen!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
        }

        notificationManager.notify(1, builder.build())
    }

    fun scheduleReminder(context: Context, deadlineMillis: Long) {
        checkAndRequestExactAlarmPermission(context)

        val reminderTime = deadlineMillis - TimeUnit.DAYS.toMillis(1)
        Log.d("DEBUG", "DEADLINE RECEIVER: $reminderTime")

        val intent = Intent(context, DeadlineReminderReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent)
    }

    private fun checkAndRequestExactAlarmPermission(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        }
    }
}