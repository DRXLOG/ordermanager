package com.drxlog.java.ordermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirRename {
    private String completeName = null;

    public DirRename() {
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }


    public DirRename(List<String> list) {
        HashMap<String, List<String>> keys = new HashMap<>();
        List<String> steps = new ArrayList<>();
        for (String string : list) {
            String[] words = string.split("_");
            if (keys.get(words[0]) != null) {
                List<String> list1 = keys.get(words[0]);

                list1.add(words[1]);

                keys.put(words[0], list1);
            } else {
                String idCase = words[0];
                List<String> list1 = new ArrayList<>();

                if (words.length == 1) {
                    list1.add("NOSTEPS");
                    keys.put(idCase, list1);
                } else {
                    list1.add(words[1]);
                    keys.put(idCase, list1);
                }

            }

        }
        StringBuilder completeName = new StringBuilder();
        System.out.println(keys);
        for (String key : keys.keySet()) {
            if (keys.get(key).get(0).equals("NOSTEPS")) {
                completeName.append(key);
            } else {
                completeName.append(key).append("_");
                for (String step : keys.get(key)) {
                    completeName.append(step);
                }
            }
            completeName.append("+");
        }
        completeName.append("KK");
        this.completeName = completeName.toString();
        System.out.println(completeName);

    }
}
