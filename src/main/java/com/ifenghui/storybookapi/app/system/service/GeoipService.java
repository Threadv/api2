package com.ifenghui.storybookapi.app.system.service;

public interface GeoipService {

    String getCountry(String ip);

    String getCity(String ip);
}
