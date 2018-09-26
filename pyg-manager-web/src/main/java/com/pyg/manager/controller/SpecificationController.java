package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.SpecificationService;
import com.pyg.pojo.TbSpecification;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;
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
@RequestMapping("/specification")
public class SpecificationController {

    //引入服务层远程对象（从zookeeper注册中心获取对象）
    @Reference(timeout = 1000000)
    private SpecificationService specificationService;


    /**
     * 需求：查询所有规格数据
     * 参数：无
     * 请求：/specification/findAll
     */
    @RequestMapping("findAll")
    public List<TbSpecification> findAll() {
        //调用远程服务方法
        List<TbSpecification> list = specificationService.findAll();

        return list;
    }

    /**
     * 需求：分页查询规格数据
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
        //调用远程服务方法，实现规格分页查询
        PageResult result = specificationService.findPage(page, rows);
        return result;
    }

    /**
     * 需求：规格数据保存
     * 参数传递：
     * angualrJS前台传递参数是：
     * json对象格式 {"id":1,"name":"牙刷"}
     * RequestBody把前台传递的json数据转换成pojo对象
     */
    @RequestMapping("add")
    public PygResult add(@RequestBody Specification specification) {
        try {
            //调用远程服务方法，实现规格保存
            specificationService.insert(specification);

            //保存成功
            return new PygResult(true, "保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            //保存失败
            return new PygResult(false, "保存失败");
        }
    }

    /**
     * 需求：根据id查询规格数据
     */
    @RequestMapping("findOne/{id}")
    public Specification findOne(@PathVariable Long id) {
        //调用远程服务方法
        Specification specification = specificationService.findOne(id);
        return specification;
    }

    /**
     * 需求：规格数据修改
     */
    @RequestMapping("update")
    public PygResult update(@RequestBody Specification specification) {
        try {
            //调用远程服务方法，实现规格修改
            specificationService.update(specification);

            //修改成功
            return new PygResult(true, "修改成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "修改失败");
        }
    }

    /**
     * 需求：规格数据删除
     */
    @RequestMapping("dele/{ids}")
    public PygResult dele(@PathVariable Long[] ids) {
        try {
            //调用远程服务方法，实现规格删除
            specificationService.delete(ids);
            //返回
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }
    }


    /*
     * 需求：查询规格属性，进行多项选择
     * */
    @RequestMapping("findSpecList")
    public List<Map> findSpecList() {
        //调用远程服务方法，查询
        List<Map> specList = specificationService.findSpecList();
        return specList;
    }
}
