package br.digitalhouse.pokedex.ui.menu.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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
import br.digitalhouse.pokedex.ui.signin.model.User
import br.digitalhouse.pokedex.data.utils.Base64Custom
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.data.utils.NetworkReceiver
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File

class PerfilActivity : AppCompatActivity() {
    private val binding: ActivityPerfilBinding by lazy { ActivityPerfilBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    private lateinit var alertDialog: AlertDialog
    private var valueEventListener: ValueEventListener? = null
    private val firebaseRef = ConfigFirebase().getFirebase()
    private val IMAGE_CAPTURE_CODE = 1
    private val IMAGE_STORAGE_CODE = 2
    private var resultBitMap: Bitmap? = null
    private var photoUri: Uri? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseStorage: FirebaseStorage
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        FirebaseDatabase.getInstance().getReference("usuarios")
        setData()
        setOnClickListener()
    }

    private fun setImageClient() {
        try {
            firebaseStorage.reference.child("usuarios/").child(auth!!.currentUser!!.uid)
                .downloadUrl.addOnSuccessListener { uri ->
                    Glide
                        .with(this)
                        .load(uri)
                        .error(R.drawable.ash)
                        .into(binding.imageViewClient)
                }
        }catch (e: Exception){
            Log.e("STORAGE", "USUARIO SEM LOGIN")
        }
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.GONE
        setImageClient()
    }

    private fun setData(){
        val userAuth = auth!!.currentUser
        val idUsuario = Base64Custom.codificarBase64(userAuth!!.email)
        val nameUser = firebaseRef!!.child("usuarios").child(idUsuario)
        valueEventListener = nameUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                binding.nomeClient.text = user!!.nome
                binding.emailClient.text = user.email
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnTirarFoto.setOnClickListener{
            openDialogGalleryCamera(it)
        }
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
                openCam()
                alertDialog.dismiss()}
            btnGal.setOnClickListener{
                binding.progressBar.visibility = View.VISIBLE
                openGal()
                alertDialog.dismiss()}
            alertDialog = build.create()
            alertDialog.show()
        }
    }

    private fun openCam() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), IMAGE_CAPTURE_CODE)
        } else {
            val intentCapture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intentCapture, IMAGE_CAPTURE_CODE)
        }
    }

    private fun openGal() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), IMAGE_STORAGE_CODE
            )
        } else {
            val intentStorage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentStorage, IMAGE_STORAGE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == IMAGE_CAPTURE_CODE && data != null) {
                val foto = data.getParcelableExtra<Bitmap>("data")
               // binding.imageViewClient.setImageBitmap(foto)
                val extras = data.extras
                val img = extras!!.get("data") as Bitmap
                //binding.imageViewClient.setImageBitmap(img)
                val tempUri = getImageUri(this, img)
                val finalFile = File(getRealPathFromURI(tempUri))
                resultBitMap = img
                photoUri = tempUri!!

                //função adicionar foto no firebase
                storageReference = FirebaseStorage.getInstance().getReference("usuarios/"+auth!!.currentUser!!.uid)
                storageReference.putFile(photoUri!!).addOnSuccessListener {
                    Toast.makeText(this, "Imagem alterada!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Não foi possível alterar a imagem.", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
            } else if (requestCode == IMAGE_STORAGE_CODE && data != null) {
                val selectedPhotoUri = data.data
                val imgPath = getRealPathFromURI(selectedPhotoUri)
                val finalFileS = File(imgPath.toString())
                try {
                    selectedPhotoUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPhotoUri)
                            //binding.imageViewClient.setImageBitmap(bitmap)
                            resultBitMap = bitmap
                            photoUri = selectedPhotoUri
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                            val bitmapT = ImageDecoder.decodeBitmap(source)
                            //binding.imageViewClient.setImageBitmap(bitmapT)
                            resultBitMap = bitmapT
                            photoUri = selectedPhotoUri
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                //função adicionar foto no firebase
                storageReference = FirebaseStorage.getInstance().getReference("usuarios/"+auth!!.currentUser!!.uid)
                storageReference.putFile(photoUri!!).addOnSuccessListener {
                    Toast.makeText(this, "Imagem alterada!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Não foi possível alterar a imagem.", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == IMAGE_CAPTURE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, IMAGE_CAPTURE_CODE)
            }
        }

        if (requestCode == IMAGE_STORAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intentG =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentG, IMAGE_STORAGE_CODE)
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(NetworkReceiver(), intentFilter)
    }

}