package com.ainullov.kamil.transportation_problem

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Test

class ConvertingJsonTest {

    private val supply = intArrayOf(14, 10, 15, 12)
    private val costs: Array<DoubleArray> = arrayOf(
        doubleArrayOf(10.0, 30.0, 25.0, 15.0),
        doubleArrayOf(20.0, 15.0, 20.0, 10.0),
        doubleArrayOf(10.0, 30.0, 20.0, 20.0),
        doubleArrayOf(30.0, 40.0, 35.0, 45.0)
    )
    private val costsJson =
        "[[10.0,30.0,25.0,15.0],[20.0,15.0,20.0,10.0],[10.0,30.0,20.0,20.0],[30.0,40.0,35.0,45.0]]"
    private val supplyJson =
        "[14,10,15,12]"

    @Test
    fun isArrayOfDoubleArraysConvertingToString() {
        val cJson: String = Gson().toJson(costs)
        Assert.assertTrue(cJson == costsJson)
    }

    @Test
    fun isStringConvertingToArrayOfDoubleArrays() {
        val c: Array<DoubleArray> = Gson().fromJson(
            costsJson,
            object : TypeToken<Array<DoubleArray>>() {}.type
        )
        Assert.assertTrue(c.contentDeepEquals(costs))
    }

    @Test
    fun isArrayOfIntsConvertingToString() {
        val sJson: String = Gson().toJson(supply)
        Assert.assertTrue(sJson == supplyJson)
    }

    @Test
    fun isStringConvertingToArrayOfInts() {
        val s: IntArray = Gson().fromJson(
            supplyJson,
            object : TypeToken<IntArray>() {}.type
        )
        Assert.assertTrue(s.contentEquals(supply))
    }
}