package com.tuv01.certification


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.Response
import org.web3j.protocol.core.methods.response.*
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.Future


class MainActivity : AppCompatActivity() {

    // TextViews
    lateinit var universityName: TextView
    lateinit var degreeName: TextView
    lateinit var firstName: TextView
    lateinit var lastName: TextView
    lateinit var newhash: TextView

    //ProgressBar
    lateinit var progressBar: ProgressBar
    lateinit var message: TextView
    private val TAG = javaClass.name


    companion object {
        // contract address
        const val CONTRACT_ADDRESS = "0xE83F8EA125224Ab293640829d5E4f3e0E5b1bb24"
        // gas limit
        val GAS_LIMIT = BigInteger.valueOf(20_000_000_000L)
        // gas price
        val GAS_PRICE = BigInteger.valueOf(4300000)
    }

    //function to generate a Universally unique identifier
    private fun getUniqueIdentifier(): String {
        val id= UUID.randomUUID().toString();
        return id
    }
    //function to generate sha-256
    private fun getSha256(uuid:String):String{
        val bytes = uuid.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    //function to convert a generate sha-256 String to bytes32 e.g: String => Hex => 32 length Hex  => byte[] => Bytes32
    private fun getHexStringToByteArray(input:String):ByteArray{
        val output = Numeric.hexStringToByteArray(input)
        return output
    }
    // Universal unique identifier
    private val uuid = getUniqueIdentifier()
    // generate sha256 from the uuid
    private val uuidSha256 = getSha256(uuid)
    // generate Hash from the sha256
    //private val hashSha256 = getHexStringToByteArray(uuidSha256)
    private val hashSha256 = Numeric.hexStringToByteArray(uuidSha256)

    // endpoint url provided by infura
    private val url = "https://ropsten.infura.io/v3/secret_endpoint" //Infura Endpoint Ropsten
    // web3j infura instance
    private val web3j = Web3j.build(HttpService(url))  //new version

    // create credentials w/ your private key (Metamask account Ropsten Test Network)
    private val credentials = Credentials.create("SECRET_CREDENTIAL")  /* University account */
    //Load smart contract

    private val certification=Certification.load(CONTRACT_ADDRESS,web3j,credentials, GAS_LIMIT, GAS_PRICE)


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TextViews and EditTexts
        var degrees = arrayOf("Bachelor of engineering in computer science","Bachelor of arts in business informatics","Bachelor of arts E-Commerce")
        var randomNumber = (0..2).random()
        degreeName = findViewById(R.id.txtDegree)
        degreeName.setText(degrees.get(randomNumber))
        universityName = findViewById(R.id.txtUniversity)
        firstName =findViewById(R.id.txtFirstName)
        lastName = findViewById(R.id.txtLastName)
        newhash = findViewById(R.id.textHash)

        //AlertDialog
        progressBar = findViewById(R.id.progress_bar)
        message =findViewById(R.id.message)


        newhash.text = uuidSha256

        fab.setOnClickListener {
           val thread = Thread (Runnable {
               try{

                   checkValiditySmartContract()

                   Log.d(TAG,"unique identifier:$uuid")   // Universal Unique Identifier
                   Log.d(TAG,"SHA256 generates from the UUID:$uuidSha256") // SHA256 generated from the UUID
                   Log.d(TAG,"Hash generated from the SHA256:$hashSha256") // HASH generated from the uuidSHA256

                   //AlertDialog
                   this@MainActivity.runOnUiThread(java.lang.Runnable {
                       progressBar.visibility = View.VISIBLE
                       message.visibility = View.VISIBLE
                   })

                   // write to contract
                   writeSmartContractAdvanced()

                   //cancelPendingTransaction()

           }catch (e:Exception){
                   e.printStackTrace()
                   Log.d(TAG,"error accessing contract:"+e.message)
               }
           })
            thread.start()
        }

    }


    //Load smart contract and check contract validity
    fun checkValiditySmartContract(){
        //check contract validity
        Log.d(TAG,"${certification.isValid}")   // returns false if the contract bytecode does not match what's deployed at the provided address
    }


