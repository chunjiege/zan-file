package com.zan.hup.file.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zan.hup.file.FileService;
import com.zan.hup.file.async.AsyncTask;
import com.zan.hup.file.dto.FileDto;
import com.zan.hup.file.repository.FileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
    private FileRepository fileRepository;

    @Autowired
    private AsyncTask asyncTask;

    public String singleFileUpload(@RequestPart("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream inputStream = null;
        ObjectId store = null;
        try {
            inputStream = file.getInputStream();
            store = gridFsTemplate.store(inputStream, originalFilename, contentType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return store.toString();
    }

    @Override
    public List<String> multiFileUpload(@RequestPart("files") MultipartFile[] files) {
        List<String> objectIds = new CopyOnWriteArrayList<>();
        for (MultipartFile multipartFile : files) {
            String objectId = singleFileUpload(multipartFile);
            objectIds.add(objectId);
        }
        return objectIds;
    }

    @Override
    public FileDto getFileByObjectId(@RequestParam("objectId") String objectId) {
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
    public void deleteFile(String objectId) {
        fileRepository.deleteById(objectId);
    }

    @Override
    public ResponseEntity download(@RequestParam("objectId") String objectId) {
        FileDto file = getFileByObjectId(objectId);
        InputStreamResource inputStreamResource = new InputStreamResource(file.getContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()))
                .header("name", file.getName())
                .header("contentType", file.getContentType())
                .body(inputStreamResource);
    }

    @Override
    public ResponseEntity preview(@RequestParam("objectId") String objectId) {
        FileDto file = getFileByObjectId(objectId);
        InputStreamResource inputStreamResource = new InputStreamResource(file.getContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .body(inputStreamResource);
    }

}
