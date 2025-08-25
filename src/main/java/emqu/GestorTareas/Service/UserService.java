package emqu.GestorTareas.Service;

import emqu.GestorTareas.Repository.IUserRepo;
import emqu.GestorTareas.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepo userRepository;



    public Users register(String email, String rawPassword){

        if(userRepository.findByEmail(email).isPresent())
        {
            throw new RuntimeException("Username Exists");

        }
        Users user = new Users();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setEmail(email);
        user.setPassword(encoder.encode(rawPassword));
        return userRepository.save(user);
    }
}
