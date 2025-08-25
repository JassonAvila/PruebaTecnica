package emqu.GestorTareas.Controller;

import emqu.GestorTareas.model.Users;
import emqu.GestorTareas.Service.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userservice;
    private final  AuthenticationManager authManager;
    private final jwtService jwtSrv;


    public AuthController(UserService userService,
                          AuthenticationManager authManager,
                          jwtService jwtService) {
        this.userservice = userService;
        this.authManager = authManager;
        this.jwtSrv = jwtService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Map<String, String> body)
    {
        return userservice.register(body.get("email"),body.get("password"));

    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("email"), body.get("password"))
        );
        String token = jwtSrv.generateToken(body.get("email"));
        return Map.of("token", token);
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, authenticated user!");
    }
}
