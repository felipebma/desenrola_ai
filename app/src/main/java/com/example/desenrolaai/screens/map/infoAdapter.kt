package com.example.desenrolaai.screens.map

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.desenrolaai.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class infoAdapter(layoutInflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {
    private var popup: View? = null
    private lateinit var inflater: LayoutInflater

    init {
        inflater = layoutInflater
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }

    @SuppressLint("InflateParams")
    override fun getInfoContents(marker: Marker): View? {
        if (popup == null) {
            popup = inflater.inflate(R.layout.info_view, null)
        }
        var tv = popup?.findViewById<View>(R.id.title) as TextView
        tv.text = marker.title
        tv = popup?.findViewById<View>(R.id.snippet) as TextView
        tv.text = marker.snippet
        return popup
    }

}