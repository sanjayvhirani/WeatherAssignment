package com.sanjay.weatherassignment.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sanjay.weatherassignment.OpenWeatherMap.OpenWeatherMap
import com.sanjay.weatherassignment.R
import com.sanjay.weatherassignment.adapter.LocationAdapter
import com.sanjay.weatherassignment.model.LocationViewModel

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var rvLocations: RecyclerView
    private lateinit var adapter: LocationAdapter
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var mContext: Context
    private var latitude: Double = 23.0225
    private var longitude: Double = 72.5714
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        rvLocations = root.findViewById<RecyclerView>(R.id.fragment_home_rv_locations)
        setupMap()
        setupLocationList()
        /* val textView: TextView = root.findViewById(R.id.text_home)
         homeViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })*/
        return root
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addCurrentLocationMarker()
        mMap.setOnMapLongClickListener(GoogleMap.OnMapLongClickListener {
            mMap.addMarker(MarkerOptions().position(it).title("${it.latitude} , ${it.longitude}"))
            addLocationToList(it)
        })
    }

    private fun addLocationToList(it: LatLng) {
        val openWeatherMap = OpenWeatherMap(activity!!)
        openWeatherMap.getWeatherByLocation(it.latitude, it.longitude, locationViewModel)
    }

    /**
     * Set marker to current location
     */
    private fun addCurrentLocationMarker() {
        val currentLocation = LatLng(latitude, longitude)
        addLocationToList(currentLocation)
        mMap.addMarker(MarkerOptions().position(currentLocation).title("$latitude , $longitude"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun setupLocationList() {

        adapter = LocationAdapter(mContext, locationViewModel)
        rvLocations.adapter = adapter
        rvLocations.layoutManager = LinearLayoutManager(mContext)

        updateTaskList()
    }

    private fun updateTaskList() {
        locationViewModel.allLocations.observe(viewLifecycleOwner, Observer { location ->
            // Update the cached copy of the words in the adapter.
            location?.let { adapter.setLocations(it) }
        })
    }

}