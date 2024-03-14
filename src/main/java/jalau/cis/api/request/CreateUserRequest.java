package jalau.cis.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
public class CreateUserRequest {
  private String userName;
  private String userLogin;
  private String userPassword;
}

