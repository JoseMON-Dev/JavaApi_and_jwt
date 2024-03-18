package jalau.cis.services.mybatis;

import jalau.cis.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    @Delete("DELETE FROM users WHERE id=#{userId}")
    int delete(String userId);

    @Select("SELECT * FROM users")
    @MapKey("id")
    Map<String, User> getAllUsers();

    @Select("SELECT * FROM users where id=#{userId}")
    @MapKey("id")
    Map<String, User> getUserById(String userId);

    @Insert("INSERT INTO users (id, name, login, password) VALUES (#{id}, #{name}, #{login}, #{password})")
    void createUser(User user);

    @Insert("UPDATE users SET name=#{name}, login=#{login}, password=#{password} WHERE id=#{id}")
    void updateUser(User user);


}
