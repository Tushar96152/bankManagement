package com.Banking.backend.service.serviceImp;

import com.Banking.backend.dto.request.LoginNetPasswordChange;
import com.Banking.backend.dto.request.UserLoginPasswordChange;
import com.Banking.backend.dto.request.UserLoginRequest;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.entity.Role;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.UserService;

import com.Banking.backend.utils.JwtUtil;

import java.util.List;
import java.util.Objects;
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
                    response.setMessage("User not registered.");
                    return response;
                }
    
                
                if (!savedUser.getPassword().equals(userLoginRequest.getPassword())) {
                    response.setCode(0);
                    response.setMessage("Invalid Password");
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
                response.setMessage("Login successful");
                response.setData(userResponse);
                
            } else {

                response.setCode(0);
                response.setMessage("User Not Found");
                
            }
    
        } catch (Exception e) {
            response.setCode(0);
            response.setMessage("Internal Server Error");
            
        }
        return response;
    }

    @Override
    public ApiResponse<UserResponse> register(UserRegisterRequest userRegisterRequest) {

        ApiResponse<UserResponse> response = new ApiResponse<>();
    try{
        User newUser = new User();


         // Check if phone number already exists
         if (RepositoryAccessor.getUserRepository().existsByPhoneAndIsActive(userRegisterRequest.getPhone(), true)) {
            response.setCode(0);
            response.setMessage("Phone number already exists");
            return response;
        }
        
        // Check if email already exists
        if (RepositoryAccessor.getUserRepository().existsByEmailAndIsActive(userRegisterRequest.getEmail(), true)) {
            response.setCode(0);
            response.setMessage("Email already exists");
            return response;
        }
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
        User savedUser = RepositoryAccessor.getUserRepository().save(newUser);
        
        response.setCode(1);
        response.setMessage("User Registerd Successfully");                
            UserResponse userResponse =
            UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .dob(savedUser.getDob())
                .gender(savedUser.getGender())
                .roleId(savedUser.getRole().getId())
                .build();
        response.setData(userResponse);

        myMailSenderImpl.sendWelcomeEmail(userRegisterRequest.getEmail(), userRegisterRequest.getName());

    }

        catch(Exception e)
        {
            LOGGER.error("[UserServiceImpl >> signup] Exception occurred during signup", e);
            response.setCode(0);
            response.setMessage("Internal server error");
        }

        return response;
    }

    @Override
    public ApiResponse<UserResponse> getUserById(Long userId) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        LOGGER.info("[UserServiceImpl >> getUserById] Validating auth code.");
       
        try {
            User savedUser = RepositoryAccessor.getUserRepository().findByIdAndIsActive(userId, true).orElse(null);
            if (Objects.nonNull(savedUser)) {
                response.setCode(1);
                response.setMessage("User fetched successfully.");


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
                        .build();
                response.setData(userResponse);
               
            } else {
                response.setCode(0);
                response.setMessage("User not found.");
            }
        } catch (Exception e) {
            LOGGER.error(
                    "[UserServiceImpl >> getUserById] Error occurred while fetching user by ID: {}", userId, e);
            response.setCode(0);
            response.setMessage("Error while fetching user.");
        }

        return response;
    }

    @Override
    public ApiResponse<?> passwordChange(UserLoginPasswordChange request) {
        ApiResponse<?> response = new ApiResponse<>();

        try {
            User user = RepositoryAccessor.getUserRepository().findByEmailIgnoreCase(request.getGmail()).orElse(null);
            if (user == null) {
                response.setCode(0);
                response.setMessage("User Not Found");
                return response;
            }

            user.setPassword(request.getPassword());
            RepositoryAccessor.getUserRepository().save(user);
            response.setCode(1);
            response.setMessage("Successfully changed.");

        } catch (Exception e) {
            LOGGER.error(
                "[UserServiceImpl >> getUserById] Error occurred while fetching user by ID: {}", request.getUserId(), e);
        response.setCode(0);
        response.setMessage("Error while fetching user.");
        }

        return response;
    }
    
}
