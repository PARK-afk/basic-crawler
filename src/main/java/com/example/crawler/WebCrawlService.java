package com.example.crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class WebCrawlService {

    // URL을 입력받아 크롤링하는 메서드
    public void crawl(String url) {
        try {
            // Jsoup을 사용해 URL에서 HTML 페이지를 가져옴
            Document document = Jsoup.connect(url).get();

            // 페이지 제목 추출
            String title = document.outerHtml();

            // 모든 링크 추출
            StringBuilder data = new StringBuilder();
            data.append("Title: ").append(title).append("\n");
            System.out.println("BEFORE : "+data.toString());

//            document.select("a[href]").forEach(link -> {
//                data.append("Link: ").append(link.attr("href")).append(" - Text: ").append(link.text()).append("\n");
//            });
            System.out.println("AFTER : "+data.toString());
            // 추출한 데이터를 텍스트 파일로 저장
            saveDataToFile(data.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
