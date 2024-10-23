package com.example.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ItemDto {
    public String getName() {
        return name;
    }
    public String getCost() {
        return cost;
    }

    private final String name;
    private final String cost;
    ItemDto(Element element) {
        Elements child = element.children();
        this. name = child.get(0).text();
        this.cost = child.get(1).text();
    }
}
