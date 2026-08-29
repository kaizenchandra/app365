package com.synechisveltiosi.apis.app365.auth;

import com.synechisveltiosi.apis.app365.auth.dto.ChangePasswordRequest;
import com.synechisveltiosi.apis.app365.auth.dto.PasswordResetRequest;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/logout", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> logOut(@NotBlank @RequestParam String deviceId) throws IOException {
        // Logout the user
        authService.logout(SessionUtils.getLoggedUser().getId(), deviceId, SessionUtils.getAccessToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/password/reset")
    public ResponseEntity<Void> resetPassword(@NotNull @RequestBody PasswordResetRequest passwordResetRequest) {
        // Reset the password via email
        if (passwordResetRequest.getEmail() != null
                && Pattern.matches("^[A-Z0-9a-z._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$",
                passwordResetRequest.getEmail())) {

            authService.resetPassword(passwordResetRequest.getEmail());
        } else { // Reset password via phone number
            authService.resetPassword(passwordResetRequest.getPhoneCountryCode(), passwordResetRequest.getPhone());
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/password/reset")
    public ResponseEntity<Void> resetPassword(
            @NotBlank @RequestHeader("X-Email") String email,
            @NotNull @NotEmpty @RequestBody Map<String, String> payload) throws IOException {

        String password = payload.get("password");
        String code = payload.get("code");

        // Reset the password
        authService.setNewPassword(email, password, code);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/password/change")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest)
            throws IOException {

        // Change password
        authService.changePassword(SessionUtils.getLoggedUser().getId(),
                changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());

        return ResponseEntity.noContent().build();
    }
}
