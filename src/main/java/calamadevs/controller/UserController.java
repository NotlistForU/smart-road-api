package calamadevs.controller;

import calamadevs.domain.User;
import calamadevs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    @PatchMapping("/community-sharing")
    public ResponseEntity<Map<String, Boolean>> updateCommunitySharing(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, Boolean> body) {
        boolean enabled = Boolean.TRUE.equals(body.get("enabled"));
        user.setCommunitySharingEnabled(enabled);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("communitySharingEnabled", enabled));
    }
}