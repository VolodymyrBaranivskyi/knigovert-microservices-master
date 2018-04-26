package core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Long id);

    @GetMapping("/users")
    List<User> getUsers();
}
