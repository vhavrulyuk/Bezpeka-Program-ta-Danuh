package com.lp.bpd.lab2.md5test;

import com.lp.bpd.lab2.MD5;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class md5test {
    @Test
    public void correctMd5Hash() throws UnsupportedEncodingException {

        HashMap<String, String> testStrings = new HashMap<>();
        testStrings.put("D41D8CD98F00B204E9800998ECF8427E", "");
        testStrings.put("0CC175B9C0F1B6A831C399E269772661", "a");
        testStrings.put("900150983CD24FB0D6963F7D28E17F72", "abc");
        testStrings.put("F96B697D7CB7938D525A2F31AAF161D0", "message digest");
        testStrings.put("C3FCD3D76192E4007DFB496CCA67E13B", "abcdefghijklmnopqrstuvwxyz");
        testStrings.put("D174AB98D277D9F5A5611C2C9F419D9F", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        testStrings.put("57EDF4A22BE3C955AC49DA2E2107B67A", "12345678901234567890123456789012345678901234567890123456789012345678901234567890");

        for (Map.Entry<String,String> testString : testStrings.entrySet()){
            Assert.assertEquals( testString.getKey(), MD5.toHexString(MD5.computeMD5(testString.getValue().getBytes())));
        }
















    }

}
