package com.crawler;

/**
 * Created by Michalis on 8/4/2017.
 */
import org.jsoup.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {
    private static final int MAX_PAGES = 100;
    private Set<String> pagesVisited = new HashSet<>();
    private Set<String> pagesToVisit = new HashSet<>();

    private String nextUrl()
    {

        String nextUrl = pagesToVisit.iterator().next();
        while(pagesVisited.contains(nextUrl))
        {
            pagesToVisit.remove(pagesToVisit.iterator().next());
            nextUrl = pagesToVisit.iterator().next();
        }
        pagesVisited.add(nextUrl);
        pagesToVisit.remove(pagesToVisit.iterator().next());
        return nextUrl;
    }

    public void search(String url, String searchWord)
    {
        SpiderLeg leg = new SpiderLeg();
        while(this.pagesVisited.size() < MAX_PAGES)
        {
            String currentUrl;
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesToVisit.add(url);
            }
            else
            {
                currentUrl = this.nextUrl();
            }

            leg.crawl(currentUrl);
            try {
                boolean success = leg.searchForWord(searchWord);
                leg.savePage();
                if(success) System.out.println("**Success** Word "+searchWord+" found at "+ currentUrl);
            }
            catch(NullPointerException ex)
            {
                System.out.println("Something did not fetch correctly");
            }


            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println("**Done** Visited all the web pages under domain "+ url);
    }


}
