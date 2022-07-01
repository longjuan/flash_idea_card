package top.zway.fic.base.constant;

/**
 * @author ZZJ
 */
public class RabbitMqConstants {
    /**
     * 部分数据更新入口交换机名称
     */
    public static final String DATA_UPDATE_EXCHANGE_NAME = "data_update_fanout_exchange";
    /**
     * 部分数据更新队列名称
     */
    public static final String DATA_UPDATE_QUEUE_NAME = "data.update.queue";
    /**
     * 全部数据更新延迟队列名称
     */
    public static final String FULL_UPDATE_TTL_QUEUE_NAME = "full.update.ttl.queue";
    /**
     * 全部数据更新死信交换机名称
     */
    public static final String FULL_UPDATE_DEAD_EXCHANGE_NAME = "full_update_dead_fanout_exchange";
    /**
     * 全部数据更新死信队列名称
     */
    public static final String FULL_UPDATE_DEAD_QUEUE_NAME = "full.update.dead.queue";
    /**
     * 全部数据更新TTL
     */
    public static final int FULL_UPDATE_TTL_MILLISECOND = 1000 * 60 * 30;
    /**
     * 全部数据更新不延迟入口交换机名称
     */
    public static final String FULL_UPDATE_TTL_EXCHANGE_NAME = "full_update_ttl_fanout_exchange";
    /**
     * 部分更新队列最大长度
     */
    public static final int DATA_UPDATE_WAITING_MAX_LENGTH = 10000;
    /**
     * 全部数据更新延迟队列最大长度
     */
    public static final int FULL_UPDATE_WAITING_MAX_LENGTH = 1000;
    /**
     * 邮件发送交换机名称
     */
    public static final String MAIL_SEND_EXCHANGE_NAME = "mail_send_fanout_exchange";
    /**
     * 邮件发送队列名称
     */
    public static final String MAIL_SEND_QUEUE_NAME = "mail.send.queue";
}
