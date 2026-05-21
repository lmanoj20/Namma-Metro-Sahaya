package com.mindmatrix.nammametro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindmatrix.nammametro.R
import com.mindmatrix.nammametro.data.Exit

class ExitAdapter(private val items: List<Exit>) : RecyclerView.Adapter<ExitAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val gate: TextView = v.findViewById(R.id.txtGate)
        val dest: TextView = v.findViewById(R.id.txtDest)
        val landmark: TextView = v.findViewById(R.id.txtLandmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exit, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val e = items[position]
        h.gate.text = e.gateLabel
        h.dest.text = e.destinations.joinToString(" • ")
        h.landmark.text = e.landmark
    }

    override fun getItemCount() = items.size
}