    // Write contract correctly
    // example an: 0x73ae626e109a82f81e4098eb59d5170deba6a7b0ba6ace1cfd8d5c499795d64a
    fun writeSmartContractAdvanced(){
        /* for security principle only TextView*/

        val degreeInformation = "University:${universityName.text},Degree:${degreeName.text},Firstname:${firstName.text},LastName:${lastName.text},Hash:${newhash.text}"

        Log.d(TAG,"University:${universityName.text},Degree:${degreeName.text},Firstname:${firstName.text},LastName:${lastName.text},Hash:${newhash.text}")

        val transactionReceipt: Future<TransactionReceipt>? = certification.setDegree(hashSha256,degreeInformation).sendAsync()
        val result = "Successful transaction:\n Block number:${transactionReceipt?.get()?.blockNumber} \n Gas used: ${transactionReceipt?.get()?.gasUsed}"
        val gasUsed=transactionReceipt?.get()?.gasUsed
        val price = gasUsed!! * BigInteger.valueOf(0.00000002.toLong())
        Log.d(TAG,result)
        Log.d(TAG, "Price:"+price.toString()+"Ether")

        // Go to MainActivity.

        val intent = Intent (this,SelectActivity::class.java)
        startActivity(intent)
    }


    // Read from contract correctly :)
    fun readSmartContract(){
        val hash = "0x73ae626e109a82f81e4098eb59d5170deba6a7b0ba6ace1cfd8d5c499795d64a"   // String => Hex => 32 length Hex  => byte[] => Bytes32
        val sha256 =  Numeric.hexStringToByteArray(hash)

        val web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
        val clientVersion = web3ClientVersion.getWeb3ClientVersion();

        //val certificating: CompletableFuture<String>? = certification.getDegrees(sha256).sendAsync()
        val certificating: Future<String> = certification.getDegrees(sha256).sendAsync()
        val convertToString : String? = certificating?.get()
        Log.d(TAG,"Your Hash information is: $convertToString")
    }

    /*Used to cancel a pending transaction (contract)
    * Nonce = the number of the pending transaction
    * to = the contract owner address
     */
    fun cancelPendingTransaction() {
        val value = Convert.toWei("0.0", Convert.Unit.ETHER).toBigInteger()

        val ethGetTransactionCount: EthGetTransactionCount? = web3j.ethGetTransactionCount(credentials.address,DefaultBlockParameterName.LATEST).send()

        val nonce:BigInteger = ethGetTransactionCount!!.transactionCount
        Log.d(TAG,"nonce: $nonce")

        // Prepare the transaction
        val rawTransaction = RawTransaction.createEtherTransaction(
            nonce,
            GAS_PRICE*BigInteger.valueOf(2),
            GAS_LIMIT*BigInteger.valueOf(2),
            "0x84C09e117C7E935a6aA1a8a6bF81fb4BA861c590",
            BigInteger.ZERO)

        // Send the transaction
        // Sign the transaction
        val signedMessage: ByteArray? = TransactionEncoder.signMessage(rawTransaction, credentials);
        // Convert it to Hexadecimal String to be sent to the node
        val hexValue:String = Numeric.toHexString(signedMessage);

        // Send transaction
        val ethSendTransaction:EthSendTransaction= web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        // Get the transaction hash
        val transactionHash = ethSendTransaction.transactionHash;

        Log.d(TAG,"transaction hash ="+transactionHash);

        var transactionReceipt : Optional<TransactionReceipt>? = null
        do {
            System.out.println("checking if transaction " + transactionHash + " is mined....");
            val ethGetTransactionReceiptResp : EthGetTransactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
            transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
            Thread.sleep(3000); // Wait 3 sec
        } while(!transactionReceipt!!.isPresent)

        System.out.println("""Transaction $transactionHash was mined in block # ${transactionReceipt.get().getBlockNumber()}""");
        System.out.println("Balance: " + Convert.fromWei(web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Convert.Unit.ETHER));

    }

     */
}
