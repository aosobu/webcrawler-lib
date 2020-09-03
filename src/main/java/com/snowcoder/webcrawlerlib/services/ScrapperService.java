package com.snowcoder.webcrawlerlib.services;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by aosobu
 */
public abstract class ScrapperService {

    private final Logger logger = LoggerFactory.getLogger(ScrapperService.class);

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
     * @param nodes
     * @param mapperClass
     * @param reflectionName
     * @param nodeIndex
     * @param <T>
     * @return
     */
    public <T> List<T> returnObjectsFromNodes(List<Node> nodes, Class<T> mapperClass, String reflectionName, int nodeIndex){
        List<T> lists = new ArrayList<>();
        String value;

        for(Node n: nodes){
            T newObject = null;
            value = "";
            @SuppressWarnings("unchecked") Class<T> nodeClass = mapperClass;
            try {
                newObject = ConstructorUtils.invokeConstructor(nodeClass, null);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                logger.error(e.getMessage());
            }

            Field[] fields = getClassFields(newObject);

            for(Field field: fields){
                if(field.getName().equalsIgnoreCase(reflectionName)) {
                    try{
                        if(!n.childNodes().isEmpty())
                            value =  n.childNodes().get(nodeIndex).toString();
                        if(!value.isEmpty() && value != "") {
                            BeanUtils.setProperty(newObject, reflectionName, value);
                            lists.add(newObject);
                        }
                    } catch (Exception e) {
                        logger.info("Error :", e);
                        throw new IllegalArgumentException("Error setting property value for " + reflectionName + " with value  " + value);
                    }
                }
            }
        }

        return lists;
    }

    private <T> Field[] getClassFields(T row) {
        Field[] fields = new Field[0];

        if (row != null) {
            ArrayList<Field> fieldList = new ArrayList<>();
            Class tmpClass = row.getClass();
            while (tmpClass != null) {
                fieldList.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
                tmpClass = tmpClass.getSuperclass();
            }

            fields = fieldList.toArray(new Field[0]);
        }
        return fields;
    }
}
