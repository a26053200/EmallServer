package servers.business;

import com.betel.config.ServerConfigVo;
import com.betel.servers.action.ImplAction;
import com.betel.servers.node.NodeServerMonitor;
import consts.Bean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servers.business.beans.*;
import servers.business.businesses.*;

/**
 * @ClassName: GateMonitor
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/8 23:01
 */
public class BusinessMonitor extends NodeServerMonitor
{
    final static Logger logger = LogManager.getLogger(BusinessMonitor.class);

    public BusinessMonitor(ServerConfigVo serverCfgInfo)
    {
        super(serverCfgInfo);
        actionMap.put(Bean.RECORD,      new ImplAction<>(this, Bean.RECORD,     Record.class,       new RecordBusiness()));
        actionMap.put(Bean.PROFILE,     new ImplAction<>(this, Bean.PROFILE,    Profile.class,      new ProfileBusiness()));
        actionMap.put(Bean.BUYER,       new ImplAction<>(this, Bean.BUYER,      Buyer.class,        new BuyerBusiness()));
        actionMap.put(Bean.SELLER,      new ImplAction<>(this, Bean.SELLER,     Seller.class,       new SellerBusiness()));
        actionMap.put(Bean.BRAND,       new ImplAction<>(this, Bean.BRAND,      Brand.class,        new BrandBusiness()));
        actionMap.put(Bean.CATEGORY,    new ImplAction<>(this, Bean.CATEGORY,   Category.class,     new CategoryBusiness()));
        actionMap.put(Bean.SPEC,        new ImplAction<>(this, Bean.SPEC,       Spec.class,         new SpecBusiness()));
        actionMap.put(Bean.SPEC_VALUE,  new ImplAction<>(this, Bean.SPEC_VALUE, SpecValue.class,    new SpecValueBusiness()));
    }
}
