package com.phishing.demo.urisimilarities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Component
public class URIsimilarities {

    @Autowired
    ResourceLoader resourceLoader;
    float min, sltper, prpper, vluper;

    ArrayList<String> susStyle=new ArrayList<>();
    ArrayList<String> urlofSUS=new ArrayList<>();
    ArrayList<String> urlofVIC=new ArrayList<>();
    public List<String> result;
    String whiteurl = "database/URL.txt";
    String whitelist = "database/Whitelist.txt";
/*

    public URIsimilarities() {
        
        frame.setVisible(true);
        frame.setSize(700, 450);
        frame.setLocation(300, 100);
        frame.setTitle("CUMP: Phishing detection tool v1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        text.setFont(new Font("Times New Roman", 0, 16));
        text.setForeground(Color.BLACK);
        text.setText("Verify the URL");
        
        text.addFocusListener(new FocusAdapter(){
            
            @Override
            public void focusGained(FocusEvent e){
                text.setText("");
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if(text.getText().isEmpty())
                    text.setText("Varify the URL");
            }
        });
        
        frame.getContentPane().add(text);
        text.setBounds(50, 50, 400, 50);
        
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        check.setBounds(500, 50, 150, 50);
        frame.getContentPane().add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //result.setText("");
                if(text.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Empty text area is not allowed");
                    text.requestFocus();
                }
                else{
                    getDetail(text.getText());
                    text.setText("");
                }
            }
        });*/
 /*       
        result.setEditable(false);
        result.setFont(new Font("Times New Roman", 0, 12));
        result.setForeground(Color.BLUE);
        result.setLineWrap(true);
        
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scroll.setViewportView(result);
        
        frame.getContentPane().add(scroll);
        scroll.setBounds(50, 150, 600, 200);
        
    }
    */
    public static void main(String[] args) {
        new URIsimilarities();
    }
    
    public List<String> getDetail(String uri){
        URL url;
        String host = null;
        boolean avail = true;
        int count=0,stop = 0;
        result = new ArrayList<>();
        if(!uri.startsWith("http"))
            uri = "http://"+uri;
        try {
            url = new URL(uri);
            host = url.getHost();
            URLConnection connection = url.openConnection();
            connection.connect();
        }
        catch (MalformedURLException ex) {
            avail = false;
            System.out.println("1. "+ex);
            result.add("Not a proper URL");
        }
        catch (IOException ex){
            avail = false;
            System.out.println("2. "+ex);
            result.add("URL does not exist");
        }
        if(avail) try {
            if(getWhitelist(host)){
                result.add("Domain "+host+" is in whitelist\n");
                result.add("Hence "+uri+" is Innocent\n");
            }
            else{
                CSSmatching suscss = new CSSmatching();
                CSSextracting scss = new CSSextracting(uri);
                
                urlofSUS = scss.cssOfURL();
                String sus = scss.styleofURL();
                for(String sCSS:urlofSUS)
                    susStyle.add(suscss.CSSasText(sCSS));
                result.add("Domain "+host+" is not in whitelist\n");
                result.add("Hence "+uri+" is Suspected\n");
                BufferedReader wl = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:database/Whitelist.txt")));
                String wlist = wl.readLine();
                OUTERMOST:
                while(wlist != null){
                    int pattern = getPattern(uri.substring(7),wlist);
                    BufferedReader wu = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:database/URL.txt")));
                    String wurl = wu.readLine();
                    while((wurl != null)&&(stop == 0)){
                        if(pattern>=((wlist.length()+1)/2)&&wurl.contains(wlist+".")){
                            System.out.println("\t\t------: "+wurl+" :------");
                            CSSmatching viccss = new CSSmatching();
                            CSSextracting vcss = new CSSextracting(wurl);
                            urlofVIC = vcss.cssOfURL();
                            String vic = vcss.styleofURL();
                            String styleVIC;
                            float perc = 0;
                            int loop = 0;
                            for(String vCSS:urlofVIC){
                                for(String urlSUS:urlofSUS){
                                    if(urlSUS.equalsIgnoreCase(vCSS)){
                                        result.add("URL of CSS: "+vCSS+" are same\n");
                                        result.add("Hence Phishing of Victim: "+wurl);
                                        break OUTERMOST;
                                    }
                                }
                                styleVIC = viccss.CSSasText(vCSS);
                                for(String styleSUS:susStyle){
                                    if(styleSUS.length()/2>=styleVIC.length()||styleVIC.length()/2>=styleSUS.length())
                                        break;
                                    CSSruleExtract vcre = new CSSruleExtract(styleVIC+vic);
                                    CSSruleExtract scre = new CSSruleExtract(styleSUS+sus);
                                    min = vcre.select.length();
                                    if(min > scre.select.length())
                                        min = scre.select.length();
                                    sltper = (float)getPattern(scre.select,vcre.select)/min;
                                    min = vcre.proper.length();
                                    if(min > scre.proper.length())
                                        min = scre.proper.length();
                                    prpper = (float)getPattern(scre.proper,vcre.proper)/min;
                                    min = vcre.values.length();
                                    if(min > scre.values.length())
                                        min = scre.values.length();
                                    vluper = (float)getPattern(scre.values,vcre.values)/min;
                                    float total = (sltper+prpper+vluper)/3;
                                    perc = perc + total;
                                    loop++;
                                }
                            }
                            float score = (perc/loop)*100;
                            System.out.println("Total Similarities: "+score);
                            if(score >= 50){
                                result.add("Threshold is "+score+"\n");
                                result.add("Hence Phishing of Victim: "+wurl);
                                break OUTERMOST;
                            }
                            if(Double.isNaN(score))
                                count++;
                            else
                                count=0;
                            System.out.println("Count:="+count);
                            if(count>=10){
                                result.add("CSS cannot Compared with many Target list");
                                result.add("\nWe considered as Legitimate this time");
                                break OUTERMOST;
                            }
                        }
                        wurl=wu.readLine();
                    }
                    wlist=wl.readLine();
                }
            }
        }
        catch (Exception ex) {
            System.out.println("3. "+ex);
        }

        return result;
    }
    
    public boolean getWhitelist(String host){
        int count = 0;
        host = "http://"+host;
        for (int i=0; i < host.length(); i++){
            if (host.charAt(i) == '.')
                count++;
        }
        if(count>3){
            return false;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:database/Whitelist.txt")))) {
            String list = br.readLine();
            while (list != null) {
               if(host.contains("://"+list+".")||host.contains("."+list+"."))
                   return true;
               list=br.readLine();
            }
        }
        catch(Exception ex){
            System.out.println("3: "+ex);
        }
        return false;
    }
    
    public int getPattern(String a, String b){
        int lengths[][] = new int[a.length()+1][b.length()+1];
        //row 0 and column 0 are initialized to 0 already
        for (int i = 0; i < a.length(); i++)
            for (int j = 0; j < b.length(); j++)
                if (a.charAt(i) == b.charAt(j))
                    lengths[i+1][j+1] = lengths[i][j] + 1;
                else
                    lengths[i+1][j+1] = Math.max(lengths[i+1][j], lengths[i][j+1]);
        return lengths[a.length()][b.length()];
    }
}
