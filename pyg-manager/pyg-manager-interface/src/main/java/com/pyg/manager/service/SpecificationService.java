package com.pyg.manager.service;

import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecification;
import com.pyg.utils.PageResult;
import com.pyg.vo.Specification;

import java.util.List;

/**
 * Created by on 2018/9/17.
 */
public interface SpecificationService {

    /**
     * 需求：查询所有规格
     */
    public List<TbSpecification> findAll();
    /**
     * 需求：分页查询规格数据
     * 参数：Integer page,Integer rows
     * 返回值：包装类对象
     */
    public PageResult findPage(Integer page, Integer rows);
    /**
     * 需求：规格数据保存
     */
    public void insert(Specification specification);
    /**
     * 需求：根据id查询规格数据
     */
    public Specification findOne(Long id);

    /**
     * 需求：规格数据修改
     */
    public void update(Specification specification);
    /**
     * 需求：规格数据删除
     */
    public void delete(Long[] ids);
}
