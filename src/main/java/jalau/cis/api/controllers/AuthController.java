package jalau.cis.api.controllers;

import jalau.cis.api.Security.JwtGenerator;
import jalau.cis.api.request.CreateUserRequest;
import jalau.cis.api.request.LoginRequest;
import jalau.cis.api.response.AuthResponse;
import jalau.cis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController extends Controller{
  private AuthenticationManager authenticationManager;
  private JwtGenerator jwtGenerator;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator,
                        PasswordEncoder encoder) {
    try {
      this.authenticationManager = authenticationManager;
      this.jwtGenerator = jwtGenerator;
      this.passwordEncoder = encoder;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
    Authentication authentication =
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.getLoginName(),loginRequest.getPassword()
      ));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
  }


  @PostMapping("/register")
  public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
    try {
      var id = UUID.randomUUID().toString();
      User user = new User(id, request.getUserName(), request.getUserLogin(), passwordEncoder.encode(request.getUserPassword()));
      getUsersService().createUser(user);
      return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    } catch (Exception ex) {
      return new ResponseEntity<>("Cannot create user. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
