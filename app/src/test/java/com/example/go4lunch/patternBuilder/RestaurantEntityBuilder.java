package com.example.go4lunch.patternBuilder;

import com.example.go4lunch.entity.RestaurantEntity;

public class RestaurantEntityBuilder {

    RestaurantEntity entity;

    public RestaurantEntityBuilder() {
        entity = new RestaurantEntity();
    }

    public RestaurantEntityBuilder name(String name) {
        entity.setName(name);
        return this;
    }

    public RestaurantEntityBuilder photoRef(String photoRef) {
        entity.setPhotoReference(photoRef);
        return this;
    }

    public RestaurantEntityBuilder opinion(float opinion) {
        entity.setOpinion(opinion);
        return this;
    }

    public RestaurantEntityBuilder address(String address) {
        entity.setAddress(address);
        return this;
    }

    public RestaurantEntityBuilder id(String id) {
        entity.setId(id);
        return this;
    }

    public RestaurantEntityBuilder latitude(double latitude) {
        entity.setLatitude(latitude);
        return this;
    }

    public RestaurantEntityBuilder longitude(double longitude){
        entity.setLongitude(longitude);
        return this;
    }

    public RestaurantEntityBuilder opening(boolean opening) {
        entity.setOpening(opening);
        return this;
    }

    public RestaurantEntity build() {
        return entity;
    }

}
