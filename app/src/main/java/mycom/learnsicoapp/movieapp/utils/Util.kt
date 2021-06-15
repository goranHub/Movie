package mycom.learnsicoapp.movieapp.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.ImageView
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.activity_entry.*
import mycom.learnsicoapp.movieapp.EntryActivity
import mycom.learnsicoapp.movieapp.R

/**
 * @author lllhr
 * @date 6/10/2021
 */
fun setDrawerHeaderImage(image: String?, context: EntryActivity) {
    //set into drawer header
    val headerProfilImageView =
        context.navigation_view.getHeaderView(0)
            .findViewById(R.id.header_imageView) as ImageView

    context.let { context ->
        Glide
            .with(context)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_local_movies_24)
            .into(headerProfilImageView)
    }
}

fun fileExtension(uri: Uri?, context: Context): String? {
    val cr: ContentResolver = context.contentResolver
    return MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(cr.getType(uri!!))
}