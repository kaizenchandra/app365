package com.synechisveltiosi.apis.app365.common.files;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/3/18.
 */
@Component
public class FileSystem {

    private static final String S3_BUCKET_NAME = "";

    @Value("${app365.digitalocean.spaces.endpoint}")
    private String DO_SPACES_ENDPOINT;

    @Autowired
    private AmazonS3 s3Client;

    public String store(MultipartFile multipartFile, String directory, String fileName) throws IOException, URISyntaxException {

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String key = directory + "/" + fileName + "." + extension;
        File fileToUpload = convertFromMultiPartToFile(multipartFile);
        s3Client.putObject(new PutObjectRequest(S3_BUCKET_NAME, key, fileToUpload)
                .withCannedAcl(CannedAccessControlList.PublicRead));


        fileToUpload.delete();
        return String.format("%s/%s", DO_SPACES_ENDPOINT, key);
    }

    public void delete(String fullFileName) {

        String fileName = fullFileName.replace(String.format("%s/", DO_SPACES_ENDPOINT), "");
        s3Client.deleteObject(new DeleteObjectRequest(S3_BUCKET_NAME, fileName));
    }

    private static File convertFromMultiPartToFile(MultipartFile multipartFile) throws IOException {

        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }
}
