package ua.helpdesk.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.UserService;

import java.util.ArrayList;
import java.util.List;


//@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

   // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        logger.info("User : {}", user);
        if (user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        } else if (!user.getActive()) {
            logger.info("User is not active");
            throw new UsernameNotFoundException("User is not active");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //System.out.println("authorities add : ROLE_" + user.getUserType().getName());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));


//		authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getUserType()));
//		for(UserType userType : user.getUserProfiles()){
//			logger.info("UserType : {}", userType);
//			authorities.add(new SimpleGrantedAuthority("ROLE_"+ userType.getName()));
//		}
        logger.info("authorities : {}", authorities);
        return authorities;
    }

}
