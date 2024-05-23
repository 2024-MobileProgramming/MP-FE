package com.example.gabit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class Calendar_Request {
    // 서버 URL 설정(jsp 파일 연동)
    final static private String URL = "http://your-server.com/mission/monthly";

    public static class RequestData {
        private int userId;
        private int year;
        private int month;

        public RequestData(int userId, int year, int month) {
            this.userId = userId;
            this.year = year;
            this.month = month;
        }
    }

    public static class ResponseData {
        private int status;
        private String message;
        private int[] data;

        // Getters and setters omitted for brevity
    }

    public static ResponseData getMonthlyProof(int userId, int year, int month) {
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // 요청 데이터 생성
            RequestData requestData = new RequestData(userId, year, month);
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(requestData);

            // 요청 본문 작성
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int code = conn.getResponseCode();
            if (code == 200) {
                // 응답 데이터 읽기
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    // JSON 파싱
                    ResponseData responseData = gson.fromJson(response.toString(), ResponseData.class);
                    return responseData;
                }
            } else {
                System.out.println("HTTP error code : " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ResponseData response = getMonthlyProof(1, 2024, 5);
        if (response != null) {
            System.out.println("Status: " + response.status);
            System.out.println("Message: " + response.message);
            for (int day : response.data) {
                System.out.print(day + " ");
            }
        } else {
            System.out.println("Failed to get response");
        }
    }
}
