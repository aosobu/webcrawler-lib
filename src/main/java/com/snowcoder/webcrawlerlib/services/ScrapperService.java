package com.snowcoder.webcrawlerlib.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * created by aosobu
 */
public abstract class ScrapperService {

    /**
     *
     * @param url represenets url of page to be crawled
     * @return
     */
    public Document connect(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     *
     * @param document
     * @param selector should be css query or evaluator
     * @return
     */
    public Elements getElementsFromDocument(Document document, String selector){
        return document.select(selector);
    }

    /**
     *
     * @param document
     * @param selector should be css query or evaluator
     * @return
     */
    public Element getElementFromDocument(Document document, String selector){
        return document.selectFirst(selector);
    }

}
