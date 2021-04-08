package com.ifenghui.storybookapi.app.system.service.impl;

import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class GeoipServiceImpl implements GeoipService {

    @Value("${geoip.mmdb}")
    String geommdbPath;

    DatabaseReader reader = null;

    @Autowired
    public void initReader(){
        File database = new File(geommdbPath);
        if(!database.exists()){
            return;
        }
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCountry(String ip) {

        //GeoIP2-City 数据库文件
        if(reader==null){
            this.initReader();
        }
        if(reader==null){
            return "";
        }
// 创建 DatabaseReader对象
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse response = reader.country(ipAddress);
            return response.getCountry().getIsoCode();
        }catch (AddressNotFoundException e){
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCity(String ip) {
        //GeoIP2-City 数据库文件
        if(reader==null){
            this.initReader();
        }
        if(reader==null){
            return "";
        }
// 创建 DatabaseReader对象
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(ipAddress);
            return response.getCity().getName();
        }catch (AddressNotFoundException e){
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
