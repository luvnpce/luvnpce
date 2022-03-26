package datastructure.binarytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class demo {

//    public static void main(String[] args) {
//        Integer[] ints = {6,2,4,7,5,3,1};
//        LinkedList<Integer> source = new LinkedList(Arrays.asList(ints));
//        System.out.println(source.toString());
//        LinkedList<Integer> result = new LinkedList<>();
//
//        reverse(source, result);
//        System.out.println(result.toString());
//    }
//
//    public static void reverse(LinkedList<Integer> source, LinkedList<Integer> result) {
//        for(int i =0 ;i< source.size()-1;i++){
//            Integer item = source.get(i);
//            result.addFirst(item);
//            Integer temp = result.pollLast();
//            result.addFirst(temp);
//        }
//        result.addFirst(source.pollLast());
//    }

    public static void main(String[] args) {
        Integer[] ints = {2,4,6,3,1,5};
        LinkedList<Integer> integers = new LinkedList();
        for (int i = 0; i < ints.length; i++) {
            integers.addLast(i);
        }

        ArrayList<Integer> indexs = new ArrayList<>();
        while(!integers.isEmpty()) {
            Integer integer = integers.pollFirst();
            integers.addLast(integer);
            Integer index = integers.pollFirst();
            indexs.add(index);
        }

        int[] res = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            res[indexs.get(i)] = ints[i];
        }

        System.out.println(Arrays.toString(res));
    }
}
