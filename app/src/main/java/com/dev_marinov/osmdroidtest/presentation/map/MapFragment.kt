package com.dev_marinov.osmdroidtest.presentation.map

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.*
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_marinov.osmdroidtest.R
import com.dev_marinov.osmdroidtest.databinding.FragmentMapBinding
import com.dev_marinov.osmdroidtest.domain.model.*
import com.dev_marinov.osmdroidtest.presentation.model.CameraNavigationArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.*
import java.lang.Math.abs
import java.util.*


// https://github.com/osmdroid/osmdroid/wiki/

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MapFragment : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var binding: FragmentMapBinding
    lateinit var viewModel: MapViewModel

    lateinit var markerCityTriangle: Marker

    private var listMarkersCityCam: MutableList<Marker> = mutableListOf()
    private var listMarkersCityTriangle: MutableList<Marker> = mutableListOf()
    private var listCityCam: MutableList<MarkerCityCam> = mutableListOf()

    private var listMarkersDomofon: MutableList<Marker> = mutableListOf()
    private var listDomofonCam: MutableList<MarkerDomofonCam> = mutableListOf()

    private var listMarkersOutDoor: MutableList<Marker> = mutableListOf()
    private var listOutDoorCam: MutableList<MarkerOutdoorCam> = mutableListOf()

    private var listMarkersOffice: MutableList<Marker> = mutableListOf()
    private var listOffice: MutableList<MarkerOffice> = mutableListOf()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val PERMISSION_REQUESTS_ACCESS_LOCATION = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("4444 ", " MapFragment loaded")

        initViewModel()
        initFusedLocationProvider()
        setMapCategories()
        setMapSpinner()
        setMapView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
    }

    private fun initFusedLocationProvider() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun setMapCategories() {
        val adapter = MapCategoriesAdapter(viewModel::onClickCategory)
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.mapCategories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setMapSpinner() {
        viewModel.locationsTitle.observe(viewLifecycleOwner) {
            it?.let { list ->
                usingArrayAdapter(list)
            }
        }
    }

    private fun usingArrayAdapter(locations: List<String>) {
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.code_spinner_layout, R.id.textView, locations)
        binding.codeSpinner.adapter = arrayAdapter
//        binding.codeSpinner.setPopupBackgroundDrawable(ResourcesCompat.getDrawable(binding.root.resources, R.color.transparent_item_map_info, null))
        binding.codeSpinner.setPopupBackgroundDrawable(
            ResourcesCompat.getDrawable(
                binding.root.resources,
                R.drawable.background_views_map,
                null
            )
        )

        binding.codeSpinner.setSelection(11)

        binding.codeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.onClick(position)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("4444 ", " onNothingSelected")
            }
        }
    }


    private fun setMapView() {
        // binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    // если вам нужно показать текущее местоположение, раскомментируйте строку ниже
                    // Manifest.permission.ACCESS_FINE_LOCATION,
                    // WRITE_EXTERNAL_STORAGE требуется для отображения карты
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }

// Максимальный зум карты
        binding.mapView.maxZoomLevel = 20.0
        binding.mapView.minZoomLevel = 6.0

//        binding.mapView.setBuiltInZoomControls(true); // включить +-
        binding.mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER) // не использовать +-
        binding.mapView.setMultiTouchControls(true) // масштабирование двумя пальцами

        binding.mapView.isTilesScaledToDpi = true

