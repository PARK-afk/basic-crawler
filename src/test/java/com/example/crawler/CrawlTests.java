package com.example.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class CrawlTests {

    private final String url = "https://web.joongna.com/search?category=147";

    private static final List<String> allowedCountries = Arrays.asList(
            // 수원시
            "장안구", "권선구", "팔달구", "영통구",
            "파장동", "율전동", "정자동", "송죽동", "조원동", "연무동", // 장안구 세부 동
            "세류동", "권선동", "곡반정동", "고색동", "오목천동", // 권선구 세부 동
            "매교동", "매산동", "고등동", "화서동", "우만동", // 팔달구 세부 동
            "영통동", "망포동", "태장동", "원천동", "이의동", // 영통구 세부 동
            // 평택시
            "팽성읍", "안중읍", "포승읍", "청북읍", "고덕면",
            "비전동", "세교동", "통복동", "동삭동", "죽백동",
            // 안산시
            "단원구", "상록구",
            "고잔동", "와동", "원곡동", "선부동", "초지동", // 단원구 세부 동
            "사동", "본오동", "부곡동", "일동", "성포동", // 상록구 세부 동
            // 화성시
            "봉담읍", "향남읍", "정남면", "병점동", "동탄동",
            "기산동", "마도면", "남양읍", "비봉면", "팔탄면",
            // 군포시
            "산본동", "금정동", "오금동", "당정동", "대야동",
            // 시흥시
            "대야동", "신천동", "은행동", "매화동", "월곶동",
            // 의왕시
            "내손동", "오전동", "포일동", "왕곡동",
            // 광주시
            "경안동", "송정동", "초월읍", "곤지암읍", "오포읍"
    );

    @Test
    void getTest() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Set<String> processedCountries = new HashSet<>();

        // 필터링된 데이터만 처리
        doc.select("ul.grid > li div.w-full").stream()
                .filter(e -> allowedCountries.contains(e.select("div.my-1").text())) // 허용된 국가만
                .filter(e -> processedCountries.add(e.select("div.my-1").text()))    // 중복된 국가 필터링
                .map(e -> e.select("div.flex.flex-col").get(0))
                .forEach(e -> {
                    saveDataToFile(e.select("h2").text());
                    saveDataToFile(e.select("div.text-lg").text());
                    saveDataToFile(e.select("div.my-1").text());
                });

//                .map(ItemDto::new)
//                .filter(e -> allowedCountries.contains(e.getCountry())) // 허용된 국가만
//                .filter(e -> processedCountries.add(e.getCountry()))    // 중복된 국가 필터링
//                .forEach(e -> {
//                    saveDataToFile(e.getName());
//                    saveDataToFile(e.getCost());
//                    saveDataToFile(e.getCountry());
//                });
    }

    public void saveDataToFile(String data) {
        // 추출한 데이터를 텍스트 파일로 저장하는 코드
        String filePath = "crawled_data.txt";

        // try-with-resources를 사용해 자동으로 리소스 정리
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);  // 데이터를 파일에 씀
            writer.newLine();    // 줄 바꿈
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
