package spring.generic.server.Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import spring.generic.server.Security.CORSFilter;
import spring.generic.server.Security.Handlers.AuthFailureHandler;
import spring.generic.server.Security.Handlers.AuthSuccessHandler;
import spring.generic.server.Security.Handlers.HttpLogoutSuccessHandler;
import spring.generic.server.Security.Others.HttpAuthenticationEntryPoint;
import spring.generic.server.Security.Others.NuvolaUserDetailsService;
import spring.generic.server.Security.Parameters.Parameters;
import spring.generic.server.Security.Parameters.ResourcePaths;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
//@ComponentScan(value = "com.nuvola.**.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_PATH = ResourcePaths.User.ROOT + ResourcePaths.User.LOGIN;
    private static final String LOGOUT_PATH = ResourcePaths.User.ROOT + ResourcePaths.User.LOGOUT;

    @Autowired
    private NuvolaUserDetailsService userDetailsService;
    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new ShaPasswordEncoder());
        return authenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Autowired
    private CORSFilter corsFilter;

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
   // }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/user/login").permitAll();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.formLogin()
                .permitAll()
                .loginProcessingUrl(LOGIN_PATH)
                .usernameParameter(Parameters.USERNAME)
                .passwordParameter(Parameters.PASSWORD)
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler);

        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH, "DELETE"))
                .logoutUrl(LOGOUT_PATH)
               .logoutSuccessHandler(logoutSuccessHandler);

              http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);


    }
}
