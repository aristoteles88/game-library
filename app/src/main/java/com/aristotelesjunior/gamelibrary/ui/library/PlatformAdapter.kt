package com.aristotelesjunior.gamelibrary.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.Platform

class PlataformAdapter(private val dataSet: Array<Platform>) :
    RecyclerView.Adapter<PlataformAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPlatform : ImageView = view.findViewById(R.id.ivPlatform)
        val tvPlatformName : TextView = view.findViewById(R.id.tvPlatformName)

//        init {
//            // Define click listener for the ViewHolder's View.
//        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.platform_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.ivPlatform = dataSet[position]
        viewHolder.tvPlatformName.text = dataSet[position].name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}