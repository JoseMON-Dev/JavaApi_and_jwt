package jalau.cis.services.mybatis;

import jalau.cis.models.User;
import jalau.cis.repository.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MySqlDBServiceTest {

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private MySqlDBService mySqlDBService;

  @Test
  void deleteUser() {
  }

  @Test
  void getUsers() {
    Map<String, User>  userData = new HashMap<>();
    User user = new User(
      "123",
      "jose",
      "josillo",
      "secret"
    );
    userData.put(user.getId(), user);
    when(userMapper.getAllUsers()).thenReturn(userData);

    try {
      List<User> result = mySqlDBService.getUsers();
      verify(userMapper, times(1)).getAllUsers();
    } catch (Exception e) {
      fail();
    }

  }

  @Test
  void createUser() {
  }

  @Test
  void updateUser() {
  }
}