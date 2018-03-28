package com.tubitv.tubitv.Helpers;

import java.util.Random;

/**
 * Created by vburian on 3/26/18.
 */

public class RandomEmail {
    public static String randomemail(){
        String ch = "abcdefghijklmnopqrstuvwxyz23456789";
        String randomString="";

        Random rand = new Random();
        int lenght= rand.nextInt(10);
        char[] text = new char[lenght];
        for (int i=0; i<lenght;i++) {
            text[i]= ch.charAt(rand.nextInt(ch.length()));
        }
        for (int i=0; i<text.length;i++) {
            randomString+=text[i];
        }
        return randomString;

    }}
