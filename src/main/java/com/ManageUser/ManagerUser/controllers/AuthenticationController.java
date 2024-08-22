package com.ManageUser.ManagerUser.controllers;

import com.ManageUser.ManagerUser.dtos.requests.AuthenticationRequest;
import com.ManageUser.ManagerUser.dtos.requests.IntrospectRequest;
import com.ManageUser.ManagerUser.dtos.responses.AuthenticationResponse;
import com.ManageUser.ManagerUser.dtos.responses.IntrospectResponse;
import com.ManageUser.ManagerUser.services.User.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService  authenticationService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){

    return ResponseEntity.ok(authenticationService.authenticate(request));
  }

  @PostMapping("/introspect")
  public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
      throws ParseException, JOSEException {
    return ResponseEntity.ok(authenticationService.introspect(request));
  }
}
