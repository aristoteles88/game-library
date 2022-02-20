package com.aristotelesjunior.gamelibrary.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.GameDB
import com.aristotelesjunior.gamelibrary.database.Platform
import com.aristotelesjunior.gamelibrary.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val libraryViewModel =
//            ViewModelProvider(this).get(LibraryViewModel::class.java)

        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val platformList = GameDB.getInstance(requireActivity().applicationContext).platformDao().getPlatforms()
        if (platformList.isEmpty()){
            val noPlatform = Platform(
                id = 0,
                name = "Não há plataformas cadastradas",
                releaseDate = "",
                image = R.drawable.pngwing_com
            )
            (platformList as MutableList<Platform>).add(noPlatform)
        }
        val platformAdapter = PlataformAdapter(dataSet = platformList as MutableList<Platform>)
        val rvLibrary: RecyclerView = binding.rvLibrary
//        libraryViewModel.re.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        rvLibrary.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = platformAdapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}