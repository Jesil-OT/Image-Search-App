package com.jesil.example.mvvm.unsplashapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.jesil.example.mvvm.unsplashapp.R
import com.jesil.example.mvvm.unsplashapp.constant.OnItemClickListener
import com.jesil.example.mvvm.unsplashapp.data.UnsplashPhoto
import com.jesil.example.mvvm.unsplashapp.databinding.ItemUnsplashPhotoBinding

class UnsplashPagingAdapter(private val _listener: OnItemClickListener):
        PagingDataAdapter<UnsplashPhoto, UnsplashPagingAdapter.PhotoViewHolder>(
    PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = getItem(position)
        if (current != null){
            holder.bind(current)
        }
    }


   inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        _listener.onItemClick(item)
                    }
                }
            }
        }

        private val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_image_place_holder)
            .error(R.drawable.ic_error)

            fun bind(photo: UnsplashPhoto){
                binding.apply {
                    textViewUsername.text = photo.unsplashUsers.unsplashUserName
                    Glide.with(itemView)
                        .setDefaultRequestOptions(requestOptions)
                        .load(photo.unsplashUrls.unsplashPhotoUrlsRegular)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                }
            }

    }

    companion object{
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.unsplashId == newItem.unsplashId

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }

}


