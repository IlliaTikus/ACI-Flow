package com.example.aciflow.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.aciflow.MainActivity
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

        val deadlineTitle = intent?.getStringExtra("deadlineTitle")
        val isToday = intent?.getBooleanExtra("isToday", false)

        val notificationText = if (isToday == true) {
            "Ihre Deadline $deadlineTitle ist heute!"
        } else {
            "Ihre Deadline $deadlineTitle ist in einem Tag!"
        }

        val launchAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, launchAppIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = context.let {
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.logo_app)
                .setContentTitle(deadlineTitle)
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        }

        notificationManager.notify(1, builder.build())
    }

    fun scheduleReminders(context: Context, deadlineMillis: Long, title: String) {
        checkAndRequestExactAlarmPermission(context)

        setTwentyFourHoursPriorToDeadlineNotification(deadlineMillis, context, title)
        setEndOfDeadlineNotification(deadlineMillis, context, title)
    }

    private fun setEndOfDeadlineNotification(
        deadlineMillis: Long,
        context: Context,
        title: String
    ) {
        val intent = Intent(context, DeadlineReminderReceiver::class.java)
        intent.putExtra("deadlineTitle", title)
        intent.putExtra("isToday", true)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            deadlineMillis,
            pendingIntent
        )
    }

    private fun setTwentyFourHoursPriorToDeadlineNotification(
        deadlineMillis: Long,
        context: Context,
        title: String
    ) {
        val twentyFourHourPriorToDeadlineAlarmTime = deadlineMillis - TimeUnit.DAYS.toMillis(1)
        Log.d("DEBUG", "DEADLINE RECEIVER: $twentyFourHourPriorToDeadlineAlarmTime")

        val intent = Intent(context, DeadlineReminderReceiver::class.java)
        intent.putExtra("deadlineTitle", title)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            twentyFourHourPriorToDeadlineAlarmTime,
            pendingIntent
        )
    }

    private fun checkAndRequestExactAlarmPermission(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        }
    }
}