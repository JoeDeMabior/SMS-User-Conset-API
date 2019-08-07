package com.joe.smsuserconsentapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : AppCompatActivity() {

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

    companion object {
        const val TAG = "SMS User Consent API"
        const val REQ_USER_CONSENT = 100
    }
}
