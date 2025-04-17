package com.example.today_be.Location;

import com.example.today_be.Location.dto.HotspotDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class HotspotCsvReader {
    public List<HotspotDto> loadHotspots() {
        List<HotspotDto> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new ClassPathResource("hotspots.csv").getInputStream(), StandardCharsets.UTF_8)
            );

            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 7)
                    continue;

                String code = data[2];
                String name = data[3];
                double lng = Double.parseDouble(data[5]);
                double lat = Double.parseDouble(data[6]);

                list.add(new HotspotDto(code, name, lng, lat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
