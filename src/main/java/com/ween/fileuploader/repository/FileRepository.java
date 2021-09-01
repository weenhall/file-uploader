package com.ween.fileuploader.repository;

import com.ween.fileuploader.entity.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface FileRepository extends CrudRepository<Attachment,String> {

	List<Attachment> findByFileSource(String source);
}
