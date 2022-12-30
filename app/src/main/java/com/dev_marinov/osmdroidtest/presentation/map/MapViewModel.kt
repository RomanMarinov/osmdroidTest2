package com.dev_marinov.osmdroidtest.presentation.map

import androidx.lifecycle.*
import com.dev_marinov.osmdroidtest.data.Constants
import com.dev_marinov.osmdroidtest.domain.IRepository
import com.dev_marinov.osmdroidtest.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val iRepository: IRepository) : ViewModel() {

    private var _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data> = _data

    private var _locationsTitle: MutableLiveData<List<String>?> = MutableLiveData()
    val locationsTitle: LiveData<List<String>?> = _locationsTitle

    private var _mapCategories: MutableLiveData<List<CityCam>> = MutableLiveData()
    val mapCategories: LiveData<List<CityCam>> = _mapCategories

    private var _mapCityCams: MutableLiveData<List<MarkerCityCam>> = MutableLiveData()
    val mapCityCams: LiveData<List<MarkerCityCam>> = _mapCityCams

    private var _mapDomofonCams: MutableLiveData<List<MarkerDomofonCam>> = MutableLiveData()
    val mapDomofonCams: LiveData<List<MarkerDomofonCam>> = _mapDomofonCams

    private var _mapOutDoorCams: MutableLiveData<List<MarkerOutdoorCam>> = MutableLiveData()
    val mapOutDoorCams: LiveData<List<MarkerOutdoorCam>> = _mapOutDoorCams

    private var _mapOffice: MutableLiveData<List<MarkerOffice>> = MutableLiveData()
    val mapOffice: LiveData<List<MarkerOffice>> = _mapOffice

    private var _flagCityCams = false
    private var _flagDomofonCams = false
    private var _flagOutDoorCams = false
    private var _flagOffice = false

//    private var _selectedCity: MutableStateFlow<Int?> = MutableStateFlow(null)
//    val selectedCity: StateFlow<Int?> = _selectedCity


    private var _setLocation: MutableStateFlow<Location?> = MutableStateFlow(
        Location(
            camerasAvailable = Constants.DefaultLocation.CAMERASAVAILABLE,
            center = Constants.DefaultLocation.CENTER,
            leftTopLat = Constants.DefaultLocation.LEFTTOPLAT,
            leftTopLon = Constants.DefaultLocation.LEFTTOPLON,
            location = Constants.DefaultLocation.LOCATION,
            rightBottomLat = Constants.DefaultLocation.RIGHTBOTTOMLAT,
            rightBottomLon = Constants.DefaultLocation.RIGHTBOTTOMLON,
            title = Constants.DefaultLocation.TITLE
        )
    )
    val setLocation: StateFlow<Location?> = _setLocation

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val job = launch {
                val data = iRepository.getData()
                data?.let {
                    _data.postValue(it)
                }
            }
            job.join()
            getMapCategories()
            getLocationTitle()
        }
    }

    private suspend fun getLocationTitle() {
        delay(100)
        val list: MutableList<String> = mutableListOf()
        val data = _data.value?.locations
        data?.map { list.add(it.title) }
        _locationsTitle.postValue(list)
    }

    private suspend fun getMapCategories() {
        delay(100)
        // в виду не совсем удобного json пришлось сделать так
        val list: MutableList<CityCam> = mutableListOf()
        _data.value?.let {
            list.add(0, CityCam(it.mapMarkers.cityCams.title, it.mapMarkers.cityCams.count))
            list.add(1, CityCam(it.mapMarkers.outdoorCams.title, it.mapMarkers.outdoorCams.count))
            list.add(2, CityCam(it.mapMarkers.domofonCams.title, it.mapMarkers.domofonCams.count))
            list.add(3, CityCam(it.mapMarkers.officeCams.title, it.mapMarkers.officeCams.count))
            _mapCategories.postValue(list)
        }
    }

    suspend fun onClick(position: Int) {
        delay(50)
        val location = data.value?.locations?.let {
            it[position]
        }
        _setLocation.value = location
    }

    fun onClickCategory(position: Int) {
        val item = _mapCategories.value?.let { it[position].title }

        if (item.equals("Городские камеры")) {
            if (_flagCityCams) {
                _flagCityCams = false
                _mapCityCams.postValue(emptyList())
            } else {
                _flagCityCams = true
                _data.value?.let {
                    _mapCityCams.postValue(it.mapMarkers.cityCams.markers)
                }
            }
        }
        if (item.equals("Дворовые камеры")) {
            if (_flagOutDoorCams) {
                _flagOutDoorCams = false
                _mapOutDoorCams.postValue(emptyList())
            } else {
                _flagOutDoorCams = true
                _data.value?.let {
                    _mapOutDoorCams.postValue(it.mapMarkers.outdoorCams.markers)
                }
            }
        }
        if (item.equals("Домофоны")) {
            if (_flagDomofonCams) {
                _flagDomofonCams = false
                _mapDomofonCams.postValue(emptyList())
            } else {
                _flagDomofonCams = true
                _data.value?.let {
                    _mapDomofonCams.postValue(it.mapMarkers.domofonCams.markers)
                }
            }
        }
        if (item.equals("Офисы")) {
            if (_flagOffice) {
                _flagOffice = false
                _mapOffice.postValue(emptyList())
            } else {
                _flagOffice = true
                _data.value?.let {
                    _mapOffice.postValue(it.mapMarkers.officeCams.markers)
                }
            }

        }
    }
}
