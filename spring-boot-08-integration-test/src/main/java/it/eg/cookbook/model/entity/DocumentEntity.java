package it.eg.cookbook.model.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_iddocument")
    @SequenceGenerator(name = "seq_iddocument", allocationSize = 1)
    private Integer id;

    private String code;
    private String name;
    private String description;

}
