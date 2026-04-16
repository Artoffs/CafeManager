package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.TableDAO;
import by.grsu.CafeManager.model.Table;
import by.grsu.CafeManager.service.interfaces.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    private final TableDAO tableDAO;

    @Autowired
    public TableServiceImpl(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    @Override
    public Table getTable(Long id) {
        return tableDAO.getTable(id).orElseThrow(() -> new IllegalArgumentException("Стол с таким id не найден"));
    }

    @Override
    public List<Table> getAll() {
        return tableDAO.getTables();
    }

    @Override
    public void updateTable(Table user) {

    }

    @Override
    public void saveTable(Table user) {

    }
}
