package com.example.desenrolaai.screens.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.desenrolaai.R
import com.example.desenrolaai.model.Product
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setInfoWindowAdapter(infoAdapter(layoutInflater))
        for (product in viewModel.products.value!!) {
            addMarker(googleMap, product)
        }
        val userPos = LatLng(viewModel.user.value?.latitude!!, viewModel.user.value?.longitude!!)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPos, 15.0f))
        googleMap.setOnInfoWindowClickListener {
            Toast.makeText(
                context,
                "Produto Escolhido: " + (it.tag as Product).name,
                Toast.LENGTH_SHORT
            ).show()
            selectProduct(it.tag as Product)
        }
    }

    fun selectProduct(product: Product) {
        val action = MapFragmentDirections.actionMapFragmentToBorrowDetailFragment()
        action.product = product
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun addMarker(googleMap: GoogleMap, product: Product) {
        val pos = LatLng(product.latitude!!, product.longitude!!)
        val snippet = StringJoiner("\n")
        val maxLength = 70
        val description =
            if (product.description.length <= maxLength) product.description else product.description.substring(
                0,
                maxLength
            ) + "..."
        snippet.add("Descri????o: $description")
        snippet.add("Pre??o por dia: R$%.2f".format(product.pricePerDay))
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
        viewModel.dataFetched.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.i("Map", "Entrou " + viewModel.countFetch)
            Log.i("Map", "Entrou " + viewModel.products.value)
            mapFragment?.getMapAsync(callback)
        })
    }
}