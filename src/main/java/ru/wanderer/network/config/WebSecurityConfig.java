package ru.wanderer.network.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.wanderer.network.domain.User;
import ru.wanderer.network.repository.UserDetailsRepository;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests(a -> a
                    .antMatchers("/", "/login**" ,"/error**", "/js/**").permitAll()
                    .anyRequest().authenticated()
                )
                .logout(l -> l
                    .logoutSuccessUrl("/").permitAll()
                )
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsRepository userDetailsRepository) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userDetailsRepository.findById(id).orElseGet(() -> {
                User newUser = new User();

                newUser.setId(id);
                newUser.setName((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setGender((String) map.get("gender"));
                newUser.setUserpic((String) map.get("picture"));
                newUser.setLocale((String) map.get("locale"));

                return newUser;
            });
            user.setLastVisit(LocalDateTime.now());

            return userDetailsRepository.save(user);
        };
    }
}
