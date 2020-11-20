package com.coder.authserver.dao;

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
    private String username;

    @OneToOne(mappedBy = "info", cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
}
