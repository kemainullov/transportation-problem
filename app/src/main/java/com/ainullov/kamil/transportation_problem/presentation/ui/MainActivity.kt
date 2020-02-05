package com.ainullov.kamil.transportation_problem.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.transportation_problem.TransportationProblem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TransportationProblem("D:\\input1.txt").execute(1)
    }
}
