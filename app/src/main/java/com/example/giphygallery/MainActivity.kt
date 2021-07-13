package com.example.giphygallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.giphy.sdk.analytics.GiphyPingbacks.context
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.pagination.GPHContent
import com.giphy.sdk.ui.views.GPHGridCallback
import com.giphy.sdk.ui.views.GiphyGridView


class MainActivity : AppCompatActivity() {
    lateinit var btn_grid: Button
    lateinit var btn_list: Button
    lateinit var parentView: LinearLayout
    lateinit var gifsGridView: GiphyGridView
    private val YOUR_ANDROID_SDK_KEY = "w3YspOAlqfiCmFrza3Ebijj5KLPm1Ryx"
    private val DEFAULT_SPAN_COUNT = 3
    private var already_click = false
    private val Tag = "myLogs"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_grid = findViewById(R.id.btn_grid)
        btn_list = findViewById(R.id.btn_list)
        parentView = findViewById(R.id.parentView)
        Giphy.configure(this, YOUR_ANDROID_SDK_KEY)

       setGridViewParam(DEFAULT_SPAN_COUNT)
    }

    fun selectMedia(){
        gifsGridView.callback = object :GPHGridCallback {
            override fun contentDidUpdate(resultCount: Int) {
            }

            override fun didSelectMedia(media: Media) {
                val intent = Intent(this@MainActivity, FullGIFView::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity,
                    "media selected: ${media.id}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    fun setGridViewParam(span_count: Int){

        gifsGridView = GiphyGridView(context)

        gifsGridView.direction = GiphyGridView.VERTICAL
        gifsGridView.spanCount = span_count
        gifsGridView.cellPadding = 0
        gifsGridView.fixedSizeCells = false
        gifsGridView.showCheckeredBackground = true

        parentView.addView(gifsGridView)
        gifsGridView.content = GPHContent.trendingGifs
        selectMedia()

    }

    fun changeGridViewTypeGrid(view: View){
        if (already_click == true) {
            parentView.removeView(gifsGridView)
            setGridViewParam(3)
        }
        already_click = false
    }

    fun changeGridViewTypeList(view: View){
        if (already_click == false) {
            parentView.removeView(gifsGridView)
            setGridViewParam(1)
        }
        already_click = true
    }
}

