package com.example.crawler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

//doc.select("ul.grid > li div.w-full").stream().map(ItemDto::new)
@Service
@Slf4j
public class WebCrawlService {
    private final List<String> itemList = Arrays.asList("삼성", "엘지", "급처");

    private final Function<String, Document> gets = url -> {
        try {
            return Jsoup.connect(url).get();
        }catch (IOException e) {
            e.printStackTrace();
        };
        return null;
    };

    public void crawl(String url) {
        List<ItemDto> itemDtos = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            // stream > elem > filter > map(ItemDto) > saveToFile
            List<ItemDto> dtos = doc.select("ul.grid > li div.w-full").stream()
                    .filter(isNullElem)
                    .map(ItemDto::new)
                    .toList();
            dtos.forEach(saveDataToFile1);
//            for (Element element : elements) {
//                String name = element.select("h2").text();
//                for (String item : itemList) {
//                    if (name.contains(item)) {
//                        ItemDto dto = new ItemDto(element);
//                        itemDtos.add(dto);
//                        saveDataToFile(name);
//                    }
//                }
//                if (itemList.equals(name)) {
//                    ItemDto dto = new ItemDto(element);
//                    itemDtos.add(dto);
//                }
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

    public Predicate<Element> isNullElem = elem -> {
        var child = elem.children().next();
        if (Objects.isNull(child.get(0))) return false;
        if (Objects.isNull(child.get(1))) return false;
        return true;
    };

    public Consumer<ItemDto> saveDataToFile1 = dto -> {
        String filePath = "crawled_data.txt";

        // try-with-resources를 사용해 자동으로 리소스 정리
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            writer.println(dto.getName() + " " + dto.getCost());
        } catch (IOException e) {
            e.printStackTrace();
        }
//    saveDataToFile(e.getName());
//    saveDataToFile(e.getCost());
//    saveDataToFile(e.getCountry());
    };
}


// Document doc = Jsoup.connect(url).get();
// doc.select("ul.grid > li div.w-full").stream().map(ItemDto::new)
//              .filter(isNullElem)
//                .forEach(saveToFile);