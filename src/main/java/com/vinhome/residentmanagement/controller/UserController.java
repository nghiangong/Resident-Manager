package com.vinhome.residentmanagement.controller;

import com.vinhome.residentmanagement.dtos.*;
import com.vinhome.residentmanagement.response.MessageResponse;
import com.vinhome.residentmanagement.service.HistoryService;
import com.vinhome.residentmanagement.service.QrCodeService;
import com.vinhome.residentmanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final QrCodeService qrCodeService;
    private final HistoryService historyService;

    public UserController(UserService userService, QrCodeService qrCodeService, HistoryService historyService) {
        this.userService = userService;
        this.qrCodeService = qrCodeService;
        this.historyService = historyService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserGetDto> saveUser(@RequestBody UserPostDto user) throws RuntimeException {
        UserGetDto newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/user/createQr")
    public ResponseEntity<QrCodeInformation> createQr(@RequestBody SignatureInformation signatureInformation) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        QrCodeInformation qrCodeInformation = qrCodeService.createQrcodeInformation(signatureInformation);
        return ResponseEntity.ok(qrCodeInformation);
    }

    @PostMapping("/user/verifyQr")
    public ResponseEntity<MessageResponse> verifyQr(@RequestBody QrCodeInformation qrCodeInformation) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        return ResponseEntity.ok(qrCodeService.verifyQrcode(qrCodeInformation));
    }

    @PostMapping("/role")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto) {
        RoleDto newRole = userService.createRole(roleDto);
        return ResponseEntity.ok(newRole);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserGetDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/page")
    public ResponseEntity<Page<UserGetDto>> getAllResidents(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam boolean acceptedStatus, @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(userService.findAllUser(pageNumber, pageSize, acceptedStatus, keyword));
    }

    @GetMapping("/statistic/pieChart")
    public ResponseEntity<PieChartStatistic> statisticPieChart() {
        return ResponseEntity.ok(userService.statisticPieChart());
    }

    @GetMapping("/statistic/overview")
    public ResponseEntity<OverviewStatistic> statisticOverview(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(historyService.overviewStatistic(month, year));
    }

    @GetMapping("/statistic/month")
    public ResponseEntity<List<DayStatistic>> statisticMonth(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(historyService.monthStatistic(month, year));
    }

    @GetMapping("/gateKeepers")
    public ResponseEntity<Page<UserGetDto>> getAllGateKeepers(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(userService.findAllGateKeeper(pageNumber, pageSize, keyword));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/{id}/family")
    public ResponseEntity<List<UserGetDto>> getFamilyMembers(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFamilyMembers(id));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable Long id) throws RuntimeException {
        UserGetDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable Long id, @RequestBody UserPostDto userPostDto) throws RuntimeException, NoSuchAlgorithmException {
        UserGetDto updatedUser = userService.updateUser(id, userPostDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
