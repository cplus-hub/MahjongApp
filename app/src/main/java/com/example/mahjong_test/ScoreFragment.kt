package com.example.mahjong_test

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.myspinner.view.*
import kotlinx.android.synthetic.main.nav_header_main.*

private var mahjong_list = ArrayList<Mahjong?>()
private var mahjong_wind_list = ArrayList<Mahjong?>()
private var spinner_list = ArrayList<Spinner>()
private var spinner_list_2 = ArrayList<Spinner>()
private var spinner_dora_list = ArrayList<Spinner>()
private var spinner_wind_list = ArrayList<Spinner>()
private var analysis_list = arrayOfNulls<Int>(14)
private var analysis_list_2 = arrayOfNulls<Int>(17)

private lateinit var yakulist: MutableList<Yaku>
private lateinit var yaku_adapter: YakuList
private lateinit var lv_yaku: ListView


class ScoreFragment : Fragment() {
    private lateinit var btn_sort: Button
    private lateinit var tv_sort: TextView
    private var img_list = arrayListOf(
        R.drawable.zhuangjia,
        R.drawable.zhuangjia_2,
        R.drawable.play_1,
        R.drawable.play_2,
        R.drawable.play_3
    )
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_score, container, false)
        initList()

        yakulist = mutableListOf()

        //元件
        btn_sort = root.findViewById(R.id.btn_sort)
        tv_sort = root.findViewById(R.id.tv_result)

        //設定Spinner樣式
        var mAdapter = MyAdapter(root.context, mahjong_list)
        var wind_mAdapter = MyAdapter(root.context, mahjong_wind_list)

        spinner_list.add(root.findViewById(R.id.spinner))
        spinner_list.add(root.findViewById(R.id.spinner2))
        spinner_list.add(root.findViewById(R.id.spinner3))
        spinner_list.add(root.findViewById(R.id.spinner4))
        spinner_list.add(root.findViewById(R.id.spinner5))
        spinner_list.add(root.findViewById(R.id.spinner6))
        spinner_list.add(root.findViewById(R.id.spinner7))
        spinner_list.add(root.findViewById(R.id.spinner8))
        spinner_list.add(root.findViewById(R.id.spinner9))
        spinner_list.add(root.findViewById(R.id.spinner10))
        spinner_list.add(root.findViewById(R.id.spinner11))
        spinner_list.add(root.findViewById(R.id.spinner12))
        spinner_list.add(root.findViewById(R.id.spinner13))
        spinner_list.add(root.findViewById(R.id.spinner14))

        //副露
        spinner_list_2.add(root.findViewById(R.id.spinner_))
        spinner_list_2.add(root.findViewById(R.id.spinner_2))
        spinner_list_2.add(root.findViewById(R.id.spinner_3))
        spinner_list_2.add(root.findViewById(R.id.spinner_4))
        spinner_list_2.add(root.findViewById(R.id.spinner_5))
        spinner_list_2.add(root.findViewById(R.id.spinner_6))
        spinner_list_2.add(root.findViewById(R.id.spinner_7))
        spinner_list_2.add(root.findViewById(R.id.spinner_8))
        spinner_list_2.add(root.findViewById(R.id.spinner_9))
        spinner_list_2.add(root.findViewById(R.id.spinner_10))
        spinner_list_2.add(root.findViewById(R.id.spinner_11))
        spinner_list_2.add(root.findViewById(R.id.spinner_12))
        spinner_list_2.add(root.findViewById(R.id.spinner_13))
        spinner_list_2.add(root.findViewById(R.id.spinner_14))
        spinner_list_2.add(root.findViewById(R.id.spinner_15))
        spinner_list_2.add(root.findViewById(R.id.spinner_16))
        spinner_list_2.add(root.findViewById(R.id.spinner_17))


        //寶牌
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_1))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_2))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_3))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_4))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_5))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_6))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_7))
        spinner_dora_list.add(root.findViewById(R.id.spinner_dora_8))

        //自風/場風
        spinner_wind_list.add(root.findViewById(R.id.spinner_player_wind))
        spinner_wind_list.add(root.findViewById(R.id.spinner_wind))

        //小提醒
        var btn_img_remind = root.findViewById<ImageButton>(R.id.img_btn_remind)
        btn_img_remind.setOnClickListener {
            //別的xml的元件
            var view = View.inflate(root.context, R.layout.alertdialogview, null)
            var img_alert = view.findViewById<ImageView>(R.id.img_alert)


            var a = AlertDialog.Builder(root.context)
                .setTitle("莊家點數")
                .setView(view)
                .setNegativeButton("上一張", null)
                .setPositiveButton("下一張", null)
                .setNeutralButton("關閉", null)
                .show()

            var img_num = 0
            //下一頁功能
            var a_positivebtn = a.getButton(AlertDialog.BUTTON_POSITIVE)
            a_positivebtn.setOnClickListener {
                img_num++
                if (img_num == img_list.count()) {
                    img_num = 0
                }

                if (img_num == 0 || img_num == 1)
                    a.setTitle("莊家點數")
                else
                    a.setTitle("閒家點數")

                img_alert.setImageResource(img_list[img_num])
            }
            //上一頁功能
            var a_negativebtn = a.getButton(AlertDialog.BUTTON_NEGATIVE)
            a_negativebtn.setOnClickListener(View.OnClickListener {
                img_num--
                if (img_num < 0)
                    img_num = img_list.count() - 1
                if (img_num == 0 || img_num == 1)
                    a.setTitle("莊家點數")
                else
                    a.setTitle("閒家點數")
                img_alert.setImageResource(img_list[img_num])
            })
        }

        //役種List
        var btn_img_yaku = root.findViewById<ImageButton>(R.id.img_btn_yaku)
        btn_img_yaku.setOnClickListener {
            //別的xml的元件
            var view = View.inflate(root.context, R.layout.alertdiaglog_yaku, null)
            AlertDialog.Builder(root.context)
                .setView(view)
                .show()

            lv_yaku = view.findViewById(R.id.lv_score)
            list_1()
            view.findViewById<Button>(R.id.btn_ll1).setOnClickListener {
                list_1()
            }
            view.findViewById<Button>(R.id.btn_ll2).setOnClickListener {
                list_2()
            }
            view.findViewById<Button>(R.id.btn_ll3).setOnClickListener {
                list_3()
            }
            view.findViewById<Button>(R.id.btn_ll4).setOnClickListener {
                list_4()
            }
            view.findViewById<Button>(R.id.btn_ll5).setOnClickListener {
                list_5()
            }

        }


        //存放Adapter
        for (i in spinner_list) {
            i.adapter = mAdapter
            i.setOnItemSelectedListener(onItemSelected)
        }
        for (i in spinner_list_2) {
            i.adapter = mAdapter
            i.setOnItemSelectedListener(onItemSelected)
        }
        for (i in spinner_dora_list) {
            i.adapter = mAdapter
            i.setOnItemSelectedListener(onItemSelected)
        }
        for (i in spinner_wind_list) {
            i.adapter = wind_mAdapter
            i.setOnItemSelectedListener(onItemSelected)
        }

        //判斷役種
        btn_sort.setOnClickListener() {
            var list_mahjong = MahjongResult(analysis_list, analysis_list_2)

            if (list_mahjong.thirteen_orphans()) {
                tv_sort.text = "國士無雙十三面聽 雙倍役滿"
            } else if (list_mahjong.thirteen()) {
                tv_sort.text = "國士無雙 役滿"
            } else if (list_mahjong.big_three_dragons()) {
                tv_sort.text = "大三元 役滿"
            } else if (list_mahjong.big_four_happiness()) {
                tv_sort.text = "大四喜 雙倍役滿"
            } else if (list_mahjong.chuuren()) {
                tv_sort.text = "九連寶燈 役滿"
            } else
                tv_sort.text = "沒聽哦~"
        }
        return root
    }

    fun list_1() {
        yakulist.clear()
        yakulist.add(Yaku("立直", "在聽牌狀態下，捨牌前喊「立直」，然後拿出一支「1000點」點棒。立直之後不能改變手牌。"))
        yakulist.add(Yaku("一發", "玩家立直後，自己摸入的第一隻牌隨即自摸胡，或者在這之間食胡他人打出的牌。但中途遇上其他玩家吃、碰或槓則無效。"))
        yakulist.add(Yaku("門前清自摸胡", "門前淸時，自摸。"))
        yakulist.add(Yaku("斷幺九", "沒有任何幺九牌（一筒、一條、一萬、九筒、九條、九萬、所有風牌和三元牌）。"))
        yakulist.add(Yaku("平胡", "以符底（20符）的胡牌。由四組順子和不加符的將（場風、自風、三元牌），胡牌形式也不可以加符（崁張、單騎、邊獨）。"))
        yakulist.add(Yaku("一盃口", "胡牌牌型中有兩個同樣的順子。"))
        yakulist.add(Yaku("役牌", "包括由三元牌、自風牌、場風牌組成的刻子或槓子。"))
        yakulist.add(Yaku("嶺上開花", "開槓後緊接著摸上來的一張牌就胡了。"))
        yakulist.add(Yaku("槍槓", "榮胡其他人加槓的牌。"))
        yakulist.add(Yaku("海底撈月", "玩家摸到最後一張牌而自摸胡。"))
        yakulist.add(Yaku("河底撈魚", "玩家以最後一張打出的牌榮胡。"))
        yaku_adapter = YakuList(root.context as Activity, yakulist!!)
        lv_yaku!!.adapter = yaku_adapter
    }

    fun list_2() {
        yakulist.clear()
        yakulist.add(Yaku("三色同順", "同時有由筒、條、萬組成的同一組數字的順子。"))
        yakulist.add(Yaku("三色同刻", "玩同時有筒、條、萬組成的同一個數字的刻子或槓子。"))
        yakulist.add(Yaku("一氣通貫", "同一色牌中（筒條萬），一至九各有一隻，組成三副順子。"))
        yakulist.add(Yaku("對對胡", "胡牌中全是刻子（槓子）以及將牌。"))
        yakulist.add(Yaku("三暗刻", "胡牌時有三組暗刻，其中包含暗槓也可以。"))
        yakulist.add(Yaku("三槓子", "胡牌時有三組槓子。"))
        yakulist.add(Yaku("七對子", "七個將的牌型。"))
        yakulist.add(Yaku("混全帶幺九", "所有順子、刻子、槓子、將都最少包含一隻么九牌，且含有字牌。"))
        yakulist.add(Yaku("混老頭", "所有刻子、槓子、將都是幺九牌，且含有字牌。"))
        yakulist.add(Yaku("小三元", "兩組三元牌為刻子或槓子，另外一組為將。"))
        yakulist.add(Yaku("雙立直", "打出第一張牌時即宣告「立直」則本役成立。"))
        yaku_adapter = YakuList(root.context as Activity, yakulist!!)
        lv_yaku!!.adapter = yaku_adapter
    }

    fun list_3() {
        yakulist.clear()
        yakulist.add(Yaku("混一色", "由一種花色的數牌和字牌組成的胡牌。"))
        yakulist.add(Yaku("純全帶么九", "即整個牌型所有順子、刻子、槓子、將都包含最少一隻么九牌，且無字牌。"))
        yakulist.add(Yaku("二盃口", "兩組一盃口。"))
        yaku_adapter = YakuList(root.context as Activity, yakulist!!)
        lv_yaku!!.adapter = yaku_adapter
    }
    fun list_4() {
        yakulist.clear()
        yakulist.add(Yaku("清一色", "由一種花色的數牌組成的胡牌。。"))
        yaku_adapter = YakuList(root.context as Activity, yakulist!!)
        lv_yaku!!.adapter = yaku_adapter
    }

    fun list_5() {
        yakulist.clear()
        yakulist.add(Yaku("國士無雙", "全數為單隻么九牌，第14隻則可為其中一隻么九牌。"))
        yakulist.add(Yaku("國士無雙十三面聽", "全數為單隻么九牌聽牌，第14隻則可為其中一隻么九牌。"))
        yakulist.add(Yaku("大三元", "全數三組三元牌為刻子或槓子。"))
        yakulist.add(Yaku("四暗刻", "由四組暗刻或暗槓組成。"))
        yakulist.add(Yaku("字一色", "只有風牌和三元牌。"))
        yakulist.add(Yaku("綠一色", "整個牌型只包含二條、三條、四條、六條、八條及發財等綠色的牌。"))
        yakulist.add(Yaku("小四喜", "其中三組風牌為刻子或槓子，另一組為將。"))
        yakulist.add(Yaku("大四喜", "全數四組風牌為刻子或槓子。"))
        yakulist.add(Yaku("清老頭", "即整個牌型所有刻子、槓子、將都是幺九牌（風牌及三元牌除外）。"))
        yakulist.add(Yaku("九蓮寶燈", "帶有1112345678999的清一色。"))
        yakulist.add(Yaku("純正九蓮寶燈", "九蓮寶燈聽九面。"))
        yakulist.add(Yaku("四槓子", "四組槓子。"))
        yakulist.add(Yaku("天胡", "牌局開始時，莊家隨即自摸。"))
        yakulist.add(Yaku("地胡", "牌局開始時，閒家摸入的第一隻牌便自摸。"))
        yakulist.add(Yaku("累計役滿", "牌型累積達13翻或以上。"))
        yaku_adapter = YakuList(root.context as Activity, yakulist!!)
        lv_yaku!!.adapter = yaku_adapter
    }

    fun initList() {
        mahjong_list.add(Mahjong(100, R.drawable.d0))
        mahjong_list.add(Mahjong(99, R.drawable.back))
        mahjong_list.add(Mahjong(1, R.drawable.f1))
        mahjong_list.add(Mahjong(2, R.drawable.f2))
        mahjong_list.add(Mahjong(3, R.drawable.f3))
        mahjong_list.add(Mahjong(4, R.drawable.f4))
        mahjong_list.add(Mahjong(5, R.drawable.d1))
        mahjong_list.add(Mahjong(6, R.drawable.d2))
        mahjong_list.add(Mahjong(7, R.drawable.d3))
        mahjong_list.add(Mahjong(11, R.drawable.m1))
        mahjong_list.add(Mahjong(12, R.drawable.m2))
        mahjong_list.add(Mahjong(13, R.drawable.m3))
        mahjong_list.add(Mahjong(14, R.drawable.m4))
        mahjong_list.add(Mahjong(15, R.drawable.m5))
        mahjong_list.add(Mahjong(16, R.drawable.m6))
        mahjong_list.add(Mahjong(17, R.drawable.m7))
        mahjong_list.add(Mahjong(18, R.drawable.m8))
        mahjong_list.add(Mahjong(19, R.drawable.m9))
        mahjong_list.add(Mahjong(21, R.drawable.p1))
        mahjong_list.add(Mahjong(22, R.drawable.p2))
        mahjong_list.add(Mahjong(23, R.drawable.p3))
        mahjong_list.add(Mahjong(24, R.drawable.p4))
        mahjong_list.add(Mahjong(25, R.drawable.p5))
        mahjong_list.add(Mahjong(26, R.drawable.p6))
        mahjong_list.add(Mahjong(27, R.drawable.p7))
        mahjong_list.add(Mahjong(28, R.drawable.p8))
        mahjong_list.add(Mahjong(29, R.drawable.p9))
        mahjong_list.add(Mahjong(31, R.drawable.s1))
        mahjong_list.add(Mahjong(32, R.drawable.s2))
        mahjong_list.add(Mahjong(33, R.drawable.s3))
        mahjong_list.add(Mahjong(34, R.drawable.s4))
        mahjong_list.add(Mahjong(35, R.drawable.s5))
        mahjong_list.add(Mahjong(36, R.drawable.s6))
        mahjong_list.add(Mahjong(37, R.drawable.s7))
        mahjong_list.add(Mahjong(38, R.drawable.s8))
        mahjong_list.add(Mahjong(39, R.drawable.s9))

        mahjong_wind_list.add(Mahjong(1, R.drawable.f1))
        mahjong_wind_list.add(Mahjong(2, R.drawable.f2))
        mahjong_wind_list.add(Mahjong(3, R.drawable.f3))
        mahjong_wind_list.add(Mahjong(4, R.drawable.f4))

    }
}

