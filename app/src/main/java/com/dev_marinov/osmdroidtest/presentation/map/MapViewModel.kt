package com.dev_marinov.osmdroidtest.presentation.map

import androidx.lifecycle.*
import com.dev_marinov.osmdroidtest.data.Constants
import com.dev_marinov.osmdroidtest.domain.IRepository
import com.dev_marinov.osmdroidtest.domain.model.*
import com.dev_marinov.osmdroidtest.presentation.model.GeoPointScreen
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

    private var _currentZoomNew: MutableStateFlow<GeoPointScreen> = MutableStateFlow(GeoPointScreen(
        zoom = Constants.DefaultGeoPointScreen.ZOOM,
        location = Constants.DefaultGeoPointScreen.LOCATION
    ))
    val currentZoomNew: StateFlow<GeoPointScreen> = _currentZoomNew

    private var _isEmptyCityCams = true
    private var _isEmptyDomofonCams = true
    private var _isEmptyOutDoorCams = true
    private var _isEmptyOffice = true

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

    private suspend fun getMapCategories() {
        delay(100)
        // ?? ???????? ???? ???????????? ???????????????? json ???????????????? ?????????????? ??????
        val list: MutableList<CityCam> = mutableListOf()
        _data.value?.let {
            list.add(0, CityCam(it.mapMarkers.cityCams.title, it.mapMarkers.cityCams.count))
            list.add(1, CityCam(it.mapMarkers.outdoorCams.title, it.mapMarkers.outdoorCams.count))
            list.add(2, CityCam(it.mapMarkers.domofonCams.title, it.mapMarkers.domofonCams.count))
            list.add(3, CityCam(it.mapMarkers.officeCams.title, it.mapMarkers.officeCams.count))
            _mapCategories.postValue(list)
        }
    }

    private suspend fun getLocationTitle() {
        delay(100)
        val list: MutableList<String> = mutableListOf()
        val data = _data.value?.locations
        data?.map { list.add(it.title) }
        _locationsTitle.postValue(list)
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
            "?????????????????? ????????????" -> {
                if (!_isEmptyCityCams) {
                    _mapCityCams.value = emptyList()
                    _isEmptyCityCams = true
                } else {
                    _isEmptyCityCams = false
                    fillingMapCams()
                }
            }

            "???????????????? ????????????" -> {
                if (!_isEmptyOutDoorCams) {
                    _mapOutDoorCams.value = emptyList()
                    _isEmptyOutDoorCams = true
                } else {
                    _isEmptyOutDoorCams = false
                    fillingMapCams()
                }
            }

            "????????????????" -> {
                if (!_isEmptyDomofonCams) {
                    _mapDomofonCams.value = emptyList()
                    _isEmptyDomofonCams = true
                } else {
                    _isEmptyDomofonCams = false
                    fillingMapCams()
                }
            }

            "??????????" -> {
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
        if (!_isEmptyOutDoorCams) { // ?????????????? ???????????? - 1?? ???????? ?????????????????? ???? ??????????
            _mapOutDoorCams.value = emptyList()
            _data.value?.let {
                _mapOutDoorCams.value = it.mapMarkers.outdoorCams.markers
            }
        }
        if (!_isEmptyDomofonCams) { // ?????????????? ???????????? - 2?? ???????? ?????????????????? ???? ??????????
            _mapDomofonCams.value = emptyList()
            _data.value?.let {
                _mapDomofonCams.value = it.mapMarkers.domofonCams.markers
            }
        }
        if (!_isEmptyCityCams) { // ?????????????? ???????????? - 3?? ???????? ?????????????????? ???? ??????????
            _mapCityCams.value = emptyList()
            _data.value?.let {
                _mapCityCams.value = it.mapMarkers.cityCams.markers
            }
        }
        if (!_isEmptyOffice) { // ?????????????? ?????????????????? - 4?? ???????? ?????????????????? ???? ??????????
            _mapOffice.value = emptyList()
            _data.value?.let {
                _mapOffice.value = it.mapMarkers.officeCams.markers
            }
        }
    }

    fun clickCountZoom(zoom: Double, getLocationScreen: String) {
        _currentZoomNew.value = GeoPointScreen(zoom = zoom, location = getLocationScreen)
    }


}
