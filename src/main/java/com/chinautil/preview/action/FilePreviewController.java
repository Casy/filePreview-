package com.chinautil.preview.controller;

import com.chinautil.preview.utils.FilePreviewUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 测试文件在线预览的控制层
 * service和dao层这里省略
 * @author 福小林
 **/
@RestController
public class FilePreviewController {
    private static final Log LOG = LogFactory.getLog(FilePreviewController.class);


     /**
     * 文件预览
     * @param id 文件保存时名称
     * @param name 文件原名
     * @param ext  文件后缀
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inline")
    public String inline(String newName,String name,String ext,HttpServletRequest request,HttpServletResponse response){

        //savepath为自己设置的文件保存路径
        return FileUtils.inline(BaseConfig.uploadPath + "flow_task", name, newName, ext,request,response);

    }
}
