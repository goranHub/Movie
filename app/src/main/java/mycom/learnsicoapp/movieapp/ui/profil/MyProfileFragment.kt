package mycom.learnsicoapp.movieapp.ui.profil

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_entry.*
import mycom.learnsicoapp.movieapp.EntryActivity
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.databinding.FragmentMyProfileBinding
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import mycom.learnsicoapp.movieapp.utils.*

@AndroidEntryPoint
class MyProfileFragment : BaseFragment() {

    var selectedImageUri: Uri? = null
    var profileImageURL: String = ""
    private val viewModel by viewModels<MyProfileViewModel>()
    private lateinit var binding: FragmentMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadFromRemoteFC(viewModel)
        val currentUserID = FireStoreClass().currentUserID()
        viewModel.getUserFromDbAndBind(currentUserID)
    }

    private fun loadFromRemoteFC(viewModel: MyProfileViewModel) {
        FireStoreClass().fireBase.collection(USERS)
            .document(FireStoreClass().currentUserID())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(UserFirebase::class.java)!!
                viewModel.loadFromRemoteVM(loggedInUser)
            }
            .addOnFailureListener {

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyProfileBinding.inflate(inflater)

        binding.data = viewModel.bindMyProfile

        imageOnClickListener()

        buttonOnClickListener()

        return binding.root
    }

    private fun imageOnClickListener() {
        binding.ivProfileUserImage.setOnClickListener {
            if (context?.let { context ->
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } == PackageManager.PERMISSION_GRANTED
            ) {
                imageChooser()
            } else {
                activity?.let { fragmentActivity ->
                    ActivityCompat.requestPermissions(
                        fragmentActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }
    }

    //send to firestorage and to firestore(collection, document)
    private fun buttonOnClickListener() {
        binding.btnUpdate.setOnClickListener {
            uploadImageToFireStorage(selectedImageUri)
            viewModel.bindMyProfile.image = selectedImageUri.toString()
        }

        viewModel.statusProfileUpdateSuccess.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                viewModel.statusProfileUpdateSuccess.value = null
                Toast.makeText(context, "profile updated successfully!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageChooser()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    context,
                    "you denied the permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun updateProfileToFireCollection(
        viewModel: MyProfileViewModel,
        profileImageURL: String
    ) {
        val userHashMap = HashMap<String, Any>()
        if (profileImageURL.isNotEmpty() && profileImageURL != viewModel.userRemote.value?.image ?: return) {
            userHashMap[USER_IMAGE] = profileImageURL
        }

        FireStoreClass().fireBase.collection(USERS)
            .document(FireStoreClass().currentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.e(viewModel.javaClass.simpleName, "updated was successfully")
                viewModel.profileUpdateSuccess()
            }
            .addOnFailureListener { msg ->
                Log.e(
                    viewModel.javaClass.simpleName,
                    "Error while updating user", msg
                )
            }
    }


    private fun uploadImageToFireStorage(selectedImageUri: Uri?) {
        if (selectedImageUri != null) {

            //get the storage reference
            val storageReference =
                FirebaseStorage
                    .getInstance()
                    .reference
                    .child("USER_IMAGE" + System.currentTimeMillis() + "."
                            + viewModel.fileExtension(selectedImageUri))

            //put image to fire storage
            storageReference
                .putFile(selectedImageUri)
                .addOnSuccessListener { snapshot ->
                    // Get the downloadable url from the snapshot
                    snapshot
                        .metadata!!
                        .reference!!
                        .downloadUrl
                        // take the url and upadate collection
                        .addOnSuccessListener { uri ->
                            profileImageURL = uri.toString()
                            updateProfileToFireCollection(viewModel, profileImageURL)
                        }
                }
                .addOnFailureListener { exception ->
                    Log.e("fail", exception.localizedMessage )
                }
        }
    }

    private fun imageChooser() {
        //select image of phone storage
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        this.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && intent!!.data != null
        ) {
            selectedImageUri = intent.data!!
            //from device to layout
            setDrawerHeaderImage(selectedImageUri.toString())
            viewModel.bindMyProfile.image = selectedImageUri.toString()
            uploadImageToFireStorage(selectedImageUri)
            (activity as EntryActivity).drawerLayout.open()
        }
    }

    private fun setDrawerHeaderImage(image: String?) {
        //set into drawer header
        val headerProfileImageView =
            (activity as mycom.learnsicoapp.movieapp.EntryActivity).navigation_view.getHeaderView(0)
                .findViewById(R.id.header_imageView) as ImageView

        context?.let { context ->
            Glide
                .with(context)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(headerProfileImageView)
        }
    }
}