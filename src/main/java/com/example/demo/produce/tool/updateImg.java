package com.example.demo.produce.tool;


import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin("*")
@RestController
public class updateImg {
   @Autowired
   Environment environment;

    public String getPort(){
        return environment.getProperty("local.server.port");
    }
    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String s = StringUtils.cleanPath(file.getOriginalFilename());
        String realFormat= ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String replace = UUID.randomUUID().toString().replace("-", "")+".jpg";
        String path = realFormat+"/"+format;
        File file1 = new File(path);
        if (!file1.exists()) file1.mkdirs();
        file.transferTo(new File(path,replace));
        String result="http://localhost:"+getPort()+"/files/"+format+"/"+replace;
        Map map = new HashMap<String,Object>();
        map.put("msg","ok");
        map.put("code",200);
        map.put("imgPath",result);
        return map;
    }
}
