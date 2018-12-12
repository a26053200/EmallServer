package servers.business;

import com.betel.common.Monitor;
import com.betel.config.ServerConfigVo;
import com.betel.servers.forward.ServerClient;
import com.betel.servers.node.NodeServer;
import consts.ServerConfig;
import consts.ServerName;

/**
 * @ClassName: BusinessServer
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/8 23:47
 */
public class BusinessServer extends NodeServer
{

    public BusinessServer(ServerConfigVo serverConfig,  Monitor monitor)
    {
        super(serverConfig, monitor);
    }

    public static void main(String[] args) throws Exception
    {
        ServerConfigVo srvCfg = ServerConfig.getServerConfig(ServerName.BUSINESS_SERVER);
        ServerConfigVo centerSrvCfg = ServerConfig.getServerConfig(consts.ServerName.BALANCE_SERVER);
        BusinessMonitor mnt = new BusinessMonitor(srvCfg);
        BusinessServer server = new BusinessServer(srvCfg, mnt);
        server.setCenterServerCfg(centerSrvCfg);
        server.setServerClient(new ServerClient(centerSrvCfg, mnt));
        server.run();
    }
}
