package com.subhambikash.call

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    lateinit var call:FloatingActionButton
    lateinit var number:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        call=findViewById(R.id.call)
        number=findViewById(R.id.phoneNumber)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            resultLauncher.launch(android.Manifest.permission.CALL_PHONE)
        }else{
            callToNumber()
        }

    }


    private fun validatorPhone(number:String):Boolean{
        val pattern=Pattern.compile("[6-9][0-9]{9}")
        return pattern.matcher(number).matches()
    }

    private val resultLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            callToNumber()
        }else{
            Toast.makeText(this,"pleaseAllowPermission",Toast.LENGTH_SHORT).show()
        }
    }

    private fun callToNumber() {
        call.setOnClickListener {
            val phoneNumber=number.text.toString().trim()
            if (TextUtils.isEmpty(phoneNumber)){
                number.error="empty"
                return@setOnClickListener
            }else{
                if (validatorPhone(phoneNumber)){
                    val intent=Intent(Intent.ACTION_DIAL)
                    intent.data= Uri.parse("tel: $phoneNumber")
                    startActivity(intent)
                }else{
                    number.error="Check Phone Number"
                    return@setOnClickListener
                }
            }
        }
    }


}