//        // Как добавить наложение шкалы масштаба карты
//        val context: Context = requireContext()
//        val dm: DisplayMetrics = context.resources.displayMetrics
//        val mScaleBarOverlay = ScaleBarOverlay(binding.mapView)
//        mScaleBarOverlay.setCentred(true)
//
////поэкспериментируйте с этими значениями, чтобы получить местоположение на экране в нужном месте для вашего приложения
//        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
//        binding.mapView.overlays.add(mScaleBarOverlay);

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.setLocation.collect { Location ->
                    Location?.let {
                        val leftTopLat: Double = it.leftTopLat.toDouble()
                        val leftTopLon: Double = it.leftTopLon.toDouble()
                        val rightBottomLat: Double = it.rightBottomLat.toDouble()
                        val rightBottomLon: Double = it.rightBottomLon.toDouble()

                        val squareABS =
                            (abs(leftTopLat - rightBottomLat) * abs(leftTopLon - rightBottomLon)) * 1000
                        if (squareABS >= 8) {
                            setZoomLocation(13.0, it.center)
                        } else {
                            setZoomLocation(16.0, it.center)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentZoomNew.collect { geoPointScreen ->
                    if (geoPointScreen.zoom != 0.0) {
                        setZoomLocation(geoPointScreen.zoom, geoPointScreen.location)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mapOutDoorCams.collect {
                    var markerOutDoorCam: Marker? = null
                    for (outDoorCam in it) {


                        markerOutDoorCam = Marker(binding.mapView) // маркер камеры

                        val geoPoint = GeoPoint(outDoorCam.latitude, outDoorCam.longitude)
                        val iconId = resources.getDrawable(R.drawable.ic_map_outdoor)
                        val title = outDoorCam.title

                        setMarkerParams(
                            marker = markerOutDoorCam,
                            geoPoint = geoPoint,
                            iconId = iconId,
                            title = title
                        )

                        binding.mapView.overlays.add(markerOutDoorCam)
                        binding.mapView.invalidate() // перерисовка из главного потока // postInvalidate из фонового

                        showInfoMarkerWindow(markerOutDoorCam, outDoorCam)

                        listMarkersOutDoor.add(markerOutDoorCam) // список для только для удаления маркеров markerCams
                        listOutDoorCam.add(outDoorCam)
                    }

                    closeInfoMarkerWindow(markerOutDoorCam)
                    removeMarkers(it, listMarkersOutDoor)

                    setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mapDomofonCams.collect {
                    var markerDomofonCam: Marker? = null
                    for (domofonCam in it) {
                        markerDomofonCam = Marker(binding.mapView) // маркер камеры

                        val geoPoint = GeoPoint(domofonCam.latitude, domofonCam.longitude)
                        val iconId = resources.getDrawable(R.drawable.ic_map_domofon)
                        val title = domofonCam.title

                        setMarkerParams(
                            marker = markerDomofonCam,
                            geoPoint = geoPoint,
                            iconId = iconId,
                            title = title
                        )

                        binding.mapView.overlays.add(markerDomofonCam)
                        binding.mapView.invalidate() // перерисовка из главного потока // postInvalidate из фонового

                        showInfoMarkerWindow(markerDomofonCam, domofonCam)

                        listMarkersDomofon.add(markerDomofonCam) // список для только для удаления маркеров markerCams
                        listDomofonCam.add(domofonCam)
                    }

                    closeInfoMarkerWindow(markerDomofonCam)
                    removeMarkers(it, listMarkersDomofon)

                    setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mapCityCams.collect {
                    var markerCityCam: Marker? = null
                    for (cityCam in it) {

                        markerCityCam = Marker(binding.mapView) // маркер камеры

                        val geoPoint = GeoPoint(cityCam.latitude, cityCam.longitude)
                        val iconId = resources.getDrawable(R.drawable.ic_map_city_cam)
                        val title = cityCam.title

                        setMarkerParams(
                            marker = markerCityCam,
                            geoPoint = geoPoint,
                            iconId = iconId,
                            title = title
                        )

                        markerCityTriangle =
                            Marker(binding.mapView) // маркер угла // маркер угла должен быть первый добавлен

                        binding.mapView.overlays.add(markerCityTriangle)
                        // маркер камер должен быть вторым установлен (т.е. поверх первого)
                        binding.mapView.overlays.add(markerCityCam)
                        binding.mapView.invalidate() // перерисовка из главного потока // postInvalidate из фонового

                        showInfoMarkerWindow(markerCityCam, cityCam)

                        listMarkersCityCam.add(markerCityCam) // список для только для удаления маркеров markerCams
                        listMarkersCityTriangle.add(markerCityTriangle)
                        listCityCam.add(cityCam)
                    }

                    closeInfoMarkerWindow(markerCityCam)

                    removeMarkers(it, listMarkersCityCam)
                    removeMarkers(it, listMarkersCityTriangle)

                    setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mapOffice.collect {
                    var markerOffice: Marker? = null
                    for (office in it) {

                        markerOffice = Marker(binding.mapView) // маркер камеры

                        val geoPoint = GeoPoint(office.latitude, office.longitude)
                        val iconId = resources.getDrawable(R.drawable.ic_map_office)
                        val title = office.title

                        setMarkerParams(
                            marker = markerOffice,
                            geoPoint = geoPoint,
                            iconId = iconId,
                            title = title
                        )

                        binding.mapView.overlays.add(markerOffice)
                        binding.mapView.invalidate() // перерисовка из главного потока // postInvalidate из фонового

                        showInfoMarkerWindow(markerOffice, office)

                        listMarkersOffice.add(markerOffice) // список для только для удаления маркеров markerCams
                        listOffice.add(office)
                    }

                    closeInfoMarkerWindow(markerOffice)
                    removeMarkers(it, listMarkersOffice)

                    setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
                }
            }
        }

        binding.mapView.setMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent?): Boolean {
                return false
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                event?.let {
                    val currentZoom = it.zoomLevel // от 9 до 21 где 9-высоко
                    setIconMarkerMap(currentZoom)
                }
                return false
            }
        })

        binding.imgFindMe.setOnClickListener {
            getLocation()
        }

        binding.imgZoomPlus.setOnClickListener {
            viewModel.clickCountZoom(getCurrentZoom(1.0), getLocationScreen())
            binding.mapView.invalidate()
        }
        binding.imgZoomMinus.setOnClickListener {
            viewModel.clickCountZoom(getCurrentZoom(-1.0), getLocationScreen())
            //binding.mapView.controller.zoomOut()
            binding.mapView.invalidate()
        }

        binding.mapView.invalidate()
    }

    private fun <T> showInfoMarkerWindow(markerCam: Marker, objectCam: T) {
        markerCam.setOnMarkerClickListener { marker, mapView ->
//            // реализовать только для cityCam и outDoorCam
            if (objectCam is MarkerCityCam) {
                moveToCameraDialogFragment(
                    CameraNavigationArgs(
                        cameraName = objectCam.additional.cameraName,
                        cameraType = objectCam,
                        address = "",
                        worktime = "",
                        visible = ""
                    )
                )
            }
            if (objectCam is MarkerOutdoorCam) {
                moveToCameraDialogFragment(
                    CameraNavigationArgs(
                        cameraName = objectCam.additional.cameraName,
                        cameraType = objectCam,
                        address = "",
                        worktime = "",
                        visible = ""
                    )
                )
            }
            if (objectCam is MarkerOffice) {
                moveToCameraDialogFragment(
                    CameraNavigationArgs(
                        cameraName = "",
                        cameraType = objectCam,
                        address = objectCam.additional.address,
                        worktime = objectCam.additional.worktime,
                        visible = objectCam.additional.phone.visible
                    )
                )
            }
            if (objectCam is MarkerDomofonCam) {
                moveToCameraDialogFragment(
                    CameraNavigationArgs(
                        cameraName = "",
                        cameraType = objectCam,
                        address = objectCam.title,
                        worktime = "",
                        visible = ""
                    )
                )
            }
            //   marker.showInfoWindow()
            true
        }
    }

    private fun moveToCameraDialogFragment(cameraNavigationArgs: CameraNavigationArgs) {
        val action =
            MapFragmentDirections.actionMapFragmentToCameraDialogFragment(cameraNavigationArgs)
        findNavController().navigate(action)
    }

    private fun closeInfoMarkerWindow(markerCam: Marker?) {    // закрыть окно описания маркета
        binding.mapView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> markerCam?.closeInfoWindow()
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    private fun <T> removeMarkers(markerCams: List<T>, listMarkersCam: MutableList<Marker>) {
        if (markerCams.isEmpty()) {
            for (marker in listMarkersCam) {
                binding.mapView.overlays.remove(marker)
            }
            binding.mapView.invalidate() // разобраться с методом
            listMarkersCam.clear()
        }

    }

    private fun getCurrentZoom(one: Double): Double {
        var currentZoomNew = binding.mapView.zoomLevelDouble
        if (one > 0) {
            currentZoomNew += 1
        } else {
            currentZoomNew -= 1
        }
        return currentZoomNew
    }

    private fun getLocationScreen(): String {
        val valueLat = binding.mapView.mapCenter.latitude
        val valueLong = binding.mapView.mapCenter.longitude
        return "$valueLat, $valueLong"
    }

    private fun setIconMarkerMap(currentZoom: Double) {
        when (currentZoom) {
            in 12.0..13.9 -> {
                createMarkerTriangle(false)
            }
            in 14.0..15.9 -> {
                createMarkerTriangle(true)
            }
            in 16.0..21.9 -> {
                createMarkerTriangle(true)
            }
        }
    }

    private fun setMarkerParams(
        marker: Marker?,
        geoPoint: GeoPoint,
        iconId: Drawable,
        title: String
    ) {
        marker?.let {
            it.position = geoPoint
            it.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            it.icon = iconId
            it.title = title
//                marker.snippet = "The Pentagon."
//                marker.subDescription = "The Pentagon is ..."
        }
    }

    private fun createMarkerTriangle(bool: Boolean) {
        if (listMarkersCityTriangle.size != 0 && listCityCam.size != 0) {
            for (index in 0 until listMarkersCityTriangle.size) {
                val geoPointTriangle =
                    GeoPoint(listCityCam[index].latitude, listCityCam[index].longitude)
                listMarkersCityTriangle[index].position = geoPointTriangle

                val angle = listCityCam[index].angle.toFloat()

                listMarkersCityTriangle[index].rotation = angle * -1
                listMarkersCityTriangle[index].setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                listMarkersCityTriangle[index].setInfoWindow(null)

                if (bool) {
                    listMarkersCityTriangle[index].icon =
                        resources.getDrawable(R.drawable.ic_map_triangle)
                } else {
                    listMarkersCityTriangle[index].icon =
                        resources.getDrawable(R.drawable.ic_map_triangle_transparent)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest: ArrayList<String?> = ArrayList()
        for (i in grantResults.indices) {
            permissionsToRequest.add(permissions[i])
        }
        if (permissionsToRequest.size > 0) {
            requestPermissions(
                permissionsToRequest.toArray(arrayOfNulls(0)),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
        if (requestCode == PERMISSION_REQUESTS_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "удача", Toast.LENGTH_LONG).show()
                //getLocation()
            } else {
                Toast.makeText(requireContext(), "не удача", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>) {
        val permissionsToRequest: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toArray(arrayOfNulls(0)),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun setZoomLocation(zoom: Double, center: String) {
        val latitude = center.substring(0, center.indexOf(",")).toDouble()
        val longitude = center.substring(center.indexOf(" ")).trim().toDouble()

        // Мы можем переместить карту на точку обзора по умолчанию. Для этого нам нужен доступ к контроллеру карты:
        val mapController: IMapController = binding.mapView.controller
        val startPoint = GeoPoint(latitude, longitude)
        mapController.setZoom(zoom)
        mapController.setCenter(startPoint)
    }

    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result
                    if (location == null) {

                        Toast.makeText(requireContext(), "null", Toast.LENGTH_LONG).show()
                    } else {

                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        val currentLocation: String =
                            "" + location.latitude + ", " + location.longitude
//                        binding.imgFindMe.setOnClickListener {
//                        setLocation(13, currentLocation)
//                        }
//                        mainBinding.apply {
//                            tvLatitude.text = "Latitude\n${list[0].latitude}"
//                            tvLongitude.text = "Longitude\n${list[0].longitude}"
//                            tvCountryName.text = "Country Name\n${list[0].countryName}"
//                            tvLocality.text = "Locality\n${list[0].locality}"
//                            tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
//                        }

                        Log.d("4444", " currentLocation=" + currentLocation)
                        setZoomLocation(16.0, currentLocation)
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Включите определение место положения",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_REQUESTS_ACCESS_LOCATION
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        // это обновит конфигурацию osmdroid при возобновлении работы.
        //если вы вносите изменения в конфигурацию, используйте
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        Configuration.getInstance().load(
            requireContext(),
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        )
        binding.mapView.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        // это обновит конфигурацию osmdroid при возобновлении работы.
        //если вы вносите изменения в конфигурацию, используйте
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        binding.mapView.onPause() //needed for compass, my location overlays, v6.0.0 and up
    }

}