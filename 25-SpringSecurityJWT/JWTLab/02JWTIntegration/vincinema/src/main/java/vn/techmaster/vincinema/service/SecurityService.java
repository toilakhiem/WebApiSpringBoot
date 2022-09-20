package vn.techmaster.vincinema.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.techmaster.vincinema.model.Role;
import vn.techmaster.vincinema.model.User;
import vn.techmaster.vincinema.repository.RoleRepository;
import vn.techmaster.vincinema.repository.UserRepository;
import vn.techmaster.vincinema.security.Authority;

@Service
public class SecurityService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder encoder;

  public User createUser(String username, String password, Role ...roles) {
    User user =  new User(username, encoder.encode(password));
    for (Role role : roles) {
      user.addRole(role);
    }
    return user;    
  }

  @Transactional
  public void generateUsersRoles() {
    Role roleAdmin = new Role("ROLE_ADMIN");
    Role roleUser = new Role("USER");
    Role roleAuthor = new Role("AUTHOR");
    Role roleEditor = new Role("EDITOR");

    roleRepository.save(roleAdmin);
    roleRepository.save(roleUser);
    roleRepository.save(roleAuthor);
    roleRepository.save(roleEditor);
    roleRepository.flush();


    User admin = createUser("admin", "123", roleAdmin);
    userRepository.save(admin);

    User bob =  createUser("bob", "123", roleUser);
    userRepository.save(bob);

    User alice =  createUser("alice", "123", roleEditor);
    userRepository.save(alice);

    User tom =  createUser("tom", "123", roleUser, roleEditor);
    userRepository.save(tom);

    User jane =  createUser("jane", "123", roleAuthor);
    userRepository.save(jane);
    
    userRepository.flush();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);      
    if (!user.isPresent()) {
        throw new UsernameNotFoundException("Could not find user");
    }
         
    return user.get();
  }  
}
