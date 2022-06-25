package top.zway.fic.base.constant;

/**
 * @author ZZJ
 */
public class RedisConstant {

    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    public static final String KANBAN_CACHE = "CACHE:KANBAN_";

    public static final long KANBAN_CACHE_EXPIRE_SECOND = 300L;

    public static final String COOPERATING_KANBAN_STATISTIC = "STATISTIC:COOPERATING_KANBAN_";

    public static final long COOPERATING_KANBAN_STATISTIC_EXPIRE_SECOND = 300L;

    public static final String KANBAN_FULL_UPDATE_TIMER_PREFIX = "TIMER:KANBAN_FULL_UPDATE_";

    public static final long KANBAN_FULL_UPDATE_TIMER_EXP_TIME = 60 * 20;

    public static final String RSA_PRIVATE_KEY = "RSA:PRIVATE_KEY_";

    public static final long RSA_PUBLIC_KEY_EXP_TIME = 60 * 10;

    public static final String CAPTCHA_CODE = "CAPTCHA:CODE_";

    public static final long CAPTCHA_CODE_EXP_TIME = 60 * 10;

}