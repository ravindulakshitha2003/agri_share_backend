package com.example.authontication_service.Controller;


import com.example.authontication_service.dto.*;
import com.example.authontication_service.entity.UserEntity;
import com.example.authontication_service.repository.UserRepository;
import com.example.authontication_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class authContoller {
    private final AuthService authService;
    private final UserRepository userRepository;

    public authContoller(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public  String getuser(){
        return "get";
    }


    @PostMapping("/register")
    public ResponseEntity<UserLoginRespondDTO> register(@RequestBody UserRequesttDTO user) {
        UserRespondDTO respond = authService.userRegister(user);
        System.out.println(user.getPassword());
        System.out.println(user.getUsername() );

        if (respond == null) {
            System.out.println("bullin");

            return new ResponseEntity<>(new UserLoginRespondDTO(null,null,null,null,"inCOrrect unsername  password" ), HttpStatus.OK);
        }
        UserLoginReqestDTO userData = new UserLoginReqestDTO(user.getUsername(),user.getPassword());
        UserLoginRespondDTO userrespond = authService.createToken(userData);
        return new ResponseEntity<>(userrespond, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginRespondDTO> login(@RequestBody UserLoginReqestDTO user) {
        System.out.println(user.getPassword());
        System.out.println(user.getUsername() );

        UserLoginRespondDTO userrespond = authService.createToken(user);
        System.out.println(userrespond);
        if(userrespond==null){
            System.out.println("nulldf[");

            return new ResponseEntity<>(new UserLoginRespondDTO(null,null,null,null,"inCOrrect unsername  password" ), HttpStatus.OK);
        }
        System.out.println("jhas");

        return new ResponseEntity<>(userrespond, HttpStatus.OK);
    }


    @GetMapping("/userdata")
    public UserSuccessRespondDTO userDataByUsername (@RequestParam  String username){

        UserEntity User = authService.getUserByUserId(username);
        return new UserSuccessRespondDTO(User.getUsername(),"******",User.getProvince(),User.getWhatsappnumber(),User.getAge(),User.getRole());
    }
}
