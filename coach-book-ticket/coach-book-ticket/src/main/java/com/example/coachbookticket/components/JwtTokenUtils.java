package com.example.coachbookticket.components;

import com.example.coachbookticket.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

/**
 * Tiện ích JWT cho dự án CoachBookingTicket
 * - Tạo token khi đăng nhập
 * - Giải mã và xác thực token
 * - Lưu username và role vào payload
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    @Value("${jwt.expiration}")
    private int expiration; // thời hạn token (giây)

    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     *  Tạo JWT token cho user
     */
    public String generateToken(User user) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phone", user.getPhone());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhone())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot create JWT token: ");
        }
    }

    /**
      Sinh khóa bí mật từ secretKey trong application.properties
     */
    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    /**
      Dùng để tạo mới secret key nếu cần
     */
    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        return Encoders.BASE64.encode(keyBytes);
    }

    /**
      Giải mã toàn bộ claims trong JWT
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
      Lấy 1 claim cụ thể từ JWT
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
      Kiểm tra token hết hạn
     */
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    /**
      Lấy username từ JWT
     */
    public String extractPhone(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
      Kiểm tra token hợp lệ so với user hiện tại
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String phone = extractPhone(token);
        return (phone.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
