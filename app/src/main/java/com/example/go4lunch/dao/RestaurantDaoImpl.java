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
import com.example.go4lunch.repo.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDaoImpl implements RestaurantDao {

    MutableLiveData currentList =  new MutableLiveData<List<RestaurantEntity>>();

    public RestaurantDaoImpl () {
    }

    @Override
    public RestaurantEntity getRestaurantById(String placeId) {
        for(RestaurantEntity r: (List<RestaurantEntity>) currentList.getValue()) {
            if(r.getId().equals(placeId)) return r;
        }
        return null;
    }

    @Override
    public LiveData<List<RestaurantEntity>> getCurrentRestaurants() {
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
                (Response.Listener<String>)
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
                + "&fields=opening_hours/open_now&radius=1500&type=restaurant&key="
                + "AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject mainObject = new JSONObject(response);

                        JSONArray arr = mainObject.getJSONArray("results");
                        for (int i = 0; i < arr.length(); i++)
                        {
                            System.out.println(" restaurant : " + arr.getJSONObject(i));
                            JSONObject geo = arr.getJSONObject(i).getJSONObject("geometry");
                            JSONObject loc = geo.getJSONObject("location");
                            RestaurantEntity restaurant = new RestaurantEntity();
                            restaurant.setLatitude(loc.getDouble("lat"));
                            restaurant.setLongitude(loc.getDouble("lng"));
                            restaurant.setName(arr.getJSONObject(i).getString("name"));
                            if(arr.getJSONObject(i).has("opening_hours")) {
                                restaurant.setOpening(arr.getJSONObject(i).getJSONObject("opening_hours").getBoolean("open_now"));
                            }
                            restaurant.setId(arr.getJSONObject(i).getString("place_id"));
                            // restaurant.setRating(arr.getJSONObject(i).getDouble("rating"));
                            if(arr.getJSONObject(i).has("photos")) {
                                if(arr.getJSONObject(i).getJSONArray("photos").getJSONObject(0).has("photo_reference")) {
                                    restaurant.setPhotoReference(arr.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference"));
                                }
                            }
                            if(arr.getJSONObject(i).has("rating")) {
                                restaurant.setOpinion((float) arr.getJSONObject(i).getDouble("rating"));
                            }
                            restaurant.setAddress(arr.getJSONObject(i).getString("vicinity"));
                            Repositories.getDinerRepository().getListDinersFromRestaurant(data -> {
                                if(data.size() > 0) {
                                    restaurant.setWorkmateDiner(true);
                                }else {
                                    restaurant.setWorkmateDiner(false);
                                }
                                vList.add(restaurant);
                                currentList.setValue(vList);
                            },restaurant.getId());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        queue.add(stringRequest);
    }

    public void getPictureList(String placeId) {


    }
}
