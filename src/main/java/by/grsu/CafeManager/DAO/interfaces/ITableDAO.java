package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.Table;

import java.util.List;
import java.util.Optional;

public interface ITableDAO {
    Optional<Table> getTable(Long id);
    List<Table> getTables();
    Table saveTable(Table table);
    void updateTable(Table table);
    void deleteTable(Table table);
}
