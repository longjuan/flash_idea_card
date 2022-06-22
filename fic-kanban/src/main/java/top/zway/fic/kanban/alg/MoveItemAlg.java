package top.zway.fic.kanban.alg;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ZZJ
 */
public class MoveItemAlg {
    public static Double countNewOrder(List<Double> orders, int getSize, boolean down) {
        double newOrder;
        if (orders.size() < getSize) {
            // 移动距离超过了
            Double last = orders.get(orders.size() - 1);
            // 加减 9-11
            double v = ThreadLocalRandom.current().nextDouble(2);
            v += 9;
            newOrder = last + (down ? v : -v);
        } else {
            // 移动后在两个item之间
            Double first = orders.get(orders.size() - 1);
            Double second = orders.get(orders.size() - 2);
            // down: 4 *5 6*   up: 5 *4 3*
            // 除以质数可以最大程度利用小点数后的精度
            double abs = Math.abs(second - first);
            double v = abs / 2.3;
            Double curr = orders.get(0);
            // 防止同时多个元素移动到同样的两个中间
            double relative = (second - curr) / (first - curr) * abs / 19;
            relative = first > second ? relative : -relative;
            newOrder = second + relative + (down ? +v : -v);
        }
        return newOrder;
    }
}
