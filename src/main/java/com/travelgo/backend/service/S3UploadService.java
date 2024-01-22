package com.travelgo.backend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    /*public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // dirName의 디렉토리가 S3 Bucket 내부에 생성됨

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }*/

    public String upload(MultipartFile multipartFile, String dirName) throws IOException{
        String fileName = dirName + "/" + multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead); //PublicRead 권한 업로드

        amazonS3Client.putObject(putObjectRequest);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    @Transactional
    public void fileDelete(String fileUrl) {
        String[] key = fileUrl.split("/");
        String deleteKey = key[key.length-2]+"/"+key[key.length-1];
        amazonS3Client.deleteObject(bucket,deleteKey);
        log.info(deleteKey);
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        String filePath = "src/main/resources/img/";
        String fileName = file.getOriginalFilename();
        File convertFile = new File(filePath + fileName); // 업로드한 파일의 이름

        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        throw new IOException("파일 생성 실패.");
        //return Optional.empty();
    }
}
