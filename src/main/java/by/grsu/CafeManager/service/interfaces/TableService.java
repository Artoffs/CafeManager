package by.grsu.CafeManager.service.interfaces;

import by.grsu.CafeManager.model.Table;
import by.grsu.CafeManager.service.DTO.UserDTO;

import java.util.List;

public interface TableService {
    Table getTable(Long id);
    List<Table> getAll();
    void updateTable(Table user);
    void saveTable(Table user);
}
