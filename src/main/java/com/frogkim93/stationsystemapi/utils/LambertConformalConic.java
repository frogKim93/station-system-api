package com.frogkim93.stationsystemapi.utils;

public class LambertConformalConic {
    private static class MapParameters {
        double Re;   // 구의 반경 (Earth Radius)
        double grid; // 격자 간격
        double slat1; // 표준위도1
        double slat2; // 표준위도2
        double olon;  // 기준 경도 (Origin Longitude)
        double olat;  // 기준 위도 (Origin Latitude)
        double xo;    // X 원점
        double yo;    // Y 원점
        boolean first; // 최초 계산 플래그
        double sn, sf, ro; // 사전 계산된 값
    }

    public static int[] lamcproj(double lon, double lat) {
        double PI = Math.PI;
        double DEGRAD = PI / 180.0;

        MapParameters map = new MapParameters();
        map.Re = 6371.00877; // 지구 반경
        map.grid = 5.0;      // 격자 간격
        map.slat1 = 30.0;    // 표준위도1
        map.slat2 = 60.0;    // 표준위도2
        map.olon = 126.0;    // 기준경도
        map.olat = 38.0;     // 기준위도
        map.xo = 43;      // X 원점
        map.yo = 136;      // Y 원점
        map.first = false;

        if (!map.first) {
            map.Re = map.Re / map.grid;
            map.slat1 *= DEGRAD;
            map.slat2 *= DEGRAD;
            map.olon *= DEGRAD;
            map.olat *= DEGRAD;

            map.sn = Math.tan(PI * 0.25 + map.slat2 * 0.5) / Math.tan(PI * 0.25 + map.slat1 * 0.5);
            map.sn = Math.log(Math.cos(map.slat1) / Math.cos(map.slat2)) / Math.log(map.sn);
            map.sf = Math.tan(PI * 0.25 + map.slat1 * 0.5);
            map.sf = Math.pow(map.sf, map.sn) * Math.cos(map.slat1) / map.sn;
            map.ro = Math.tan(PI * 0.25 + map.olat * 0.5);
            map.ro = map.Re * map.sf / Math.pow(map.ro, map.sn);
            map.first = true;
        }

        int[] coords = new int[2];
        double ra = Math.tan(PI * 0.25 + lat * DEGRAD * 0.5);
        ra = map.Re * map.sf / Math.pow(ra, map.sn);
        double theta = lon * DEGRAD - map.olon;
        if (theta > PI) theta -= 2.0 * PI;
        if (theta < -PI) theta += 2.0 * PI;
        theta *= map.sn;

        coords[0] = (int) Math.round(ra * Math.sin(theta) + map.xo);
        coords[1] = (int) Math.round(map.ro - ra * Math.cos(theta) + map.yo);

        return coords;
    }
}
