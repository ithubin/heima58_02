package com.pyg.mgb;

import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbBrandExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by on 2018/9/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-dao.xml")
public class MgbTest {

    //注入mapper接口代理对象
    @Autowired
    private TbBrandMapper brandMapper;

    /**
     * 需求：品牌表添加
     */
    @Test
    public void add() {

        //创建对象
        TbBrand brand = new TbBrand();
        brand.setName("范姐");
        brand.setFirstChar("F");
        //保存
        brandMapper.insertSelective(brand);
    }

    /**
     * 需求：品牌表修改
     */
    @Test
    public void update() {

        //创建对象
        TbBrand brand = new TbBrand();
        brand.setId(23L);
        brand.setName("番茄");
        brand.setFirstChar("F");
        //保存
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 需求：品牌表删除
     */
    @Test
    public void delete() {

        //删除
        brandMapper.deleteByPrimaryKey(23L);
    }

    /**
     * 需求：查询所有
     */
    @Test
    public void findAll(){
        //查询所有
        List<TbBrand> list = brandMapper.selectByExample(null);

    }

    /**
     * 需求：条件查询 AND
     */
    @Test
    public void findBrandByCondition(){

        //创建example对象，使用example创建criteria对象，设置查询条件
        //每一个pojo对应他自己的example
        TbBrandExample example = new TbBrandExample();
        //创建criteria对象
        TbBrandExample.Criteria criteria = example.createCriteria();
        //设置参数
        criteria.andNameLike("%海%");
        criteria.andIdEqualTo(17L);

        //查询所有
        List<TbBrand> list = brandMapper.selectByExample(example);

    }

    /**
     * 需求：条件查询 OR
     */
    @Test
    public void findBrandByConditionOR(){

        //创建example对象，使用example创建criteria对象，设置查询条件
        //每一个pojo对应他自己的example
        TbBrandExample example = new TbBrandExample();
        //创建criteria对象
        example.or().andNameLike("%海%");
        example.or().andIdEqualTo(12L);
        //查询所有
        List<TbBrand> list = brandMapper.selectByExample(example);

    }


}
