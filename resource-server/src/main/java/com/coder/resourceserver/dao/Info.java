package com.coder.resourceserver.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private Long bill;

    @OneToOne(mappedBy = "info", cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
}
