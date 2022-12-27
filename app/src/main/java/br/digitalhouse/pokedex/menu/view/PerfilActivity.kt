package br.digitalhouse.pokedex.menu.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityPerfilBinding
import br.digitalhouse.pokedex.signin.view.SignInHostActivity
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream
import java.io.File

class PerfilActivity : AppCompatActivity() {
    private val binding: ActivityPerfilBinding by lazy { ActivityPerfilBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    private lateinit var alertDialog: AlertDialog
    private val IMAGE_CAPTURE_CODE = 1
    private val IMAGE_STORAGE_CODE = 2
    private var resultBitMap: Bitmap? = null
    private lateinit var fotoCliente: File
    private var currentPhotoUri = Uri.EMPTY
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.progressBar.visibility = View.GONE
        setOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.GONE
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnAlterar.setOnClickListener{
            finish()
        }

        binding.btnTirarFoto.setOnClickListener{
            openDialogGalleryCamera(it)
        }
    }

    private fun changeName(){
        binding.nomeClient
    }

    private fun openDialogGalleryCamera(v: View?) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 10)
            }else {
                val intentCapture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intentCapture, IMAGE_CAPTURE_CODE)}
        } else {
            val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
            val view = layoutInflater.inflate(R.layout.alert_dialog_gallery_cam, null)
            build.setView(view)
            val btnCam = view.findViewById<ImageButton>(R.id.camera_ib)
            val btnGal = view.findViewById<ImageButton>(R.id.gallery_ib)
            val btnCancel = view.findViewById<TextView>(R.id.cancel_tv)
            btnCancel.setOnClickListener{ alertDialog.dismiss() }
            btnCam.setOnClickListener{
                binding.progressBar.visibility = View.VISIBLE
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
                startActivityForResult(cameraIntent, 0)
                alertDialog.dismiss()}
            btnGal.setOnClickListener{
                binding.progressBar.visibility = View.VISIBLE
                val pickPhoto =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhoto, 1)
                alertDialog.dismiss()}
            alertDialog = build.create()
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == IMAGE_CAPTURE_CODE) {
                val foto = data?.getParcelableExtra<Bitmap>("data")
                binding.imageViewClient.setImageBitmap(foto)
                val extras = data?.extras
                val img = extras!!.get("data") as Bitmap
                val tempUri = getImageUri(this, img)
                val finalFile = File(getRealPathFromURI(tempUri))
                binding.imageViewClient.setImageBitmap(img)
                resultBitMap = img
                fotoCliente = finalFile
            } else if (requestCode == IMAGE_STORAGE_CODE && data != null) {
                val selectedPhotoUri = data.data
                val imgPath = getRealPathFromURI(selectedPhotoUri)
                val finalFile = File(imgPath.toString())
                try {
                    selectedPhotoUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                selectedPhotoUri
                            )
                            binding.imageViewClient.setImageBitmap(bitmap)
                            resultBitMap = bitmap
                            fotoCliente = finalFile
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageViewClient.setImageBitmap(bitmap)
                            resultBitMap = bitmap
                            fotoCliente = finalFile
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage, "image", null
        )
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (this.contentResolver != null) {
            val cursor: Cursor? = this.contentResolver.query(
                uri!!,
                null, null, null, null
            )
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }







//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        for (permission in permissions) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    permission!!
//                )
//            ) {
//                //denied
//                Toast.makeText(
//                    this,
//                    "Por favor, aceite todas as permissões necessárias!",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                startInstalledAppDetailsActivity(this)
//                break
//            } else {
//                if (ActivityCompat.checkSelfPermission(
//                        this, permission
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    //never ask again
//                    Toast.makeText(
//                        this,
//                        "Por favor, aceite todas as permissões necessárias!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    startInstalledAppDetailsActivity(this)
//                    break
//                }
//            }
//        }
//    }
//
//    private fun startInstalledAppDetailsActivity(context: Activity?) {
//        if (context == null) {
//            return
//        }
//
//        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        val uri = Uri.fromParts("package", this.packageName, null)
//        intent.data = uri
//        context.startActivity(intent)
//    }
}