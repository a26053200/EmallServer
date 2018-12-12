package servers.business.businesses;


import com.betel.asd.Business;
import com.betel.consts.FieldName;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import servers.business.beans.Brand;

/**
 * @ClassName: BuyerAction
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 23:00
 */
public class BrandBusiness extends Business<Brand>
{

    @Override
    public Brand newEntry(Session session)
    {
        String nowTime = now();

        Brand brandInfo = new Brand();
        brandInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        brandInfo.setName(session.getRecvJson().getString(FieldName.NAME));
        brandInfo.setAddTime(nowTime);
        brandInfo.setUpdateTime(nowTime);
        return  brandInfo;
    }

    @Override
    public Brand updateEntry(Session session)
    {
        Brand brandInfo = service.getEntryById(session.getRecvJson().getString(FieldName.ID));
        if(brandInfo != null)
        {
            brandInfo.setName(session.getRecvJson().getString(FieldName.NAME));
            brandInfo.setUpdateTime(now());
        }
        return brandInfo;
    }
}
