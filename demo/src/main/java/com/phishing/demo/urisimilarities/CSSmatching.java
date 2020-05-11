package com.phishing.demo.urisimilarities;

import com.steadystate.css.parser.CSSOMParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

public class CSSmatching {
    
    public String CSSasText(String vic){
        Document doc = null;
        String result = null;
        try {
            if(vic.startsWith("http://")){
                doc = Jsoup.connect(vic).maxBodySize(0).timeout(0).userAgent("Mozilla").get();
            }
            else if(vic.startsWith("https://")){
                doc = Jsoup.connect(vic).maxBodySize(0).timeout(0).userAgent("Mozilla").post();
            }
            result = doc.text();
        }
        catch (Exception ex) {
            System.out.println("6: "+ex);
        }
        if(result==null)
            return CSSasRule(vic);
        return result;
    }
    
    public String CSSasRule(String uri){
        String result = "";
        try {
            InputSource source = new InputSource(uri);
            CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList ruleList = stylesheet.getCssRules();
            for (int i = 0; i < ruleList.getLength(); i++)
                result = result+ruleList.item(i);
        }
        catch (Exception ex) {
            System.out.println("5: "+ex);
        }
        return result;
    }
}
