package com.knigovert.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryService implements UserService {
    private UserRepository userRepository;
    private int pageSize;

    @Autowired
    public UserRepositoryService(UserRepository userRepository, @Value("${api.page.size: #{10}}") int pageSize) {
        this.userRepository = userRepository;
        this.pageSize = pageSize;
    }

    @Override
    public List<User> getList() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Page<User> getPage(int page) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(String email, String password, String name) {
        User user = new User(email, password, name);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(updatedUser.getName()).ifPresent(user::setName);
        Optional.ofNullable(updatedUser.getBooksRead()).ifPresent((ids) -> ids.forEach(user::addBook));
        return userRepository.save(user);
    }
}
