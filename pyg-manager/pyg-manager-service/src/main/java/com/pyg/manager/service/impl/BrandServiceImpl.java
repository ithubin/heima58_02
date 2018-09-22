package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.BrandService;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by on 2018/9/17.
 */
@Service
public class BrandServiceImpl implements BrandService {

    //注入接口mapper对象
    @Autowired
    private TbBrandMapper brandMapper;

    /**
     * 需求：查询所有品牌
     */
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }

    /**
     * 需求：分页查询品牌数据
     * 参数：Integer page,Integer rows
     * 返回值：包装类对象
     */
    public PageResult findPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);
        //直接查询
        Page<TbBrand> pageInfo = (Page<TbBrand>) brandMapper.selectByExample(null);
        //返回分页包装类对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    /**
     * 需求：品牌数据保存
     */
    public void insert(TbBrand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 需求：根据id查询品牌数据
     */
    public TbBrand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 需求：品牌数据修改
     */
    public void update(TbBrand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 需求：品牌数据删除
     */
    public void delete(Long[] ids) {
        //循环遍历删除
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }


}
