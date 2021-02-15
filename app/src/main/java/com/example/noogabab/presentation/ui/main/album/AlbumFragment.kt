package com.example.noogabab.presentation.ui.main.album

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.noogabab.R
import com.example.noogabab.presentation.entity.PresenterAlbumImage
import com.example.noogabab.presentation.ui.start.enterGroup.EnterGroupViewModel
import kotlinx.android.synthetic.main.fragment_album.*
fun getDummy(): ArrayList<PresenterAlbumImage> {
    val items = ArrayList<PresenterAlbumImage>()
    items.add(PresenterAlbumImage(1, "https://cdn.pixabay.com/photo/2019/09/06/04/25/beach-4455433_960_720.jpg"))
    items.add(PresenterAlbumImage(2, "https://cdn.pixabay.com/photo/2020/04/24/03/35/heart-5084900_960_720.jpg"))
    items.add(PresenterAlbumImage(3, "https://cdn.pixabay.com/photo/2017/06/27/14/20/waterfalls-2447450_960_720.jpg"))
    items.add(PresenterAlbumImage(4, "https://cdn.pixabay.com/photo/2021/01/17/07/35/dog-5924174_960_720.jpg"))
    items.add(PresenterAlbumImage(5, "https://cdn.pixabay.com/photo/2015/03/26/09/54/pug-690566_960_720.jpg"))
    items.add(PresenterAlbumImage(6, "https://t1.daumcdn.net/cfile/tistory/2705AB38583C9CB33A?download"))
    return items
}

class AlbumFragment : Fragment(R.layout.fragment_album) {
    private val viewModel: AlbumViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.select_album, menu)
//        inflater.inflate(R.menu.select_album, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.select_album_camera -> {}
//            R.id.select_album_gallery -> {}
        }
        return true
    }

    private fun load() {
        requireActivity().window.statusBarColor = Color.WHITE;
        setRecyclerView()
//        refreshAlbum()
    }

    private fun refreshAlbum() {

    }

    private fun setRecyclerView() {
        val items = getDummy()
        if (items.size == 0) {
            txt_album_background.isVisible = true
            image_album_background.isVisible = true
            swipe_refresh_album.isVisible = false
            recycler_grid_album.isVisible = false
        }else {
            txt_album_background.isVisible = false
            image_album_background.isVisible = false
            swipe_refresh_album.isVisible = true
            recycler_grid_album.isVisible = true
            val adapter = AlbumAdapter(items)
            recycler_grid_album.adapter = adapter
            recycler_grid_album.layoutManager = GridLayoutManager(context, 3)
        }
    }
}