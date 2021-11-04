package me.sunpeng;

import org.junit.Test;

import java.util.*;

/**
 * @author sp
 * @date 2021-11-03 10:07
 */
public class JudgeTest {

    @Test
    public void judge(){
        final TreeMap<Integer, Object> treeMap = new TreeMap<>();
        treeMap.put(1,"1");
        treeMap.put(2,"2");
        treeMap.put(4,"4");
        treeMap.put(3,"3");

        final Set<Map.Entry<Integer, Object>> entries = treeMap.entrySet();
        for (Map.Entry<Integer, Object> entry : entries) {
            System.out.println(entry.getValue());
        }
        System.out.println("**********");
        final Set<Integer> integers = treeMap.keySet();
        final TreeSet<Integer> integers1 = new TreeSet<>(integers);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
        final ArrayList<Integer> integers2 = new ArrayList<>(integers);
        for (int i = 0; i < integers2.size()-1; i++) {
            if (integers2.get(i)+1==integers2.get(i+1)){
                System.out.println(integers2.get(i));
                System.out.println("相邻");
            }
        }

    }
}
