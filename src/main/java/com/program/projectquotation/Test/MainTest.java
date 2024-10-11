package com.program.projectquotation.Test;

/**
 * Created by WHY on 2024/10/10.
 * Functions:
 */
public class MainTest {
    public static void main(String[] args) {
        String s = "http://54.251.36.109:8888/1728562098743_avatar_product_新建商品测试.png";
        String s1 = s.split("//")[1].split("/")[1];
        System.out.println(s1);
    }
}
