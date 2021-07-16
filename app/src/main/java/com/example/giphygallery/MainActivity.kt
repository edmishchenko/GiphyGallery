package com.example.giphygallery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.giphy.sdk.analytics.GiphyPingbacks.context
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.MediaType
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.pagination.GPHContent
import com.giphy.sdk.ui.views.GPHGridCallback
import com.giphy.sdk.ui.views.GiphyGridView


class MainActivity : AppCompatActivity() {
    lateinit var btn_grid: Button
    lateinit var btn_list: Button
    lateinit var btn_search: Button
    lateinit var searchInput: EditText
    lateinit var parentView: LinearLayout
    lateinit var ll_search: LinearLayout
    lateinit var gifsGridView: GiphyGridView
    private val YOUR_ANDROID_SDK_KEY = "w3YspOAlqfiCmFrza3Ebijj5KLPm1Ryx"
    private val DEFAULT_SPAN_COUNT = 3
    private var already_click = false
    private var click = false
    private val Tag = "myLogs"
    private val ll_params_true: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    private val ll_params_false : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fullgifview, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ic_search ->  {
                if(!click) {
                    showSearchLine()
                    click = true
                }
                else {
                    hideSearchLine()
                    click = false
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showSearchLine() {
        ll_search.layoutParams = ll_params_true
    }

    fun hideSearchLine(){
        ll_search.layoutParams = ll_params_false
    }

    private fun performCustomSearch() {
        if (searchInput.text.isNullOrEmpty())
            gifsGridView.content = GPHContent.trendingGifs
        else
            gifsGridView.content = GPHContent.searchQuery(searchInput.text.toString(), MediaType.gif)
    }

    fun dismissKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_grid = findViewById(R.id.btn_grid)
        btn_list = findViewById(R.id.btn_list)
        parentView = findViewById(R.id.parentView)
        ll_search = findViewById(R.id.ll_search)
        searchInput = findViewById(R.id.search_input)
        btn_search = findViewById(R.id.btn_search)
        Giphy.configure(this, YOUR_ANDROID_SDK_KEY)

       setGridViewParam(DEFAULT_SPAN_COUNT)

        btn_search.setOnClickListener{
            dismissKeyboard()
            performCustomSearch()
        }

        searchInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_GO){
                dismissKeyboard()
                performCustomSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        searchInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performCustomSearch()
            }
        })
    }

    fun selectMedia(){
        gifsGridView.callback = object :GPHGridCallback {
            override fun contentDidUpdate(resultCount: Int) {
            }

            override fun didSelectMedia(media: Media) {
                callFullGIFView(convertGIFurl(media))
            }

        }
    }

    fun convertGIFurl(media: Media): String {
        var med = media.embedUrl.toString()
        var result = med.replace("https://giphy.com/embed/", "https://i.giphy.com/media/") + "/giphy.webp"
        return result
    }


    fun callFullGIFView(result: String?){
        val intent = Intent(this@MainActivity, FullGIFView::class.java)
        intent.putExtra(FullGIFView.gif_url, result)
        startActivity(intent)
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
        if (already_click) {
            parentView.removeView(gifsGridView)
            setGridViewParam(3)
        }
        already_click = false
    }
    fun changeGridViewTypeList(view: View) {
        if (!already_click) {
            parentView.removeView(gifsGridView)
            setGridViewParam(1)
        }
        already_click = true
    }
}
