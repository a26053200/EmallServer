package consts;

import com.betel.config.ServerConfigVo;

import java.util.HashMap;

/**
 * @ClassName: ServerConfig
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/12/3 0:01
 */
public class ServerConfig
{
    private static HashMap<String, ServerConfigVo> s_cfgMap;

    /**
     * 获取服务器配置
     * @param serverName
     * @return
     */
    public static ServerConfigVo getServerConfig(String serverName)
    {
        if(s_cfgMap == null)
        {
            s_cfgMap = new HashMap<>();

            /** HTTP网关服务器配置 **/
            s_cfgMap.put(ServerName.HTTP_GATE_SERVER,
                    new ServerConfigVo(ServerName.HTTP_GATE_SERVER,
                            "127.0.0.1",8001, //服务器地址
                            "127.0.0.1",6379));//数据库地址

            /** 均衡服务器配置 **/
            s_cfgMap.put(ServerName.BALANCE_SERVER,
                    new ServerConfigVo(ServerName.BALANCE_SERVER,
                            "127.0.0.1",8010, //服务器地址
                            "127.0.0.1",6379));//数据库地址

            /** 业务服务器配置 **/
            s_cfgMap.put(ServerName.BUSINESS_SERVER,
                    new ServerConfigVo(ServerName.BUSINESS_SERVER,
                            "127.0.0.1",8011, //服务器地址
                            "127.0.0.1",6379,
                            2));//数据库索引
        }
        return s_cfgMap.get(serverName);
    }
}
