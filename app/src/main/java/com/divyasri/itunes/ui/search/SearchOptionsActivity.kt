package com.divyasri.itunes.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.divyasri.itunes.R
import com.divyasri.itunes.databinding.ActivitySearchOptionsBinding
import com.divyasri.itunes.utils.AppConstants
import com.divyasri.itunes.utils.Utility
import kotlinx.android.synthetic.main.activity_search_options.*

class SearchOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySearchOptionsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search_options)

        binding.lifecycleOwner = this
        btn_search.setOnClickListener {
            if (valid()) {
                val intent = Intent(this, SearchResultsActivity::class.java).apply {
                    putExtra(AppConstants.ARTIST_NAME, tv_artist_name.text.toString())
                    putExtra(AppConstants.TRACK_NAME,tv_track_name.text.toString())
                    putExtra(AppConstants.COLL_NAME,tv_coll_name.text.toString())
                    putExtra(AppConstants.COLL_PRICE,tv_coll_price.text.toString())
                }
                startActivity(intent)
            }
        }

    }

    private fun valid(): Boolean {
        if (Utility.isNullOrEmpty(tv_artist_name)) {
            Toast.makeText(this, "Please enter any artist name", Toast.LENGTH_LONG).show()
            return false
        }
        if (Utility.isNullOrEmpty(tv_track_name)) {
            Toast.makeText(this, "Please enter any track name", Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }


}