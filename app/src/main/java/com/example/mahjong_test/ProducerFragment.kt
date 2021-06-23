package com.example.mahjong_test

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.myspinner.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception
import java.net.URI
import java.net.URL


//介紹我自己
class ProducerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_producer, container, false)

        var imgbtn_fb = root.findViewById<ImageButton>(R.id.imgbtn_fb)
        var imgbtn_ig = root.findViewById<ImageButton>(R.id.imgbtn_ig)

        imgbtn_ig.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.instagram.com/cplus_wu/")
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT > 9) {
            var policy: StrictMode.ThreadPolicy =
                StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            val inputstream: InputStream = URL("https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/NYCS-bull-trans-C.svg/1024px-NYCS-bull-trans-C.svg.png").openStream()
            var img_show = BitmapFactory.decodeStream(inputstream)
            root.findViewById<ImageView>(R.id.img_pro).setImageBitmap(img_show)
        } catch (e: Exception) {
            println("ERR == $e")
        }


        imgbtn_fb.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.facebook.com/profile.php?id=100000712687623")
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        return root
    }
}