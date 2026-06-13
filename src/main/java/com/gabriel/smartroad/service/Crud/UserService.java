package com.gabriel.smartroad.service.Crud;

import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.dto.User.RequestChangePasswordDTO;
import com.gabriel.smartroad.dto.User.RegisterDTO;
import com.gabriel.smartroad.dto.User.ResponseDTO;
import com.gabriel.smartroad.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // SALVA (CRIA)
    public ResponseDTO create(RegisterDTO dto) {

        // Verifica se o e-mail já existe
        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new RuntimeException("This e-mail address is already in use!");
        }
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        // Criptografa a senha antes de passa para o objeto
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
        return  new ResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public void changePassword(Long userId, RequestChangePasswordDTO dto){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(dto.currentPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }
}
