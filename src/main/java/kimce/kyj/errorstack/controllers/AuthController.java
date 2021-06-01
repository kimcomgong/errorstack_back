package kimce.kyj.errorstack.controllers;

import kimce.kyj.errorstack.models.Member;
import kimce.kyj.errorstack.repositories.MemberRepository;
import kimce.kyj.errorstack.services.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository mRepo;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> userInfo) {
        if(mRepo.findByEmail(userInfo.get("email")).isPresent()) {
            return "already registered";
            //throw new IllegalArgumentException("Already registered");
        }

        return mRepo.save(Member.builder()
            .email(userInfo.get("email"))
            .password(passwordEncoder.encode(userInfo.get("password")))
            .roles(Collections.singletonList("ROLE_USER"))
            .build()).getId().toString();
    }

    //custom http response status code needed
    //로그인, JWT 반환
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> userInfo) {
        Member m = mRepo.findByEmail(userInfo.get("email")).orElse(null);
        if(m == null) return "didn't find email";

        if(!passwordEncoder.matches(userInfo.get("password"), m.getPassword())) {
            return "wrong password";
        }

        return jwtTokenProvider.createToken(m.getUsername(), m.getRoles());
    }

    //인증
    @GetMapping("/auth")
    public String auth(@RequestHeader(value="JWT") String token) {
        return jwtTokenProvider.getUserPk(token);
    }
}
