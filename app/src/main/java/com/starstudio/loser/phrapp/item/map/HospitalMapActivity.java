package com.starstudio.loser.phrapp.item.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BikingRouteOverlay;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;
import java.util.List;

public class HospitalMapActivity extends AppCompatActivity {

    private static final String TAG = "HospitalMapActivity";
    public LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private MapView mapView;
    private BaiduMap baiduMap;
    private PoiSearch mPoiSearch;
    private RoutePlanSearch mRoutePlanSearch;
    private boolean isFirstLocation = true;
    private LinearLayout linearLayout;
    private LatLng latLng;
    private String city;
    private BikingRouteOverlay overlay;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_hospital_map);
        getPermissions();

        initView();
        mapView = (MapView) findViewById(R.id.baidumap);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showRoute(marker);
                Toast.makeText(HospitalMapActivity.this,"已经为您规划好了路线！",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void initView(){
        linearLayout = (LinearLayout) View.inflate(this,R.layout.infowindow_layout,null);
        toolbar = findViewById(R.id.phr_hospital_map_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getPermissions(){
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(HospitalMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(HospitalMapActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(HospitalMapActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(HospitalMapActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        //**********
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);              //打开GPS
        option.setCoorType("bd09ll");        //设置坐标类型
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        //**********可以使定位更准确
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //option.setScanSpan(2000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
//            StringBuilder currentPosition = new StringBuilder();
//            currentPosition.append(location.getCity());
            latLng = new LatLng(location.getLatitude(),location.getLongitude());
            city = location.getCity();
            Log.d(TAG, "onReceiveLocation: 位置信息"+location.getCity()+"   "+location.getLatitude()+"  "+location.getLongitude()+" "+location.getAddrStr());
            if (location.getLocType() == BDLocation.TypeGpsLocation||location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);
                initPOI();
                initRoute();
            }
        }
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocation){
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(15f);
            baiduMap.animateMapStatus(update);
            isFirstLocation = false;
            Log.d(TAG, "navigateTo: ***************");
        }
        MyLocationData.Builder builder=new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData locationData=builder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void initPOI(){
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
            @Override
            public void onGetPoiResult(PoiResult poiResult){
                //获取POI检索结果
                if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(HospitalMapActivity.this, "没有搜索内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                for (PoiInfo info : allPoi) {
                    MarkerOptions overlay = new MarkerOptions();
                    overlay.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_maker));
                    overlay.position(info.location);
                    Marker marker = (Marker)baiduMap.addOverlay(overlay);
                    //Log.d("tag", "info: " + info.location);
                    Bundle bundle = new Bundle();
                    bundle.putString("arr", info.address);
                    bundle.putString("phone", info.phoneNum);
                    bundle.putString("name", info.name);
                    bundle.putDouble("latitude",info.location.latitude);
                    bundle.putDouble("longitude",info.location.longitude);
                    marker.setExtraInfo(bundle);
                }
            }
            @Override
            public void onGetPoiDetailResult(PoiDetailResult result){
                //获取Place详情页检索结果
            }
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                //获取门址类列表
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword("医院")
                .radius(6000)//检索半径范围，单位：米
                .location(latLng));
    }

    private void initRoute(){
        mRoutePlanSearch=RoutePlanSearch.newInstance();
        mRoutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //TODO 步行回调
                return;
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                //TODO 公交回调
                return;
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
                return;
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                //TODO 驾车回调
                Log.d("TAG","result_biking:"+drivingRouteResult.error);
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(HospitalMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                //TODO
                Log.d("TAG","result_biking:"+bikingRouteResult.error);
                if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(HospitalMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    //return ;
                }
                if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //Log.d(TAG, "onGetBikingRouteResult: " + bikingRouteResult.getSuggestAddrInfo());
                    return;
                }
                if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (overlay!=null)
                        overlay.removeFromMap();
                    overlay = new BikingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(bikingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }
        });
    }

    private void showRoute(Marker marker){
        Bundle info= marker.getExtraInfo();
        if (info != null) {
            Log.d("TAG", "name" + info.getString("name"));
            TextView tv = linearLayout.findViewById(R.id.map_content);
            //TextView tv = new TextView(getApplication());
            tv.setText(info.getString("name"));
            InfoWindow infoWindow = new InfoWindow(linearLayout, marker.getPosition(), 60);
            baiduMap.showInfoWindow(infoWindow);
            PlanNode start = PlanNode.withLocation(latLng);
            PlanNode end = PlanNode.withLocation(new LatLng(info.getDouble("latitude"), info.getDouble("longitude")));
            Log.d(TAG, "onMarkerClick: " + latLng.latitude + "  " + latLng.longitude + "    " + city + info.getString("arr"));
            BikingRoutePlanOption options = new BikingRoutePlanOption()
                    .from(start)
                    .to(end);
            mRoutePlanSearch.bikingSearch(options);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        mPoiSearch.destroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
