package com.group.bbq.service.impl;

import com.group.bbq.common.lang.Result;
import com.group.bbq.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${upload.path}")
    private String path;

    @Override
    public Result upload(MultipartFile file, HttpServletRequest request) {
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        String oldName = file.getOriginalFilename();
        if (StringUtils.isEmpty(oldName)) {
            return Result.fail("Invalid file name.");
        }
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(file1, newName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/" + newName;
            System.out.println(url);
            return Result.succ(url);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
}
