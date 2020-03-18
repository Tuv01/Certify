package com.tuv01.certification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_read_contract.*
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.concurrent.Future


class ReadContract : AppCompatActivity() {

    companion object {
        // contract address
        const val CONTRACT_ADDRESS = "0xE83F8EA125224Ab293640829d5E4f3e0E5b1bb24"
        // gas limit
        val GAS_LIMIT = BigInteger.valueOf(20_000_000_000L)
        // gas price
        val GAS_PRICE = BigInteger.valueOf(4300000)

        // to display the result
        lateinit var resultText:TextView
        lateinit var qrResultText:String

    }
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

    //EditText and TextView
    lateinit var editTextHash:EditText
    lateinit var errorMsg:String
    private val TAG = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_contract)
        resultText=findViewById(R.id.result_text)
        editTextHash = findViewById<EditText>(R.id.editTextHash)
        errorMsg=""

        btnRead.setOnClickListener {
            val thread = Thread (Runnable {
                try{
                    // read from contract
                    readSmartContract()
                }catch (e:Exception){
                    e.printStackTrace()
                    Log.d(TAG,"Error accessing contract: "+e.message)
                    errorMsg=e.message.toString()
                    setErrorText(errorMsg)
                }
            })
            thread.start()
        }
        btnQrCode.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    ScanQRcodeActivity::class.java
                )
            )
        })

    }
    // Read from contract correctly :)
    fun readSmartContract(){

        val hash = editTextHash.text.toString() // String => Hex => 32 length Hex  => byte[] => Bytes32
        val sha256 =  Numeric.hexStringToByteArray(hash)

        val web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
        val clientVersion = web3ClientVersion.web3ClientVersion;

        val certificating: Future<String> = certification.getDegrees(sha256).sendAsync()
        val convertToString : String? = certificating.get()
        val parts: List<String>? = convertToString?.split(",")
        val resultstr:String = parts!![0]+"\n"+ parts[1] +"\n"+ parts[2] +"\n"+ parts[3] +"\n"+ parts[4]
        resultText.text = resultstr
    }

    fun setErrorText(errorMsg:String){
        resultText.text = "Error accessing contract: incorrect hash code \n Information: $errorMsg"
    }
	
}
