package com.microdev.Controller;

import com.microdev.common.oss.ObjectStoreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URLDecoder;

public class Test {

    @Autowired
    private ObjectStoreService objectStoreService;

    public void uploadApp() throws Exception{
        // app 上传
        File file;
        String fileURI = null;
        String filePath;
        String  path = getClass().getResource("/").getFile();
        path = URLDecoder.decode(path,  "utf-8");
        file = new File( path, File.separator + "static" + File.separator +  "app-release.apk");
        filePath = "WGB".toLowerCase() + "/" + "Android";
        fileURI = objectStoreService.uploadFile(filePath, file);
        System.out.println ("fileURI:"+fileURI);
    }
}
