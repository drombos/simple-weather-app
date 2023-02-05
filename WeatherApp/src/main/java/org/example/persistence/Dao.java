package org.example.persistence;

import org.example.persistence.model.DbLocation;

import java.util.List;

public interface Dao {
    boolean create(DbLocation location);

    List<DbLocation> readAll();

    boolean doesAlreadyContain(DbLocation location);
}
