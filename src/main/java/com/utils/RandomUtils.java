package com.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RandomUtils {
    /**
     * 获取随机数
     * @param numnumber 随机数个数
     * @param minnum  随机数最小值
     * @param maxnum  随机数最大值
     * @throws Exception
     */
    public static String GetRandomNumber(int numnumber,int minnum,int maxnum) throws Exception {

        URL randomOrg = new URL("https://www.random.org/integers/?num="+numnumber+"&min="+minnum+"&max="+maxnum+"&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection con = (HttpURLConnection) randomOrg.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        //System.out.println("Response Code: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        String randomnum = "";
        while ((inputLine = in.readLine()) != null)
            randomnum += inputLine;
        in.close();
        return randomnum;
    }
    public static void main(String[] args) {
        try {
            GetRandomNumber(5, 1, 100);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
