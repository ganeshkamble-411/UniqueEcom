package Ecom.SecurityConfig;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//        if (null != authentication) {
//        	
//            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
//            
//            authentication.getAuthorities()
//            .stream()
//            .map(obj-> obj)
//            .forEach(System.out::println); 
//           
//            String jwt = Jwts.builder()
//            		.setIssuer("e-commerse")
//            		.setSubject("JWT Token")
//                    .claim("username", authentication.getName())
//                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(new Date().getTime()+ 30000000)) 
//                    .signWith(key).compact();       
//            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
// 
//        }
//        filterChain.doFilter(request, response);	
//     
//	}
//	
//    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
//        
//    	Set<String> authoritiesSet = new HashSet<>();
//        
//        for (GrantedAuthority authority : collection) {
//            authoritiesSet.add(authority.getAuthority());
//      
//        }
//        return String.join(",", authoritiesSet);
//   
//    
//    }
//	
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//	
//        return !request.getServletPath().equals("/ecom/signIn");	
//	}
//
//}



import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import Ecom.utils.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            log.debug("Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return; 
        }

        final String token = header.substring(7);
        String username;

        try {
            username = jwtUtil.extractUsername(token);
            log.debug("Extracted username: {}", username);
        } catch (Exception ex) {
            log.warn("Invalid JWT token structure");
            sendForbidden(response, "Invalid JWT token");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails ud;
            try {
                ud = userDetailsService.loadUserByUsername(username);
            } catch (Exception e) {
                log.error("User not found for username: {}", username);
                sendForbidden(response, "User not found");
                return;
            }

            if (!jwtUtil.validateToken(token, ud)) {
                log.warn("JWT token expired or invalid for user: {}", username);
                sendForbidden(response, "Token expired or invalid");
                return;
            }

            String rolesClaim = jwtUtil.extractRoles(token);
            var authorities = Arrays.stream(rolesClaim.split(","))
                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(ud, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
            log.debug("SecurityContext authentication set for user: {}", username);
        }

        filterChain.doFilter(request, response);
    }

    private void sendForbidden(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}

