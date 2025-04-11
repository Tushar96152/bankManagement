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

            ApiResponse<UserResponse> response = new ApiResponse<>();
        try {

            Optional<User> userOptional = RepositoryAccessor.getUserRepository()
                    .findByEmailIgnoreCase(userLoginRequest.getEmail());
    
            if (userOptional.isPresent()) {
                User user = userOptional.get();
    
               
                if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {

                    response.setCode(0);
                    response.setMeassage("User not registered.");
                    return response;
                }
    
                
                if (!user.getPassword().equals(userLoginRequest.getPassword())) {
                    response.setCode(0);
                    response.setMeassage("Invalid Password");
                    return response;
                }
    
               
                String authToken = jwtUtil.generateToken(user).replaceFirst("^Bearer ", "");
    
              
                response.setCode(1);
                response.setMeassage("Login successful");
                response.getData().setToken(authToken);
                return response;
            } else {

                response.setCode(0);
                response.setMeassage("User Not Found");
                return response;
            }
    
        } catch (Exception e) {
            response.setCode(0);
            response.setMeassage("Internal Server Error");
            return response;
        }
    }

    @Override
    public ApiResponse<User> register(UserRegisterRequest userRegisterRequest) {

        ApiResponse<User> response = new ApiResponse<>();

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
    

}
