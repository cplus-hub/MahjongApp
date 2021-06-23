package com.example.mahjong_test

class Mahjong(name: Int, img: Int) {
    var _name = name
    var _img = img
}

//留言討論區用
class Artist(
    val post_id: String,
    val title: String,
    val writer: String,
    val pic_url: String,
    val comment: String
)

class Yaku(val name: String, val cont: String)

class Message(val user: String, val comment: String)