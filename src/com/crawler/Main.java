package com.crawler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Spider spider = new Spider();


        System.out.println("Select what function the crawler should execute");
        System.out.println("1.search word in domain");
        System.out.println("2.download and store webpages");
        System.out.print(">");

        int functionality;
        Scanner in = new Scanner(System.in);
        //select site and functionality
        functionality = in.nextInt();
        switch (functionality)
        {
            case 1: spider.search("http://www.csd.uoc.gr","computer"); break;
            case 2: spider.download("http://www.csd.uoc.gr"); break;
            default: System.out.print("No such function"); break;
        }
    }
}
