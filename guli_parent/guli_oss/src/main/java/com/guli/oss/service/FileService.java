package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface FileService {

    /**
     * 文件上传阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
