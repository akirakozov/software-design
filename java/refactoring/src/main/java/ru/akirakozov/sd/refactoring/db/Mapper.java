package ru.akirakozov.sd.refactoring.db;

import java.sql.ResultSet;

@FunctionalInterface
public interface Mapper<T> {
    T map(ResultSet rs) throws Exception;
}
