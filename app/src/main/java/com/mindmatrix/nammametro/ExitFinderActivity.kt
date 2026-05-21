package com.mindmatrix.nammametro

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindmatrix.nammametro.adapter.ExitAdapter
import com.mindmatrix.nammametro.data.ExitData
import com.mindmatrix.nammametro.data.MetroNetwork
import com.mindmatrix.nammametro.databinding.ActivityExitFinderBinding
import com.mindmatrix.nammametro.util.BaseActivity

class ExitFinderActivity : BaseActivity() {

    private lateinit var binding: ActivityExitFinderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExitFinderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stationId = intent.getStringExtra("station_id") ?: return finish()
        val station = MetroNetwork.findById(stationId) ?: return finish()

        binding.txtStationName.text = station.name
        binding.txtSubtitle.text = getString(R.string.exit_subtitle)

        val exits = ExitData.forStation(stationId)
        binding.recyclerExits.layoutManager = LinearLayoutManager(this)
        binding.recyclerExits.adapter = ExitAdapter(exits)
    }
}
