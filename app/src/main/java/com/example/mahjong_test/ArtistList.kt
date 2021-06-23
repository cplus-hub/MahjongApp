package com.example.mahjong_test

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView


/**
 * Created by Belal on 5/21/2017.
 * Resource : https://www.simplifiedcoding.net/volley-with-kotlin/
 */
class ArtistList(private val context: Activity, internal var artists: List<Artist>) :
    ArrayAdapter<Artist>(context, R.layout.fragment_message, artists) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_message, null, true)

        var tv_title = listViewItem.findViewById(R.id.tv_title) as TextView
        var tv_writer = listViewItem.findViewById(R.id.tv_writer) as TextView

        val artist = artists[position]
        tv_title.text = artist.title
        tv_writer.text = artist.writer

        return listViewItem
    }
}

class MessageList(private val context: Activity, internal var artists: List<Message>) :
    ArrayAdapter<Message>(context, R.layout.fragment_comment, artists) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_comment, null, true)

        var tv_user = listViewItem.findViewById(R.id.tv_comment_user_) as TextView
        var tv_comment = listViewItem.findViewById(R.id.tv_comment_comment_) as TextView
        val artist = artists[position]
        tv_user.text = artist.user
        tv_comment.text = artist.comment
        return listViewItem
    }
}

class YakuList(private val context: Activity, internal var artists: List<Yaku>) :
    ArrayAdapter<Yaku>(context, R.layout.fragment_score, artists) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_yaku, null, true)

        var tv_yaku_title = listViewItem.findViewById(R.id.tv_yaku_title) as TextView
        var tv_yaku_cont = listViewItem.findViewById(R.id.tv_yaku_cont) as TextView
        val artist = artists[position]

        tv_yaku_title.text = artist.name
        tv_yaku_cont.text = artist.cont

        return listViewItem
    }
}