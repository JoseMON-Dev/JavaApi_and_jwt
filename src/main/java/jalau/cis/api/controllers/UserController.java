package jalau.cis.api.controllers;


import jalau.cis.api.request.CreateUserRequest;
import jalau.cis.models.User;
import jalau.cis.services.ServicesFacade;
import jalau.cis.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/user")
public class UserController extends Controller {



  @GetMapping("/read")
  public String getUsers() throws Exception {
    var users = ServicesFacade.getInstance().getUsersService().getUsers();
    StringBuilder resultStringBuilder = new StringBuilder();
    resultStringBuilder.append(String.format("Users found: [%d]\n", users.size()));

    AtomicInteger count = new AtomicInteger(0);
    for (User user : users) {
      resultStringBuilder.append(String.format("[%d] %s\n", count.addAndGet(1), user));
    }
    String resultString = resultStringBuilder.toString();
    return resultString;
  }

}
