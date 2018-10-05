package org.test.sms.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.test.sms.web.jwt.JwtAuthenticationEntryPoint;
import org.test.sms.web.jwt.JwtAuthenticationTokenFilter;

import java.util.Properties;

@Configuration
@ComponentScan("org.test.sms")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Environment environment;

    private UserDetailsService userDetailsService;

    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public WebSecurityConfig(Environment environment, UserDetailsService userDetailsService, JwtAuthenticationTokenFilter authenticationTokenFilter,
                             JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.environment = environment;
        this.userDetailsService = userDetailsService;
        this.authenticationTokenFilter = authenticationTokenFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getRequiredProperty("mail.server"));
        mailSender.setPort(environment.getRequiredProperty("mail.port", Integer.class));
        mailSender.setUsername(environment.getRequiredProperty("mail.username"));
        mailSender.setPassword(environment.getRequiredProperty("mail.password"));

        Properties properties = mailSender.getJavaMailProperties();

        properties.put("mail.transport.protocol", environment.getRequiredProperty("mail.transport.protocol"));
        properties.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", environment.getRequiredProperty("mail.smtp.starttls.enable"));

        return mailSender;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        return new FreeMarkerConfigurationFactoryBean();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth", "/auth/resetPassword/*", "/auth/changePassword/**", "/auth/savePassword").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();
    }
}