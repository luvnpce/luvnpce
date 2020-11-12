package trivia;

/**
 * 给定一个正整数N，表示有N份情操统一对方在仓库里。
 * 有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
 * 不过是牛还是羊，每一轮能吃的草量必须是：
 * 1，4，16，64（4的次幂）
 * 谁最先把草吃完，谁获胜
 * 假设牛和羊都绝顶聪明，都想赢，谁都会做出理性的决定，根据唯一的参数N，返回谁赢
 */
public class EatGrass {
    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.println(i + ": " + brute(i) + " : " + optimize(i));
        }
    }

    /**
     * 打表找规律
     * 直接写死 O(1)
     */
    public static String optimize(int n) {
        return (n % 5 == 0 || n % 5 == 2) ? "后手" : "先手";
    }

    public static String brute(int n) {
        // 0, 1, 2, 3, 4
        // 后，先，后，先，先
        if (n < 5) {
            return (n == 0 || n == 2) ? "后手" : "先手";
        }
        int base = 1;
        while (base <= n) {
            // 每次进到方法时我们都默认为第一个吃的是先手
            // 所以当第一次进到这个子方法中时，是羊先手，如果该方法返回后手，则代表牛获胜
            if (brute(n - base).equals("后手")) {
                return "先手";
            }
            if (base > n >> 2) {
                // 防止 base * 4后溢出，溢出可能导致 base * 4等于负数
                break;
            }
            base <<= 2;
        }
        return "后手";
    }
}
