package com.example.today_be.Weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {
    @JsonProperty("TEMP")
    private String temp;               // 기온

    @JsonProperty("SENSIBLE_TEMP")
    private String sensibleTemp;       // 체감온도

    @JsonProperty("MAX_TEMP")
    private String maxTemp;            // 일 최고온도

    @JsonProperty("MIN_TEMP")
    private String minTemp;            // 일 최저온도

    @JsonProperty("HUMIDITY")
    private String humidity;           // 습도

    @JsonProperty("WIND_SPD")
    private String windSpd;            // 풍속

    @JsonProperty("WIND_DEG")
    private String windDeg;            // 강수량

    @JsonProperty("PRECPT_TYPE")
    private String precptType;         // 강수형태

    @JsonProperty("PCP_MSG")
    private String pcpMsg;             // 강수 관련 메세지

    @JsonProperty("SUNRISE")
    private String sunrise;            // 일출시각

    @JsonProperty("SUNSET")
    private String sunset;             // 일몰시각

    @JsonProperty("UV_INDEX_LVL")
    private String uvIndexLvl;         // 자외선지수 단계

    @JsonProperty("UV_INDEX")
    private String uvIndex;            // 자외선지수

    @JsonProperty("UV_MSG")
    private String uvMsg;              // 자외선 메시지

    @JsonProperty("PM25_INDEX")
    private String pm25Index;          // 초미세먼지 지표

    @JsonProperty("PM25")
    private String pm25;               // 초미세먼지 농도

    @JsonProperty("PM10_INDEX")
    private String pm10Index;          // 미세먼지 지표

    @JsonProperty("PM10")
    private String pm10;               // 미세먼지 농도
}
