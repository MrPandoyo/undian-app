package com.lomba.undianapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@Service
public class FileService {

    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    @Value("${app.folder.upload}")
    public String uploadDir;

    public File moveCsv(MultipartFile multipartFile) {
        try {
            String basicFolderUrl = uploadDir + File.separator + "file-csv" + File.separator;
            File basicFolder = new File(basicFolderUrl);
            if (!basicFolder.exists()) {
                logger.info("Creating basic folder : [{}]", basicFolder.getAbsolutePath());
                Files.createDirectories(basicFolder.toPath());
            } else {
                logger.info("Basic folder already exist : [{}]", basicFolder.getAbsolutePath());
            }

            String timestamp = yyyyMMddHHmmss.format(new Date());

            File originalFile = new File(basicFolderUrl + timestamp + "-" + multipartFile.getOriginalFilename());
            logger.info("original File [{}]", originalFile.getPath());
            Files.copy(multipartFile.getInputStream(), originalFile.toPath());

            return originalFile;
        } catch (IOException ex) {
            logger.error("=== ERROR : [{}] ===", ex.getMessage());
            return null;
        }

    }

}
