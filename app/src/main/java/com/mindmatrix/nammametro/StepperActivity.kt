package com.mindmatrix.nammametro

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mindmatrix.nammametro.data.Pathfinder
import com.mindmatrix.nammametro.data.StepData
import com.mindmatrix.nammametro.data.VisualStep
import com.mindmatrix.nammametro.databinding.ActivityStepperBinding
import com.mindmatrix.nammametro.util.BaseActivity
import com.mindmatrix.nammametro.util.LocaleHelper

class StepperActivity : BaseActivity() {

    private lateinit var binding: ActivityStepperBinding
    private var steps: List<VisualStep> = emptyList()
    private var currentIndex = 0
    private var lastStationId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStepperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromId = intent.getStringExtra("from_id") ?: return finish()
        val toId = intent.getStringExtra("to_id") ?: return finish()
        lastStationId = toId

        val route = Pathfinder.findRoute(fromId, toId) ?: return finish()
        steps = StepData.buildForRoute(route)
        render()

        binding.btnPrev.setOnClickListener {
            if (currentIndex > 0) { currentIndex--; render() }
        }
        binding.btnNext.setOnClickListener {
            if (currentIndex < steps.lastIndex) { currentIndex++; render() }
            else {
                val i = Intent(this, ExitFinderActivity::class.java)
                    .putExtra("station_id", lastStationId)
                startActivity(i)
            }
        }
    }

    private fun render() {
        val s = steps[currentIndex]
        val isKn = LocaleHelper.getLanguage(this) == LocaleHelper.KN
        binding.txtStepTitle.text = if (isKn) s.titleKn else s.titleEn
        binding.txtStepBody.text = if (isKn) s.instructionKn else s.instructionEn
        binding.imgStep.setImageResource(s.drawableRes)
        binding.txtProgress.text = "Step ${currentIndex + 1} of ${steps.size}"
        binding.progressBar.max = steps.size
        binding.progressBar.progress = currentIndex + 1
        binding.btnPrev.visibility = if (currentIndex == 0) View.INVISIBLE else View.VISIBLE
        binding.btnNext.text = if (currentIndex == steps.lastIndex)
            getString(R.string.btn_find_exit) else getString(R.string.btn_next)
    }
}
