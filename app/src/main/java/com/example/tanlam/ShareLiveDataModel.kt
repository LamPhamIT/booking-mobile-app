package com.example.tanlam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareLiveDataModel: ViewModel() {
    private val _inputdata = MutableLiveData<String>("")
    val inputData: LiveData<String> get() = _inputdata

    fun updateLiveData(data: String) {
        _inputdata.value = data
    }
}