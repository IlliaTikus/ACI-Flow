package com.example.aciflow

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : ComponentActivity() {

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.POST_NOTIFICATIONS] == true) {
            sendTestNotification()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AciFlowApp()
        }

        requestNecessaryPermissions()

        createNotificationChannel()
    }

    private fun requestNecessaryPermissions() {
        if (!checkAndRequestExactAlarmPermission()) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionsLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
            } else {
                sendTestNotification()
            }
        } else {
            sendTestNotification()
        }
    }

    private fun checkAndRequestExactAlarmPermission(): Boolean {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
            false
        } else {
            true
        }
    }

    private fun createNotificationChannel() {
        val channelId = "test_channel"
        val channelName = "Test Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel for test notifications"
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendTestNotification() {
        val channelId = "test_channel"

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_app)
            .setContentTitle("Test Notification")
            .setContentText("This is a test notification")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(1001, notification)
            }
        }
    }
}