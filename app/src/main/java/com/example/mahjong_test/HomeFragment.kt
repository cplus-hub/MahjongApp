package com.example.mahjong_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)


        //歡迎動畫

        var welcome_animation = root.findViewById<TextView>(R.id.tv_show)
        var am = AlphaAnimation(1.0f, 0.0f)
        am.duration = 1000
        am.repeatCount = -1
        welcome_animation.startAnimation(am)
        welcome_animation.setOnClickListener {
            am.repeatCount = 0
            root.findViewById<LinearLayout>(R.id.ll_index).visibility = View.GONE
            println("Hello")
        }

        root.findViewById<TextView>(R.id.tv_index).setOnClickListener {
            if (root.findViewById<LinearLayout>(R.id.ll_index_detail).visibility == View.GONE)
                root.findViewById<LinearLayout>(R.id.ll_index_detail).visibility = View.VISIBLE
            else
                root.findViewById<LinearLayout>(R.id.ll_index_detail).visibility = View.GONE
        }
        root.findViewById<TextView>(R.id.tv_score).setOnClickListener {
            if (root.findViewById<LinearLayout>(R.id.ll_score_detail).visibility == View.GONE)
                root.findViewById<LinearLayout>(R.id.ll_score_detail).visibility = View.VISIBLE
            else
                root.findViewById<LinearLayout>(R.id.ll_score_detail).visibility = View.GONE
        }
        root.findViewById<TextView>(R.id.tv_comment).setOnClickListener {
            if (root.findViewById<LinearLayout>(R.id.ll_comment_detail).visibility == View.GONE)
                root.findViewById<LinearLayout>(R.id.ll_comment_detail).visibility = View.VISIBLE
            else
                root.findViewById<LinearLayout>(R.id.ll_comment_detail).visibility = View.GONE
        }
        root.findViewById<TextView>(R.id.tv_else).setOnClickListener {
            if (root.findViewById<LinearLayout>(R.id.ll_else_detail).visibility == View.GONE)
                root.findViewById<LinearLayout>(R.id.ll_else_detail).visibility = View.VISIBLE
            else
                root.findViewById<LinearLayout>(R.id.ll_else_detail).visibility = View.GONE
        }



        return root
    }
}