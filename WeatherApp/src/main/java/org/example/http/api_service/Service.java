package org.example.http.api_service;

sealed public interface Service permits AccuweatherRetrofitService, OpenweatherRetrofitService { }