package consts;

/**
 * @ClassName: Action
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/12/8 0:55
 */
public class Action
{
    /**
     * 空的行为
     */
    public final static String NONE = "none";

    /**
     * 小程序探测服务器是否开启
     */
    public final static String MP_PROBE = "mp_probe";

    /**
     * 小程序通过服务器登录微信官方接口服务器
     */
    public final static String MP_LOGIN_WX_SERVER = "mp_login_wx_server";

    /**
     * 小程序用户获取个人信息
     */
    public final static String MP_GET_PROFILE = "mp_get_profile";

    /**
     * 小程序用扫码web端登录
     */
    public final static String MP_SCAN_WEB_LOGIN = "mp_scan_web_login";

    /**
     * Web管理页面探测服务器是否开启
     */
    public final static String WEB_ADMIN_PROBE = "mp_probe";

    /**
     * web端请求登录的二维码
     */
    public final static String WEB_RQST_SCAN_CODE_LOGIN = "web_rqst_login_QR_code";

    /**
     * web端扫码登录
     */
    public final static String WEB_SCAN_LOGIN = "web_scan_login";

    /**
     * web端直接登录
     */
    public final static String WEB_QUICK_LOGIN = "web_quick_login";

}
