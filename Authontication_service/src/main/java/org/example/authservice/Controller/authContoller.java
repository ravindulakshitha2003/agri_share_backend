package org.example.authservice.Controller;
import org.example.authservice.dto.UserLoginReqestDTO;
import org.example.authservice.dto.UserLoginRespondDTO;
import org.example.authservice.dto.UserRequesttDTO;
import org.example.authservice.dto.UserRespondDTO;
import org.example.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class authContoller {
    private final AuthService authService;

    public authContoller(AuthService authService) {
        this.authService = authService;


    }

    @GetMapping("")
    public  String getuser(){
        return "get";
    }


    @PostMapping("/register")
    public ResponseEntity<UserLoginRespondDTO> register(@RequestBody UserRequesttDTO user) {
        UserRespondDTO respond = authService.userRegister(user);

        if (respond == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserLoginReqestDTO userData = new UserLoginReqestDTO(user.getUsername(),user.getPassword());
        UserLoginRespondDTO userrespond = authService.createToekn( userData);
        return new ResponseEntity<>(userrespond, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginRespondDTO> login(@RequestBody UserLoginReqestDTO user) {
        System.out.println("loing resived");
        UserLoginRespondDTO userrespond = authService.createToekn(user);
        if(userrespond==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       return new ResponseEntity<>(userrespond, HttpStatus.OK);
    }
}
