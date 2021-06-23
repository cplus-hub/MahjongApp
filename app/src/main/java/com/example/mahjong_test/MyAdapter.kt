package com.example.mahjong_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import java.util.*


//參考資料 + 自己修
//https://hjwang520.pixnet.net/blog/post/405091702
//https://blog.csdn.net/Aaron121314/article/details/72457674

class MyAdapter(context: Context, PlanetList: ArrayList<Mahjong?>) :
    ArrayAdapter<Mahjong?>(
        context, 0, PlanetList!!
    ) {
    private var spos = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        spos = position
        var row: View = LayoutInflater.from(getContext()).inflate(R.layout.myspinner, parent, false)
        val imageViewFlag = row.findViewById<ImageView>(R.id.image)
        //val textViewName = row.findViewById<TextView>(R.id.text1)
        val currentItem: Mahjong? = getItem(position)
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem._img)
            //textViewName.setText(currentItem._name)
            if (position == spos) {
                //textViewName.setTextColor(Color.argb(255, 255, 255, 255))
            }
        }
        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row: View = LayoutInflater.from(getContext()).inflate(R.layout.myspinner, parent, false)
        val imageViewFlag = row.findViewById<ImageView>(R.id.image)

        val currentItem: Mahjong? = getItem(position)
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem._img)
            //textViewName.setText(currentItem._name)
        }
        return row
    }
}