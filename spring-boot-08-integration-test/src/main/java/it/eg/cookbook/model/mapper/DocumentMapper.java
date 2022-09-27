package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DocumentMapper {

    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    Document requestEntityToApi(DocumentEntity request);

    DocumentEntity requestApiToEntity(Document request);

    List<Document> requestEntityToApi(Iterable<DocumentEntity> request);

    List<DocumentEntity> requestApiToEntity(Iterable<Document> request);

}
