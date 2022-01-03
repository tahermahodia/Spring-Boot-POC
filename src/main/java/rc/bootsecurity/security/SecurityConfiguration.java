package rc.bootsecurity.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public final static String AUTHORIZATION_HEADER = "Authorization";


    @Autowired
    private TokenProvider tokenProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JWTFilter customFilter = new JWTFilter(this.tokenProvider);
        http.addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().antMatchers("/*.css").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/signout").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/font/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/fontawesome/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().fullyAuthenticated()
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/signout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        //.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/signout");
        // @formatter:on
        http.csrf().disable();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
