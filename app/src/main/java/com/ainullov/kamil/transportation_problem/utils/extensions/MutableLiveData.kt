package com.ainullov.kamil.transportation_problem.utils.extensions

import androidx.lifecycle.MutableLiveData

//Set default value for any type of MutableLiveData
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

//Set new value for any type of MutableLiveData
fun <T> MutableLiveData<T>.set(newValue: T) = apply { setValue(newValue) }

//Post new value for any type of MutableLiveData
fun <T> MutableLiveData<T>.post(newValue: T) = apply { postValue(newValue) }

