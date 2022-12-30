package com.dev_marinov.osmdroidtest.presentation.activity

import androidx.lifecycle.ViewModel
import com.dev_marinov.osmdroidtest.domain.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val iRepository: IRepository) : ViewModel() {

//    private var _categories: MutableLiveData<List<String>> = MutableLiveData()
//    var categories: LiveData<List<String>> = _categories
//
//    private var _data: MutableLiveData<Data> = MutableLiveData()
//    var data: LiveData<Data> = _data
//
//    private var _titleLocations: MutableLiveData<List<String>> = MutableLiveData()
//    var titleLocations: LiveData<List<String>> = _titleLocations
//
//    init {
//        getData()
//        getCategories()
//    }
//
//    private fun getCategories() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val categories = iRepository.getCategories()
//            _categories.postValue(categories)
//        }
//    }
//
//    private fun getData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val data = iRepository.getData()
//
//            Log.d("4444", " data=" + data)
//            data?.let {
//                _data.postValue(it)
//            }
//
//            delay(50)
//            getTitleLocations()
//        }
//    }
//
//    private fun getTitleLocations() {
//        val list: MutableList<String> = ArrayList()
//        val result = _data.value?.locations
//        result?.let {
//            for (item in it) {
//                list.add(item.title)
//            }
//            _titleLocations.postValue(list)
//        }
//    }
//
//    fun onClick(category: String) {
//
//    }
}




