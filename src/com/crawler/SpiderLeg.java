package com.crawler;

import java.io.*;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.Connection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Michalis on 8/4/2017.
 */
public class SpiderLeg {
    private Set<String> links = new HashSet<>();
    private String USER_AGENT;
    private Document htmlDocument;
    private String url;

    public SpiderLeg()
    {
        USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/7.1.0.12633";
        selectUserAgent();
    }

    public boolean crawl(String url)
    {
        try{
            this.url = url;
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document html = connection.get();
            this.htmlDocument = html;
            if(connection.response().statusCode() == 200)
            {
                System.out.println("**Visiting** Received web page at url " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.print("**Failure** Retrieved something other than HTML");
                return false;
            }
            //System.out.print(htmlDocument.text());
            Elements linksOnePage = htmlDocument.select("a[href]");

            for(Element link: linksOnePage) {
                this.links.add(link.absUrl("href"));
            }
            return true;
        }
        catch (IOException ex)
        {
            System.out.println("Error in out HTTP request "+ex);
            return false;
        }
    }

    public void savePage()
    {
        String[] file_name = url.split("//");
        String[] name = file_name[1].split("/");
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name[0]+".html"),"utf-8"));
            writer.write(htmlDocument.outerHtml());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean searchForWord(String word)
    {
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(word.toLowerCase());
    }

    public Set<String> getLinks()
    {
        return this.links;
    }

    public void selectUserAgent()
    {
        //print message to make user select a user agent or leave default
        System.out.println("Please select a user agent from the following list( 0 for default)");
        System.out.println("0.Internet Explorer");
        System.out.println("1.Chrome");
        System.out.println("2.Firefox");
        System.out.println("3.Lynx");
        System.out.println("4.Microsoft Edge");
        System.out.println("5.Vivaldi");
        System.out.println("6.Opera");
        System.out.println("7.Chromium");
        System.out.print(">");
        Scanner in = new Scanner(System.in);
        int selection = in.nextInt();
        switch (selection)
        {
            case 1: this.USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.19 (KHTML, like Gecko) Chrome/1.0.154.53 Safari/525.19"; break;
            case 2: this.USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0"; break;
            case 3: this.USER_AGENT = "Lynx/2.8.5dev.16 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.7a"; break;
            case 4: this.USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; Xbox; Xbox One) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586"; break;
            case 5: this.USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.77 Safari/537.36 Vivaldi/1.7.735.27"; break;
            case 6: this.USER_AGENT = "Opera/9.80 (Windows NT 5.1) Presto/2.12.388 Version/12.14"; break;
            case 7: this.USER_AGENT = "Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Ubuntu/10.10 Chromium/8.0.552.237 Chrome/8.0.552.237 Safari/534.10"; break;
            default: System.out.print("Default selection\n"); break;
        }

    }
}
