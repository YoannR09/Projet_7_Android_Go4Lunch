package com.example.go4lunch.dao;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.entity.RestaurantEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDaoImpl implements RestaurantDao {

    List<RestaurantEntity> currentList =  new ArrayList<>();

    public RestaurantDaoImpl () {
    }

    @Override
    public RestaurantEntity getRestaurantById(String placeId) {
        for(RestaurantEntity r: currentList) {
            if(r.getId().equals(placeId)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public List<RestaurantEntity> getCurrentRestaurants() {
        return currentList;
    }

    @Override
    public void findByName(Location location, String name) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Go4LunchApplication.getContext());
        String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + location.getLatitude()
                + "," + location.getLongitude()
                + "&radius=1500&type=restaurant&key="
                + "AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        System.out.println(response);
                        JSONObject mainObject = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });
        queue.add(stringRequest);
    }

    @Override
    public void refreshList(double latitude, double longitude) {

        List<RestaurantEntity> vList = new ArrayList<>();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Go4LunchApplication.getContext());
        String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + latitude
                + "," + longitude
                + "&radius=1500&type=restaurant&key="
                + "AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject mainObject = new JSONObject(response);

                        JSONArray arr = mainObject.getJSONArray("results");
                        for (int i = 0; i < arr.length(); i++)
                        {
                            System.out.println(" arr " + i  +" : "+ arr.getJSONObject(i).toString());
                            JSONObject geo = arr.getJSONObject(i).getJSONObject("geometry");
                            JSONObject loc = geo.getJSONObject("location");
                            RestaurantEntity restaurant = new RestaurantEntity();
                            restaurant.setLatitude(loc.getDouble("lat"));
                            restaurant.setLongitude(loc.getDouble("lng"));
                            restaurant.setName(arr.getJSONObject(i).getString("name"));
                            restaurant.setId(arr.getJSONObject(i).getString("place_id"));
                            // restaurant.setRating(arr.getJSONObject(i).getDouble("rating"));
                            restaurant.setAddress(arr.getJSONObject(i).getString("vicinity"));
                            vList.add(restaurant);
                        }
                        currentList = vList;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        queue.add(stringRequest);
    }
}
