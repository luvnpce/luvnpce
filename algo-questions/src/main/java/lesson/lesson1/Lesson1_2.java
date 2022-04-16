package lesson.lesson1;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个文件目录的路径,写一个函数统计这个目录下所有的文件数并返回
 */
public class Lesson1_2 {

    public static void main(String[] args) {
        String path = "/Users/dannyzou/Desktop";
        System.out.println(solution(path));
    }

    /**
     * 用BFS（队列）
     */
    public static int solution(String path) {
        File file = new File(path);
        if (!file.isDirectory() && !file.isFile()) {
            return 0;
        }
        if (file.isFile()) {
            return 1;
        }
        Queue<File> queue = new LinkedList<>();
        queue.add(file);
        int result = 0;
        while (!queue.isEmpty()) {
            File folder = queue.poll();
            for (File child : folder.listFiles()) {
                if (child.isFile()) {
                    result++;
                } else if (child.isDirectory()) {
                    queue.offer(child);
                }
            }
        }
        return result;
    }
}
