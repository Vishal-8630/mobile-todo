package com.example.to_doapplication.util

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

fun sendOtpViaSendGrid(email: String, otp: String, onResult: (Boolean, String?) -> Unit) {
    val client = OkHttpClient()

    val json = """
        {
          "personalizations":[{"to":[{"email":"$email"}]}],
          "from":{"email":"vishh8630@gmail.com"},
          "subject":"Your OTP Code",
          "content":[{"type":"text/plain","value":"Your OTP is: $otp"}]
        }
    """.trimIndent()

    val request = Request.Builder()
        .url("https://api.sendgrid.com/v3/mail/send")
        .addHeader("Authorization", "Bearer ${BuildConfig.SENDGRID_API_KEY}")
        .addHeader("Content-Type", "application/json")
        .post(RequestBody.create("application/json".toMediaTypeOrNull(), json))
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResult(false, e.message)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, response.message)
            }
        }
    })
}
