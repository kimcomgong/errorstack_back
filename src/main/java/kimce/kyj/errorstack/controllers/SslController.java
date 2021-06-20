package kimce.kyj.errorstack.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known")
public class SslController {
    @GetMapping("/acme-challenge/4XQ9JNvZYrTdIZdqmEXte_ttX8waiLPmjnsWhcc7hFo")
    public String letsencrypt() {
        return "4XQ9JNvZYrTdIZdqmEXte_ttX8waiLPmjnsWhcc7hFo.osqBj5ZLklOcRtzUysc9YyqlyDmCT0xQnvEVLUA-Zb8";
    }
}
