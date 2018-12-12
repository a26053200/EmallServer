package servers.business.businesses;


import com.alibaba.fastjson.JSONObject;
import com.betel.asd.Business;
import com.betel.session.Session;
import com.betel.utils.HttpRequest;
import com.betel.utils.StringUtils;
import consts.Action;
import consts.Bean;
import servers.business.beans.Buyer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servers.business.beans.Profile;

import java.util.HashMap;

/**
 * @ClassName: BuyerBusiness
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/22 0:58
 */
public class BuyerBusiness extends Business<Buyer>
{
    class Field
    {
        public static final String PROBE_STATE = "probe_state"; //探测结果
        public static final String PROBE_MSG = "probe_msg"; //探测消息
        public static final String OPEN_ID = "openid"; //微信openid
        public static final String UNION_ID = "unionid"; //unionid
        public static final String PROFILE_INFO = "profile_info"; //用户
        public static final String BUYER_INFO = "buyer_info"; //买家
        public static final String WEB_CLIENT_ID = "web_client_id"; //Web端ID
        public static final String SCAN_ID = "scan_id"; //扫码ID
        public static final String SCAN_STATE = "scan_state"; //扫码状态
        public static final String TOKEN = "token"; //扫码状态
        public static final String SELLER_INFO = "seller_info"; //卖家
        public static final String profile_id               = "profile_id"; //
    }

    final static Logger logger = LogManager.getLogger(BuyerBusiness.class);

    private final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private final String WX_LOGIN_PARAM = "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private final String APP_ID = "wx4bf12d9a5d200dfb";

    private final String APP_SECRET = "94d170fe988568d97d3cb5e05ee42cfd";

    private HashMap<String, Buyer> buyerMap;

    public BuyerBusiness()
    {
        buyerMap = new HashMap<>();
    }
    @Override
    public void Handle(Session session, String method)
    {
        switch (method)
        {
            case Action.NONE:
                break;
            case Action.MP_PROBE:
                mp_probe(session);
                break;
            case Action.MP_LOGIN_WX_SERVER:
                login_wx_server(session);
                break;
            default:
                break;
        }
    }

    private void mp_probe(Session session)
    {
        JSONObject sendJson = new JSONObject();
        sendJson.put(Field.PROBE_STATE, true);
        sendJson.put(Field.PROBE_MSG, "探测返回.服务器运行正常");
        action.rspdClient(session, sendJson);
    }

    // 微信用户登录
    private void login_wx_server(Session session)
    {
        RecordBusiness recordBusiness = (RecordBusiness) monitor.getAction(Bean.RECORD).getBusiness();
        ProfileBusiness profileBusiness = (ProfileBusiness) monitor.getAction(Bean.PROFILE).getBusiness();
        String nowTime = now();
        String channelId = session.getChannelId();
        String code = session.getRecvJson().getString("code");

        //请求微信登录接口服务器返回OpenId 和 UnionId
        String wxApiRspd = HttpRequest.sendPost(WX_LOGIN_URL, String.format(WX_LOGIN_PARAM, APP_ID, APP_SECRET, code));
        //"openid":"oqZlN5Qw3-Ch1WqidzgW9DX5uGg0","session_key":"8bKQ0+2Seux3Ce6yEs5BNw=="
        logger.info(wxApiRspd);

        // 买家登录信息
        JSONObject loginInfoJson = JSONObject.parseObject(wxApiRspd);
        String errcode = loginInfoJson.getString("errcode");
        if (StringUtils.isNullOrEmpty(errcode))
        {
            // 用户信息
            Profile profile = profileBusiness.profileLogin(session, loginInfoJson);
            loginInfoJson.put(Field.PROFILE_INFO, JSONObject.toJSON(profile));

            // 买家信息
            Buyer buyer = getBuyerInfo(channelId);
            if (buyer == null)
            {
                buyer = service.getEntryById(profile.getId());
                if (buyer == null)
                {//买家第一次登陆
                    buyer = new Buyer();
                    buyer.setId(profile.getId());//公用profile Id
                    buyer.setRegisterTime(nowTime);
                    service.addEntry(buyer);
                }
                buyerMap.put(channelId, buyer);
            }

            loginInfoJson.put(Field.BUYER_INFO, JSONObject.toJSON(buyer));

            recordBusiness.addBuyerLoginRecord(buyer,"微信买家登录");
        }
        action.rspdClient(session, loginInfoJson);
    }

    public Buyer getBuyerInfo(String channelId)
    {
        return buyerMap.get(channelId);
    }
}
