package com.yeahitsSuri.booksharing.file;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

  @Value("${spring.application.file.upload.photos-output-path}")
  private String fileUploadPath;

  public String saveFile(
          @NotNull MultipartFile sourceFile,
          @NotNull int userId) {
    final String fileUploadSubPath = "users" + File.separator + userId;
    return uploadFile(sourceFile, fileUploadSubPath);

  }

  private String uploadFile(
          @NotNull  MultipartFile sourceFile,
          @NotNull String fileUploadSubPath) {
    final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
    File targetFolder = new File(finalUploadPath);
    if (!targetFolder.exists()) {
      boolean folderCreated = targetFolder.mkdirs();
      if (!folderCreated) {
        log.warn("Failed to create folder {}", targetFolder.getAbsolutePath());
        return null;
      }
    }
    final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
    // ,uploads/user/1/235636.jpg
    String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis();
    Path targetPath =  Paths.get(targetFilePath);
    try {
      Files.write(targetPath, sourceFile.getBytes());
      log.info("Saved file to {}", targetFilePath);
      return targetFilePath;
    } catch (IOException e) {
      log.error("Failed to save file", e);
    }
    return null;
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return "";
    }
    int lastDotIndex = fileName.lastIndexOf(".");
    // does not have extension
    if (lastDotIndex == -1) {
      return "";
    }
    //.JPG -> .jpg
    return fileName.substring(lastDotIndex + 1).toLowerCase();
  }
}
