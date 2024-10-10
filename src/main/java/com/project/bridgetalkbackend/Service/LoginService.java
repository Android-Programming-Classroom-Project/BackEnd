package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.Liked;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.Schools;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.dto.InitDTO;
import com.project.bridgetalkbackend.repository.LikedRepository;
import com.project.bridgetalkbackend.repository.PostRepository;
import com.project.bridgetalkbackend.repository.SchoolsRepository;
import com.project.bridgetalkbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    private final UserRepository  userRepository;
    private final PostRepository postRepository;

    @Autowired
    public LoginService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 로그인시 정보 제공
    public InitDTO loginSuccess(String email){
        User user = userRepository.findByEmail(email);
        List<Post> posts = postRepository.findBySchoolsSchoolId(user.getSchools().getSchoolId());
        return new InitDTO(user,posts);
    }
}
