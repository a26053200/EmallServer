package servers.business.businesses;


import com.betel.asd.Business;
import com.betel.consts.FieldName;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.TimeUtils;
import servers.business.beans.Category;

import java.util.Date;

/**
 * @ClassName: BuyerAction
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 23:00
 */
public class CategoryBusiness extends Business<Category>
{
    private static final String ViceKey = "pid";
    @Override
    public String getViceKey()
    {
        return ViceKey;
    }
    @Override
    public Category newEntry(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());

        Category categoryInfo = new Category();
        categoryInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        categoryInfo.setPid(session.getRecvJson().getString(ViceKey));
        categoryInfo.setName(session.getRecvJson().getString(FieldName.NAME));
        categoryInfo.setAddTime(nowTime);
        categoryInfo.setUpdateTime(nowTime);

        return categoryInfo;
    }

    @Override
    public Category updateEntry(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());
        Category categoryInfo = service.getEntryById(session.getRecvJson().getString(FieldName.ID));
        if(categoryInfo != null)
        {
            categoryInfo.setName(session.getRecvJson().getString(FieldName.NAME));
            categoryInfo.setUpdateTime(nowTime);
        }
        return categoryInfo;
    }
}
