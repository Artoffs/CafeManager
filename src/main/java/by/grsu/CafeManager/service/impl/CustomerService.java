package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.impl.CustomerDAOPostgresImpl;
import by.grsu.CafeManager.model.Customer;
import by.grsu.CafeManager.service.interfaces.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements GenericService<Customer> {

    private final CustomerDAOPostgresImpl customerDAOPostgres;

    public CustomerService(CustomerDAOPostgresImpl customerDAOPostgres) {
        this.customerDAOPostgres = customerDAOPostgres;
    }

    @Override
    public List<Customer> getAll() {
        return customerDAOPostgres.getAll();
    }
}
