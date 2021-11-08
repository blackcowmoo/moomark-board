package com.moomark.board.util;


public class StringUtils {
  
  private StringUtils() throws IllegalAccessException {
    throw new IllegalAccessException("String utility class");
  }

  public static String removeSpecialCharacter(String data) {
    String match = "[^\\uAC00-\\uD7A3xfe0-9a-zA-Z\\\\s]";
    return data.replace(match, "");
  }
}
