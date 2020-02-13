package com.slin.study.kotlin.support;

import java.io.File;
import java.util.List;

/**
 * author: slin
 * date: 2020/2/13
 * description:可空性
 */
public class CollectionUtils {

    public static List<String> uppercaseAll(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, items.get(i).toUpperCase());
        }
        return items;
    }

    public interface FileContentProcessor {
        void processContents(File path, byte[] binaryContents, List<String> textContents);
    }

    public interface DataParser<T> {
        void parseData(String input, List<T> output, List<String> errors);
    }

}
