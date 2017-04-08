package com.crawler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Spider spider = new Spider();
        int functionality;
        Scanner in = new Scanner(System.in);
        String webpage, keyword;


        System.out.println("Select what function the crawler should execute");
        System.out.println("1.search word in domain");
        System.out.println("2.download and store webpages");
        System.out.print(">");


        //select site and functionality
        functionality = in.nextInt();
        switch (functionality)
        {
            case 1:
                System.out.print("Give the webpage full domain:");
                webpage = in.next();
                System.out.print("Give keyword:");
                keyword = in.next();
                spider.search(webpage,keyword);
                break;
            case 2:
                System.out.print("Give the webpage full domain:");
                webpage = in.next();
                spider.download(webpage);
                break;
            default: System.out.print("No such function"); break;
        }
    }
}
