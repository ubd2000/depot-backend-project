package com.depot.shopping.api.user.controller;

import com.depot.shopping.api.user.dto.UserDto;
import com.depot.shopping.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author DongMin Kim
 */
@Tag(name = "Depot Shop", description = "Depot Shop Api")
@RestController("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("SUCCESS");
    }

    @Operation(summary = "사용자 조회", description = "ID로 사용자 정보를 조회합니다.")
    @GetMapping("/user/{seqId}")
    public ResponseEntity<?> getUser(@Parameter(description = "사용자 ID", example = "1") @PathVariable("seqId") Long seqId) {
        return ResponseEntity.ok(userService.find(seqId));
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(UserDto.toEntity(userDto)));
    }
}