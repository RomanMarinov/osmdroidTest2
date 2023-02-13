package com.dev_marinov.osmdroidtest.presentation.cameraDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.osmdroidtest.domain.IRepository
import com.dev_marinov.osmdroidtest.domain.model.*
import com.dev_marinov.osmdroidtest.presentation.model.CameraDialog
import com.dev_marinov.osmdroidtest.presentation.model.CameraNavigationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class CameraDialogViewModel @Inject constructor(private val iRepository: IRepository) :
    ViewModel() {

    private var _cameraDialog: MutableStateFlow<CameraDialog> =
        MutableStateFlow(CameraDialog())
    val cameraDialog: StateFlow<CameraDialog> = _cameraDialog

    private var _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data> = _data

    private var _checkEmpty: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var checkEmpty: StateFlow<Boolean> = _checkEmpty

    private lateinit var _cameraName: CameraNavigationArgs

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val job = launch {
                val data = iRepository.getData()
                data?.let {
                    _data.postValue(it)
                }
            }
            job.join()

            getCamInfo(_cameraName.cameraName, _cameraName.cameraType, _cameraName.address)
        }
    }

    fun getNavigationArgs(args: CameraNavigationArgs) {
        _cameraName = args
    }

    private fun getCamInfo(cameraName: String?, cameraType: @RawValue Any?, address: String) {

        when (cameraType) {
            is MarkerCityCam -> {
                val list = _data.value?.mapMarkers?.cityCams?.markers
                list?.let {
                    for (item in it.indices) {
                        if (it[item].additional.cameraName == cameraName) {
                            val title = _data.value?.mapMarkers?.cityCams?.markers?.get(item)?.title
                            val previewUrl =
                                _data.value?.mapMarkers?.cityCams?.markers?.get(item)?.additional?.previewUrl

                            _cameraDialog.value = CameraDialog(
                                titleType = cameraType,
                                titleAddress = title,
                                previewUrl = previewUrl,
                                worktime = "",
                                visible = ""
                            )
                        }
                    }
                } ?: checkIsEmpty()
            }
            is MarkerOutdoorCam -> {
                val list = _data.value?.mapMarkers?.outdoorCams?.markers
                list?.let {
                    for (item in list.indices) {
                        if (list[item].additional.cameraName == cameraName) {
                            val title =
                                _data.value?.mapMarkers?.outdoorCams?.markers?.get(item)?.title
                            val previewUrl =
                                _data.value?.mapMarkers?.outdoorCams?.markers?.get(item)?.additional?.previewUrl
                            _cameraDialog.value = CameraDialog(
                                titleType = cameraType,
                                titleAddress = title,
                                previewUrl = previewUrl,
                                worktime = "",
                                visible = ""
                            )
                        }
                    }
                } ?: checkIsEmpty()
            }
            is MarkerOffice -> {
                val list = _data.value?.mapMarkers?.officeCams?.markers
                list?.let {
                    for (item in list.indices) {
                        if (list[item].additional.address == address) {
                            val title =
                                _data.value?.mapMarkers?.officeCams?.markers?.get(item)?.additional?.address
                            val workTime =
                                _data.value?.mapMarkers?.officeCams?.markers?.get(item)?.additional?.worktime
                            val visible =
                                _data.value?.mapMarkers?.officeCams?.markers?.get(item)?.additional?.phone?.visible
                            _cameraDialog.value = CameraDialog(
                                titleType = cameraType,
                                titleAddress = title,
                                previewUrl = "",
                                worktime = workTime,
                                visible = visible
                            )
                        }
                    }
                } ?: checkIsEmpty()
            }
            is MarkerDomofonCam -> {
                val list = _data.value?.mapMarkers?.domofonCams?.markers
                list?.let {
                    for (item in list.indices) {
                        if (list[item].title == address) {
                            val title =
                                _data.value?.mapMarkers?.domofonCams?.markers?.get(item)?.title
                            _cameraDialog.value = CameraDialog(
                                titleType = cameraType,
                                titleAddress = title,
                                previewUrl = "",
                                worktime = "",
                                visible = ""
                            )
                        }
                    }
                } ?: checkIsEmpty()
            }
        }
    }

    private fun checkIsEmpty() {
        _checkEmpty.value = true
    }
}