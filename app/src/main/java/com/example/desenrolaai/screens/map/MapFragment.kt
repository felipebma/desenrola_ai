package com.example.desenrolaai.screens.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.desenrolaai.R
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.User
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        if(viewModel._products.value != null){
            for (product in viewModel._products.value!!) {
                addMarker(googleMap, product)
            }
        }

        if(viewModel._user.value != null){
            val userPos = LatLng(viewModel._user.value?.latitude!!, viewModel._user.value?.longitute!!)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPos, 15.0f))
            googleMap.setOnInfoWindowClickListener {
                Toast.makeText(context, "Produto Escolhido: " + (it.tag as Product).name, Toast.LENGTH_SHORT).show()
                selectProduct(it.tag as Product);
            }
        }
    }

    fun selectProduct(product: Product){
        val action = MapFragmentDirections.actionMapFragmentToBorrowDetailFragment()
        action.product = product
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun addMarker(googleMap: GoogleMap, product: Product){
        val pos = LatLng(product.latitude!!, product.longitude!!)
        val snippet = StringJoiner("\n")
        val maxLength = 70
        val description = if(product.description.length <= maxLength) product.description else product.description.substring(0, maxLength)+"..."
        snippet.add("Descrição: $description")
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
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        db.collection("users")
            .whereEqualTo("email", currentUser.email)
            .get()
            .addOnSuccessListener { documents ->

                viewModel._user.value = documents.toObjects(User::class.java)[0]
                Log.d("FIREBASE", viewModel._user.value.toString())
                mapFragment?.getMapAsync(callback)

                db.collection("users")
                    .whereEqualTo("email", currentUser.email)
                    .get()
                    .addOnSuccessListener { documents ->

                        for(document in documents){
                            viewModel._products.value?.add(document.toObject(Product::class.java))
                            Log.d("FIREBASE",  viewModel._products.value.toString())
                        }

                        mapFragment?.getMapAsync(callback)


                    }
                    .addOnFailureListener { exception ->
                        Log.w("FIREBASE", "Error getting documents: ", exception)
                    }


            }
            .addOnFailureListener { exception ->
                Log.w("FIREBASE", "Error getting documents: ", exception)
            }

    }
}