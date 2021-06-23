package com.example.mahjong_test

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection


//Resource : https://github.com/johncodeos-blog/UploadImageImgurAndroidExample Class
//Resource : https://johncodeos.com/how-to-upload-image-to-imgur-in-android-using-kotlin/
//遇到的問題 Fragment 呼叫完 ImagePicker 無法像正常 Activity 執行 onRequestPermissionsResult
//我的解法 將ImagePicker 會執行的 Funtion 移至 Fragment


class UploadFragment : Fragment() {
    private lateinit var root: View
    private var selectedImage: Bitmap? = null
    private var imgurUrl: String = ""
    private val CLIENT_ID = "45e2febe3d9de2d"
    private lateinit var activity: Activity
    private lateinit var selected_image: ImageButton
    private lateinit var ll_img: LinearLayout
    private lateinit var btn_upload: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_upload, container, false)
        activity = root.context as Activity
        

        if (Build.VERSION.SDK_INT > 9) {
            var policy: StrictMode.ThreadPolicy =
                StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //設定介面
        selected_image = root.findViewById(R.id.selected_image)
        ll_img = root.findViewById(R.id.ll_img)
        btn_upload = root.findViewById(R.id.btn_upload)

        selected_image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                askPermissions()
            } else {
                pickImage()
            }
        }


        btn_upload.setOnClickListener {
            if (root.findViewById<EditText>(R.id.ed_user).text.toString() != "" && root.findViewById<EditText>(R.id.ed_comment).text.toString() != ""
                && root.findViewById<EditText>(R.id.ed_title).text.toString() != "") {
                selectedImage?.let { uploadImageToImgur(it) }
                Thread.sleep(5000)
                if (imgurUrl == "")
                    Thread.sleep(3000)
                addDatatoDB()
            }
            else {
                AlertDialog.Builder(root.context)
                    .setTitle("注意")
                    .setMessage("請輸入名稱、標題及內文哦～")
                    .setPositiveButton("確定", null)
                    .show()
            }
        }
        return root
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun askPermissions() {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(activity)
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Allow") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                        activity.finish()
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )

            }
        } else {
            pickImage()
        }
    }

    fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("aspectX", 16)
        intent.putExtra("aspectY", 9)
        startActivityForResult(intent, MY_REQUEST_PICK_IMAGE)
    }

    //圖片上傳至Imgur
    private fun uploadImageToImgur(image: Bitmap) {
        getBase64Image(image, complete = { base64Image ->
            GlobalScope.launch(Dispatchers.Default) {
                val url = URL("https://api.imgur.com/3/image")

                val boundary = "Boundary-${System.currentTimeMillis()}"

                val httpsURLConnection =
                    withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                httpsURLConnection.setRequestProperty("Authorization", "Client-ID $CLIENT_ID")
                httpsURLConnection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )

                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                var body = ""
                body += "--$boundary\r\n"
                body += "Content-Disposition:form-data; name=\"image\""
                body += "\r\n\r\n$base64Image\r\n"
                body += "--$boundary--\r\n"


                val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
                withContext(Dispatchers.IO) {
                    outputStreamWriter.write(body)
                    outputStreamWriter.flush()
                }
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                val jsonObject = JSONTokener(response).nextValue() as JSONObject
                val data = jsonObject.getJSONObject("data")
                Log.d("TAG", "Link is : ${data.getString("link")}")
                imgurUrl = data.getString("link")
            }
        })
    }

    //資料上傳至000webhost
    fun addDatatoDB() {
        Thread.sleep(5000)
        var user = root.findViewById<EditText>(R.id.ed_user).text.toString()
        var comment = root.findViewById<EditText>(R.id.ed_comment).text.toString()
        var title = root.findViewById<EditText>(R.id.ed_title).text.toString()
        var pic_url = imgurUrl


        if (Build.VERSION.SDK_INT > 9) {
            var policy: StrictMode.ThreadPolicy =
                StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        val stringRequest = object : StringRequest(
            Method.POST, EndPoints.URL_ADD_ARTIST,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(root.context, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(root.context, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("user", user)
                params.put("title", title)
                params.put("comment", comment)
                params.put("pic_url", pic_url)
                return params
            }
        }

        VolleySingleton.instance?.addToRequestQueue(stringRequest)

    }

    //拿到選取資料動態設定圖片大小
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == MY_REQUEST_PICK_IMAGE) {
            val uri = data?.data
            if (uri != null) {
                selectedImage = uriToBitmap(uri)
                selected_image.setImageBitmap(selectedImage)
                ll_img.layoutParams.width = selectedImage!!.width
                ll_img.layoutParams.height = selectedImage!!.height
            }
        }
    }

    //轉Bitmap
    fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
    }

    //權限
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //結束執行
    override fun onDestroy() {
        super.onDestroy()
        unbindDrawables(root)
    }

    //給他歸還一下，怕記憶體洩漏
    private fun unbindDrawables(view: View) {
        if (view.background != null) {
            view.background.callback = null
        }
        if (view is ViewGroup && view !is AdapterView<*>) {
            for (i in 0 until view.childCount) {
                unbindDrawables(view.getChildAt(i))
            }
            view.removeAllViews()
        }
    }


    private fun getBase64Image(image: Bitmap, complete: (String) -> Unit) {
        GlobalScope.launch {
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val b = outputStream.toByteArray()
            complete(Base64.encodeToString(b, Base64.DEFAULT))
        }
    }

    //權限或拿取會用到的常數
    companion object {
        const val MY_REQUEST_PICK_IMAGE = 1000
        const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1001
    }
}
