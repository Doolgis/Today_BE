package com.example.today_be.Bike.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class BikeResponseDto {
    public String SBIKE_SPOT_NM;     // 따릉이 대여소 명
    public String SBIKE_SPOT_ID;     // 따릉이 대여소 ID
    public String SBIKE_SHARED;      // 따릉이 거치율
    public String SBIKE_PARKING_CNT; // 따릉이 주차 건수
    public String SBIKE_RACK_CNT;    // 따릉이 거치대 개수
    public double SBIKE_X;           // 따릉이 대여소 X좌표(경도)
    public double SBIKE_Y;           // 따릉이 대여소 Y좌표(위도)
}
