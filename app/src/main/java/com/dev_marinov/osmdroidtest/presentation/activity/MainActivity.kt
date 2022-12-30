package com.dev_marinov.osmdroidtest.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dev_marinov.osmdroidtest.R
import com.dev_marinov.osmdroidtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.d("4444", " MainActivity loaded")

//        setCategories()
//        setSpinner(viewModel, binding, this)
//        setMap()


        }

//    private fun setCategories() {
//
//        val adapter = CategoryAdapter(viewModel::onClick)
//        binding.recyclerview.apply {
//            this.adapter = adapter
//        }
//
//        viewModel.categories.observe(this, Observer {
//            adapter.submitList(it)
//        })
//
//
//    }
//
//    private fun setMap() {
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            requestPermissions(
////                arrayOf(
////                    // если вам нужно показать текущее местоположение, раскомментируйте строку ниже
////                     Manifest.permission.ACCESS_FINE_LOCATION,
////                    // WRITE_EXTERNAL_STORAGE требуется для отображения карты
////                    Manifest.permission.WRITE_EXTERNAL_STORAGE
////                )
////            )
////        }
//
//// Затем мы добавляем кнопки масштабирования по умолчанию и возможность масштабирования двумя пальцами (мультитач).
//        binding.mapView.setBuiltInZoomControls(true);
//        binding.mapView.setMultiTouchControls(true)
//
//// Как добавить оверлей «Мое местоположение»
//        val mGpsMyLocationProvider = GpsMyLocationProvider(this)
//        val mLocationOverlay = MyLocationNewOverlay(mGpsMyLocationProvider, binding.mapView)
//        mLocationOverlay.enableMyLocation()
//        binding.mapView.overlays.add(mLocationOverlay)
//
//// Как добавить наложение компаса
//        val mCompassOverlay = CompassOverlay(this, InternalCompassOrientationProvider(this),
//            binding.mapView
//        )
//        mCompassOverlay.enableCompass()
//        binding.mapView.overlays.add(mCompassOverlay)
//
//// Как включить жесты вращения
//        val mRotationGestureOverlay = RotationGestureOverlay(this, binding.mapView)
//        mRotationGestureOverlay.isEnabled = false
//        binding.mapView.overlays.add(mRotationGestureOverlay)
//
////
////
////
////
////
////
////        // binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
////        val mapController = binding.mapView.controller
////        mapController.setZoom(12)
////        val startPoint = GeoPoint(59.130156, 39.532724);
////        mapController.setCenter(startPoint);
////
//
//
//    //
////
////        // Мы можем переместить карту на точку обзора по умолчанию. Для этого нам нужен доступ к контроллеру карты:
////        val mapController: IMapController = binding.mapView.controller
////        mapController.setZoom(9.5)
////
////        val startPoint = GeoPoint(59.130156, 39.532724)
////        mapController.animateTo(startPoint)
////        addMarker(center = startPoint)
//////        mapController.setCenter(startPoint)
//
/////////////////////////////////////////////////////
////        val ctx = this.applicationContext
////        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
////        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID;
////        binding.mapView.setUseDataConnection(true)
////        //val map = view.findViewById(R.id.map) as MapView
////        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)
////        //map.setBuiltInZoomControls(true) //Map ZoomIn/ZoomOut Button Visibility
////        binding.mapView.setMultiTouchControls(true)
////        val mapController: IMapController
////        mapController = binding.mapView.getController()
////
//////mapController.zoomTo(14, 1)
////        mapController.setZoom(14)
////
////        val mGpsMyLocationProvider = GpsMyLocationProvider(this)
////        val mLocationOverlay = MyLocationNewOverlay(mGpsMyLocationProvider, binding.mapView)
////        mLocationOverlay.enableMyLocation()
////        mLocationOverlay.enableFollowLocation()
////
////        val icon = BitmapFactory.decodeResource(resources, R.drawable.icon_android)
////        mLocationOverlay.setPersonIcon(icon)
////        binding.mapView.overlays.add(mLocationOverlay)
////
////        mLocationOverlay.runOnFirstFix {
////            binding.mapView.overlays.clear()
////            binding.mapView.overlays.add(mLocationOverlay)
////            mapController.animateTo(mLocationOverlay.myLocation)
////        }
//
//        viewModel.mapCityCams.observe(viewLifecycleOwner) {
//            //        Как разместить значки на карте с помощью прослушивателя кликов
//            val items = ArrayList<OverlayItem>()
//            // // Lat/Lon decimal degrees
//            for (item in it) {
//                items.map { overlayItem -> overlayItem.setMarker(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.icon_camera_map, null)) }
//                items.add(OverlayItem(item.title, "Descr", GeoPoint(item.marker.latitude.toDouble(), item.marker.longitude.toDouble())))
//
//
//                binding.mapView.isTilesScaledToDpi = true
//
////                val geoPoint = GeoPoint(item.marker.latitude.toDouble(), item.marker.longitude.toDouble())
////                val points: MutableList<GeoPoint> = ArrayList()
////                val startMarker = Marker(binding.mapView)
////                points.add(geoPoint)
////                startMarker.position = geoPoint
////                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
////                startMarker.icon = resources.getDrawable(R.drawable.icon_camera_map)
////                startMarker.title = "Pentagon"
////                startMarker.snippet = "The Pentagon."
////                startMarker.subDescription =
////                    "The Pentagon is the headquarters of the United States Department of Defense."
////                startMarker.setOnMarkerClickListener { marker, mapView ->
////                    marker.showInfoWindow()
////                    true
////                }
////                binding.mapView.overlays.add(startMarker)
//
//            }
//
//
//            ////////////////////////////
////            for (i in 0 until markers.length()) {
////                val marker: JSONObject = markers.getJSONObject(i)
////                m.setPosition(GeoPoint(marker.getDouble("lat"), marker.getDouble("lon")))
////                m.setTitle(marker.getString("data") + " @ " + marker.getString("date"))
////                m.setIcon(resources.getDrawable(R.drawable.action_item_read))
////                m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
////                map.getOverlays().add(m)
////            }
////
//
//
//
//
//            //////////////////////////////
//
//
//            val mOverlay = ItemizedOverlayWithFocus(
//                requireContext(),
//                items,
//                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
//
//
//                    override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
//                        //do something
//                        return true
//                    }
//
//                    override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
//
//
//                        return false
//                    }
//                }
//            )
//            mOverlay.setFocusItemsOnTap(true)
//
//            binding.mapView.overlays.add(mOverlay)
//            binding.mapView.setHasTransientState(true)
//
//
////            ////////////////////////////////////
////            private class MyItemizedIconOverlay(
////                pList: List<OverlayItem?>?, pOnItemGestureListener: OnItemGestureListener<OverlayItem?>?,
////                pResourceProxy: ResourceProxy?
////            ) :
////                ItemizedIconOverlay<OverlayItem?>(pList, pOnItemGestureListener, pResourceProxy) {
////                override fun draw(canvas: Canvas, mapview: MapView, arg2: Boolean) {
////                    super.draw(canvas, mapview, arg2)
////                    if (!overlayItemArray.isEmpty()) {
////                        //overlayItemArray have only ONE element only, so I hard code to get(0)
////                        val `in`: GeoPoint = overlayItemArray.get(0).getPoint()
////                        val out = Point()
////                        mapview.getProjection().toPixels(`in`, out)
////                        val bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)
////                        canvas.drawBitmap(bm, out.x - bm.width / 2, out.y - bm.height / 2, null)
////                    }
////                }
////
////                override fun onSingleTapUp(event: MotionEvent?, mapView: MapView?): Boolean {
////                    //return super.onSingleTapUp(event, mapView);
////                    return true
////                }
////
////                override fun onSingleTapConfirmed(event: MotionEvent?, mapView: MapView?): Boolean {
////                    return true
////                }
////            }
////
//
//
//
//
//        }
//
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        val permissionsToRequest: ArrayList<String?> = ArrayList()
//        for (i in grantResults.indices) {
//            permissionsToRequest.add(permissions[i])
//        }
//        if (permissionsToRequest.size > 0) {
//            requestPermissions(
//                permissionsToRequest.toArray(arrayOfNulls(0)),
//                REQUEST_PERMISSIONS_REQUEST_CODE
//            )
//        }
//    }
//    // REQUEST_PERMISSIONS_REQUEST_CODE
//
//    private fun requestPermissions(permissions: Array<String>) {
//        val permissionsToRequest: ArrayList<String> = ArrayList()
//        for (permission in permissions) {
//            if (ContextCompat.checkSelfPermission(this, permission)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                // Permission is not granted
//                permissionsToRequest.add(permission)
//            }
//        }
//        if (permissionsToRequest.size > 0) {
//            ActivityCompat.requestPermissions(
//                this,
//                permissionsToRequest.toArray(arrayOfNulls(0)),
//                REQUEST_PERMISSIONS_REQUEST_CODE
//            )
//        }
//// Как добавить наложение шкалы масштаба карты
//        val context: Context = this;
//        val dm: DisplayMetrics = context.resources.displayMetrics;
//        val mScaleBarOverlay = ScaleBarOverlay(binding.mapView);
//        mScaleBarOverlay.setCentred(true);
////play around with these values to get the location on screen in the right place for your application
//        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
//        binding.mapView.overlays.add(mScaleBarOverlay);
//    }
//
//    override fun onResume() {
//        super.onResume()
//        // это обновит конфигурацию osmdroid при возобновлении работы.
//        //если вы вносите изменения в конфигурацию, используйте
//        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
//
//        Configuration.getInstance().load(
//            this,
//            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
//        )
//        binding.mapView.onResume() //needed for compass, my location overlays, v6.0.0 and up
//    }
//
//    override fun onPause() {
//        super.onPause()
//        // это обновит конфигурацию osmdroid при возобновлении работы.
//        //если вы вносите изменения в конфигурацию, используйте
//        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        //Configuration.getInstance().save(this, prefs);
//        binding.mapView.onPause() //needed for compass, my location overlays, v6.0.0 and up
//    }
//
//
//    private fun addMarker(center: GeoPoint) {
//        val marker = Marker(binding.mapView)
//        marker.position = GeoPoint(center)
//        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//       // marker.icon = this.resources.getDrawable(R.drawable.icon_android);
//
//        binding.mapView.overlays.clear()
//        binding.mapView.overlays.add(marker);
//        binding.mapView.invalidate()
//
//    }
//
//
//    private fun setSpinner(
//        viewModel: MainViewModel,
//        binding: ActivityMainBinding,
//        mainActivity: MainActivity
//    ) {
//        viewModel.titleLocations.observe(mainActivity, Observer {
//            val adapter = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_item, it)
//            binding.spinner.adapter = adapter
//        })
//
//        binding.spinner.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View, position: Int, id: Long
//            ) {
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//
//        }
//    }


}





