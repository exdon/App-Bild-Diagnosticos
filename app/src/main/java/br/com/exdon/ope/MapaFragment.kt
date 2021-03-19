package br.com.exdon.ope

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng as LatLng

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapaFragment : Fragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap

        // solicita permissão para acessar localização
        val ok = PermissionsUtils.validate(activity!!, 1,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION) // posso passar outras opções de permissões aqui.

        if (ok) map?.isMyLocationEnabled = true

        // get item selected spinner fale conosco
        var intent = Intent()
        val locationSelected = intent.getIntExtra("locationSelected", 0)

        // cria um objeto de latitude e longitude
        val locationBildHospSI = LatLng(-22.8636357, -47.2156678)

        // colocar um marcador no local selecionado
        map?.addMarker(MarkerOptions()
            .title("BILD")
            .snippet("Hospital Stª Ignês\n" +
                    "Indaiatuba")
            .position(locationBildHospSI)
        )
        // posicionar o mapa na coordenada criada, com um valor de zoom
        val update = CameraUpdateFactory.newLatLngZoom(locationBildHospSI, 18f)
        map?.moveCamera(update)

            /*
        } else if (locationSelected == "Shopping Hortolândia"){
            val locationBildShopHorto = LatLng(-22.8636357, -47.2156678)

            // colocar um marcador no local selecionado
            map?.addMarker(MarkerOptions()
                .title("BILD")
                .snippet("Shopping Hortolândia")
                .position(locationBildShopHorto)
            )
            // posicionar o mapa na coordenada criada, com um valor de zoom
            val update = CameraUpdateFactory.newLatLngZoom(locationBildShopHorto, 18f)
            map?.moveCamera(update)
        }

             */



        val location = LatLng(-23.525322, -46.649481)  //Latitude e longitude do endereço da impacta

        // colocar um marcador no local selecionado
        map?.addMarker(MarkerOptions()
            .title("FIT")
            .snippet("FACULDADE IMPACTA")
            .position(location)
        )

        // tipo de mapa
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    // Habilitar o botão de localização
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        for (r in results) {
            if (r == PackageManager.PERMISSION_GRANTED) {
                map?.isMyLocationEnabled = true
                return
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_mapa, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

}