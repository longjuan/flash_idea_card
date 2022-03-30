package top.zway.fic.base.constant;

/**
 * @author ZZJ
 */
public class AuthConstant {
    /**
     * 请求头key
     */
    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    /**
     * authorities的前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";

    /**
     * 令牌声明的名称
     */
    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 主token过期时间
     */
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 2;

    /**
     * 刷新token过期时间
     */
    public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7;

    /**
     * 请求头开头
     */
    public static final String TOKEN_REQUEST_HEADER_START_WITH = "Bearer ";
}
