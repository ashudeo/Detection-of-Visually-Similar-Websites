package com.phishing.demo.urisimilarities;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.*;

import java.io.BufferedReader;
import java.io.StringReader;

public class CSSruleExtract {
    
    String select = "";
    String proper = "";
    String values = "";

    public CSSruleExtract(String style){
        try {
            BufferedReader br = new BufferedReader(new StringReader(style));
            InputSource source = new InputSource(br);
            CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList ruleList = stylesheet.getCssRules();
            for (int i = 0; i < ruleList.getLength(); i++) {
                CSSRule rule = ruleList.item(i);
                if (rule instanceof CSSStyleRule) {
                    CSSStyleRule styleRule=(CSSStyleRule)rule;
                    select = select+styleRule.getSelectorText();
                    CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
                    for (int j = 0; j < styleDeclaration.getLength(); j++) {
                        String property = styleDeclaration.item(j);
                        proper = proper+property;
                        values = values+styleDeclaration.getPropertyCSSValue(property).getCssText();
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("7: "+ex);
        }
    }
    
    public String selectors(){
        return select;
    }
    
    public String properties(){
        return proper;
    }
    
    public String value(){
        return values;
    }
}
