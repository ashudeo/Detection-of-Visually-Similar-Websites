package com.phishing.demo.urisimilarities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class CSSextracting {
    
    String css;
    Document doc;
    int count = 0;
    String cstyle="";
    Boolean iptag=false;
    Boolean result=false;
    ArrayList<String> ArrCS=new ArrayList<>();

    public CSSextracting(String uri){
        try {
            if(uri.startsWith("http://")){
                doc = Jsoup.connect(uri).maxBodySize(0).timeout(0).userAgent("Mozilla").get();
            }
            else if(uri.startsWith("https://")){
                doc = Jsoup.connect(uri).maxBodySize(0).timeout(0).userAgent("Mozilla").post();
            }
            Elements links = doc.select("link[rel=stylesheet]");
            for (Element link : links){
                css = link.attr("href");
                if(!css.startsWith("http"))
                    css = uri+css.substring(1);
                ArrCS.add(css);
            }
            for(Element style : doc.select("style"))
                cstyle=cstyle+style.html();
            Elements iptags = doc.select("input");
            for(Element iptg: iptags)
                count++;
            if(count>=2)
                iptag = true;
        }
        catch (Exception ex) {
            System.out.println("4: "+ex);
        }
    }
    
    public ArrayList<String> cssOfURL(){
        return ArrCS;
    }
    
    public String styleofURL(){
        return cstyle;
    }
    
    public boolean heuristic(){
        return iptag;
    }
}
