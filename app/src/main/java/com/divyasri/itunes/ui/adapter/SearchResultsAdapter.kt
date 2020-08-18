package com.divyasri.itunes.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.divyasri.itunes.R
import com.divyasri.itunes.data.models.Results
import com.divyasri.itunes.databinding.ItemSearchResultsBinding
import com.divyasri.itunes.utils.ImageLoaderUtils
import java.util.*

class SearchResultsAdapter(
    private val searchList: ArrayList<Results>,
    private val _context: Context
) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//        Removing duplicates and DESC of Release Date
        val hashSet = HashSet<Results>()
        hashSet.addAll(searchList)
        searchList.clear()
        searchList.addAll(hashSet)

        searchList.sortWith(Comparator { object1, object2 ->
            object1.releaseDate.compareTo(object2.releaseDate)
        })

        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemSearchResultsBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(searchList[position])
        holder.ivAlbum?.let {
            ImageLoaderUtils.display(
                it,
                searchList.get(position).artworkUrl100,
                R.drawable.profile_edit,R.drawable.profile_edit
            )
        }
    }


    inner class ViewHolder(private val binding: ItemSearchResultsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var ivAlbum: ImageView? = null

        init {
            ivAlbum = itemView.findViewById(R.id.iv_album_cover)
        }
        fun bind(itemx: Results) {
            binding.item = itemx
            binding.executePendingBindings()
        }
    }

    fun sortByTrackName() {
        searchList.sortWith(Comparator { object1, object2 ->
            object1.trackName.compareTo(object2.trackName)
        })
        notifyDataSetChanged()
    }

    fun sortByCollName() {
        searchList.sortWith(Comparator { object1, object2 ->
            object1.collectionName.compareTo(object2.collectionName)
        })
        notifyDataSetChanged()
    }

    fun sortByArtistName() {
        searchList.sortWith(Comparator { object1, object2 ->
            object1.artistName.compareTo(object2.artistName)
        })
        notifyDataSetChanged()
    }

    fun sortDescPrice() {
        searchList.sortWith(Comparator { object1, object2 ->
            object2.collectionPrice.compareTo(object1.collectionPrice)
        })
        notifyDataSetChanged()
    }
}
