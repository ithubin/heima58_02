package com.pyg.fdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by on 2018/9/26.
 */
public class MyFastDfs {

    /**
     * 需求：使用fastdfs实现图片上传
     */
    @Test
    public void uploadPic() throws Exception{

        //指定图片服务器连接地址
        String conf = "C:\\workspace\\heima58\\pyg-shop-web\\" +
                "src\\main\\resources\\conf\\client.conf";
        //指定上传图片
        String picPath = "C:\\images\\a.png";

        //创建配置文件，连接图片服务器
        ClientGlobal.init(conf);

        //创建服务器调度客户端对象
        TrackerClient tc = new TrackerClient();
        //从TrackerClient获取服务对象
        TrackerServer trackerServer = tc.getConnection();


        //创建storage存储客户端对象
        StorageServer storageServer=null;
        StorageClient sc = new StorageClient(trackerServer, storageServer);

        //上传图片
        String[] urls = sc.upload_file(picPath, "png", null);

        for (String url : urls) {

            System.out.println(url);
        }




    }

}
