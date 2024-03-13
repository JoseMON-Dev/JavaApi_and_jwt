package jalau.cis.api.request;

public class UpdateUserRequest {
  private String userId;
  private String userName;
  private String userLogin;
  private String userPassword;

  public UpdateUserRequest(String userId, String userName, String userLogin, String userPassword) {
    this.userId = userId;
    this.userName = userName;
    this.userLogin = userLogin;
    this.userPassword = userPassword;
  }

}
