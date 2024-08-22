package com.ManageUser.ManagerUser.services.User;

import com.ManageUser.ManagerUser.dtos.requests.AuthenticationRequest;
import com.ManageUser.ManagerUser.dtos.requests.IntrospectRequest;
import com.ManageUser.ManagerUser.dtos.responses.AuthenticationResponse;
import com.ManageUser.ManagerUser.dtos.responses.IntrospectResponse;
import com.ManageUser.ManagerUser.models.User;
import com.ManageUser.ManagerUser.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @NonFinal
  protected String SIGNER_KEY = "2tQelpPrXCEylPZpvi5NPqJVmSLBYiprDRx/cjksrYd+ePQOEP6nX/CuFSw8a17S";



  public AuthenticationResponse authenticate(AuthenticationRequest request){
    var user = userRepository.findByUserName(request.getUserName())
        .orElseThrow(() -> new RuntimeException("User not existed"));

    boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

    if(!authenticated){
      throw  new RuntimeException("Unauthenticated");
    }

    var token = generateToken(user);
    return AuthenticationResponse.builder()
        .token(token)
        .authenticated(true)
        .build();
  }

  public IntrospectResponse introspect(IntrospectRequest request)
      throws JOSEException, ParseException {
    var token = request.getToken();
    JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

    SignedJWT signedJWT = SignedJWT.parse(token);

    Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
    var verified = signedJWT.verify(jwsVerifier);

    return IntrospectResponse.builder()
        .valid(verified && expiryTime.after(new Date()))
        .build();
  }


  private String generateToken(User user){
    JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(user.getUserName())
        .issuer("Chien")
        .issueTime(new Date())
        .expirationTime(new Date(Instant.now().plus(24, ChronoUnit.HOURS)
            .toEpochMilli()))
        .claim("scope",buildScope(user))
        .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(jwsHeader,payload);

    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  private String buildScope(User user){
    StringJoiner stringJoiner = new StringJoiner(" ");

    if(!CollectionUtils.isEmpty(user.getRoles())){
      user.getRoles().forEach(role -> {
        stringJoiner.add("ROLE_"+ role.getName());
        if(!CollectionUtils.isEmpty(role.getPermissions())){
          role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
        }
      });
    }
    return stringJoiner.toString();
  }
}
