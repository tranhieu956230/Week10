/**
 * @author Trần Đức Hiếu
 * @version 1.0.0
 * @date 13/11/2018
 */
package com.company;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("./ZipUtils.java");
        list = getAllFunctions(file);
        for(String s : list) {
            System.out.println(s);
        }


//       bubbleSort();
    }

    /**
     *
     * @param path đường đẫn đến file
     * @return một array list nội dung các hàm
     */
    public static List<String> getAllFunctions(File path) {
        List<String> res = new ArrayList<>();
        try {
            Stack comment = new Stack();
            Scanner sc = new Scanner(path);
            String func = "";
            Stack st = new Stack();
            boolean inFunc = false;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("/*")) comment.push(1);
                else if (!comment.empty() && line.contains("*/")) {
                    comment.pop();
                }
                if (comment.empty()) {
                    if (isCommented(line)) continue;
                    if (!inFunc) {
                        inFunc = isStaticFunction(line);
                    }
                    if (inFunc) {
                        func += line + "\n";
                        for (int i = 0; i < line.length(); i++) {
                            if (line.charAt(i) == '{') st.push("{");
                            else if (line.charAt(i) == '}') st.pop();
                        }
                        if (st.empty()) {
                            res.add(func);
                            func = "";
                            inFunc = false;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exceptions Caught");
        }
        return res;
    }

    /**
     *
     * @param name tên hàm cần tìm
     * @return nội dung hàm cần tìm
     */
    public static String findFunctionByName(String name) {
        String functionName = getFunctionName(name);
        ArrayList<String> params = getFunctionParams(name);
        boolean found = false;
        for (String s : list) {
            String[] str = s.split("\n");
            String[] words = str[0].split(" ");
            String func;
            for (String word : words) {
                if (word.contains("(")) {
                    func = str[0].substring(str[0].indexOf(word), str[0].indexOf("{") - 1);
                    String funcName = getFunctionName(func);
                    ArrayList<String> funcParams = getFunctionParams1(func);
                    if(functionName.equals(funcName) && params.size() == funcParams.size()) {
                        for(int i = 0; i < params.size(); i++) {
                            if(params.get(i).equals(funcParams.get(i))) {
                                found = true;
                            } else {
                                found = false;
                                break;
                            }
                        }
                    }
                }
            }
            if(found) return s;
        }
        return "";
    }

    /**
     *
     * @param name tên hàm bao gồm cả param theo format 1
     * @return ArrayList gồm tên kiểu biến
     */
    public static ArrayList<String> getFunctionParams1(String name) {
        String paramString = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
        ArrayList<String> params = new ArrayList<>();
        for (String str : paramString.split(",")) {
            params.add(str.split(" ")[0]);
        }
        return params;
    }

    /**
     *
     * @param name tên hàm bao gồm cả param
     * @return tên hàm
     */
    public static String getFunctionName(String name) {
        return name.substring(0, name.indexOf("("));
    }

    /**
     *
     * @param name tên hàm bao gồm cả param theo format 0
     * @return ArrayList gồm tên kiểu biến
     */
    public static ArrayList<String> getFunctionParams(String name) {
        String paramString = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
        ArrayList<String> params = new ArrayList<>();
        for (String str : paramString.split(",")) {
            params.add(str);
        }
        return params;
    }

    /**
     *
     * @param line 1 dòng code
     * @return hàm có phải static hay không
     */
    public static boolean isStaticFunction(String line) {
        String temp = line;
        if(line.contains("//")) {
            temp = temp.substring(0, line.indexOf("//"));
        }
        if(line.contains("/*")) {
            temp = temp.substring(0, line.indexOf("/*"));
        }
        if (temp.contains("static") && temp.contains("(") && temp.contains(")") && temp.contains("{")) return true;
        return false;
    }

    /**
     *
     * @param line 1 dòng code
     * @return dòng code có bị comment 2 gạch hay không
     */
    public static boolean isCommented(String line) {
        if (line.isEmpty()) return false;
        line = line.replaceAll("\\s", "");
        if (line.charAt(0) == '/' && line.charAt(1) == line.charAt(0)) return true;
        return false;
    }

    /**
     * Bubble sort 1 mảng ngẫu nhiên 1000 phần tử
     */
    public static void bubbleSort() {
        double[] arr = new double[1000];
        for(int i = 0; i < 1000; i++) {
            arr[i] = Math.random() * 10000;
        }

        for(int i = 0; i < 1000; i++) {
            for(int j = 1; j < 1000 - i; j++) {
                if(arr[j-1] > arr[j]) {
                    double temp = arr[j-1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        for(int i = 0; i < 1000; i++) {
            System.out.println(arr[i]);
        }
    }

}
