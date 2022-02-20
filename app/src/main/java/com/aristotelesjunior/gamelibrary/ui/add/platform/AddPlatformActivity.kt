package com.aristotelesjunior.gamelibrary.ui.add.platform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import androidx.core.content.FileProvider
import com.aristotelesjunior.gamelibrary.R
import java.io.File

class AddPlatformActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_platform)

        val btnTakePicture = findViewById<Button>(R.id.btnTakePicture)
        val btnOpenGallery = findViewById<Button>(R.id.btnOpenGallery)

        fun getPhotoFile(fileName: String): File {
            val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(fileName, ".jpg", directoryStorage)
        }

        var filePhoto : File
        btnTakePicture.setOnClickListener {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            filePhoto = getPhotoFile(FILE_NAME)
            val providerFile = FileProvider.getUriForFile(this,"com.aristotelesjunior.gamelibrary", filePhoto)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        }
    }
}