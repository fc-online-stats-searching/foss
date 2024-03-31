package com.foss.server.service;

import com.foss.server.domain.event.Event;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Value("${nexon.event.url}")
    private String nexonBaseUrl;

    private final String searchType = "&strSearch=&emSearchType=title&n4ArticleCategorySN=0&n4ArticleCategory2SN=1&_=1711215080855";
    public List<Event> getEvents(int limitPage) {
        List<Event> events = new ArrayList<>();
        try {
            for (int pageNo = 1; pageNo <= limitPage; pageNo++) {
                String url = nexonBaseUrl + pageNo + searchType;
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("div.board_list > div.content.event > div > div > div");

                for (Element element : elements) {
                    String image = element.select("a > span.thumb > img").first().attr("src");
                    String link = element.select("a").first().attr("href");
                    String text = element.select("a > span.content > span > span").first().text();
                    Element stateProceeding = element.select("a > span.state.proceeding").first();

                    if (stateProceeding != null) {
                        events.add(new Event(link, image, text));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }
}
