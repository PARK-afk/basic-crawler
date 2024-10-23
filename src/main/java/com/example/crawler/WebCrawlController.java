package com.example.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebCrawlController {
    @Autowired
    private WebCrawlService webCrawlService;

//    @GetMapping("/")
//    public void index() {
//        webCrawlService.crawl();
//    }

    @GetMapping("/crawl")
    public String crawl(@RequestParam String url) {
        webCrawlService.crawl(url);
//        return "Crawling URL: " + url;
        return "crawling" + url;
    }
}
