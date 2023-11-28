package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var timeView : TextView
    lateinit var timerBinder : TimerService.TimerBinder
    var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeView = findViewById<TextView>(R.id.timeView)
//initialize text view
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                timerBinder = service as TimerService.TimerBinder
                isConnected = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isConnected = false
            }
        }

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )
        findViewById<Button>(R.id.startButton).setOnClickListener {
            if(isConnected) timerBinder.start(10)
        }

        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            if(isConnected) timerBinder.pause()
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            if(isConnected) timerBinder.stop()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.pauseButton -> if(isConnected) timerBinder.pause()
            R.id.startButton -> if(isConnected) timerBinder.start(10)
            R.id.stopButton -> if(isConnected) timerBinder.stop()
        }
        return super.onOptionsItemSelected(item)
    }

}