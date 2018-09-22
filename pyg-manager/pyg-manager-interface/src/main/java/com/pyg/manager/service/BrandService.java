package com.pyg.manager.service;

import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;

import java.util.List;

/**
 * Created by on 2018/9/17.
 */
public interface BrandService {

    /**
     * 需求：查询所有品牌
     */
    public List<TbBrand> findAll();
    /**
     * 需求：分页查询品牌数据
     * 参数：Integer page,Integer rows
     * 返回值：包装类对象
     */
    public PageResult findPage(Integer page,Integer rows);
    /**
     * 需求：品牌数据保存
     */
    public void insert(TbBrand brand);
    /**
     * 需求：根据id查询品牌数据
     */
    public TbBrand findOne(Long id);

    /**
     * 需求：品牌数据修改
     */
    public void update(TbBrand brand);
    /**
     * 需求：品牌数据删除
     */
    public void delete(Long[] ids);
}
