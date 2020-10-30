package dp;

import java.util.LinkedList;
import java.util.List;

/**
 * 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6
 * a b c d e f g h i j k l n m o p q r s t u v w x y z
 *
 * 给一个纯数字的字符串，把字符串按照上面的形式转换成字母
 */
public class ConvertLetterToString {

    public static void main(String[] args) {
        char[] dict = "?abcdefghijklnmopqrstuvwxyz".toCharArray();
        String str = "11111"; // abc, lc, aw
        char[] input = str.toCharArray();
        String path = "";
        List<String> res = new LinkedList<>();
        brute(dict, input, 0, path, res);
        for (String s : res) {
            System.out.println(s);
        }
    }


    public static void brute(char[] dict, char[] input, int index, String path, List<String> res) {
        if (index == input.length) {
            res.add(path);
            return;
        }
        if (input[index] == '0') {
            return;
        }
        if (input[index] == '1') {
            String one = path + dict[input[index] - '0'];
            brute(dict, input, index + 1, one, res);
            if (index + 1 < input.length) {
                String two = path + dict[(input[index] - '0') * 10 + (input[index+1] - '0')];
                brute(dict, input, index + 2, two, res);
            }
            return;
        }
        if (input[index] == '2') {
            String one = path + dict[input[index] - '0'];
            brute(dict, input, index + 1, one, res);
            if (index + 1 < input.length && (input[index + 1] >= '0' && input[index + 1] <= '6')) {
                String two = path + dict[(input[index] - '0') * 10 + (input[index+1] - '0')];
                brute(dict, input, index + 2, two, res);
            }
            return;
        }
        String one = path + dict[input[index] - '0'];
        brute(dict, input, index + 1, one, res);
    }
}
