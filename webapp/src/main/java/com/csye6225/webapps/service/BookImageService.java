package com.csye6225.webapps.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.BookImages;
import com.csye6225.webapps.repository.BookImagesRepository;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class BookImageService {

    @Autowired
    BookImagesRepository repository;

    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

    @Value("${Bucketname:aDefaultValue}")
    private String bucketName;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void save(BookImages image){
        repository.save(image);
    }

    public void uploadeImage (MultipartFile file, Book b){
        long startTime = System.currentTimeMillis();
        String filename = new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename());
        BookImages image = new BookImages();
        image.setBook(b);
        image.setImageName(filename);
        save(image);
        uploadToS3(file,filename);
        statsd.recordExecutionTime("upload image to S3", System.currentTimeMillis() - startTime);
    }

    public void uploadToS3(MultipartFile file, String fileName) {

        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        File covFile = convertMultiPartToFile(file);
        s3client.putObject(new PutObjectRequest(bucketName,fileName, covFile));
        covFile.delete();
        log.info("Book image uploaded to S3");
    }

    private File convertMultiPartToFile(MultipartFile file) {

        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException | AmazonServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return convFile;
    }

    public void deleteS3Image(String fileName) {
        long startTime = System.currentTimeMillis();
        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        log.info("Book image deleted from S3");
        statsd.recordExecutionTime("delete image from s3", System.currentTimeMillis() - startTime);
    }

    public String viewImage(String key){
        long startTime = System.currentTimeMillis();
        String base64 ="";
        try {

        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        S3Object o = s3client.getObject(bucketName, key);
        BufferedImage imgBuf = ImageIO.read(o.getObjectContent());
        base64 = encodeBase64URL(imgBuf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        statsd.recordExecutionTime("get image from s3", System.currentTimeMillis() - startTime);
        return base64.isEmpty() ? null : base64;
    }

    public String encodeBase64URL(BufferedImage imgBuf) throws IOException {
        String base64;

        if (imgBuf == null) {
            base64 = null;
        } else {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            ImageIO.write(imgBuf, "PNG", out);

            byte[] bytes = out.toByteArray();
            base64 = "data:image/png;base64," + new String(Base64.getEncoder().encode(bytes), "UTF-8");
        }

        return base64;
    }

    public List<BookImages> images(Long bookID){
        return repository.images(bookID);
    }

    public List<String> imagesName(Long bookID){
        return  repository.imagesName(bookID);
    }

    public void deleteDBImage(Long imageID){
        repository.deleteById(imageID);
    }

    public BookImages imageBYID(Long ID){
        return repository.findById(ID).orElse(null);
    }

   public BookImages imageByName(String imageName){
        return repository.imageByName(imageName);
    }

}

