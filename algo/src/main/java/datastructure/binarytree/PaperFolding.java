package datastructure.binarytree;

/**
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折一次，即折痕突起的方向指向纸条的背面。只晓得下边向上方连续对折两次，压出折痕后展开，此时有三条折痕，从上到下一次是下折痕，下折痕和上折痕。
 * 给定一个输入参数N，代表纸条从下向上连续对折N次。请从上到下打印折痕的防线。
 * 例如：N=1时，打印down
 * 	    N=2时，打印down down up
 *
 * 其实就是中序排序
 */
public class PaperFolding {

    public static void main(String[] args) {
        int n = 3;
        print(3);
    }

    private static void print(int n) {
        doPrint(1, n, true);
    }

    /**
     *
     * @param i 当前第几层
     * @param n 总共多少层
     * @param down 是否是下折痕
     */
    private static void doPrint(int i, int n, boolean down) {
        if (i > n) {
            return;
        }
        doPrint(i+1, n, true);
        System.out.println(down ? "down" : "up");
        doPrint(i+1, n, false);
    }
}
