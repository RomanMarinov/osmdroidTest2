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

    private var _mapCityCams: MutableStateFlow<List<MarkerCityCam>> = MutableStateFlow(emptyList())
    val mapCityCams: StateFlow<List<MarkerCityCam>> = _mapCityCams

    private var _mapDomofonCams: MutableStateFlow<List<MarkerDomofonCam>> =
        MutableStateFlow(emptyList())
    val mapDomofonCams: StateFlow<List<MarkerDomofonCam>> = _mapDomofonCams

    private var _mapOutDoorCams: MutableStateFlow<List<MarkerOutdoorCam>> =
        MutableStateFlow(emptyList())
    val mapOutDoorCams: StateFlow<List<MarkerOutdoorCam>> = _mapOutDoorCams

    private var _mapOffice: MutableStateFlow<List<MarkerOffice>> = MutableStateFlow(emptyList())
    val mapOffice: StateFlow<List<MarkerOffice>> = _mapOffice

    private var _countZoom: MutableStateFlow<Int> = MutableStateFlow(0)
    val countZoom: StateFlow<Int> = _countZoom


    private var _countMethod: MutableStateFlow<Int> = MutableStateFlow(0)
    val countMethod: StateFlow<Int> = _countMethod


    private var _isEmptyCityCams = true
    private var _isEmptyDomofonCams = true
    private var _isEmptyOutDoorCams = true
    private var _isEmptyOffice = true
    private var _cZoom = 0

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
        val category = _mapCategories.value?.let { it[position].title }

        when (category) {
            "Городские камеры" -> {
                if (!_isEmptyCityCams) {
                    _mapCityCams.value = emptyList()
                    _isEmptyCityCams = true
                } else {
                    _isEmptyCityCams = false
                    fillingMapCams()
                }
            }

            "Дворовые камеры" -> {
                if (!_isEmptyOutDoorCams) {
                    _mapOutDoorCams.value = emptyList()
                    _isEmptyOutDoorCams = true
                } else {
                    _isEmptyOutDoorCams = false
                    fillingMapCams()
                }
            }

            "Домофоны" -> {
                if (!_isEmptyDomofonCams) {
                    _mapDomofonCams.value = emptyList()
                    _isEmptyDomofonCams = true
                } else {
                    _isEmptyDomofonCams = false
                    fillingMapCams()
                }
            }

            "Офисы" -> {
                if (!_isEmptyOffice) {
                    _mapOffice.value = emptyList()
                    _isEmptyOffice = true
                } else {
                    _isEmptyOffice = false
                    fillingMapCams()
                }
            }
        }
    }

    private fun fillingMapCams() {
        if (!_isEmptyOutDoorCams) { // очередь первая - 1й слой наложения на карте
            _mapOutDoorCams.value = emptyList()
            _data.value?.let {
                _mapOutDoorCams.value = it.mapMarkers.outdoorCams.markers
            }
        }
        if (!_isEmptyDomofonCams) { // очередь вторая - 2й слой наложения на карте
            _mapDomofonCams.value = emptyList()
            _data.value?.let {
                _mapDomofonCams.value = it.mapMarkers.domofonCams.markers
            }
        }
        if (!_isEmptyCityCams) { // очередь третья - 3й слой наложения на карте
            _mapCityCams.value = emptyList()
            _data.value?.let {
                _mapCityCams.value = it.mapMarkers.cityCams.markers
            }
        }
        if (!_isEmptyOffice) { // очередь четвертая - 4й слой наложения на карте
            _mapOffice.value = emptyList()
            _data.value?.let {
                _mapOffice.value = it.mapMarkers.officeCams.markers
            }
        }
    }

    fun clickCountZoom(one: String) {
        if (one.toInt() > 0) {
            _countZoom.value = _cZoom++
        } else {
            _countZoom.value = _cZoom--
        }
    }

    fun method(i: Int, center: String) {
        _countMethod.value = i
    }
}
