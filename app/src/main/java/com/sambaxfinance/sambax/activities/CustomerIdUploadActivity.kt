package com.sambaxfinance.sambax.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.MyApi2
import com.sambaxfinance.sambax.models.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream



const val CUSTOMER_ID_URL = "com.sambax.sambax.customer_id_url"
class CustomerIdUploadActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private var selectedImageUri: Uri? = null
    private var selectedImageUri2: Uri? = null
    private var uploadSuccess1: String? = null
    private var uploadSuccess2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_id_upload)

        // Get the Intent that started this activity and extract the string
        val first_name_fresh = intent.getStringExtra(FIRST_NAME_ASSIGN)
        val last_name_fresh = intent.getStringExtra(LAST_NAME_ASSIGN)
        val password_fresh = intent.getStringExtra(PASSWORD_ASSIGN)
        val phone_number_fresh = intent.getStringExtra(PHONE_NUMBER_ASSIGN)
        val customer_image_url = intent.getStringExtra(CUSTOMER_IMAGE_URL)
        //val signtoken = intent.getStringExtra(EXTRA_MESSAGE2)

        val buttonId = findViewById<Button>(R.id.pick_national_id_button)
        val imageViewId = findViewById<ImageView>(R.id.image_view_national_id)
        val button_customer_id_upload = findViewById<Button>(R.id.button_customer_id_upload)



        buttonId.setOnClickListener {
            //uploadImage()
            //println("you have clicked buttonId")
            openImageChooser2()
        }

        button_customer_id_upload.setOnClickListener {
            //uploadImage()
            //println("you have clicked buttonId")
            //let us create a namestring to be used as name of folder on server
            val folderName = phone_number_fresh.toString()

            // let us try to deal with the image upload
            //println("kokolioko here")
            //upload image here
            //uploadImage(folderName)
            uploadImage2(folderName)

            //println("hehe")
            //println(uploadSuccess1)
            //println(uploadSuccess2)


            //let us get the file name for customers id
            //val id_file = File(cacheDir, contentResolver.getFileName(selectedImageUri2!!))
            //val customer_id_url = id_file.name
            //println(customer_id_url)

        }


    }

    private fun openImageChooser2() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, CustomerIdUploadActivity.REQUEST_CODE_PICK_IMAGE2)
        }
    }

    private fun uploadImage2(nameofapplicant:String) {
        println("this is the second upload image function")

        // Get the Intent that started this activity and extract the string
        val first_name_fresh = intent.getStringExtra(FIRST_NAME_ASSIGN)
        val last_name_fresh = intent.getStringExtra(LAST_NAME_ASSIGN)
        val password_fresh = intent.getStringExtra(PASSWORD_ASSIGN)
        val phone_number_fresh = intent.getStringExtra(PHONE_NUMBER_ASSIGN)
        val customer_image_url = intent.getStringExtra(CUSTOMER_IMAGE_URL)
        //val signtoken = intent.getStringExtra(EXTRA_MESSAGE2)

        val layout_root = findViewById<ConstraintLayout>(R.id.layout_root)
        val progress_bar = findViewById<ProgressBar>(R.id.progress_bar)
        if (selectedImageUri2 == null) {
            layout_root.snackbar("Select an Image First")
            return
        }

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri2!!, "r", null) ?: return

        //val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri2!!))

        val customer_id_url = file.name
        println(customer_id_url)

        val outputStream = FileOutputStream(file)
        //inputStream.copyTo(outputStream)

        //let us try to downscale the image before upload
        val imageByteArray = getImageByteArray(selectedImageUri2!!)

        outputStream.write(imageByteArray)
        //outputStream.flush()
        //outputStream.close()


        //inputStream.copyTo(outputStream)


        progress_bar.progress = 0
        val body = UploadRequestBody(file, "image", this)
        MyApi2().uploadImage(
            MultipartBody.Part.createFormData(
                "picture",
                file.name,
                body
            ),
            RequestBody.create(MediaType.parse("multipart/form-data"), nameofapplicant)
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                //layout_root.snackbar(t.message!!)
                progress_bar.progress = 0

                Toast.makeText(this@CustomerIdUploadActivity,t.message, Toast.LENGTH_LONG).show()
                println("bigaanyi")
                println(t.message!!)
                println(t.toString())

                // Capture the layout's TextView and set the string as its text
                val tvLoginResponse = findViewById<TextView>(R.id.tvResponseIdUpload).apply {
                    text = "Failed to upload image.Please check your internet connection"
                }
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                Toast.makeText(this@CustomerIdUploadActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                uploadSuccess2 = "ok"

                println("bikoze")
                println(uploadSuccess2)
                println(response.body().toString())
                println("hipopo hipopo")
                println(response.message().toString())
                progress_bar.progress = 100

                //response.body()?.let {
                //layout_root.snackbar(it.message)

                //progress_bar.progress = 100


                //println(response.body().message)
                //}

                val bodyMessage = response.body()?.message
                println(bodyMessage)


                //val okResponse = response.message().toString()

                if (bodyMessage == "done"){
                    //startActivity(intent)
                    val intent = Intent(this@CustomerIdUploadActivity, OtpActivity::class.java).apply {
                        putExtra(FIRST_NAME_ASSIGN, first_name_fresh)
                        putExtra(LAST_NAME_ASSIGN, last_name_fresh)
                        putExtra(PASSWORD_ASSIGN, password_fresh)
                        putExtra(PHONE_NUMBER_ASSIGN, phone_number_fresh)
                        putExtra(CUSTOMER_IMAGE_URL, customer_image_url)
                        putExtra(CUSTOMER_ID_URL, customer_id_url)
                    }
                    startActivity(intent)

                } else {
                    // Capture the layout's TextView and set the string as its text
                    val tvLoginResponse = findViewById<TextView>(R.id.tvResponseIdUpload).apply {
                        text = "Failed to upload image"
                    }
                }


            }
        })

    }

    override fun onProgressUpdate(percentage: Int) {
        val progress_bar = findViewById<ProgressBar>(R.id.progress_bar)
        progress_bar.progress = percentage
    }

    companion object {
        //const val REQUEST_CODE_PICK_IMAGE = 101
        const val REQUEST_CODE_PICK_IMAGE2 = 1005

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {


                CustomerIdUploadActivity.REQUEST_CODE_PICK_IMAGE2 -> {
                    selectedImageUri2 = data?.data
                    val buttonId = findViewById<Button>(R.id.pick_national_id_button)
                    val imageViewId = findViewById<ImageView>(R.id.image_view_national_id)
                    imageViewId.setImageURI(selectedImageUri2)


                }

            }
        }
    }

    private fun saveImageToSambaxFinance(){
        //let us try to downscale the image before upload
        val imageByteArray = getImageByteArray(selectedImageUri2!!)
    }

    private fun getImageByteArray(photoUri:Uri):ByteArray{
        val originalBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val source = ImageDecoder.createSource(contentResolver, photoUri)
            ImageDecoder.decodeBitmap(source)
        } else{
            MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
        }
        Log.i(ContentValues.TAG, "original width ${originalBitmap.width} and height ${originalBitmap.height}")
        println(originalBitmap.width)
        println(originalBitmap.height)

        val scaledBitmap = BitmapScaler.scaleToFitHeight(originalBitmap, 250)

        Log.i(ContentValues.TAG, "scaled width ${scaledBitmap.width} and height ${scaledBitmap.height}")
        println(scaledBitmap.width)
        println(scaledBitmap.height)

        val byteOutputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteOutputStream)

        return byteOutputStream.toByteArray()

    }


}