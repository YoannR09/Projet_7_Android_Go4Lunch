package com.example.go4lunch.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.repo.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.go4lunch.util.Util.checkDiner;

public class RestaurantDaoImpl implements RestaurantDao {

    private static final String GOOGLE_PLACE_API_KEY = BuildConfig.GOOGLE_PLACE_API_KEY;

    MutableLiveData<List<RestaurantEntity>> currentList =  new MutableLiveData<>();
    MutableLiveData<RestaurantEntity> restaurantData = new MutableLiveData<>();

    public RestaurantDaoImpl () {
    }

    @Override
    public RestaurantEntity getRestaurantById(String placeId) {
        for(RestaurantEntity r: Objects.requireNonNull(currentList.getValue())) {
            if(r.getId().equals(placeId)) return r;
        }
        return null;
    }

    public void getRestaurantNotFoundOnMapById(
            String placeId,
            DaoOnSuccessListener<RestaurantEntity> listener) {
        RequestQueue queue = Volley.newRequestQueue(Go4LunchApplication.getContext());
        String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id="
                +placeId+
                "&fields=vicinity,name,rating,photo," +
                "formatted_phone_number,website,geometry,opening_hours" +
                "&key="+GOOGLE_PLACE_API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject main = new JSONObject(response);
                        JSONObject mainObject = main.getJSONObject("result");
                        JSONObject geo = mainObject.getJSONObject("geometry");
                        JSONObject loc = geo.getJSONObject("location");
                        RestaurantEntity restaurant = new RestaurantEntity();
                        restaurant.setLatitude(loc.getDouble("lat"));
                        restaurant.setLongitude(loc.getDouble("lng"));
                        restaurant.setName(mainObject.getString("name"));
                        if(mainObject.has("formatted_phone_number")) {
                            restaurant.setPhoneNumber(mainObject.getString("formatted_phone_number"));
                        }
                        if(mainObject.has("website")) {
                            restaurant.setWebSite(mainObject.getString("website"));
                        }
                        if(mainObject.has("opening_hours")) {
                            restaurant.setOpening(mainObject.getJSONObject("opening_hours").getBoolean("open_now"));
                        }
                        restaurant.setId(placeId);
                        // restaurant.setRating(arr.getJSONObject(i).getDouble("rating"));
                        if(mainObject.has("photos")) {
                            if(mainObject.getJSONArray("photos").getJSONObject(0).has("photo_reference")) {
                                restaurant.setPhotoReference(mainObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference"));
                            }
                        }
                        if(mainObject.has("rating")) {
                            restaurant.setOpinion((float) mainObject.getDouble("rating"));
                        }
                        restaurant.setAddress(mainObject.getString("vicinity"));
                        Repositories.getDinerRepository().getListDinersFromRestaurant(data -> {
                            if(data.size() > 0) {
                                restaurant.setWorkmateDiner(checkDinerFromDinerList(data));
                            }else {
                                restaurant.setWorkmateDiner(false);
                            }
                            listener.onSuccess(restaurant);
                        },restaurant.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        queue.add(stringRequest);
    }

    @Override
    public LiveData<List<RestaurantEntity>> getCurrentRestaurants() {
        return currentList;
    }

    @Override
    public LiveData<RestaurantEntity> getCurrentRestaurant() {
        return restaurantData;
    }

    @Override
    public void refreshList(double latitude, double longitude) {

        List<RestaurantEntity> vList = new ArrayList<>();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Go4LunchApplication.getContext());
        String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + latitude
                + "," + longitude
                + "&radius=1500&type=restaurant&contact&key="
                + GOOGLE_PLACE_API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject mainObject = new JSONObject(response);

                        JSONArray arr = mainObject.getJSONArray("results");
                        for (int i = 0; i < arr.length(); i++)
                        {
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
                                    restaurant.setWorkmateDiner(checkDinerFromDinerList(data));
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

    public boolean checkDinerFromDinerList(List<DinerModel> vList) {
        boolean checkedDiner = false;
        for(DinerModel diner: vList) {
            if(checkDiner(diner)) {
                checkedDiner = true;
            }
        }
        return checkedDiner;
    }
}
