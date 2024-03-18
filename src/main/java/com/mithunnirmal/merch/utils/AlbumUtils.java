package com.mithunnirmal.merch.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumUtils {

    public static String getFileIdFromGDriveLink (String input) {
        String regex = "/file/d/([a-zA-Z0-9_-]+)/";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String id = matcher.group(1);
            System.out.println("ID: " + id);
            return id;
        } else {
            System.out.println("ID not found");
            return null;
        }
    }

    public static String createGDriveThumbnailLink(String id) {
        return "https://drive.google.com/thumbnail?id=" + id;
    }
}
