package com.sanjay.weatherassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjay.weatherassignment.R
import com.sanjay.weatherassignment.model.Location
import com.sanjay.weatherassignment.model.LocationViewModel

class LocationAdapter internal constructor(
    context: Context,
    locationViewModel: LocationViewModel
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val mContext: Context = context
    private val locationViewModel: LocationViewModel = locationViewModel
    private var locations = emptyList<Location>()

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationItemTvName: TextView = itemView.findViewById(R.id.locationItemTvName)
        val locationItemTvDescription: TextView =
            itemView.findViewById(R.id.locationItemTvDescription)
        val locationItemTvTemp: TextView = itemView.findViewById(R.id.locationItemTvTemp)
        val locationItemTvSpeed: TextView = itemView.findViewById(R.id.locationItemTvSpeed)
        val locationItemIvIcon: ImageView = itemView.findViewById(R.id.locationItemIvIcon)
        val locationItemIvDelete: ImageView = itemView.findViewById(R.id.locationItemIvDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = inflater.inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val current = locations[position]
        holder.locationItemTvName.text = current.name
        holder.locationItemTvDescription.text = current.description
        holder.locationItemTvTemp.text = current.temperature
        holder.locationItemTvSpeed.text = current.speed
        Glide.with(mContext).load(current.icon).into(holder.locationItemIvIcon)

        holder.locationItemIvDelete.setOnClickListener {
            locationViewModel.delete(current)
        }
    }

    internal fun setLocations(locations: List<Location>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}


