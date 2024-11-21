package com.program.projectquotation.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by WHY on 2024/10/10.
 * Functions:
 */
public class MainTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String name = "tt300超越离合18珠6孔6（）#丝";
        String encode = URLEncoder.encode(name, "UTF-8");
        System.out.println(encode);
    }
}
