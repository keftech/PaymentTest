package kef.technology.paymenttest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.remita.paymentsdk.core.RemitaInlinePaymentSDK;
import com.remita.paymentsdk.data.PaymentResponse;
import com.remita.paymentsdk.listener.RemitaGatewayPaymentResponseListener;
import com.remita.paymentsdk.util.JsonUtil;
import com.remita.paymentsdk.util.RIPGateway;

import java.util.Date;

import kef.technology.paymenttest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RemitaGatewayPaymentResponseListener {

    private final String encrypt_key = "FLWSECK_TESTca8bf129b9a7";
    private final String pub_key = "FLWPUBK_TEST-f4a359327ca1a8d654f53a96be2086a2-X";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.remitaInitiatePaymentPage.setOnClickListener(view -> {
            double amount = Double.parseDouble(binding.amountBox.getText().toString());
            String url = RIPGateway.Endpoint.DEMO;
            String api_key = "QzAwMDAxOTUwNjl8NDMyNTkxNjl8ZTg0MjI2MDg4MjU0NzA2NTY2MTYwNGU1NjNiMjUzYjk4ZDQwZjljZGFiMTVmYTljMDUwMGQ0MDg2MjIyYjEyNTA1ZTE2MTMxNmE3ZjM1OTZmYmJkOTE2MTRiY2NmZTY5NTM4MGQ2MDBlZGJlZmM2ODc2YTc2M2M4MjgyZmFjODc=";
            String email = "kefblogtech@gmail.com";
            String currencyCode = "NGN";
            String firstName = "Jamiu";
            String lastName = "Akinyemi";
            String customerId = "kefblogtech@gmail.com";
            String phoneNumber = "08168156922";
            String transactionId = String.valueOf(new Date().getTime());
            String narration = "HP Pavilion x360";

            new RaveUiManager(this).setAmount(amount).setCurrency(currencyCode).acceptCardPayments(true).setfName(firstName).setlName(lastName).setEmail(email).setTxRef(transactionId).setPublicKey(pub_key).setEncryptionKey(encrypt_key).setNarration(narration).initialize();
            //RemitaInlinePaymentSDK remitaInlinePaymentSDK = RemitaInlinePaymentSDK.getInstance();
            //remitaInlinePaymentSDK.setRemitaGatewayPaymentResponseListener(MainActivity.this);
            //remitaInlinePaymentSDK.initiatePayment(MainActivity.this, url, api_key, email, amount, currencyCode, firstName, lastName, customerId, phoneNumber, transactionId, narration);
        });
    }

    @Override
    public void onPaymentCompleted(PaymentResponse paymentResponse) {
        Log.d("", JsonUtil.toJson(paymentResponse));
        Toast.makeText(this, JsonUtil.toJson(paymentResponse), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}