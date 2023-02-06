package org.example.persistence;

import org.example.persistence.model.DbForecast;
import org.example.persistence.model.DbLocation;

import java.util.List;

public interface Dao {
    boolean create(DbLocation location);

    List<DbLocation> readAll();

    boolean update(DbLocation location);

    boolean doesAlreadyContain(DbLocation location);
}
