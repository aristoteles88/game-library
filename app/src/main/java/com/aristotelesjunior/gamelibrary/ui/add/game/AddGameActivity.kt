package com.aristotelesjunior.gamelibrary.ui.add.game

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.DataConverter
import com.aristotelesjunior.gamelibrary.database.GameDB
import com.aristotelesjunior.gamelibrary.databinding.ActivityAddGameBinding
import com.aristotelesjunior.gamelibrary.models.Game
import com.aristotelesjunior.gamelibrary.models.Platform
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddGameActivity : AppCompatActivity() {

    //Our variables
    private var ivAddGame: ImageView? = null
    private var tvCoverFilename: TextView? = null
    private var pictureURI: Uri? = null
    private lateinit var binding: ActivityAddGameBinding
    private var PLATFORM_ID: Int = 0
    private var PLATFORM_NAME: String = ""

    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_add_game)

        val tvAddGameActivity = findViewById<TextView>(R.id.tvAddGameTitle)

        PLATFORM_ID = intent.extras!!["PLATFORM_ID"] as Int
        PLATFORM_NAME = intent.extras!!["PLATFORM_NAME"].toString()

        tvAddGameActivity.text = getString(R.string.title_add_game, PLATFORM_NAME)

        val etGameName = findViewById<EditText>(R.id.etGameName)
        val etGameGenre = findViewById<EditText>(R.id.etGameGenre)
        val etGameReleaseDate = findViewById<EditText>(R.id.etGameReleaseDate)
        val etGameRating = findViewById<EditText>(R.id.etGameRating)
        val rgGameStatus = findViewById<RadioGroup>(R.id.rgGameStatus)
        val cbAddToWishlist = findViewById<CheckBox>(R.id.cbAddToWishlist)
        val etMultilineDescription = findViewById<EditText>(R.id.etMultiLineDescription)
        ivAddGame = findViewById(R.id.ivGameCover)
        tvCoverFilename = findViewById(R.id.tvCoverFilename)
        val ibAddCover = findViewById<ImageButton>(R.id.ibAddCover)
        val btnAddGame = findViewById<Button>(R.id.btnAddGame)
        val btnCancel = findViewById<Button>(R.id.btnCancel)


        ibAddCover.setOnClickListener {
            chooseImage(view.context)
        }

        btnAddGame.setOnClickListener {
            val gameCover: ByteArray = if (pictureURI != null) {
                val bitmapGameCover: Bitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, pictureURI!!)
                DataConverter.DbBitmapUtility.getBytes(bitmapGameCover)
            } else {
                DataConverter.DbBitmapUtility.getBytes(BitmapFactory.decodeResource(resources, R.drawable.pngwing_com))
            }
            if (etGameName.text.toString().isNotEmpty()) {
                val checkedGameStatus = rgGameStatus.checkedRadioButtonId
                val newGame = Game(
                    id = 0,
                    name = etGameName.text.toString(),
                    releaseDate = if (etGameReleaseDate.text.toString().isNotEmpty()) etGameReleaseDate.text.toString() else "YYYY",
                    description = if (etMultilineDescription.text.toString().isNotEmpty()) etMultilineDescription.text.toString() else "",
                    genre = if (etGameGenre.text.toString().isNotEmpty()) etGameGenre.text.toString() else "",
                    rating = if (etGameRating.text.toString().isNotEmpty()) etGameRating.text.toString().toInt() else 0,
                    gameStatus = findViewById<RadioButton>(checkedGameStatus).text.toString(),
                    wishlist = cbAddToWishlist.isChecked,
                    platform = PLATFORM_ID,
                    image = gameCover
                )
                GameDB.getInstance(this).gameDao().insertGame(newGame)
                val platform = GameDB.getInstance(this).platformDao().getPlatformByID(platformId = PLATFORM_ID)
                val updatedPlatform = Platform(
                    id = platform.id,
                    name = platform.name,
                    releaseDate = platform.releaseDate,
                    gamesAmount = GameDB.getInstance(this).gameDao().getGamesFromPlatform(platform.id).size,
                    image = platform.image
                )
                GameDB.getInstance(this).platformDao().updatePlatform(platform = updatedPlatform)
            }
            finish()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun takePhoto() {
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

    private fun chooseFromGallery(){
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI). also {
            getPictureFromGalleryIntent -> getPictureFromGalleryIntent.resolveActivity(packageManager)?.also {
                getPictureFromGalleryIntent.type = "image/*"
                startActivityForResult(getPictureFromGalleryIntent, OPERATION_CHOOSE_PHOTO)
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    ivAddGame!!.setImageURI(pictureURI)
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    pictureURI = data!!.data
                    ivAddGame!!.setImageURI(pictureURI)
                }
        }
        tvCoverFilename!!.text = pictureURI.toString()
    }

    private fun chooseImage(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Tirar Foto",
            "Escolher da Galeria",
            "Sair"
        )
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setItems(optionsMenu) { dialogInterface, i ->
            when {
                optionsMenu[i] == "Tirar Foto" -> {
                    takePhoto()
                }
                optionsMenu[i] == "Escolher da Galeria" -> {
                    chooseFromGallery()
                }
                optionsMenu[i] == "Sair" -> {
                    dialogInterface.dismiss()
                }
            }
        }
        builder.show()
    }
}