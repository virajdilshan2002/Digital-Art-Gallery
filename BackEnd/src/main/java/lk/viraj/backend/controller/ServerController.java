package lk.viraj.backend.controller;

import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/server")
@CrossOrigin
public class ServerController {
    @GetMapping(path = "/status")
    public ResponseEntity<ResponseDTO> status(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Server is running", null));
    }
}
