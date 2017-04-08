package com.crawler;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Spider spider = new Spider();
        //select site and functionality
        spider.search("http://www.csd.uoc.gr","computer");
    }
}
