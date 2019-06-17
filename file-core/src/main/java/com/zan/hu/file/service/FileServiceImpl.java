package com.zan.hu.file.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zan.hu.file.FileService;
import com.zan.hu.file.async.AsyncTask;
import com.zan.hu.file.model.FileDto;
import com.zan.hu.file.model.request.FileUploadRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class FileServiceImpl implements FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private AsyncTask asyncTask;

    @Override
    public String singleUpload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.singleUpload(originalFilename, contentType, inputStream);
    }

    @Override
    public List<String> multiUpload(MultipartFile[] files) {
        List<String> objectIds = new CopyOnWriteArrayList<>();
        for (MultipartFile multipartFile : files) {
            String objectId = singleUpload(multipartFile);
            objectIds.add(objectId);
        }
        return objectIds;
    }

    @Override
    public String singleUpload(FileUploadRequest fileUploadRequest) {
        return this.singleUpload(fileUploadRequest.getOriginalFilename(), fileUploadRequest.getContentType(), fileUploadRequest.getInputStream());
    }

    @Override
    public List<String> multiUpload(List<FileUploadRequest> fileUploadRequests) {
        List<String> objectIds = new CopyOnWriteArrayList<>();
        fileUploadRequests.forEach(fileUploadRequest -> {
            String objectId = this.singleUpload(fileUploadRequest);
            objectIds.add(objectId);
        });
        return objectIds;
    }

    @Override
    public FileDto getByObjectId(String objectId) {
        Query query = Query.query(Criteria.where("_id").is(objectId));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        String fileName = gridFSFile.getFilename().replace(",", "");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        gridFSBucket.downloadToStream(gridFSFile.getId(), byteArrayOutputStream);
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        FileDto file = new FileDto();
        file.setName(fileName);
        file.setContentType(gridFSFile.getMetadata().get("_contentType").toString());
        file.setLength(gridFSFile.getLength());
        file.setChunkSize(gridFSFile.getChunkSize());
        file.setUploadDate(gridFSFile.getUploadDate());
        file.setContent(arrayInputStream);
        return file;
    }

    @Override
    public void delete(String objectId) {
        Query query = Query.query(Criteria.where("_id").is(objectId));
        gridFsTemplate.delete(query);
    }

    @Override
    public void batchDelete(List<String> objectIds) {
        Query query = Query.query(Criteria.where("_id").in(objectIds));
        gridFsTemplate.delete(query);
    }

    @Override
    public ResponseEntity download(String objectId) {
        FileDto file = getByObjectId(objectId);
        InputStreamResource inputStreamResource = new InputStreamResource(file.getContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()))
                .header("name", file.getName())
                .header("contentType", file.getContentType())
                .body(inputStreamResource);
    }

    @Override
    public ResponseEntity preview(String objectId) {
        FileDto file = getByObjectId(objectId);
        InputStreamResource inputStreamResource = new InputStreamResource(file.getContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .body(inputStreamResource);
    }


    private String singleUpload(String originalFilename, String contentType, InputStream inputStream) {
        ObjectId store = gridFsTemplate.store(inputStream, originalFilename, contentType);
        asyncTask.create(originalFilename, contentType, store.toString());
        return store.toString();
    }

}
