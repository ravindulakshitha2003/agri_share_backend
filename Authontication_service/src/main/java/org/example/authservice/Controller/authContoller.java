package org.example.authservice.Controller;
import org.example.authservice.dto.UserLoginReqestDTO;
import org.example.authservice.dto.UserLoginRespondDTO;
import org.example.authservice.dto.UserRequesttDTO;
import org.example.authservice.dto.UserRespondDTO;
import org.example.authservice.service.AuthService;
import org.example.authservice.service.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class authContoller {
    private final AuthService authService;
    private  final AuthenticationManager authenticationManager;
    private  final JWTService jwtService;

    public authContoller(AuthService authService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
        String token=  jwtService.createToken(user.getUsername());
        UserLoginRespondDTO userrespond = new UserLoginRespondDTO(user.getUsername(),null,token,null,"success");
        return new ResponseEntity<>(userrespond, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginRespondDTO> login(@RequestBody UserLoginReqestDTO user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token=  jwtService.createToken(user.getUsername());
        UserLoginRespondDTO respond = new UserLoginRespondDTO(user.getUsername(),null,token,null,"success");
        return new ResponseEntity<>(respond, HttpStatus.OK);
    }
}
