package com.lbnkosi.weatherapp.data.mappers

import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import com.lbnkosi.weatherapp.domain.models.places.Place

fun PlacesEntity.map(): Place {
    return Place(id, lat, long, name, description, created, weatherData)
}

fun ArrayList<PlacesEntity>.map(): ArrayList<Place> {
    return map { item -> item.map() } as ArrayList<Place>
}

fun Place.map(): PlacesEntity {
    return PlacesEntity(id, lat, long, name, description, created, weatherData)
}

fun ArrayList<Place>.mapPlaces(): ArrayList<PlacesEntity> {
    return map { item -> item.map() } as ArrayList<PlacesEntity>
}
