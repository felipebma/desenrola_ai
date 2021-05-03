package com.example.desenrolaai.screens.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.R
import com.example.desenrolaai.model.Product
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.setInfoWindowAdapter(infoAdapter(layoutInflater))
        for (product in viewModel.products.value!!) {
            addMarker(googleMap, product)
        }
        val userPos = LatLng(viewModel.user.value?.latitude!!, viewModel.user.value?.longitute!!)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPos, 15.0f))
        googleMap.setOnInfoWindowClickListener {
            Toast.makeText(context, "Produto Escolhido: " + (it.tag as Product).name, Toast.LENGTH_SHORT).show()
        }
    }

    fun addMarker(googleMap: GoogleMap, product: Product){
        val pos = LatLng(product.latitude!!, product.longitude!!)
        val snippet = StringJoiner("\n")
        snippet.add("Descrição: " + product.description)
        snippet.add("Preço por dia: R$%.2f".format(product.pricePerDay))
        val marker = googleMap.addMarker(
            MarkerOptions().position(pos).title(product.name)
                .snippet(snippet.toString())
                .flat(false)
        )
        marker.tag = product
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}