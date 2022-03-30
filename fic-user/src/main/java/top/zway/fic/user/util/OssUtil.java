package top.zway.fic.user.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZZJ
 */
@Component
public class OssUtil {

    private AmazonS3 s3client;

    @Value("${oss.s3.endpoint}")
    private String endpoint;
    @Value("${oss.s3.default-bucket}")
    private String bucketName;
    @Value("${oss.s3.dirname}")
    private String dirname;
    @Value("${oss.aws.access-key-id}")
    private String accessKey;
    @Value("${oss.aws.access-key-secret}")
    private String secretKey;;


    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, null))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withChunkedEncodingDisabled(true)
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileName = null;
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return System.currentTimeMillis() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName+ "/" +dirname, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

    public List<String> listFiles() {
        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(bucketName)
                        .withPrefix(dirname);

        List<String> keys = new ArrayList<>();
        ObjectListing objects = s3client.listObjects(listObjectsRequest);
        while (true) {
            List<S3ObjectSummary> summaries = objects.getObjectSummaries();
            if (summaries.size() < 1) {
                break;
            }
            for (S3ObjectSummary item : summaries) {
                if (!item.getKey().endsWith("/")) {
                    keys.add(item.getKey());
                }
            }

            objects = s3client.listNextBatchOfObjects(objects);
        }
        return keys;
    }
}