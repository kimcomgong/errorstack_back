package kimce.kyj.errorstack.controllers;

import kimce.kyj.errorstack.models.Stack;
import kimce.kyj.errorstack.repositories.StackRepository;
import kimce.kyj.errorstack.services.ResponseProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stack")
@RequiredArgsConstructor
public class StackController {
    private final ResponseProvider responseProvider;
    private final StackRepository sRepo;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/get")
    public ResponseEntity<List> get() {
        List<Stack> list = sRepo.findAll();
        return responseProvider.createResponse(list, 200);
    }

    //tag, code, body, email
    @PostMapping("/push")
    public ResponseEntity<String> push(@RequestBody Map<String, String> stackInfo) {
        LocalDateTime now = LocalDateTime.now();
        String sId = sRepo.save(
                Stack
                        .builder()
                        .tag(stackInfo.get("tag"))
                        .code(stackInfo.get("code"))
                        .body(stackInfo.get("body"))
                        .createdBy(stackInfo.get("email"))
                        .createdAt(now.format(dtf))
                        .build()
        ).getId().toString();

        return responseProvider.createResponse(sId, 200);
    }

    /*
    @PostMapping("/comment")
    public ResponseEntity<String> comment(@RequestBody Map<String, String> commentInfo) {

    }
    */
}
