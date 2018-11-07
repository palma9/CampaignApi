package com.salesianostriana.campaing.security.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.salesianostriana.campaing.config.Exceptions;
import com.salesianostriana.campaing.formbean.RegistroDto;
import com.salesianostriana.campaing.security.JwtAuthenticationRequest;
import com.salesianostriana.campaing.security.JwtTokenUtil;
import com.salesianostriana.campaing.security.service.JwtAuthenticationResponse;
import com.salesianostriana.campaing.service.UsuarioService;

// Controller de logueo
@RestController
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	// Inicio de sesión
	@RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		// Reload password post-security so we can generate the token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(token, authenticationRequest.getEmail()));
	}
	
    @PostMapping("/registro")
    public ResponseEntity<?> createRegistrationToken(@RequestBody RegistroDto nuevoUsuario) throws AuthenticationException, Exceptions {

    	if (usuarioService.checkEmailRegistered(nuevoUsuario.getEmail())) {
    		throw new Exceptions("Email Exists");
    	}
    	// Create de User
    	usuarioService.save(nuevoUsuario);
    	
        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(nuevoUsuario.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, nuevoUsuario.getEmail()));
    }

//    // Refrescar usuario
//    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//        String authToken = request.getHeader(tokenHeader);
//        final String token = authToken.substring(7);
//        
//        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
//            String refreshedToken = jwtTokenUtil.refreshToken(token);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

	// Error de autenticacion
	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	// Autentica el usuario. Si algo va mal, lanza una excepción
	private void authenticate(String email, String password) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("User is disabled!", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Bad credentials!", e);
		}
	}
}
