package com.Banking.backend.service.serviceImp;

import com.Banking.backend.dto.request.UserLoginRequest;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.entity.Role;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.UserService;

import com.Banking.backend.utils.JwtUtil;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

        private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired private JwtUtil jwtUtil;

    @Autowired private MyMailSenderImpl myMailSenderImpl;


    @Override
    public ApiResponse<UserResponse> login(UserLoginRequest userLoginRequest) {
        LOGGER.info("[UserServiceImpl >> login] Request received:");
        System.out.println("Login apu hit");
            ApiResponse<UserResponse> response = new ApiResponse<>();
        try {

            Optional<User> userOptional = RepositoryAccessor.getUserRepository()
                    .findByEmailIgnoreCase(userLoginRequest.getEmail());
    
            if (userOptional.isPresent()) {
                User savedUser = userOptional.get();
    
               
                if (savedUser.getPassword() == null || savedUser.getPassword().trim().isEmpty()) {

                    response.setCode(0);
                    response.setMeassage("User not registered.");
                    return response;
                }
    
                
                if (!savedUser.getPassword().equals(userLoginRequest.getPassword())) {
                    response.setCode(0);
                    response.setMeassage("Invalid Password");
                    return response;
                }
    
               
                String authToken = jwtUtil.generateToken(savedUser).replaceFirst("^Bearer ", "");
    
                            //changes
                            UserResponse userResponse =
                                UserResponse.builder()
                            .id(savedUser.getId())
                            .name(savedUser.getName())
                            .email(savedUser.getEmail())
                            .phone(savedUser.getPhone())
                            .dob(savedUser.getDob())
                            .gender(savedUser.getGender())
                            .roleId(savedUser.getRole().getId())
                            .token(authToken)
                            .build();

                response.setCode(1);
                response.setMeassage("Login successful");
                response.setData(userResponse);
                
            } else {

                response.setCode(0);
                response.setMeassage("User Not Found");
                
            }
    
        } catch (Exception e) {
            response.setCode(0);
            response.setMeassage("Internal Server Error");
            
        }
        return response;
    }

    @Override
    public ApiResponse<UserResponse> register(UserRegisterRequest userRegisterRequest) {

        ApiResponse<UserResponse> response = new ApiResponse<>();
    try{
        User newUser = new User();

        newUser.setName(userRegisterRequest.getName());
        newUser.setDob(userRegisterRequest.getDob());
        newUser.setEmail(userRegisterRequest.getEmail());
        newUser.setGender(userRegisterRequest.getGender());
        newUser.setPassword(userRegisterRequest.getPassword());
        newUser.setPhone(userRegisterRequest.getPhone());
        Optional<Role>  defaultRole = RepositoryAccessor.getRoleRepository().findById(2L);
        if (defaultRole.isPresent()) {
            newUser.setRole(defaultRole.get());
        }
        User user = RepositoryAccessor.getUserRepository().save(newUser);

        // sending welcome mail
        myMailSenderImpl.sendWelcomeEmail(userRegisterRequest.getEmail(), userRegisterRequest.getName());

        return response;

    }
        catch(Exception e)
        {
            
        }

        return response;
    }
    

}
