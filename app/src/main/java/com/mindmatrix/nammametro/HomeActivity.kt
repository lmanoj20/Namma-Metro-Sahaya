package com.mindmatrix.nammametro

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mindmatrix.nammametro.data.MetroNetwork
import com.mindmatrix.nammametro.databinding.ActivityHomeBinding
import com.mindmatrix.nammametro.util.BaseActivity
import com.mindmatrix.nammametro.util.LocaleHelper

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupStationDropdowns()
        setupOfflineBanner()

        binding.btnGo.setOnClickListener { onGoClicked() }
        binding.btnTokenGuide.setOnClickListener {
            startActivity(Intent(this, TokenMachineActivity::class.java))
        }
        binding.btnLangToggle.setOnClickListener {
            LocaleHelper.toggle(this)
            recreate()
        }
        binding.btnSwap.setOnClickListener {
            val from = binding.spinnerFrom.text.toString()
            val to = binding.spinnerTo.text.toString()
            binding.spinnerFrom.setText(to, false)
            binding.spinnerTo.setText(from, false)
        }
    }

    private fun setupStationDropdowns() {
        val names = MetroNetwork.allStations.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, names)
        binding.spinnerFrom.setAdapter(adapter)
        binding.spinnerTo.setAdapter(adapter)
    }

    private fun setupOfflineBanner() {
        val cm = getSystemService(ConnectivityManager::class.java)
        val online = cm.activeNetwork?.let {
            cm.getNetworkCapabilities(it)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
        binding.offlineBanner.visibility = if (online) View.GONE else View.VISIBLE
    }

    private fun onGoClicked() {
        val fromName = binding.spinnerFrom.text.toString().trim()
        val toName = binding.spinnerTo.text.toString().trim()

        if (fromName.isEmpty() || toName.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_empty), Toast.LENGTH_SHORT).show()
            return
        }
        val from = MetroNetwork.findByName(fromName)
        val to = MetroNetwork.findByName(toName)
        if ((from == null) || (to == null)) {
            Toast.makeText(this, getString(R.string.err_invalid), Toast.LENGTH_LONG).show()
            return
        }
        if (from.id == to.id) {
            Toast.makeText(this, getString(R.string.err_same), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, RouteActivity::class.java)
            .putExtra("from_id", from.id)
            .putExtra("to_id", to.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        setupOfflineBanner()
    }
}
