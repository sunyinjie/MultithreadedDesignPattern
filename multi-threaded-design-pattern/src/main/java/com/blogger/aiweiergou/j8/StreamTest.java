package com.blogger.aiweiergou.j8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

/**
 * Created by user on 2017/10/5.
 */
public class StreamTest {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            arr.add(new Random().nextInt() * i++);
        }
        System.out.println(arr);

        List<Integer> arr2 = arr.stream().filter(a -> a > 0).collect(toList());
        System.out.println(arr2);

        long count = arr2.stream().count();
        System.out.println(count);

        System.out.println(arr2.stream().sorted().collect(toList()));
    }
}
