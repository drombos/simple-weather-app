package org.example.persistence.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class DbObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
}
