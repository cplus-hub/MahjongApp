package com.example.mahjong_test

class MahjongResult(list: Array<Int?>, list_2: Array<Int?>) {
    //var list = list
    var list_2 = list_2
    var text_array = arrayListOf<String>()

    var list = arrayListOf<Int>(1,1,1,2,2,2,3,3,3,4,4,4,21,21)


    var return_text = ""



    //國十三
    fun thirteen_orphans(): Boolean {
        var temp_list = arrayOfNulls<Int>(13)
        for (i in 0..list.count() - 2)
            temp_list[i] = list[i]

        var mahjongMap = mutableMapOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            11 to 0,
            19 to 0,
            21 to 0,
            29 to 0,
            31 to 0,
            39 to 0
        )
        for (i in temp_list) {
            if (i != null) {
                if (mahjongMap.containsKey(i))
                    mahjongMap.put(i, (mahjongMap[i].toString().toInt() + 1))
            }
            if (mahjongMap[i] == null)
                return false
            else if (mahjongMap[i].toString().toInt() == 2)
                return false
        }

        if (mahjongMap.containsKey(list[13]))
            return true

        return false
    }

        //國
        fun thirteen(): Boolean {
            var mahjongMap = mutableMapOf(
                1 to 0,
                2 to 0,
                3 to 0,
                4 to 0,
                5 to 0,
                6 to 0,
                7 to 0,
                11 to 0,
                19 to 0,
                21 to 0,
                29 to 0,
                31 to 0,
                39 to 0
            )

            for (i in list) {
                if (i != null) {
                    if (mahjongMap.containsKey(i.toInt()))
                        mahjongMap.put(i, (mahjongMap[i].toString().toInt() + 1))
                }
                if (mahjongMap[i] == null)
                    return false
                else if (mahjongMap[i] == 3)
                    return false
            }

            if (mahjongMap.containsValue(0))
                return false

            return true
        }

        //大三元
        fun big_three_dragons(): Boolean {
            var mahjongMap = mutableMapOf(5 to 0, 6 to 0, 7 to 0)
            var mahjongMap_2 = mutableMapOf(100 to 0)

            mahjongMap_2.clear()

            for (i in list) {
                if (i != null) {
                    if (mahjongMap.containsKey(i.toInt()))
                        mahjongMap.put(i, (mahjongMap[i].toString().toInt() + 1))
                    else if (mahjongMap_2.containsKey(i))
                        mahjongMap_2.put(i, (mahjongMap_2[i].toString().toInt() + 1))
                    else
                        mahjongMap_2.put(i, 1)
                }
            }


            var temp = mahjongMap_2.keys.toIntArray()
            temp.sort()

            if (mahjongMap[5] == 3 && mahjongMap[6] == 3 && mahjongMap[7] == 3) {
                if (mahjongMap_2.containsValue(3)) {
                    if (temp[0] > 10) {
                        if (temp[0] + 1 == temp[1] && temp[0] + 2 == temp[2])
                            return true
                    } else if (temp[0] != 0 && mahjongMap_2.containsValue(2))
                        return true
                } else if (mahjongMap_2.containsValue(2)) {
                    for (i in 0..temp.count() - 1) {
                        if (temp[i] < 10) {
                            if (mahjongMap_2[temp[i]] == 1)
                                return false
                        } else {
                            if (mahjongMap_2[temp[i]] == 1)
                                if (temp[i] + 1 == temp[i + 1] && temp[i] + 2 == temp[i + 2])
                                    return true
                        }
                    }
                }
            }
            return false
        }

        //大四喜
        fun big_four_happiness(): Boolean {
            var mahjongMap = mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0)
            var mahjongMap_2 = mutableMapOf(100 to 0)

            mahjongMap_2.clear()

            for (i in list) {
                if (i != null) {
                    if (mahjongMap.containsKey(i.toInt()))
                        mahjongMap.put(i, (mahjongMap[i].toString().toInt() + 1))
                    else if (mahjongMap_2.containsKey(i))
                        mahjongMap_2.put(i, (mahjongMap_2[i].toString().toInt() + 1))
                    else
                        mahjongMap_2.put(i, 1)
                }
            }

            var temp = mahjongMap_2.keys.toIntArray()

            if (mahjongMap[1] == 3 && mahjongMap[2] == 3 && mahjongMap[3] == 3 && mahjongMap[4] == 3) {
                if (mahjongMap_2[temp[0]] == 2)
                    return true
            }
            return false
        }

        //九蓮寶燈
        fun chuuren(): Boolean {
            var mmap = mutableMapOf(100 to 0)

            mmap.clear()
            for (i in list) {
                if (i != null) {
                    if (mmap.containsKey(i.toInt()))
                        mmap.put(i, mmap[i].toString().toInt() + 1)
                    else
                        mmap.put(i, 1)
                }
            }

            var temp = mmap.keys.toIntArray()
            temp.sort()

            var test = "0"
            //m
            for (key in temp) {
                if (key in 11..19) {
                    test = "m"
                } else {
                    test = "0"
                    break
                }
            }

            //p
            for (key in temp) {
                if (test != "0")
                    break
                if (key >= 21 && key <= 29)
                    test = "p"
                else {
                    test = "0"
                    break
                }
            }

            //s
            for (key in temp) {
                if (test != "0")
                    break
                if (key >= 31 && key <= 39)
                    test = "s"
                else {
                    test = "0"
                    break
                }
            }

            when (test) {
                "m" -> {
                    if (mmap[11].toString().toInt() >= 3 && mmap[19].toString().toInt() >= 3) {
                        if (mmap.containsValue(4)) {
                            if (mmap.containsValue(2))
                                return false
                            else
                                for (i in 12..18)
                                    if (mmap[i] == 3)
                                        return false
                        } else {
                            if (mmap.containsValue(2)) {
                                for (i in 12..18) {
                                    if (mmap[i] == 3)
                                        return false
                                }
                            }
                        }
                        return true
                    } else
                        return false
                }
                "p" -> {
                    if (mmap[21].toString().toInt() >= 3 && mmap[29].toString().toInt() >= 3) {
                        if (mmap.containsValue(4)) {
                            if (mmap.containsValue(2))
                                return false
                            else
                                for (i in 22..28)
                                    if (mmap[i] == 3)
                                        return false
                        } else {
                            if (mmap.containsValue(2)) {
                                for (i in 22..28) {
                                    if (mmap[i] == 3)
                                        return false
                                }
                            }
                        }
                        return true
                    } else
                        return false

                }
                "s" -> {
                    if (mmap[31].toString().toInt() >= 3 && mmap[39].toString().toInt() >= 3) {
                        if (mmap.containsValue(4)) {
                            if (mmap.containsValue(2))
                                return false
                            else
                                for (i in 32..38)
                                    if (mmap[i] == 3)
                                        return false
                        } else {
                            if (mmap.containsValue(2)) {
                                for (i in 32..38) {
                                    if (mmap[i] == 3)
                                        return false
                                }
                            }
                        }
                        return true
                    } else
                        return false

                }
                "0" -> {
                    return false
                }
            }
            return false
        }



        //======================未完成
        /*非役滿牌
        fun normal(): Boolean {
            var mahjongMap = mutableMapOf(100 to 0)
            var mahjongMap_2 = mutableMapOf(100 to 0)

            mahjongMap.clear()
            mahjongMap_2.clear()

            //手牌
            for (i in list) {
                if (i != null) {
                    if (mahjongMap.containsKey(i.toInt()))
                        mahjongMap.put(i, (mahjongMap[i].toString().toInt() + 1))
                    else
                        mahjongMap.put(i, 1)
                }
            }
            //副露
            for (i in list_2) {
                if (i != null) {
                    if (mahjongMap_2.containsKey(i.toInt()))
                        mahjongMap_2.put(i, (mahjongMap_2[i].toString().toInt() + 1))
                    else
                        mahjongMap_2.put(i, 1)
                }
            }

            var map_list = mahjongMapmerge(mahjongMap, mahjongMap_2)
            mahjongMap.remove(100)
            mahjongMap_2.remove(100)
            var mahjong_num = mahjongMap.values.toIntArray()
            var mahjong_num_2 = mahjongMap.values.toIntArray()

            var num = 0
            var num_t = 0
            for (i in mahjong_num) num += i
            for (i in mahjong_num_2) num_t += i

            num_t += num


            //門前清自摸
            if (num == 14) {
                if (menzenchin(mahjongMap_2)) {
                    if (is_chiitoi(mahjongMap, mahjongMap_2)) {
                        fu(mahjongMap)
                    }
                } else {

                }
            }
            //門前清榮和
            else if (num == 13) {

            }
            //含槓
            else if (num_t >= 14) {

            } else {
                println("沒聽")
                text_array.add("沒聽")
            }

            tan_yaochuu(mahjongMap)


            for (i in getresult()) {
                println("i == $i")
            }

            //println(getreturn_text())

            return true
        }

         */


        //門清
        fun menzenchin(listmap: MutableMap<Int, Int>): Boolean {
            for (i in 1..4)
                if (listmap.containsValue(i))
                    return false
            return true
        }

        //斷么九
        fun tan_yaochuu(listmap: MutableMap<Int, Int>) {
            var yaochuu_map = arrayListOf(1, 2, 3, 4, 5, 6, 7, 11, 19, 21, 29, 31, 39)
            var temp_list = listmap.keys.toIntArray()
            var temp = "-1"
            for (i in temp_list) {
                if (i in yaochuu_map) {
                    temp = "-1"
                    break
                }
                temp = "1"
            }
            if (temp == "1") {
                text_array.add("斷么九")
            }
        }

        //七對子
        fun is_chiitoi(listmap: MutableMap<Int, Int>, listmap_2: MutableMap<Int, Int>): Boolean {
            var list_temp = listmap.values.toIntArray()
            if (menzenchin(listmap_2)) {
                if (1 !in list_temp && 3 !in list_temp && 4 !in list_temp) {
                    text_array.add("七對子")
                    return false
                }
            }
            return true
        }

        //和
        fun fu(listmap: MutableMap<Int, Int>) {
            var list_keys = listmap.keys.toIntArray()

            for (i in list_keys) {
                if (i != null) {
                    if (i > 10) {
                        when (listmap[i]) {
                            0 -> {
                                listmap.remove(i)
                            }
                            1 -> {
                                if (listmap[i + 1] != null && listmap[i + 1].toString()
                                        .toInt() >= 1
                                ) {
                                    if (listmap[i + 2] != null && listmap[i + 2].toString()
                                            .toInt() >= 1
                                    ) {
                                        listmap.remove(i)
                                        listmap.put(i + 1, listmap[i + 1].toString().toInt() - 1)
                                        listmap.put(i + 2, listmap[i + 2].toString().toInt() - 1)
                                    }
                                }
                            }
                            2 -> {
                                if (listmap[i + 1] != null && listmap[i + 1].toString()
                                        .toInt() >= 2
                                ) {
                                    if (listmap[i + 2] != null && listmap[i + 2].toString()
                                            .toInt() >= 2
                                    ) {
                                        listmap.remove(i)
                                        listmap.put(i + 1, listmap[i + 1].toString().toInt() - 2)
                                        listmap.put(i + 2, listmap[i + 2].toString().toInt() - 2)
                                    }
                                }
                            }
                            3 -> {
                                if (listmap[i + 1] != null && listmap[i + 1].toString()
                                        .toInt() == 1
                                ) {
                                    if (listmap[i + 2] != null && listmap[i + 2].toString()
                                            .toInt() >= 1
                                    ) {
                                        listmap.put(i, listmap[i].toString().toInt() - 1)
                                        listmap.put(i + 1, listmap[i + 1].toString().toInt() - 1)
                                        listmap.put(i + 2, listmap[i + 2].toString().toInt() - 1)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            var temp_list = listmap.keys.toIntArray()
            var temp = "0"
            for (i in temp_list) {
                if (listmap[i] != 2) {
                    temp = "0"
                    break
                }
                temp = "1"
            }

            if (temp == "1") {
                text_array.add("榮")
            }
        }

        //手牌副露合併
        fun mahjongMapmerge(
            map1: MutableMap<Int, Int>,
            map2: MutableMap<Int, Int>
        ): MutableMap<Int, Int> {
            var temp = map2.keys.toIntArray()
            for (i in temp) {
                if (map1[i] == null) {
                    map1.set(i, map2[i].toString().toInt())
                } else {
                    map1.set(i, (map1[i].toString().toInt() + map2[i].toString().toInt()))
                }
            }
            map1.remove(100)
            return map1
        }
    }
