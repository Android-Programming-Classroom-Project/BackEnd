package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.Schools;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.dto.JoinDTO;
import com.project.bridgetalkbackend.repository.SchoolsRepository;
import com.project.bridgetalkbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final SchoolsRepository schoolsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,SchoolsRepository schoolsRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.schoolsRepository = schoolsRepository;
    }

    @Transactional
    public void joinProcess(JoinDTO userDTO) {
        String username = userDTO.getUsername();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String role = userDTO.getRole();
        String school = userDTO.getSchoolName();
        boolean isExist = userRepository.existsByEmail(email);
        if (isExist) {
            throw new IllegalStateException("이메일이 존재합니다: " + email);
        }

        if(password == null){
            throw new IllegalStateException("비번X");
        }
        //학교 정보 가져오기
        Schools schools = schoolsRepository.findBySchoolName(school);

        if(schools.getSchoolId() == null ){
            throw new IllegalStateException("학교 존재X");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(role);
        user.setUsername(username);
        user.setSchools(schools);
        userRepository.save(user);
    }
}

