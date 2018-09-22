package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.SpecificationService;
import com.pyg.mapper.TbSpecificationMapper;
import com.pyg.mapper.TbSpecificationOptionMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.utils.PageResult;
import com.pyg.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by on 2018/9/17.
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    //注入接口mapper对象
    @Autowired
    private TbSpecificationMapper specificationMapper;

    //注入规格属性mapper接口代理对象
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 需求：查询所有规格
     */
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 需求：分页查询规格数据
     * 参数：Integer page,Integer rows
     * 返回值：包装类对象
     */
    public PageResult findPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);
        //直接查询
        Page<TbSpecification> pageInfo = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        //返回分页包装类对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    /**
     * 需求：规格数据保存
     */
    public void insert(Specification specification) {

        //获取规格对象，实现规格保存
        TbSpecification specification1 = specification.getSpecification();
        //执行保存
        specificationMapper.insertSelective(specification1);

        //保存规格选项
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        //遍历集合，实现规格选项保存
        for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
            //设置外键
            tbSpecificationOption.setSpecId(specification1.getId());
            //保存规格属性
            specificationOptionMapper.insertSelective(tbSpecificationOption);


        }
    }

    /**
     * 需求：根据id查询规格数据
     */
    public Specification findOne(Long id) {
        //查询规格对象
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        //根据外键查询规格选项
        //创建example对象
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        //创建criteria对象
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        //设置查询参数
        criteria.andSpecIdEqualTo(id);
        //执行查询
        List<TbSpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);

        //创建包装类对象，封装查询结果
        Specification specification =  new Specification();
        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(specOptionList);

        return specification;
    }

    /**
     * 需求：规格数据修改
     */
    public void update(Specification specification) {
        //获取规格对象，先修改规格
        TbSpecification specification1 = specification.getSpecification();
        specificationMapper.updateByPrimaryKeySelective(specification1);

        //创建规格选项example对象
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        //创建criteria对象
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        //设置查询参数
        criteria.andSpecIdEqualTo(specification1.getId());
        //先删除规格属性,根据外键删除
        specificationOptionMapper.deleteByExample(example);


        //获取规格选项，再实现保存
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();

        //循环遍历规格选项，实现保存
        for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
            //设置外键
            tbSpecificationOption.setSpecId(specification1.getId());
            //保存规格属性
            specificationOptionMapper.insertSelective(tbSpecificationOption);


        }

    }

    /**
     * 需求：规格数据删除
     */
    public void delete(Long[] ids) {
        //循环遍历删除
        for (Long id : ids) {

            //删除规格选项

            //创建规格选项example对象
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            //创建criteria对象
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            //设置查询参数
            criteria.andSpecIdEqualTo(id);
            //删除规格选项
            specificationOptionMapper.deleteByExample(example);

            //删除规格
            specificationMapper.deleteByPrimaryKey(id);
        }
    }


}
