package com.mindmatrix.nammametro.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindmatrix.nammametro.R
import com.mindmatrix.nammametro.data.RouteStep

class RouteStepAdapter(private val items: List<RouteStep>)
    : RecyclerView.Adapter<RouteStepAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.txtStop)
        val badge: TextView = v.findViewById(R.id.txtBadge)
        val dot: ImageView = v.findViewById(R.id.imgDot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_route_stop, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val step = items[position]
        h.name.text = step.station.name
        h.dot.setColorFilter(Color.parseColor(step.station.line.colorHex))
        if (step.isInterchange) {
            h.badge.visibility = View.VISIBLE
            h.badge.text = "INTERCHANGE → ${step.switchToLine?.displayName ?: ""}"
        } else {
            h.badge.visibility = View.GONE
        }
    }

    override fun getItemCount() = items.size
}
