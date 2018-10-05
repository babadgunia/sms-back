package org.test.sms.web.controller.general;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.AuthenticationService;
import org.test.sms.web.jwt.JwtAuthenticationRequest;
import org.test.sms.web.jwt.JwtAuthenticationResponse;
import org.test.sms.web.jwt.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("auth")
@RestController
public class AuthenticationController {

    private Logger log = LogManager.getLogger(AuthenticationController.class);

    private Environment environment;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(Environment environment, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                    UserDetailsService userDetailsService, AuthenticationService authenticationService) {
        this.environment = environment;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String getIndexPage() {
        return "index";
    }

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        String token = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            log.error("Authentication failed! ", e);
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(environment.getRequiredProperty("jwt.header"));
        String username = jwtTokenUtil.getUsernameFromToken(token);
        userDetailsService.loadUserByUsername(username);

        if (!jwtTokenUtil.isTokenExpired(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);

            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("resetPassword/{usernameOrEmail}")
    public ResponseEntity<Void> resetPassword(HttpServletRequest request, @PathVariable("usernameOrEmail") String usernameOrEmail) {
        try {
            authenticationService.resetPassword(usernameOrEmail, request.getContextPath());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            log.error("Error while reseting password", e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("changePassword/{token}/{password}/{id}")
    public ResponseEntity<Void> showChangePasswordPage(Model model, @PathVariable("token") String token, @PathVariable("password") String password, @PathVariable("id") long id) {
        if (authenticationService.isValidPasswordResetToken(id, token)) {
            authenticationService.saveNewPassword(id, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Error while saving password");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}