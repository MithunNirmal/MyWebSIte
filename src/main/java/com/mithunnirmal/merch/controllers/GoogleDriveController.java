package com.mithunnirmal.merch.controllers;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/drive")
public class GoogleDriveController {

    @Autowired
    private Drive driveService;

    @GetMapping("/files")
    public FileList listFiles() throws IOException {
        return driveService.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());

        InputStream inputStream = file.getInputStream();
        File uploadedFile = driveService.files().create(fileMetadata, new com.google.api.client.http.InputStreamContent(file.getContentType(), inputStream))
                .setFields("id")
                .execute();

        return "File ID: " + uploadedFile.getId();
    }
}

