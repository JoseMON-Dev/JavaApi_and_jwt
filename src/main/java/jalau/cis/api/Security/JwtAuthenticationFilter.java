package jalau.cis.api.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jalau.cis.services.ServicesFacade;
import jalau.cis.services.UsersService;
import jalau.cis.services.mybatis.MySqlDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UserDetailsService userService;
  private final JwtGenerator jwtGenerator;

  public JwtAuthenticationFilter(UserDetailsService userService, JwtGenerator generator) {
    UserDetailsService userService1;
    try {
      userService1 = userService;
    } catch (Exception e) {
      userService1 = null;
      System.out.println(e.getMessage());
    }
    this.userService = userService1;
    this.jwtGenerator = generator;
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {

      final String token = getTokenFromRequest(request);
      final String username;

      if (token==null)
      {
        filterChain.doFilter(request, response);
        return;
      }

      username=jwtGenerator.getUsernameFromToken(token);

      if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
      {
        UserDetails userDetails=userService.loadUserByUsername(username);

        if (jwtGenerator.validateToken(token))
        {
          UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }

      }

      filterChain.doFilter(request, response);
      System.out.println("finalmente esfjkasdkhbjfasdjkhf");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
