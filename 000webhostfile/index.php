<?php

//adding dboperation file 
require_once 'DbOperation.php';

//response array 
$response = array();

//if a get parameter named op is set we will consider it as an api call 
if (isset($_GET['op'])) {

    //switching the get op value 
    switch ($_GET['op']) {


            //新增貼文
        case 'addartist':
            if (isset($_POST['user']) && isset($_POST['title']) && isset($_POST['comment']) && isset($_POST['pic_url'])) {
                $db = new DbOperation();
                if ($db->createArtist($_POST['user'], $_POST['title'], $_POST['comment'], $_POST['pic_url'])) {
                    $response['error'] = false;
                    $response['message'] = 'Artist added successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Could not add artist';
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Required Parameters are missing';
            }
            break;

            //查詢列表
        case 'getartists':
            $db = new DbOperation();
            $artists = $db->getArtists();
            if (count($artists) <= 0) {
                $response['error'] = true;
                $response['message'] = 'Nothing found in the database';
            } else {
                $response['error'] = false;
                $response['artists'] = $artists;
            }
            break;

            //查詢Title
        case 'select_title':
            if (isset($_GET['title'])) {
                $db = new DbOperation();
                $artists = $db->selectTitle($_GET['title']);
                if (count($artists) <= 0) {
                    $response['error'] = true;
                    $response['message'] = 'Nothing found in the database';
                } else {
                    $response['error'] = false;
                    $response['artists'] = $artists;
                }
                break;
            }

            //查詢內文comment
        case 'select_comment':
            if (isset($_GET['post_id'])) {
                $db = new DbOperation();
                $artists = $db->selectComment($_GET['post_id']);
                if (count($artists) <= 0) {
                    $response['error'] = true;
                    $response['message'] = 'Nothing found in the database';
                } else {
                    $response['error'] = false;
                    $response['artists'] = $artists;
                }
                break;
            }


            //新增留言
        case 'newcomment':
            if (isset($_POST['user']) && isset($_POST['post_id']) && isset($_POST['comment'])) {
                $db = new DbOperation();
                if ($db->newcomment($_POST['user'], $_POST['post_id'], $_POST['comment'])) {
                    $response['error'] = false;
                    $response['message'] = 'Artist added successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Could not add artist';
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Required Parameters are missing';
            }
            break;

        default:
            $response['error'] = true;
            $response['message'] = 'No operation to perform';
    }
} else {
    $response['error'] = false;
    $response['message'] = 'Invalid Request';
}

//displaying the data in json 
echo json_encode($response);
