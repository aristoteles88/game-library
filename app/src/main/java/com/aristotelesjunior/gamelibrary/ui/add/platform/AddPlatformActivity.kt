package com.aristotelesjunior.gamelibrary.ui.add.platform

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.DataConverter
import com.aristotelesjunior.gamelibrary.database.GameDB
import com.aristotelesjunior.gamelibrary.database.Platform
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddPlatformActivity : AppCompatActivity() {

    //Our variables
    private var ivAddPlatform: ImageView? = null
    private var pictureURI: Uri? = null

    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_platform)

        val etPlatformName = findViewById<EditText>(R.id.etPlatformName)
        val etReleaseDate = findViewById<EditText>(R.id.etReleaseDate)
        ivAddPlatform = findViewById(R.id.ivAddPlatform)
        val btnTakePicture = findViewById<Button>(R.id.btnTakePicture)
        val btnOpenGallery = findViewById<Button>(R.id.btnOpenGallery)
        val btnAddPlatform = findViewById<Button>(R.id.btnAddPlatform)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        btnTakePicture.setOnClickListener {
            capturePhoto()
        }

        btnOpenGallery.setOnClickListener {
            openGallery()
        }

        btnAddPlatform.setOnClickListener {
            val bitmapImage: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, pictureURI!!)
            if (etPlatformName.text.toString().isNotEmpty() && DataConverter.DbBitmapUtility.getBytes(bitmapImage).isNotEmpty()) {
                val newPlatform = Platform(
                    id = 0,
                    name = etPlatformName.text.toString(),
                    releaseDate = etReleaseDate.text.toString(),
                    gamesAmount = 0,
                    image = DataConverter.DbBitmapUtility.getBytes(bitmapImage)
                )
                GameDB.getInstance(this).platformDao().insertPlatform(newPlatform)
            }
            finish()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun show(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "Photo_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun capturePhoto(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = createImageFile()
        pictureURI = FileProvider.getUriForFile(
            this,
            "com.aristotelesjunior.gamelibrary.fileprovider",
            photoFile!!
        )
        takePictureIntent.resolveActivity(packageManager)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureURI)
        startActivityForResult(takePictureIntent, OPERATION_CAPTURE_PHOTO)
    }

    private fun openGallery(){
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI). also {
            getPictureFromGalleryIntent -> getPictureFromGalleryIntent.resolveActivity(packageManager)?.also {
                getPictureFromGalleryIntent.type = "image/*"
                startActivityForResult(getPictureFromGalleryIntent, OPERATION_CHOOSE_PHOTO)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    ivAddPlatform!!.setImageURI(pictureURI)
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    pictureURI = data!!.data
                    ivAddPlatform!!.setImageURI(pictureURI)
                }
        }
    }
}