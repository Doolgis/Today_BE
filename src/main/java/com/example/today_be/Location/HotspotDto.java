package com.example.today_be.Location;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotspotDto {
    private String areaCode;
    private String areaName;
    private double longtitude;
    private double latitude;
}
