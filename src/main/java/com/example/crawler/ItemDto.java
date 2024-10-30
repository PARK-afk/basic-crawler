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
    public String getCountry() { return country; }

    private final String name;
    private final String cost;
    private final String country;

    ItemDto(Element element) {
//        System.out.println("Element is = " + element);
        Elements child = element.children();
        Elements nextChild = child.next();
        this. name = child.get(0).text();
        this.cost = child.get(1).text();
        this.country = nextChild.select("div.my-1").text();
    }
}
