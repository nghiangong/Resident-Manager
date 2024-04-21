package com.vinhome.residentmanagement.controller;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vinhome.residentmanagement.dtos.LoginRequest;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.House;
import com.vinhome.residentmanagement.entity.Role;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.GateRepository;
import com.vinhome.residentmanagement.repository.HouseRepository;
import com.vinhome.residentmanagement.repository.RoleRepository;
import com.vinhome.residentmanagement.repository.UserRepository;
import com.vinhome.residentmanagement.response.JwtResponse;
import com.vinhome.residentmanagement.response.MessageResponse;
import com.vinhome.residentmanagement.security.jwt.JwtUtils;
import com.vinhome.residentmanagement.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GateRepository gateRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getPhone(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.isAcceptedStatus(),
                userDetails.isGender(),
                userDetails.getImage(),
                userDetails.getDate(),
                userDetails.getOwnId(),
                userDetails.getIdCard(),
                userDetails.isCreateQrPermission(),
                userDetails.getHouse(),
                userDetails.getGate(),
                userDetails.getDeletedAt(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserPostDto signUpRequest) throws NoSuchAlgorithmException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        // Create new user's account
        User user = mapStructMapper.userPostDtoToUser(signUpRequest);
        if(signUpRequest.getHouseId() != null){
            House newHouse = houseRepository.findById(signUpRequest.getHouseId()).orElseThrow(() -> new EntityNotFoundException(House.class, "id", signUpRequest.getHouseId().toString()));
            user.setHouse(newHouse);
        }
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<Long> roleIds = signUpRequest.getRoleIds();
        if (roleIds.isEmpty()) {
            throw new NullPointerException("Người dùng chưa có vai trò");
        } else {
            for (Long id : roleIds) {
                Role newRole = roleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Role.class, "id", id.toString()));
                user.addRole(newRole);
            }
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair keyPair = kpg.genKeyPair();
            user.setPrivateKey(keyPair.getPrivate());
            user.setPublicKey(keyPair.getPublic());
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Người dùng đã đăng ký thành công!"));
        }
    }
}