package br.senai.sp.jandira.tcc_pas.map

import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import android.widget.TextView
import br.senai.sp.jandira.tcc_pas.R

class CustomInfoWindow(
    layoutResId: Int,
    mapView: MapView?
) : InfoWindow(layoutResId, mapView) {

    override fun onOpen(item: Any?) {
        val marker = item as? Marker ?: return

        val titleView = mView.findViewById<TextView>(R.id.txtTitle)
        val descView = mView.findViewById<TextView>(R.id.txtDescription)

        titleView.text = marker.title
        descView.text = marker.snippet
    }

    override fun onClose() {
        // opcional
    }
}
