package com.qiumu.controll;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.qiumu.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${takeOut.path}")
    private String path;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID() +suffix;

        File dir = new File(path);

        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    @GetMapping("/download")
    public void Get(HttpServletResponse response,String name){
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            if ("null".equals(name) || "".equals(name)){
                return;
            }
            File file = new File(path + name);
            if (!file.exists()){
                log.error(file.getAbsolutePath()+"图片文件不存在！");
                return;
            }
            FileInputStream fileInputStream =new FileInputStream(file);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
