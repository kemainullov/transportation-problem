package com.ainullov.kamil.transportation_problem.data.db

import androidx.room.TypeConverter
import com.ainullov.kamil.transportation_problem.data.models.ProblemSolutionModel
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    companion object {
        val gson = Gson()
    }

    @TypeConverter
    fun fromSolutionModel(problemSolutionModel: ProblemSolutionModel): String? {
        return gson.toJson(problemSolutionModel)
    }

    @TypeConverter
    fun toSolutionModel(jsonData: String?): ProblemSolutionModel? {
        return gson.fromJson(jsonData, ProblemSolutionModel::class.java)
    }

    @TypeConverter
    fun fromMatrix(matrix: Array<Array<Shipment>>): String? {
        return gson.toJson(matrix)
    }

    @TypeConverter
    fun toMatrix(jsonData: String?): Array<Array<Shipment>>? {
        return gson.fromJson(
            jsonData,
            object : TypeToken<Array<Array<Shipment>>>() {}.type
        )
    }

    @TypeConverter
    fun fromTransportationProblemData(transportationProblemData: TransportationProblemData): String? {
        return gson.toJson(transportationProblemData)
    }

    @TypeConverter
    fun toTransportationProblemData(jsonData: String?): TransportationProblemData? {
        return gson.fromJson(jsonData, TransportationProblemData::class.java)
    }
}