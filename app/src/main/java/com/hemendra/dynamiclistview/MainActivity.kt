package com.hemendra.dynamiclistview

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var status = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(status==PackageManager.PERMISSION_GRANTED)
        {
            readFiles()
        }
   else{
            ActivityCompat.requestPermissions(this@MainActivity,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),12)
        }



    }// onCreateMethod

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
    {
        readFiles()
    }else{
        finish()
    }


    }



    fun readFiles()
    {
        var path = "/storage/emulated/0/TestFolder"
        var f: File = File(path)
        var files:Array<File> = f.listFiles();


        var ListAdapter =object : BaseAdapter(){

            override fun getCount(): Int {
               return files.size
            }

            override fun getItem(p0: Int): Any {
                return 0
            }

            override fun getItemId(p0: Int): Long {
                return 0
            }

            override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
                var inflater = LayoutInflater.from(this@MainActivity)
                var view = inflater.inflate(R.layout.list_item,null)

                var file_ :File = files[position];

                var bmp = BitmapFactory.decodeFile(file_.path)

                var thumbnail = ThumbnailUtils.extractThumbnail(bmp,50,50)

                view.item_iv.setImageBitmap(thumbnail)

                view.item_tilte.text = file_.name

                view.item_tv_size.text = file_.length().toString()+"bytes"


                view.item_iv_delete.setOnClickListener {
                    file_.delete()
                    files = f.listFiles()
                    notifyDataSetChanged()
                }

                return view
            }
        }

      lv.adapter = ListAdapter



    }
}