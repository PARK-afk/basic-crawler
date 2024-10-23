package com.example.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class CrawlTests {

    private final String url = "https://web.joongna.com/search?category=147";

    @Test
    void getTest() throws IOException {
        Document doc = Jsoup.connect(url).get();
        doc.select("ul.grid > li div.w-full").stream().map(ItemDto::new)
                .forEach(e -> {
                    System.out.println(e.getName());
                    System.out.println(e.getCost());
        });
    }
}
