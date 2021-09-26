package com.example.readingisgood.controller;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.readingisgood.model.Response;
import com.example.readingisgood.model.Token;
import com.example.readingisgood.util.ReadingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;;

@RestController
public class UserController {

    @PostMapping("/user")
    public ResponseEntity<Response<Token>> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        Response<Token> resp = new Response<>();
        ResponseEntity<Response<Token>> response = new ResponseEntity<>(resp, HttpStatus.OK);

        String token = getJWTToken(username);
        Token t = new Token();
        t.setToken(token);

        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        response.getBody().setResultObj(t);
        return response;

    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("getirJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
