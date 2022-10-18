package com.group.bbq.service;

import com.group.bbq.common.lang.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {
    Result upload(MultipartFile file, HttpServletRequest request);

}
