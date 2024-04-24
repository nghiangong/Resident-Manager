package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.*;
import com.vinhome.residentmanagement.entity.*;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.GateRepository;
import com.vinhome.residentmanagement.repository.HouseRepository;
import com.vinhome.residentmanagement.repository.RoleRepository;
import com.vinhome.residentmanagement.repository.UserRepository;
import com.vinhome.residentmanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private MapStructMapper mapStructMapper;
    private RoleRepository roleRepository;
    private HouseRepository houseRepository;
    private GateRepository gateRepository;

    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper mapStructMapper, RoleRepository roleRepository, HouseRepository houseRepository, GateRepository gateRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
        this.roleRepository = roleRepository;
        this.houseRepository = houseRepository;
        this.gateRepository = gateRepository;
        this.encoder = encoder;
    }

        @Override
    public UserGetDto createUser(UserPostDto user) {
        if (user.getRoleIds().isEmpty()) {
            throw new NullPointerException("Người dùng chưa có vai trò");
        } else {
            User newUser = mapStructMapper.userPostDtoToUser(user);
            if(user.getGateId() != null){
                Gate newGate = gateRepository.findById(user.getGateId()).orElseThrow(() -> new EntityNotFoundException(Gate.class, "id", user.getGateId().toString()));
                newUser.setGate(newGate);
            }
            newUser.setAcceptedStatus(true);
            newUser.setPassword(encoder.encode(user.getPassword()));
            for (Long id : user.getRoleIds()) {
                Role newRole = roleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Role.class, "id", id.toString()));
                newUser.addRole(newRole);
            }
            return mapStructMapper.userToUserGetDto(userRepository.save(newUser));
        }
    }
//    @Override
//    public UserGetDto createUser(UserPostDto user) {
//        try {
//            if (user.getRoleIds().isEmpty()) {
//                throw new NullPointerException("Người dùng chưa có vai trò");
//            } else {
//                User newUser = mapStructMapper.userPostDtoToUser(user);
//                if (user.getGateId() != null) {
//                    Gate newGate = gateRepository.findById(user.getGateId()).orElseThrow(() -> new EntityNotFoundException(Gate.class, "id", user.getGateId().toString()));
//                    newUser.setGate(newGate);
//                }
//                newUser.setAcceptedStatus(true);
//                newUser.setPassword(encoder.encode(user.getPassword()));
//                for (Long id : user.getRoleIds()) {
//                    Role newRole = roleRepository.findById(id)
//                            .orElseThrow(() -> new EntityNotFoundException(Role.class, "id", id.toString()));
//                    newUser.addRole(newRole);
//                }
//                return mapStructMapper.userToUserGetDto(userRepository.save(newUser));
//            }
//        } catch (NullPointerException | EntityNotFoundException ex) {
//            // Xử lý ngoại lệ tại đây
//            ex.printStackTrace(); // hoặc log ngoại lệ
//            // Trả về một giá trị hoặc thực hiện các thao tác phù hợp khác nếu cần
//            return null; // hoặc trả về một giá trị mặc định hoặc thích hợp
//        }
//    }


    @Override
    public UserGetDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return mapStructMapper.userToUserGetDto(optionalUser.get());
        } else {
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapStructMapper.usersToUserGetDtos(users);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return mapStructMapper.rolesToRoleDtos(roleRepository.findAll());
    }

    @Override
    public Page<UserGetDto> findAllUser(int pageNumber, int pageSize, boolean acceptedStatus, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<User> users = userRepository.findAllResidents(acceptedStatus, keyword);
        return getUserGetDtos(pageRequest, users);
    }

    @Override
    public PieChartStatistic statisticPieChart() {
        int living = userRepository.findAllResidents(true, null).size();
        Long leaved = userRepository.countLeavedResidents();
        int unApproved = userRepository.findAllResidents(false, null).size();
        return new PieChartStatistic(living, leaved, unApproved);
    }

    @Override
    public Page<UserGetDto> findAllGateKeeper(int pageNumber, int pageSize, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<User> users = userRepository.findAllGateKeepers(null, keyword);
        return getUserGetDtos(pageRequest, users);
    }

    private Page<UserGetDto> getUserGetDtos(PageRequest pageRequest, List<User> users) {
        List<UserGetDto> userGetDtoList = mapStructMapper.usersToUserGetDtos(users);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), userGetDtoList.size());
        return new PageImpl<>(userGetDtoList.subList(start, end), pageRequest, userGetDtoList.size());
    }

    @Override
    public List<UserGetDto> getFamilyMembers(Long ownId) {
        List<User> users = userRepository.getFamilyMembers(ownId);
        return mapStructMapper.usersToUserGetDtos(users);
    }

    @Override
    public UserGetDto updateUser(Long id, UserPostDto newUser) throws NoSuchAlgorithmException {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User oldUser = existingUser.get();
            mapStructMapper.updateUserFromDTO(newUser, oldUser);
            if (newUser.getRoleIds().isEmpty()) {
                throw new NullPointerException("Người dùng chưa có vai trò");
            } else {
                oldUser.clearRoles();
                for (Long idDto : newUser.getRoleIds()) {
                    if (idDto == 2 && oldUser.getPrivateKey() == null) {
                        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                        kpg.initialize(1024);
                        KeyPair keyPair = kpg.genKeyPair();
                        oldUser.setPrivateKey(keyPair.getPrivate());
                        oldUser.setPublicKey(keyPair.getPublic());
                    }
                    Role newRole = roleRepository.findById(idDto)
                            .orElseThrow(() -> new EntityNotFoundException(Role.class, "id", idDto.toString()));
                    oldUser.addRole(newRole);
                }
                if (newUser.getHouseId() != null) {
                    House newHouse = houseRepository.findById(newUser.getHouseId())
                            .orElseThrow(() -> new EntityNotFoundException(House.class, "id", newUser.getHouseId().toString()));
                    oldUser.setHouse(newHouse);
                }
                if (newUser.getGateId() != null) {
                    Gate newGate = gateRepository.findById(newUser.getGateId())
                            .orElseThrow(() -> new EntityNotFoundException(Gate.class, "id", newUser.getGateId().toString()));
                    oldUser.setGate(newGate);
                }
                if (newUser.getPassword() != null) {
                    oldUser.setPassword(encoder.encode(newUser.getPassword()));
                }
                return mapStructMapper.userToUserGetDto(userRepository.save(oldUser));
            }
        } else {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role newRole = mapStructMapper.roleDtoToRole(roleDto);
        return mapStructMapper.roleToRoleDto(roleRepository.save(newRole));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
