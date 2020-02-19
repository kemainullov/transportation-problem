package com.ainullov.kamil.transportation_problem.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.base.App
import kotlinx.android.synthetic.main.activity_about_app.*

class AboutAppActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AboutAppActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        setOnClickListeners()
        initCheckBoxChangeListener()
    }

    private fun setOnClickListeners() {
        ib_back.setOnClickListener { onBackPressed() }
        ll_write.setOnClickListener { writeToSupport() }
    }

    private fun initCheckBoxChangeListener() {
        cb_do_not_show_hints.isChecked =
            App.transportationProblemSharedPreferences.getCustomBoolean("do_not_show_hints")
        cb_do_not_show_hints.setOnCheckedChangeListener { _, b ->
            App.transportationProblemSharedPreferences.setCustomBoolean("do_not_show_hints", b)
        }
    }

    private fun writeToSupport() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Error")
//            intent.putExtra(Intent.EXTRA_TEXT, "Problems with ...")
        startActivity(Intent.createChooser(intent, ""))
    }
}
