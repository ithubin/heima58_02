package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/9/17.
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    //引入服务层远程对象（从zookeeper注册中心获取对象）
    @Reference(timeout = 1000000)
    private BrandService brandService;


    /**
     * 需求：查询所有品牌数据
     * 参数：无
     * 请求：/brand/findAll
     */
    @RequestMapping("findAll")
    public List<TbBrand> findAll() {
        //调用远程服务方法
        List<TbBrand> list = brandService.findAll();

        return list;
    }

    /**
     * 需求：分页查询品牌数据
     * 参数：Integer page,Integer rows
     * 返回值：包装类对象
     * springmvc restfull传递参数：
     * 1，web.xml 拦截方法
     * 2, springmvc参数绑定
     * findPage/1/10
     * findPage/{page}/{rows}
     */
    @RequestMapping("findPage/{page}/{rows}")
    public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows) {
        //调用远程服务方法，实现品牌分页查询
        PageResult result = brandService.findPage(page, rows);
        return result;
    }

    /**
     * 需求：品牌数据保存
     * 参数传递：
     * angualrJS前台传递参数是：
     * json对象格式 {"id":1,"name":"牙刷"}
     * RequestBody把前台传递的json数据转换成pojo对象
     */
    @RequestMapping("add")
    public PygResult add(@RequestBody TbBrand brand) {
        try {
            //调用远程服务方法，实现品牌保存
            brandService.insert(brand);

            //保存成功
            return new PygResult(true, "保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            //保存失败
            return new PygResult(false, "保存失败");
        }
    }

    /**
     * 需求：根据id查询品牌数据
     */
    @RequestMapping("findOne/{id}")
    public TbBrand findOne(@PathVariable Long id) {
        //调用远程服务方法
        TbBrand brand = brandService.findOne(id);
        return brand;
    }

    /**
     * 需求：品牌数据修改
     */
    @RequestMapping("update")
    public PygResult update(@RequestBody TbBrand brand) {
        try {
            //调用远程服务方法，实现品牌修改
            brandService.update(brand);

            //修改成功
            return new PygResult(true, "修改成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "修改失败");
        }
    }

    /**
     * 需求：品牌数据删除
     */
    @RequestMapping("dele/{ids}")
    public PygResult dele(@PathVariable Long[] ids) {
        try {
            //调用远程服务方法，实现品牌删除
            brandService.delete(ids);
            //返回
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }
    }

    /**
     * 需求：查询品牌数据，进行下拉框列表展示，进行多项选择
     * [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
     */
    @RequestMapping("findBrandList")
    public List<Map> findBrandList() {
        List<Map> brandList = brandService.findBrandList();
        return brandList;
    }

}
