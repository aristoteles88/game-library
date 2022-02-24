package com.aristotelesjunior.gamelibrary.ui.library

import android.content.Context
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
import com.aristotelesjunior.gamelibrary.database.Platform
import com.aristotelesjunior.gamelibrary.databinding.FragmentLibraryBinding


class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var rvLibrary: RecyclerView
    private lateinit var platformAdapter: PlataformAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        populatePlatformRecyclerView()

        return root
    }

    override fun onResume() {
        super.onResume()
        populatePlatformRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addNoPlatformRow() : List<Platform> {
        val bitmapImage: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pngwing_com)

        val noPlatform = Platform(
            id = 0,
            name = "Não há plataformas cadastradas",
            releaseDate = "",
            gamesAmount = -1,
            image = DataConverter.DbBitmapUtility.getBytes(bitmapImage)
        )
        return listOf(noPlatform)
    }

    private fun populatePlatformRecyclerView() {
        val platformDBList = GameDB.getInstance(requireActivity().applicationContext).platformDao().getPlatforms()
        val platformList = platformDBList.ifEmpty { addNoPlatformRow() }

        platformAdapter = PlataformAdapter(dataSet = platformList)
        rvLibrary = binding.rvLibrary
        rvLibrary.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = platformAdapter
            addItemDecoration(DividerItemDecoration(rvLibrary.context,
                DividerItemDecoration.VERTICAL))
        }
    }
}