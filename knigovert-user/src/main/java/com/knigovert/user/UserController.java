package com.knigovert.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Map<String, String> getEndpoints(HttpServletRequest servletRequest) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("getUser",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/user/{id}").build().toString());
        map.put("getUsers",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/users/").build().toString());
        map.put("createUser",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/user/").build().toString());
        map.put("updateUser",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/user/{id}").build().toString());
        return map;
    }

    @GetMapping("/users")
    public Page<User> getUsers(@RequestParam(name = "page", defaultValue = "0") int page) {
        return userService.getPage(page);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User newUser, HttpServletRequest servletRequest) {
        User created = userService.createUser(newUser.getEmail(), newUser.getPassword(), newUser.getName());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/user/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @GetMapping("/configuration")
    public Map<String, String> getConfiguration(@Value("${api.page.size}") int pageSize,
                                   @Value("${user.hidden.displayedName}") String hiddenName) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Page size", String.valueOf(pageSize));
        map.put("Displayed name for hidden users", hiddenName);
        return map;
    }

    @GetMapping("/config/local")
    public String localConfig(@Value("${lab3.local.something}") String local) {
        return local;
    }
}
