package com.mindmatrix.nammametro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindmatrix.nammametro.adapter.RouteStepAdapter
import com.mindmatrix.nammametro.data.Pathfinder
import com.mindmatrix.nammametro.data.Route
import com.mindmatrix.nammametro.data.StepData
import com.mindmatrix.nammametro.databinding.ActivityRouteBinding
import com.mindmatrix.nammametro.util.BaseActivity

class RouteActivity : BaseActivity() {

    private lateinit var binding: ActivityRouteBinding
    private var currentRoute: Route? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromId = intent.getStringExtra("from_id") ?: return finishWithError()
        val toId = intent.getStringExtra("to_id") ?: return finishWithError()

        val start = System.currentTimeMillis()
        val route = Pathfinder.findRoute(fromId, toId)
        val elapsed = System.currentTimeMillis() - start

        if (route == null) return finishWithError()
        currentRoute = route

        binding.txtFromName.text = route.steps.first().station.name
        binding.txtToName.text = route.steps.last().station.name
        binding.txtFare.text = getString(R.string.fare_format, route.fare)
        binding.txtTime.text = getString(R.string.time_format, route.travelTimeMinutes)
        binding.txtStops.text = getString(R.string.stops_format, route.totalStops)
        binding.txtPathfindingTime.text = "Route computed in ${elapsed}ms"

        if (route.hasInterchange && (route.interchangeStation != null)) {
            binding.cardInterchange.visibility = View.VISIBLE
            binding.txtInterchangeName.text = route.interchangeStation.name
        } else {
            binding.cardInterchange.visibility = View.GONE
        }

        binding.lineDotFrom.setBackgroundColor(route.steps.first().station.line.colorHex.toColorInt())
        binding.lineDotTo.setBackgroundColor(route.steps.last().station.line.colorHex.toColorInt())

        binding.recyclerStops.layoutManager = LinearLayoutManager(this)
        binding.recyclerStops.adapter = RouteStepAdapter(route.steps)

        binding.layoutTips.removeAllViews()
        for (tip in StepData.journeyTips(route)) {
            val tv = layoutInflater.inflate(R.layout.item_tip, binding.layoutTips, false) as android.widget.TextView
            tv.text = "• $tip"
            binding.layoutTips.addView(tv)
        }

        binding.btnStartGuide.setOnClickListener {
            val i = Intent(this, StepperActivity::class.java)
                .putExtra("from_id", fromId)
                .putExtra("to_id", toId)
            startActivity(i)
        }
        binding.btnExitFinder.setOnClickListener {
            val i = Intent(this, ExitFinderActivity::class.java)
                .putExtra("station_id", route.steps.last().station.id)
            startActivity(i)
        }
    }

    private fun finishWithError() {
        Toast.makeText(this, getString(R.string.err_no_route), Toast.LENGTH_LONG).show()
        finish()
    }
}
