package org.springframework.samples.peddler.images;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir = "/var/www/html/uploads";

    public String getUploadDir() {
    	uploadDir = "/var/www/html/uploads";
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}