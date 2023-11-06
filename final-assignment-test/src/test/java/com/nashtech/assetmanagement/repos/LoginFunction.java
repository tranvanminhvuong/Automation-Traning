package com.nashtech.assetmanagement.repos;

import java.util.ArrayList;

public class LoginFunction {

    public static boolean verifyTextOfTabFollowPermission(ArrayList<String> listTabActual, String[] listTabExpected) {
        for (int i = 0; i < listTabExpected.length; i++) {
            if (!listTabActual.get(i).equals(listTabExpected[i]))
                return false;
        }
        return true;
    }
}
