package hexlet.code.controllers;

import hexlet.code.dto.users.UserCreateDTO;
import hexlet.code.dto.users.UserDTO;
import hexlet.code.dto.users.UserUpdateDTO;
import hexlet.code.mappers.UserMapper;
import hexlet.code.services.UserService;
import hexlet.code.util.UserUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class Users {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserUtils userUtils;

    public Users(UserService userService, UserMapper userMapper, UserUtils userUtils) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userUtils = userUtils;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(
            @RequestParam(name = "_start", defaultValue = "0") int start,
            @RequestParam(name = "_end", defaultValue = "1000") int end,
            @RequestParam(name = "_sort", defaultValue = "id") String sort,
            @RequestParam(name = "_order", defaultValue = "ASC") String order) {
        var direction = "ASC".equals(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
        var pageable = PageRequest.of(start, end, Sort.by(direction, sort));
        var result = userService.getAll(pageable).map(userMapper::map);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.getTotalElements()))
                .body(result.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return userService.create(userCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userUtils.checkUserPermission(#id)")
    public UserDTO updateById(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        return userService.update(userUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@userUtils.checkUserPermission(#id)")
    public void destroyById(@PathVariable Long id) {
        userService.delete(id);
    }
}
