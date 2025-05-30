package com.project.crash.service;

import com.project.crash.exception.user.UserAlreadyExistsException;
import com.project.crash.exception.user.UserNotFoundException;
import com.project.crash.model.entity.UserEntity;
import com.project.crash.model.user.User;
import com.project.crash.model.user.UserAuthenticationResponse;
import com.project.crash.model.user.UserLoginRequestBody;
import com.project.crash.model.user.UserSignUpRequestBody;
import com.project.crash.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserEntityByUsername(username);
    }

    public User signUp(UserSignUpRequestBody userSignUpRequestBody) {
        // 회원가입이 된 적 있는지 검증
        userEntityRepository.findByUsername(userSignUpRequestBody.username())
                .ifPresent(
                        user -> {
                            throw new UserAlreadyExistsException();
                        });

        UserEntity userEntity = userEntityRepository.save(
                UserEntity.of(
                        userSignUpRequestBody.username(),
                        passwordEncoder.encode(userSignUpRequestBody.password()),
                        userSignUpRequestBody.name(),
                        userSignUpRequestBody.email()));
        return User.from(userEntity);
    }

    public UserAuthenticationResponse authenticate(UserLoginRequestBody userLoginRequestBody) {
        // 존재 여부 확인
        UserEntity userEntity = getUserEntityByUsername(userLoginRequestBody.username());

        if (passwordEncoder.matches(userLoginRequestBody.password(), userEntity.getPassword())) {
            String accessToken = jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(accessToken);
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserEntity getUserEntityByUsername(String username) {
        return userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
