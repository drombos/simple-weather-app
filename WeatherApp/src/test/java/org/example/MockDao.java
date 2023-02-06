package org.example;

import org.example.persistence.Dao;
import org.example.persistence.model.DbLocation;

import java.util.List;

public class MockDao implements Dao {
    @Override
    public boolean create(DbLocation location) {
        return false;
    }

    @Override
    public List<DbLocation> readAll() {
        return null;
    }

    @Override
    public boolean update(DbLocation location) {
        return false;
    }

    @Override
    public boolean doesAlreadyContain(DbLocation location) {
        return false;
    }
}
