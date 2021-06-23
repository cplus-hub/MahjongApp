package com.example.mahjong_test

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import org.w3c.dom.Comment
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CommentFragment : Fragment() {
    private lateinit var root: View
    private lateinit var img_show: Bitmap
    private lateinit var artistList: MutableList<Message>
    lateinit var listView: ListView
    lateinit var adapter:MessageList
    var select_post_id: String = EndPoints.URL_GET_COMMENT + "&post_id=" + Post.POST_ID


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_comment, container, false)

        root.findViewById<TextView>(R.id.tv_comment_user).text = Post.USER + " "
        root.findViewById<TextView>(R.id.tv_comment_title).text = Post.TITLE
        root.findViewById<TextView>(R.id.tv_comment_comment).text = Post.COMMENT

        listView = root.findViewById(R.id.lv_comment_message)

        artistList = mutableListOf()

        select_comment()



        if (Build.VERSION.SDK_INT > 9) {
            var policy: StrictMode.ThreadPolicy =
                StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (Post.PIC_URL == "")
            root.findViewById<ImageView>(R.id.img_comment).visibility = View.GONE
        else {
            try {
                val inputstream: InputStream = URL(Post.PIC_URL).openStream()
                img_show = BitmapFactory.decodeStream(inputstream)
                root.findViewById<ImageView>(R.id.img_comment).setImageBitmap(img_show)
            } catch (e: Exception) {
                println("ERR == $e")
            }
        }

        root.findViewById<Button>(R.id.btn_comment_send).setOnClickListener {
            if (root.findViewById<EditText>(R.id.ed_comment_user).text.toString() != "" &&
                root.findViewById<EditText>(R.id.ed_comment_comment).text.toString() != ""
            ) {
                addCommenttoDB()
                Thread.sleep(3000)
                select_comment()
            } else {
                AlertDialog.Builder(root.context)
                    .setMessage("請輸入暱稱及內文哦~")
                    .setTitle("注意")
                    .setPositiveButton("確定", null)
                    .show()
            }
        }
        return root
    }

    private fun addCommenttoDB() {
        var user = root.findViewById<EditText>(R.id.ed_comment_user).text.toString()
        var comment = root.findViewById<EditText>(R.id.ed_comment_comment).text.toString()
        var post_id = Post.POST_ID


        if (Build.VERSION.SDK_INT > 9) {
            var policy: StrictMode.ThreadPolicy =
                StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        val stringRequest = object : StringRequest(
            Method.POST, EndPoints.URL_ADD_COMMENT,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(root.context, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(root.context, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("user", user)
                params.put("post_id", post_id)
                params.put("comment", comment)
                return params
            }
        }

        VolleySingleton.instance?.addToRequestQueue(stringRequest)

    }

    private fun select_comment() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            select_post_id,
            { s ->
                try {
                    artistList.clear()
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("artists")

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = Message(
                                objectArtist.getString("user"),
                                objectArtist.getString("comment")
                            )
                            artistList!!.add(artist)
                            adapter = MessageList(root.context as Activity, artistList!!)
                            listView!!.adapter = adapter
                        }
                    } else {
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError -> /*Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()*/ })

        val requestQueue = Volley.newRequestQueue(root.context)
        requestQueue.add<String>(stringRequest)
    }
}