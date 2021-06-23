package com.example.mahjong_test

object EndPoints {
    private val URL_ROOT = "https://mahjongapp.000webhostapp.com/?op="
    val URL_ADD_ARTIST = URL_ROOT + "addartist" //新增貼文
    val URL_ADD_COMMENT = URL_ROOT + "newcomment" //新增留言
    val URL_GET_ARTIST = URL_ROOT + "getartists" //顯示列表
    val URL_GET_TITLE = URL_ROOT + "select_title" //查詢Title
    val URL_GET_COMMENT = URL_ROOT + "select_comment" // 查詢內文
}

object Post {
    var POST_ID = ""
    var USER = ""
    var TITLE = ""
    var PIC_URL = ""
    var COMMENT = ""
}