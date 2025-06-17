package com.gdsc.nitcconnect.routes;
import com.gdsc.nitcconnect.model.AccessCode;
import com.gdsc.nitcconnect.repository.AccessCodeRepository;
import com.gdsc.nitcconnect.service.AccessCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/test/access-codes")
public class AccessCodeTest {

    @Autowired
    private AccessCodeService accessCodeService;

    @GetMapping("/validate/{code}")
    public ResponseEntity<?> validateCode(@PathVariable String code) {
        Optional<Integer> igId = accessCodeService.getIgIdByValidCode(code);

        if (igId.isPresent()) {
            return ResponseEntity.ok(igId.get());
        } else {
            return ResponseEntity.badRequest()
                    .body("Invalid or expired code");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AccessCode> createAccessCode(@RequestBody AccessCode accessCode) {
        // Make sure the code is set
        if (accessCode.getCode() == null || accessCode.getCode().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        AccessCode saved = accessCodeService.createAccessCode(accessCode);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Controller is working!");
    }
}