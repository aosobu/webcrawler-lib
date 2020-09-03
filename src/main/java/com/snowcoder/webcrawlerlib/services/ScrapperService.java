package com.snowcoder.webcrawlerlib.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by aosobu
 */
public abstract class ScrapperService {

    /**
     *
     * @param url represents address of page to be crawled
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

    /**
     *
     * @param elements
     * @return
     */
    public List<Node> getNodesFromElement(Elements elements){
        List<Node> nodes = new ArrayList<>();
        for(Element element: elements)
            nodes = element.childNodes();
        return nodes;
    }

    /**
     *
     * @param nodes
     * @param elementClass
     * @param reflectionNames
     * @param <T>
     * @return
     */
    public <T> T returnObjectsFromNodes(List<Node> nodes, Class<?> elementClass, List<String> reflectionNames){
        for(Node n: nodes){
//            Sector sector = new Sector();
//            if(!n.childNodes().isEmpty())
//                sector.setName(n.childNodes().get(0).toString());
//            sectors.add(sector);
        }
        return null;
    }

}
