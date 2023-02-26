package com.example.userservice.service;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import jakarta.inject.Inject;
import lombok.Data;
import org.springframework.stereotype.Service;
@Data
@Service
public class UserService {
    @Inject
    private UserRepository repository;

    public User findByUserName(String username) {
        return repository.findByUsername(username);
    }
}
