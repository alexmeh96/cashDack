package com.coder.resourceserver.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    private Long amount;
    private String description;
    private String type;
    private Date date;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User author;
}
