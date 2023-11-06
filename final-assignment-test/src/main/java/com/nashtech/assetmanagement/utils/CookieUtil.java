package com.nashtech.assetmanagement.utils;

import com.nashtech.assetmanagement.driver.Browser;
import org.openqa.selenium.Cookie;

import java.util.Date;

public class CookieUtil {
    public static void addCookie(String name, String value){
        Browser.getDriver().manage().addCookie(new Cookie(name,value));
    }

}
