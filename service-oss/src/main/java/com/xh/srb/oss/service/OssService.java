package com.xh.srb.oss.service;

import java.io.InputStream;

public interface OssService {

    String upload(String filename, String model, InputStream inputStream);

    void removeFile(String url);
}
