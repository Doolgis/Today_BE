package com.example.today_be.Bike;

import com.example.today_be.Bike.dto.BikeResponseDto;
import com.example.today_be.Bike.dto.RecommendedCourseDto;
import com.example.today_be.Weather.dto.WeatherResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Value("${DATA_KEY}")
    private String dataKey;

    @GetMapping("/now")
    public List<BikeResponseDto> test(@RequestParam double userX, @RequestParam double userY) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/" + dataKey);
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("citydata", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("강남역", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response Code : " + conn.getResponseCode());

        BufferedReader br;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        conn.disconnect();
//        System.out.println(sb);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(sb.toString());

        JsonNode sbikeArray = root.at("/CITYDATA/SBIKE_STTS");

        List<BikeResponseDto> bikeResponseDtos = objectMapper.readValue(sbikeArray.toString(), new TypeReference<List<BikeResponseDto>>() {});

//        System.out.println("---- 따릉이 Response");
//        for(BikeResponseDto bikeResponseDto : bikeResponseDtos) {
//            System.out.println(bikeResponseDto);
//        }

        System.out.println("---- 반경 1km 내 따릉이 정보 반환");
        return bikeResponseDtos.stream()
                .filter(dto -> calculateDistance(userY, userX, dto.getSBIKE_Y(), dto.getSBIKE_X()) <= 1.0)
                .collect(Collectors.toList());
    }

    // 사용자 위치 기반 거리 계산
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // 지구 반지름 (km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    @GetMapping("/weather/5")
    public void resultBike() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/" + dataKey);
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("citydata", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("강남역", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response Code : " + conn.getResponseCode());

        BufferedReader br;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        conn.disconnect();
//        System.out.println(sb);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(sb.toString());

        JsonNode resultBike = root.at("/CITYDATA/WEATHER_STTS");

        JsonNode weather = resultBike.get(0);

        WeatherResponseDto weatherResponseDto = objectMapper.readValue(weather.toString(), new TypeReference<WeatherResponseDto>() {});
        System.out.println(weatherResponseDto);
        System.out.println(isSuitableForBike(weatherResponseDto));
    }

    // 오늘 날씨가 따릉이를 탈만한 날씨인지에 대한 판단 여부
    public boolean isSuitableForBike(WeatherResponseDto weather) {
        try {
            double temp = Double.parseDouble(weather.getTemp());
            double windSpd = Double.parseDouble(weather.getWindSpd());
            String precptType = weather.getPrecptType();
            String pm10Index = weather.getPm10Index(); // "좋음", "보통", "나쁨" 등

            boolean tempOk = temp >= 0 && temp <= 30;
            boolean windOk = windSpd <= 5.0;
            boolean rainOk = "없음".equals(precptType);
            boolean airOk = "좋음".equals(pm10Index) || "보통".equals(pm10Index);

            return tempOk && windOk && rainOk && airOk;

        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/recommand-course")
    public List<RecommendedCourseDto> recommandCourse5(@RequestParam double userX, @RequestParam double userY) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/" + dataKey);
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("bikeList", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1000", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response Code : " + conn.getResponseCode());

        BufferedReader br;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        conn.disconnect();
//        System.out.println(sb);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(sb.toString());
        JsonNode bikeArray = root.path("rentBikeStatus").path("row");

        List<RecommendedCourseDto> candidates = new ArrayList<>();

        for (JsonNode station : bikeArray) {
            double lat = station.path("stationLatitude").asDouble();  // 위도
            double lng = station.path("stationLongitude").asDouble(); // 경도
            int parkingCount = station.path("parkingBikeTotCnt").asInt(); // 주차 가능 따릉이 수

            double distance = calculateDistance(userY, userX, lat, lng);
            System.out.println("distance : " + distance);

            if (distance >= 4.5 && distance <= 5.5 && parkingCount > 0) {
                candidates.add(RecommendedCourseDto.builder()
                        .startLat(userY)
                        .startLon(userX)
                        .endLat(lat)
                        .endLon(lng)
                        .endStationName(station.get("stationName").asText())
                        .distanceKm(distance)
                        .build());
            }
        }

        return candidates;
    }

    @GetMapping("/recommand-course/10")
    public List<RecommendedCourseDto> recommandCourse10(@RequestParam double userX, @RequestParam double userY) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/" + dataKey);
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("bikeList", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1000", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response Code : " + conn.getResponseCode());

        BufferedReader br;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        conn.disconnect();
//        System.out.println(sb);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(sb.toString());
        JsonNode bikeArray = root.path("rentBikeStatus").path("row");

        List<RecommendedCourseDto> candidates = new ArrayList<>();

        for (JsonNode station : bikeArray) {
            double lat = station.path("stationLatitude").asDouble();  // 위도
            double lng = station.path("stationLongitude").asDouble(); // 경도
            int parkingCount = station.path("parkingBikeTotCnt").asInt(); // 주차 가능 따릉이 수

            double distance = calculateDistance(userY, userX, lat, lng);
            System.out.println("distance : " + distance);

            if (distance >= 9.5 && distance <= 10.5 && parkingCount > 0) {
                candidates.add(RecommendedCourseDto.builder()
                        .startLat(userY)
                        .startLon(userX)
                        .endLat(lat)
                        .endLon(lng)
                        .endStationName(station.get("stationName").asText())
                        .distanceKm(distance)
                        .build());
            }
        }

        return candidates;
    }
}
