package com.joe.smsuserconsentapi

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : AppCompatActivity() {

    lateinit var receiver: SmsBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun startSmsUserConsent() {
        SmsRetriever.getClient(this).also {
            it.startSmsUserConsent("+254780433770")
                .addOnSuccessListener {
                    Log.d(TAG, "success")
                }
                .addOnFailureListener {
                    Log.d(TAG, "failure")
                }
        }
    }

    private fun registerToSmsBroadcastReceiver() {
        receiver = SmsBroadcastReceiver().also {
            it.listener = object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent?) {
                    intent?.let { context -> startActivityForResult(context, REQ_USER_CONSENT) }
                }

                override fun onFailure() {

                }

            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(receiver, intentFilter)
    }

    companion object {
        const val TAG = "SMS User Consent API"
        const val REQ_USER_CONSENT = 100
    }
}
