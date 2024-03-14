package jalau.cis.api.request;

import lombok.Data;

@Data
public class LoginRequest {
  String loginName;
  String password;
}
