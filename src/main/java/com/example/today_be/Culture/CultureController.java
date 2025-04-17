package com.example.today_be.Culture;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/event")
public class CultureController {

    public void eventOpenAPI() throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/6870584e4379756e3936566761644c");
        urlBuilder.append("/" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("citydata", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("5", "UTF-8"));
        // 여기 부분이 이제 장소코드 (현 위치의 장소 가져오기)
        urlBuilder.append("/" + URLEncoder.encode("POI001", "UTF-8"));

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    }
}
