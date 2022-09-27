package it.eg.cookbook.service;


import it.eg.cookbook.model.entity.DocumentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentEntity, Integer> {

    Optional<DocumentEntity> findByCode(String code);
}