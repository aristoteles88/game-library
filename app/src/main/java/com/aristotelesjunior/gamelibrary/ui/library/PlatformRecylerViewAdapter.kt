package com.aristotelesjunior.gamelibrary.ui.library

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.DataConverter
import com.aristotelesjunior.gamelibrary.database.GameDB
import com.aristotelesjunior.gamelibrary.databinding.PlatformItemBinding
import com.aristotelesjunior.gamelibrary.models.Platform

class PlataformRecyclerViewAdapter(private val dataSet: List<Platform>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<PlataformRecyclerViewAdapter.PlatformViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PlatformViewHolder {

//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.platform_item, viewGroup, false)
//        return PlatformViewHolder(view)

        return PlatformViewHolder(
            PlatformItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(platformViewHolder: PlatformViewHolder, position: Int) {
        val platform = dataSet[position]
        platformViewHolder.ivPlatform.setImageBitmap(DataConverter.DbBitmapUtility.getImage(platform.image))
        platformViewHolder.tvPlatformName.text = platform.name
        platformViewHolder.tvGamesAmount.text = if (platform.gamesAmount != -1) platform.gamesAmount.toString() + " jogos" else ""
        if (platform.gamesAmount == -1) platformViewHolder.imBtnArrow.setImageResource(0)
        if (platform.gamesAmount != -1) platformViewHolder.itemView.setOnClickListener {
            onClickListener.onClick(platform)
        }
    }

    override fun getItemCount(): Int = dataSet.size

    inner class PlatformViewHolder(binding: PlatformItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var ivPlatform : ImageView = binding.ivPlatform
        val tvPlatformName : TextView = binding.tvPlatformName
        val tvGamesAmount : TextView = binding.tvGamesAmount
        val imBtnArrow : ImageButton = binding.imBtnArrow

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

    class OnClickListener(val clickListener: (platform: Platform) -> Unit) {
        fun onClick(platform : Platform) = clickListener(platform)
    }
}