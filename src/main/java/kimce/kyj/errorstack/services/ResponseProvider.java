package kimce.kyj.errorstack.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

@Service
public class ResponseProvider {
    public ResponseEntity createResponse(Object object, int status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json",Charset.forName("UTF-8")));
        return new ResponseEntity<>(object, headers, status);
    }
}
