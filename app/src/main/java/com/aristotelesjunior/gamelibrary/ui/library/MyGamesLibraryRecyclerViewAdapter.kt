package com.aristotelesjunior.gamelibrary.ui.library

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aristotelesjunior.gamelibrary.database.DataConverter
import com.aristotelesjunior.gamelibrary.databinding.GameItemBinding
import com.aristotelesjunior.gamelibrary.models.Game

class MyGamesLibraryRecyclerViewAdapter(
    private val dataSet: List<Game>
) : RecyclerView.Adapter<MyGamesLibraryRecyclerViewAdapter.GamesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return GamesViewHolder(
            GameItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holderGames: GamesViewHolder, position: Int) {
        val item = dataSet[position]
        holderGames.ivGamePicture.setImageBitmap(DataConverter.DbBitmapUtility.getImage(item.image))
        holderGames.tvGameTitle.text = item.name
        holderGames.tvRating.text = if (item.rating != -1) item.rating.toString() else ""
        holderGames.tvGenre.text = item.genre
        holderGames.tvReleaseYear.text = item.releaseDate
    }

    override fun getItemCount(): Int = dataSet.size

    inner class GamesViewHolder(binding: GameItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivGamePicture : ImageView = binding.ivGamePicture
        val tvGameTitle : TextView = binding.tvGameTitle
        val tvRating : TextView = binding.tvRating
        val tvGenre : TextView = binding.tvGenre
        val tvReleaseYear : TextView = binding.tvReleaseYear

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}