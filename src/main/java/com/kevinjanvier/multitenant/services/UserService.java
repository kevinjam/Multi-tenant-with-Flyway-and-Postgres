package com.kevinjanvier.multitenant.services;

import com.kevinjanvier.multitenant.dto.UserDto;
import com.kevinjanvier.multitenant.entity.User;
import com.kevinjanvier.multitenant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TenantService tenantService;

    @Transactional
    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setTenantId(userDto.getTenantId());
        tenantService.initDatabase(userDto.getTenantId());
        log.info("tenant id==== {} ", userDto.getTenantId());
        return userRepository.save(user);
    }

    public User getUserByTenantId(String tenantId){
        return userRepository.findByTenantId(tenantId);
    }
}
