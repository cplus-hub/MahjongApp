<?php

class DbOperation
{
    private $con;

    function __construct()
    {
        require_once 'DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
    }

    //創建標題
    public function createArtist($user, $title, $comment, $pic_url)
    {
        //計算標題篇數
        $stmt = $this->con->prepare("SELECT MAX(post_id) AS count FROM `message` WHERE is_manager = 1");
        $stmt->execute();
        $stmt->bind_result($count);
        $artists = array();
        $num = 0;

        while ($stmt->fetch()) {
            $num = $count + 1;
        }
        $is_manager = 1;

        $stmt = $this->con->prepare("INSERT INTO message (user, title, post_id, comment, is_manager, pic_url) VALUES (?, ?, ?, ?, ?,  ?)");
        $stmt->bind_param("ssssss", $user, $title, $num, $comment, $is_manager, $pic_url);
        if ($stmt->execute())
            return true;
        return false;
    }

    //新增留言
    public function newComment($user, $post_id, $comment)
    {
        $is_manager = 0;
        $num = 0;
        $pic_url = "";
        $title = "";

        $stmt = $this->con->prepare("INSERT INTO message (post_id, title, user, comment, pic_url) VALUES (?, ?, ?, ?, ?)");
        $stmt->bind_param("sssss", $post_id, $title, $user, $comment, $pic_url);
        if ($stmt->execute())
            return true;
        return false;
    }


    //查詢
    public function getArtists()
    {
        $stmt = $this->con->prepare("SELECT post_id, user, title, comment, pic_url genre FROM message WHERE `is_manager` = 1");
        $stmt->execute();
        $stmt->bind_result($post_id, $user, $title, $comment, $pic_url);
        $artists = array();

        while ($stmt->fetch()) {
            $temp = array();
            $temp['post_id'] = $post_id;
            $temp['user'] = $user;
            $temp['title'] = $title;
            $temp['comment'] = $comment;
            $temp['pic_url'] = $pic_url;
            array_push($artists, $temp);
        }
        return $artists;
    }

    //查詢title
    public function selectTitle($select_title)
    {
        $stmt = $this->con->prepare("SELECT post_id, user, title, comment, pic_url genre FROM message WHERE `title` LIKE '%$select_title%' AND `is_manager` = 1");
        $stmt->execute();
        $stmt->bind_result($post_id, $user, $title, $comment, $pic_url);
        $artists = array();

        while ($stmt->fetch()) {
            $temp = array();
            $temp['post_id'] = $post_id;
            $temp['user'] = $user;
            $temp['title'] = $title;
            $temp['comment'] = $comment;
            $temp['pic_url'] = $pic_url;
            array_push($artists, $temp);
        }
        return $artists;
    }

    //查詢內文 
    public function selectComment($post_id)
    {
        $stmt = $this->con->prepare("SELECT post_id, user, title, comment, pic_url genre FROM message WHERE post_id = $post_id AND is_manager = 0");
        $stmt->execute();
        $stmt->bind_result($post_id, $user, $title, $comment, $pic_url);
        $artists = array();

        while ($stmt->fetch()) {
            $temp = array();
            $temp['post_id'] = $post_id;
            $temp['user'] = $user;
            $temp['title'] = $title;
            $temp['comment'] = $comment;
            $temp['pic_url'] = $pic_url;
            array_push($artists, $temp);
        }
        return $artists;
    }
}
