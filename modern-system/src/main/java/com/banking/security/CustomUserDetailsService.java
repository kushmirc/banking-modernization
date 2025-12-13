package com.banking.security;

import com.banking.model.Administrator;
import com.banking.model.Banker;
import com.banking.model.Customer;
import com.banking.repository.AdministratorRepository;
import com.banking.repository.BankerRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private BankerRepository bankerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BankingUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] userParts = username.split(":");
        String userType = userParts[0];
        String userid = userParts[1];

        if (userType.equals("ADMINISTRATOR")) {
            Administrator user = administratorRepository.findByUserId(userid)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userid));

            return new BankingUserDetails(
                    user.getUserId(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    "ADMINISTRATOR"
            );

            /*return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserId())
                    .password(user.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))
                    .build();*/

        } else if (userType.equals("BANKER")) {
            Banker user = bankerRepository.findByUserId(userid)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userid));

            return new BankingUserDetails(
                    user.getUserId(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    "BANKER"
            );

            /*return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserId())
                    .password(user.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_BANKER")))
                    .build();*/

        } else if (userType.equals("CUSTOMER")) {
            Customer user = customerRepository.findByUserId(userid)
                    .orElseThrow(() -> new UsernameNotFoundException("Account not found: " + userid));

            return new BankingUserDetails(
                    user.getUserId(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    "CUSTOMER"
            );

            /*return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserId())
                    .password(user.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                    .build();*/
        }
        return null;
    }
}
