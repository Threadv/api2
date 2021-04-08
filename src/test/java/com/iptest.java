package com;

import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.util.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date: 2019/2/13 13:32
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class iptest {

    @Autowired
    HttpServletRequest request;

    @Autowired
    GeoipService geoipService;

    @Test
    public void main232() {

//        String ip = HttpRequest.getIpAddr(request);
        String ip = "101.37.174.227";
        String country = geoipService.getCountry(ip);
        if (country == null) {

        }

    }
}
