package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据parentId查询子节点
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        Criteria criteria =tbItemCatExample.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(tbItemCatExample);
        //创建返回值tbItemCats
        List<EasyUITreeNode> resultList = new ArrayList<>();
        //把列表转换成EasyUITreeNode列表
        for (TbItemCat tbItemCat:list) {
            EasyUITreeNode node = new EasyUITreeNode();
            //设置属性
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加结果列表
            resultList.add(node);
        }
        return resultList;
    }
}
