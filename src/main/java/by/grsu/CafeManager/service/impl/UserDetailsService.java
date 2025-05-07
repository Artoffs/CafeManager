package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public UserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.getByUsername(username).map(user -> User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString()).build()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким логином не найден"));
    }
}
