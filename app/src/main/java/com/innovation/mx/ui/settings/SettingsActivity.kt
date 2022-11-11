package com.innovation.mx.ui.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.innovation.mx.R
import com.innovation.mx.databinding.ActivitySettingsBinding
import com.innovation.mx.utils.showMessage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SettingsActivity : AppCompatActivity() {
    private val launcherRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        val response = it.all {
            it.value
        }
        responsePermission(response)
    }

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            toolbar.title = getString(R.string.settings_title)

            buttonGallery.setOnClickListener {
                openGallery()
            }

            buttonCamera.setOnClickListener {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    launcherRequest.launch(arrayOf(Manifest.permission.CAMERA))
                } else {
                    openCamera()
                }
            }

            galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        val targetUri = result.data?.getData()
                        targetUri?.let {
                            val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(it))
                            imageView.setImageBitmap(bitmap)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        val photoFile = createImageFile();
                        val bitmapOptions = BitmapFactory.Options()
                        var bitmap = BitmapFactory.decodeFile(photoFile?.getAbsolutePath(), bitmapOptions)
                        imageView.setImageBitmap(bitmap)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            setType("image/*")
        }
        galleryLauncher.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun responsePermission(isGranted: Boolean) {
        if (!isGranted) {
            showMessage(getString(R.string.permission_denied))
        } else {
            openCamera()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        //currentPhotoPath = image.absolutePath
        return image
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

}