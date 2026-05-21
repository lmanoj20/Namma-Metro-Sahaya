package com.mindmatrix.nammametro.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindmatrix.nammametro.R
import com.mindmatrix.nammametro.data.Station

class StationAdapter(
    private var items: List<Station>,
    private val onClick: (Station) -> Unit
) : RecyclerView.Adapter<StationAdapter.VH>() {

    fun submit(list: List<Station>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.txtStationName)
        val line: TextView = v.findViewById(R.id.txtStationLine)
        val dot: View = v.findViewById(R.id.lineDot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_station, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val s = items[position]
        h.name.text = s.name
        h.line.text = if (s.isInterchange) "Interchange • ${s.line.displayName}" else s.line.displayName
        h.dot.setBackgroundColor(Color.parseColor(s.line.colorHex))
        h.itemView.setOnClickListener { onClick(s) }
    }

    override fun getItemCount() = items.size
}
