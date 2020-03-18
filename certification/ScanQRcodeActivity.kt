package com.tuv01.certification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import java.util.concurrent.Future


/**
 * QR reader
 */
class ScanQRcodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    var ScannerView: ZXingScannerView? = null

    // endpoint url provided by infura
    private val url = "https://ropsten.infura.io/v3/secret_endpoint" //Infura Endpoint Ropsten
    // web3j infura instance
    private val web3j = Web3j.build(HttpService(url))  //new version
    // create credentials w/ your private key (Metamask account Ropsten Test Network)
    private val credentials = Credentials.create("SECRET_CREDENTIAL")  /* University account */

    //Load smart contract
    private val certification=Certification.load(
        MainActivity.CONTRACT_ADDRESS,web3j,credentials,
        MainActivity.GAS_LIMIT,
        MainActivity.GAS_PRICE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScannerView = ZXingScannerView(this)
        setContentView(ScannerView)
    }

    override fun handleResult(rawResult: Result) {
        readSmartContract(rawResult.text)
        onBackPressed ()
    }

    override fun onPause() {
        super.onPause()
        ScannerView?.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        ScannerView?.setResultHandler(this)
        ScannerView?.startCamera()
    }

    // Read from contract correctly :)
    fun readSmartContract(hash:String){
        val thread = Thread (Runnable {
            try{
                val sha256 =  Numeric.hexStringToByteArray(hash)

                val web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
                val clientVersion = web3ClientVersion.web3ClientVersion;

                val certificating: Future<String> = certification.getDegrees(sha256).sendAsync()
                val convertToString : String? = certificating?.get()

                val parts: List<String>? = convertToString?.split(",")
                val resultstr:String = parts!![0]+"\n"+ parts[1] +"\n"+ parts[2] +"\n"+ parts[3] +"\n"+ parts[4]
                ReadContract.resultText.setText(resultstr);

                //ReadContract.resultText.setText(convertToString) //set the QrCode txt to the txtView in ReadContract.kt
            }catch (e:Exception){
                e.printStackTrace()
            }
        })
        thread.start()
    }
}