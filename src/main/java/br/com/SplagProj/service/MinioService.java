package br.com.SplagProj.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(MinioClient minioClient, @Value("${minio.bucket}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file) throws Exception {
        try {
            // Verifica se o bucket existe, se não, cria
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // Gera um nome único para o arquivo
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Faz o upload do arquivo
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return fileName;
        } catch (Exception e) {
            log.error("Erro ao fazer upload do arquivo: ", e);
            throw new RuntimeException("Erro ao fazer upload do arquivo", e);
        }
    }

    public String getPresignedUrl(String fileName) throws Exception {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .method(Method.GET)
                            .expiry(5, TimeUnit.MINUTES)
                            .build()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar URL pré-assinada: ", e);
            throw new RuntimeException("Erro ao gerar URL pré-assinada", e);
        }
    }

    public void deleteFile(String fileName) throws Exception {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo: ", e);
            throw new RuntimeException("Erro ao deletar arquivo", e);
        }
    }
} 