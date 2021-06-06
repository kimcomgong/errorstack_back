package kimce.kyj.errorstack.controllers;

import kimce.kyj.errorstack.models.Member;
import kimce.kyj.errorstack.repositories.MemberRepository;
import kimce.kyj.errorstack.services.JwtTokenProvider;
import kimce.kyj.errorstack.services.ResponseProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository mRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseProvider responseProvider;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> userInfo) {
        LocalDateTime now = LocalDateTime.now();

        if(mRepo.findByEmail(userInfo.get("email")).isPresent()) {
            responseProvider.createResponse("already registered", 400);
        }

        String mId = mRepo.save(
            Member
                .builder()
                .email(userInfo.get("email"))
                .password(passwordEncoder.encode(userInfo.get("password")))
                .roles(Collections.singletonList("ROLE_USER"))
                .createdAt(now.format(dtf))
                .build()
        ).getId().toString();

        return responseProvider.createResponse(mId, 200);
    }

    //로그인, JWT 반환
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userInfo) {
        Member m = mRepo.findByEmail(userInfo.get("email")).orElse(null);
        if(m == null) {
            return responseProvider.createResponse("didn't find email", 400);
        }

        if(!passwordEncoder.matches(userInfo.get("password"), m.getPassword())) {
            return responseProvider.createResponse("wrong password", 400);
        }

        String token = jwtTokenProvider.createToken(m.getUsername(), m.getRoles());
        return responseProvider.createResponse(token,200);
    }

    //인증
    @GetMapping("/auth")
    public String auth(@RequestHeader(value="JWT") String token) {
        return jwtTokenProvider.getUserPk(token);
    }
}
