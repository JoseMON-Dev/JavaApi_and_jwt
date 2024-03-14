package jalau.cis.models;

import jalau.cis.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class User  implements UserDetails {
    private String id;
    private String name;
    private String login;
    private String password;

    public User cloneFrom(User refUser) {
        User clonedUser = new User(id, name, login, password);
        if (StringUtils.isNullOrEmpty(clonedUser.getId())) {
            clonedUser.setId(refUser.getId());
        }

        if (StringUtils.isNullOrEmpty(clonedUser.getLogin())) {
            clonedUser.setLogin(refUser.getLogin());
        }

        if (StringUtils.isNullOrEmpty(clonedUser.getName())) {
            clonedUser.setName(refUser.getName());
        }

        if (StringUtils.isNullOrEmpty(clonedUser.getPassword())) {
            clonedUser.setPassword(refUser.getPassword());
        }
        return clonedUser;
    }
    @Override
    public String toString() {
        return String.format("Id: [%s], Name: [%s], Login: [%s]", id, name, login);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