private val onItemSelected: AdapterView.OnItemSelectedListener =
    object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
            if (parent != null) {
                when (parent.id) {
                    R.id.spinner -> {
                        val select_mahjong: Mahjong? = spinner_list[0].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[0] = select_mahjong._name
                        //println("Spinner抓取測試${analysis_list[0]}")
                    }
                    R.id.spinner2 -> {
                        val select_mahjong: Mahjong? = spinner_list[1].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[1] = select_mahjong._name
                    }
                    R.id.spinner3 -> {
                        val select_mahjong: Mahjong? = spinner_list[2].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[2] = select_mahjong._name
                    }
                    R.id.spinner4 -> {
                        val select_mahjong: Mahjong? = spinner_list[3].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[3] = select_mahjong._name
                    }
                    R.id.spinner5 -> {
                        val select_mahjong: Mahjong? = spinner_list[4].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[4] = select_mahjong._name
                    }
                    R.id.spinner6 -> {
                        val select_mahjong: Mahjong? = spinner_list[5].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[5] = select_mahjong._name
                    }
                    R.id.spinner7 -> {
                        val select_mahjong: Mahjong? = spinner_list[6].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[6] = select_mahjong._name
                    }
                    R.id.spinner8 -> {
                        val select_mahjong: Mahjong? = spinner_list[7].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[7] = select_mahjong._name
                    }
                    R.id.spinner9 -> {
                        val select_mahjong: Mahjong? = spinner_list[8].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[8] = select_mahjong._name
                    }
                    R.id.spinner10 -> {
                        val select_mahjong: Mahjong? = spinner_list[9].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[9] = select_mahjong._name
                    }
                    R.id.spinner11 -> {
                        val select_mahjong: Mahjong? = spinner_list[10].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[10] = select_mahjong._name
                    }
                    R.id.spinner12 -> {
                        val select_mahjong: Mahjong? = spinner_list[11].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[11] = select_mahjong._name
                    }
                    R.id.spinner13 -> {
                        val select_mahjong: Mahjong? = spinner_list[12].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[12] = select_mahjong._name
                    }
                    R.id.spinner14 -> {
                        val select_mahjong: Mahjong? = spinner_list[13].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list[13] = select_mahjong._name
                    }

                    //副露
                    R.id.spinner_ -> {
                        val select_mahjong: Mahjong? = spinner_list_2[0].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[0] = select_mahjong._name
                    }
                    R.id.spinner_2 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[1].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[1] = select_mahjong._name
                    }
                    R.id.spinner_3 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[2].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[2] = select_mahjong._name
                    }
                    R.id.spinner_4 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[3].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[3] = select_mahjong._name
                    }
                    R.id.spinner_5 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[4].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[4] = select_mahjong._name
                    }
                    R.id.spinner_6 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[5].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[5] = select_mahjong._name
                    }
                    R.id.spinner_7 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[6].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[6] = select_mahjong._name
                    }
                    R.id.spinner_8 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[7].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[7] = select_mahjong._name
                    }
                    R.id.spinner_9 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[8].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[8] = select_mahjong._name
                    }
                    R.id.spinner_10 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[9].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[9] = select_mahjong._name
                    }
                    R.id.spinner_11 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[10].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[10] = select_mahjong._name
                    }
                    R.id.spinner_12 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[11].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[11] = select_mahjong._name
                    }
                    R.id.spinner_13 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[12].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[12] = select_mahjong._name
                    }
                    R.id.spinner_14 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[13].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[13] = select_mahjong._name
                    }
                    R.id.spinner_15 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[14].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[14] = select_mahjong._name
                    }
                    R.id.spinner_16 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[15].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[15] = select_mahjong._name
                    }
                    R.id.spinner_17 -> {
                        val select_mahjong: Mahjong? = spinner_list_2[16].selectedItem as Mahjong?
                        if (select_mahjong != null)
                            analysis_list_2[16] = select_mahjong._name
                    }
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.i("owo", "不做事")
        }
    }
