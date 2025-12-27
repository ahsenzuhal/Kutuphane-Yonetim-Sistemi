package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.config.JwtUtil;
import com.kutuphane.AkilliKutuphane.dto.AuthRequest;
import com.kutuphane.AkilliKutuphane.dto.AuthResponse;
import com.kutuphane.AkilliKutuphane.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.Map;
import java.util.HashMap;
import com.kutuphane.AkilliKutuphane.model.Kullanici;
import com.kutuphane.AkilliKutuphane.service.KullaniciService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final KullaniciService kullaniciService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          KullaniciService kullaniciService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.kullaniciService = kullaniciService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> giris(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getKullaniciAdi(), request.getSifre())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        String rol = userDetails.getAuthorities().stream().findFirst().map(Object::toString).orElse("ROLE_USER");
        return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), rol));
    }

    @PostMapping("/register")
    public ResponseEntity<?> kayit(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        if (kullaniciService.kullaniciVarMi(request.getKullaniciAdi())) {
            Map<String, String> err = new HashMap<>();
            err.put("message", "Bu kullanıcı adı zaten kayıtlı");
            return ResponseEntity.status(409).body(err);
        }

        String rol = request.getRol() != null ? request.getRol() : "USER";
        Kullanici yeni = new Kullanici(request.getIsim(), request.getEmail(), request.getKullaniciAdi(), request.getSifre(), rol);
        kullaniciService.kullaniciKaydet(yeni);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getKullaniciAdi(), request.getSifre())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), "ROLE_" + rol));
    }
}



