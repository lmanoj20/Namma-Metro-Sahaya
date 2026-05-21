package com.mindmatrix.nammametro

import android.os.Bundle
import android.view.View
import com.mindmatrix.nammametro.data.StepData
import com.mindmatrix.nammametro.data.VisualStep
import com.mindmatrix.nammametro.databinding.ActivityTokenMachineBinding
import com.mindmatrix.nammametro.util.BaseActivity
import com.mindmatrix.nammametro.util.LocaleHelper

class TokenMachineActivity : BaseActivity() {

    private lateinit var binding: ActivityTokenMachineBinding
    private val steps: List<VisualStep> by lazy { StepData.tokenMachineSteps() }
    private var idx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        render()
        binding.btnPrev.setOnClickListener { if (idx > 0) { idx--; render() } }
        binding.btnNext.setOnClickListener {
            if (idx < steps.lastIndex) { idx++; render() } else finish()
        }
    }

    private fun render() {
        val s = steps[idx]
        val isKn = LocaleHelper.getLanguage(this) == LocaleHelper.KN
        binding.txtStepTitle.text = if (isKn) s.titleKn else s.titleEn
        binding.txtStepBody.text = if (isKn) s.instructionKn else s.instructionEn
        binding.imgStep.setImageResource(s.drawableRes)
        binding.txtProgress.text = getString(R.string.progress_format, idx + 1, steps.size)
        binding.progressBar.max = steps.size
        binding.progressBar.progress = idx + 1
        binding.btnPrev.visibility = if (idx == 0) View.INVISIBLE else View.VISIBLE
        binding.btnNext.text = if (idx == steps.lastIndex)
            getString(R.string.btn_done) else getString(R.string.btn_next)
    }
}
