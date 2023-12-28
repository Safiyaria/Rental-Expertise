package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mapView extends AppCompatActivity implements OnMapReadyCallback {


    MapView mapView;

    Point dhaka = Point.fromLngLat(90.4093106446669, 23.744399534278724);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.app_token));

        setContentView(R.layout.activity_map_view);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override

            public void onStyleLoaded(@NonNull Style style) {

                CameraPosition position = new CameraPosition.Builder().target(new LatLng(dhaka.latitude(), dhaka.longitude()))
                        .zoom(13).tilt(13).build();

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);

                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {

                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {

                        mapboxMap.clear();
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(point);
                        markerOptions.title("Location");
                        mapboxMap.addMarker(markerOptions);

                        MapboxGeocoding reverseGeocode = MapboxGeocoding.builder()
                                .accessToken("pk.eyJ1Ijoic2FmaXlhMDMiLCJhIjoiY2t1ZWhneWxvMTExbTJvb2Jpbmc0enBvbyJ9.4UiV9kXrwwr45b4N8XB72g")
                                .query(Point.fromLngLat(point.getLongitude(), point.getLatitude()))
                                .geocodingTypes(GeocodingCriteria.TYPE_POI)
                                .build();

                        reverseGeocode.enqueueCall(new Callback<GeocodingResponse>() {
                            @Override
                            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                                List<CarmenFeature> results = response.body().features();

                                if (results.size() > 0) {

                                    CarmenFeature feature;

                                    Point firstResultPoint = results.get(0).center();
                                    for (int i = 0; i < results.size(); i++) {
                                        feature = results.get(i);

                                        Toast.makeText(mapView.this, "" + feature.placeName(), Toast.LENGTH_LONG).show();

                                    }
                                    Log.d("MyActivity", "onResponse: " + firstResultPoint.toString());

                                } else {

                                    Toast.makeText(mapView.this, "Not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
                        return true;
                    }

                });
            }
        });
    }
}