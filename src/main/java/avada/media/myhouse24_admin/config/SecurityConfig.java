package avada.media.myhouse24_admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/css/**", "/img/**", "/js/**").permitAll()
                        .antMatchers("/statistics").hasAnyAuthority("ROLE_DIRECTOR", "STATISTICS")
                        .antMatchers("/transactions").hasAnyAuthority("ROLE_DIRECTOR", "TRANSACTIONS")
                        .antMatchers("/invoices").hasAnyAuthority("ROLE_DIRECTOR", "INVOICES")
                        .antMatchers("/accounts").hasAnyAuthority("ROLE_DIRECTOR", "ACCOUNTS")
                        .antMatchers("/flats").hasAnyAuthority("ROLE_DIRECTOR", "FLATS")
                        .antMatchers("/users").hasAnyAuthority("ROLE_DIRECTOR", "USERS")
                        .antMatchers("/buildings").hasAnyAuthority("ROLE_DIRECTOR", "BUILDINGS")
                        .antMatchers("/messages").hasAnyAuthority("ROLE_DIRECTOR", "MESSAGES")
                        .antMatchers("/master-requests").hasAnyAuthority("ROLE_DIRECTOR", "MASTER_REQUESTS")
                        .antMatchers("/counters").hasAnyAuthority("ROLE_DIRECTOR", "COUNTERS")
                        .antMatchers("/website-control/**").hasAnyAuthority("ROLE_DIRECTOR", "WEBSITE_CONTROL")
                        .antMatchers("/system-settings/services").hasAnyAuthority("ROLE_DIRECTOR", "SYSTEM_SETTINGS_SERVICES")
                        .antMatchers("/system-settings/tariffs").hasAnyAuthority("ROLE_DIRECTOR", "SYSTEM_SETTINGS_TARIFFS")
                        .antMatchers("/system-settings/roles").hasAnyAuthority("ROLE_DIRECTOR", "SYSTEM_SETTINGS_ROLES")
                        .antMatchers("/system-settings/staffs").hasAnyAuthority("ROLE_DIRECTOR", "SYSTEM_SETTINGS_STAFFS")
                        .antMatchers("/system-settings/payment-details").hasAnyAuthority("ROLE_DIRECTOR", "SYSTEM_SETTINGS_PAYMENT_DETAILS")
                        .antMatchers("/system-settings/transaction-purposes").hasAnyAuthority("ROLE_DIRECTOR", "SYSTEM_SETTINGS_TRANSACTION_PURPOSES")
                        .anyRequest().authenticated()
                )
                .logout().logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .rememberMe();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
