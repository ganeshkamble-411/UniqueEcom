//package Ecom.Controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import Ecom.Exception.UserException;
//import Ecom.ModelDTO.UserSignInDetail;
//import Ecom.Service.UserService;
//import Ecom.utils.JwtUtils;
//
//@RestController
//@RequestMapping("/ecom")
//public class LoginController {
//
//	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
//
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private JwtUtils jwtUtils; 
//
//	@GetMapping("/signIn")
//	public ResponseEntity<UserSignInDetail> getLoggedInCustomerDetailsHandler(Authentication auth) {
//		try {
//			logger.info("Cusor enter in to /signIn method inside ..!");
//			var customer = userService.getUserByEmailId(auth.getName());
//			UserSignInDetail signinSuceesData = new UserSignInDetail();
//			
//			String token = jwtUtils.generateToken(auth.getName());
//			System.out.println(token);
//			
//			signinSuceesData.setId(customer.getUserId());
//			signinSuceesData.setFirstNAme(customer.getFirstName());
//			signinSuceesData.setLastName(customer.getLastName());
//			signinSuceesData.setSigninStatus("Success");
//
//			return new ResponseEntity<>(signinSuceesData, HttpStatus.OK);
//		} catch (UserException ex) {
//			throw new UserException(" Invalid Password");
//
//		}
//
//	}
//}

package Ecom.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ecom.ModelDTO.AuthRequest;
import Ecom.Service.UserService;
import Ecom.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final JwtUtil jwtUtils;
    private final UserService userService;
    private final org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

//    @PostMapping("/login")
//    public ResponseEntity<UserSignInDetail> login(AuthRequest authRequest) {
//    	
//    	log.info(authRequest.getUsername());
//    	
//        // Authenticate user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Generate JWT token
//        String token = jwtUtils.generateToken(authRequest.getUsername());
//
//        // Fetch user details
//        var customer = userService.getUserByEmailId(authRequest.getUsername());
//
//        UserSignInDetail signinSuccessData = new UserSignInDetail();
//        signinSuccessData.setId(customer.getUserId());
//        signinSuccessData.setFirstName(customer.getFirstName());
//        signinSuccessData.setLastName(customer.getLastName());
//        signinSuccessData.setSigninStatus("Success");
//        signinSuccessData.setJwtToken(token);
//
//        return new ResponseEntity<>(signinSuccessData, HttpStatus.OK);
//    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
 
        boolean ok = userService.checkUserCredentails(username, password);
        if (!ok) {
            log.warn("Unauthorized login attempt: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
 
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtils.generateToken(userDetails);
 
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        response.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList()));
 
        log.info("User logged in: {}", username);
        return ResponseEntity.ok(response);
    }
 
}


