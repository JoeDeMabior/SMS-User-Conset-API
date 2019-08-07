package com.joe.smsuserconsentapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver : BroadcastReceiver() {

    lateinit var listener: SmsBroadcastReceiverListener

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val bundle = intent.extras
            val smsRetrieverStatus = bundle?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (smsRetrieverStatus.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    bundle.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT).also {
                        listener.onSuccess(it)
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    listener.onFailure()
                }
            }
        }
    }

    interface SmsBroadcastReceiverListener {
        fun onSuccess(intent: Intent?)
        fun onFailure()
    }

}
