package com.aristotelesjunior.gamelibrary.ui.library

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.DataConverter
import com.aristotelesjunior.gamelibrary.database.GameDB
import com.aristotelesjunior.gamelibrary.models.Platform
import com.aristotelesjunior.gamelibrary.databinding.FragmentLibraryBinding
import com.aristotelesjunior.gamelibrary.models.Game
import com.aristotelesjunior.gamelibrary.ui.add.platform.AddPlatformActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var fabAdd :FloatingActionButton

    private lateinit var rvLibrary: RecyclerView
    private lateinit var platformRecyclerViewAdapter: PlataformRecyclerViewAdapter
    private lateinit var gameRecyclerViewAdapter: MyGamesLibraryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fabAdd = binding.fabAdd

        val rvData = fetchPlatformFromDB(requireContext())

        platformRecyclerViewAdapter = PlataformRecyclerViewAdapter(dataSet = rvData, PlataformRecyclerViewAdapter.OnClickListener {platform  -> clickPlatform(platform)} )
        rvLibrary = binding.rvLibrary
        rvLibrary.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = platformRecyclerViewAdapter
            addItemDecoration(
                DividerItemDecoration(rvLibrary.context,
                DividerItemDecoration.VERTICAL)
            )
        }
        fabAdd.setOnClickListener {
            val addItemIntent = Intent(context, AddPlatformActivity::class.java)
            startActivity(addItemIntent)
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        val rvData = fetchPlatformFromDB(requireContext())

        platformRecyclerViewAdapter = PlataformRecyclerViewAdapter(dataSet = rvData, PlataformRecyclerViewAdapter.OnClickListener {platform  -> clickPlatform(platform)} )
        rvLibrary = binding.rvLibrary
        rvLibrary.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = platformRecyclerViewAdapter
            addItemDecoration(
                DividerItemDecoration(rvLibrary.context,
                    DividerItemDecoration.VERTICAL)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickPlatform(platform: Platform) {
        val rvData = fetchGamesFromDB(requireContext(), platform.id)
        gameRecyclerViewAdapter = MyGamesLibraryRecyclerViewAdapter(rvData)
        rvLibrary.adapter = gameRecyclerViewAdapter
    }

    private fun addNoPlatformItem(context: Context) : List<Platform> {
        val bitmapImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pngwing_com)

        val noPlatform = Platform(
            id = 0,
            name = "Não há plataformas cadastradas",
            releaseDate = "",
            gamesAmount = -1,
            image = DataConverter.DbBitmapUtility.getBytes(bitmapImage)
        )
        return listOf(noPlatform)
    }

    private fun fetchPlatformFromDB(context: Context) : List<Platform> {
        return GameDB.getInstance(context)
            .platformDao()
            .getPlatforms()
            .ifEmpty { addNoPlatformItem(context) }
    }

    private fun addNoGameItem(context: Context) : List<Game> {
        val bitmapImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pngwing_com)

        val noGame = Game(
            id = 0,
            name = "Não há jogos cadastrados",
            releaseDate = "",
            description = "",
            genre = "",
            rating = -1,
            gameStatus = false,
            finished = false,
            wishlist = false,
            platform = "",
            image = DataConverter.DbBitmapUtility.getBytes(bitmapImage),
        )
        return listOf(noGame)
    }

    private fun fetchGamesFromDB(context: Context, platformId: Int) : List<Game>{
        return GameDB.getInstance(context)
            .gameDao()
            .getGamesFromPlatform(platformId)
            .ifEmpty { addNoGameItem(context) }
    }
}