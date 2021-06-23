package com.example.mahjong_test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.selects.select
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MessageFragment : Fragment() {
    private lateinit var artistList: MutableList<Artist>
    lateinit var root: View
    lateinit var listView: ListView
    lateinit var select_title:String
    lateinit var adapter:ArtistList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_message, container, false)

        var btn_uploadmessage = root.findViewById<Button>(R.id.btn_uploadmessage)
        btn_uploadmessage.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_nav_message_to_nav_upload)
        }
        listView = root.findViewById(R.id.lv_message)

        listView.setOnItemClickListener { parent, view, position, id ->
            Post.USER = adapter.getItem(position)?.writer.toString()
            Post.POST_ID = adapter.getItem(position)?.post_id.toString()
            Post.TITLE = adapter.getItem(position)?.title.toString()
            Post.PIC_URL = adapter.getItem(position)?.pic_url.toString()
            Post.COMMENT = adapter.getItem(position)?.comment.toString()

            Navigation.findNavController(root).navigate(R.id.action_nav_message_to_nav_comment)
        }

        artistList = mutableListOf()

        loadList()

        root.findViewById<Button>(R.id.btn_select_title).setOnClickListener {
            select_title = EndPoints.URL_GET_TITLE + "&title=" + root.findViewById<EditText>(R.id.ed_select_title).text.toString()
            select_title()
        }

        return root
    }

    private fun loadList() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            EndPoints.URL_GET_ARTIST,
            { s ->
                try {
                    artistList.clear()
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("artists")

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = Artist(
                                objectArtist.getString("post_id"),
                                objectArtist.getString("title"),
                                objectArtist.getString("user"),
                                objectArtist.getString("pic_url"),
                                objectArtist.getString("comment")
                            )
                            artistList!!.add(artist)
                            adapter = ArtistList(root.context as Activity, artistList!!)
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

    private fun select_title() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            select_title,
            { s ->
                try {
                    artistList.clear()
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("artists")

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = Artist(
                                objectArtist.getString("post_id"),
                                objectArtist.getString("title"),
                                objectArtist.getString("user"),
                                objectArtist.getString("pic_url"),
                                objectArtist.getString("comment")
                                )
                            artistList!!.add(artist)
                            adapter = ArtistList(root.context as Activity, artistList!!)
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



