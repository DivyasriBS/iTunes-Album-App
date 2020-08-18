package com.divyasri.itunes.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divyasri.itunes.R
import com.divyasri.itunes.data.models.ResultWrapper
import com.divyasri.itunes.data.models.Results
import com.divyasri.itunes.databinding.ActivitySearchResultsBinding
import com.divyasri.itunes.ui.adapter.SearchResultsAdapter
import com.divyasri.itunes.ui.custom.PopupMenuCustomLayout
import com.divyasri.itunes.utils.AppConstants
import kotlinx.android.synthetic.main.activity_search_results.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SearchResultsActivity : AppCompatActivity() {
    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var adapter: SearchResultsAdapter? = null
    var artistName: String? =null
    var trackName:String?=null
    var collName:String?=null
    var collPrice:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySearchResultsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search_results)
        binding.lifecycleOwner = this
        binding.searchViewHolder = viewModel

        toolbar_search_results.setNavigationOnClickListener { onBackPressed() }

        layoutManager = LinearLayoutManager(this)
        val itemDecorator =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.divider
            )!!
        )
        rv_search_results.setHasFixedSize(true)
        rv_search_results.addItemDecoration(itemDecorator)
        rv_search_results.layoutManager = layoutManager
        val intent = intent

         artistName = intent.getStringExtra(AppConstants.ARTIST_NAME)
         trackName = intent.getStringExtra(AppConstants.TRACK_NAME)
        collName = intent.getStringExtra(AppConstants.COLL_NAME)
        collPrice = intent.getStringExtra(AppConstants.COLL_PRICE)
        viewModel.fetchSearchResults("$artistName $trackName $collName $collPrice").observe(this, Observer {
            when (it) {
                is ResultWrapper.Success -> {
                    adapter = SearchResultsAdapter(it.value.results as ArrayList<Results>, this)
                    rv_search_results.adapter = adapter

                }
                is ResultWrapper.NetworkError -> {
                    Toast.makeText(this, "Check your network !!", Toast.LENGTH_SHORT).show()

                }
                is ResultWrapper.GenericError -> {
                    Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()
                }
            }
        })


        iv_more.setOnClickListener {
            if (adapter != null) {
                PopupMenuCustomLayout(
                    this, R.layout.popup_menu_custom_layout,
                    PopupMenuCustomLayout.PopupMenuCustomOnClickListener { itemId ->
                        when (itemId) {
                            R.id.tv_sort_artist_name -> {
                                adapter!!.sortByArtistName()
                            }
                            R.id.tv_sort_track_name -> {
                                adapter!!.sortByTrackName()
                            }
                            R.id.tv_sort_coll_name -> {
                                adapter!!.sortByCollName()
                            }
                            R.id.tv_sort_price -> {
                                adapter!!.sortDescPrice()
                            }
                        }
                    }).show()
            }
        }


    }
}
