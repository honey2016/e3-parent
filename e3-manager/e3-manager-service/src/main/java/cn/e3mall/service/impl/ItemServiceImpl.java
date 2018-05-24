package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItem getItemById(long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //区总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
         //生成商品id
        long itemId = IDUtils.genItemId();
        //补全item属性
        item.setId(itemId);
        //1 正常 2 下架 3 删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建一个商品描述对应的pojo对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //向商品描述插入数据
        tbItemMapper.insert(tbItemDesc);
        //返回成功
        return E3Result.ok();
    }
}
