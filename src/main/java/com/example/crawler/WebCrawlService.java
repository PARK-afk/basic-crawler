package com.example.crawler;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class WebCrawlService {
    public void crawl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            List<ItemDto> dtos = doc.select("ul.grid").select("div:has(h2, div").stream().map(ItemDto::new).toList();
            dtos.forEach( dto -> {
                log.info("name: {}, cost: {}", dto.getName(), dto.getCost());
                saveDataToFile(dto.getName());
                saveDataToFile(dto.getCost());
            });
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
