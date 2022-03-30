package top.zway.fic.kanban.alg;

import java.util.List;

/**
 * @author ZZJ
 */
public class MoveItemAlg {
    public static Double countNewOrder(List<Double> orders, int getSize, boolean down) {
        double newOrder;
        if (orders.size() < getSize) {
            // 移动距离超过了
            Double last = orders.get(orders.size() - 1);
            newOrder = last + (down ? 1 : -1);
        } else {
            // 移动后在两个item之间
            Double first = orders.get(orders.size() - 1);
            Double second = orders.get(orders.size() - 2);
            // down: 4 *5 6*   up: 5 *4 3*
            // 除以7可以最大程度利用小点数后的精度
            double v = (Math.abs(second - first)) / 7;
            newOrder = second + (down ? v : -v);
        }
        return newOrder;
    }
}
