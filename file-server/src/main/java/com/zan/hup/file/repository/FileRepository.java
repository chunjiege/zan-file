package com.zan.hup.file.repository;

import com.zan.hup.model.FileDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<FileDto, String> {
}
