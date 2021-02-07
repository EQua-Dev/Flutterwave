package com.androidstrike.flutterwaverave

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RaveUiManager
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Test Mastercard details for mock
Card number: 5531 8866 5214 2950
cvv: 564
Expiry: 09/32
Pin: 3310
OTP: 12345

**/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            makepayment()
        }
    }

    private fun makepayment() {
        RaveUiManager(this)
                .setAmount("2000".toDouble())
                .setEmail("example@gmail.com")
                .setCountry("NG")
                .setCurrency("NGN").setfName("Luomy").setlName("EQua")
                .setNarration("Purchase for test implementation")
                .setPublicKey("insert public key here")
                .setEncryptionKey("insert encryption key here")
                .setTxRef("${System.currentTimeMillis()} Ref ")
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                //.isPreAuth(true) this is used if you want to charge the customer but not withdraw it immediately, eg uber
                //.setSubAccounts() takes in list as parameter, it is used if you want to pay the charged fee into 2 accounts
                //.setPaymentPlan() used for subscription basis
                .onStagingEnv(true)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == )
//        super.onActivityResult(requestCode, resultCode, data)

        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
        */
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
        */
        if (requestCode === RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message: String = data.getStringExtra("response").toString() //returns the entire raw data of the transaction
            Log.d("EQUA", "onActivityResult: $message")
            if (resultCode === RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS $message", Toast.LENGTH_LONG).show()
            } else if (resultCode === RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR $message", Toast.LENGTH_LONG).show()
            } else if (resultCode === RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED $message", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}