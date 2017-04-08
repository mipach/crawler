package com.crawler;

import java.io.IOException;
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
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Document htmlDocument;

    public boolean crawl(String url)
    {
        try{
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

    public boolean searchForWord(String word)
    {
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(word.toLowerCase());
    }

    public Set<String> getLinks()
    {
        return this.links;
    }
}
