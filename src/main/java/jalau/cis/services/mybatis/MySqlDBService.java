package jalau.cis.services.mybatis;

import jalau.cis.models.User;
import jalau.cis.services.UsersService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class MySqlDBService implements UsersService, UserDetailsService {

    private final SqlSessionFactory factory;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            List<User> users = getUsers();

          return users.stream()
              .filter(u -> u.getUsername().equals(username))
              .findFirst()
              .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private interface SessionOperation<T> {
        public T execute(SqlSession session, UserMapper mapper) throws Exception;
    }

    public MySqlDBService(InputStream stream) {
        factory = new SqlSessionFactoryBuilder().build(stream);
        factory.getConfiguration().addMapper(UserMapper.class);
    }

    private <T> T execute(SessionOperation<T> operation) throws Exception {
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        System.out.println("Opening Session");
        try {
            return operation.execute(session, userMapper);
        }
        catch (Exception ex)
        {
            System.out.printf("Error on SQL operation [%s]\n", ex.getMessage());
            throw  ex;
        }
        finally {
            System.out.println("Closing Session");
            session.close();
        }
    }

    @Override
    public int deleteUser(String id) throws Exception{
        return execute((session, userMapper) -> {
            try {
                int count = userMapper.delete(id);
                session.commit();
                return count;
            }
            catch (Exception ex) {
                session.rollback();
                throw ex;
            }
        });
    }

    @Override
    public List<User> getUsers() throws Exception {
        return execute( (session, userMapper) -> {
             var data  = userMapper.getAllUsers();
             if (data != null) {
                return new ArrayList<>(data.values());
              }
                else
                {
                    return new ArrayList<>();
                }
            });
    }

    @Override
    public void createUser(User user) throws Exception{
        execute((session, userMapper) -> {
            try {
                userMapper.createUser(user);
                session.commit();
                return 0;
            }
            catch (Exception ex) {
                session.rollback();
                throw  ex;
            }
        });

    }

    @Override
    public void updateUser(User user) throws Exception{
        execute((session, userMapper) -> {
            try {
                var userMap = userMapper.getUserById(user.getId());
                if (userMap.containsKey(user.getId())) {
                    User userToUpdate = user.cloneFrom(userMap.get(user.getId()));
                    userMapper.updateUser(userToUpdate);
                    session.commit();
                    return 0;
                }
                else {
                    throw new Exception("User does not exist");
                }
            }
            catch (Exception ex) {
                session.rollback();
                throw ex;
            }
        });
    }

}
