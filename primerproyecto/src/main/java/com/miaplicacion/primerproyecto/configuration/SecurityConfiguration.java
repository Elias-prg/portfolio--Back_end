package com.miaplicacion.primerproyecto.configuration;

import com.miaplicacion.primerproyecto.model.Persona;
import com.miaplicacion.primerproyecto.service.Roles;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/*

 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
    
    private final RSAPublicKey key;
    private final RSAPrivateKey priv;
    
        @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Autowired
    private DataSource dataSource;

    // the following method configures the Authentication library to use MySQL datasource
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    public SecurityConfiguration(@Value("${jwt.public.key}") RSAPublicKey key, @Value("${jwt.private.key}") RSAPrivateKey priv) {
        this.key = key;
        this.priv = priv;
    }
    
    @Bean
public UserDetailsService userDetailsService(DataSource dataSource) {
    JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
    jdbcDao.setDataSource(dataSource);
    return jdbcDao;
}

   @Bean
UserDetailsService users() {
    return new InMemoryUserDetailsManager(
            User.withUsername("Diosteamo")
                    .password(passwordEncoder().encode("1234"))
                    .roles(Roles.USER)
                    .build()
    );
} 
    
    
    // With newer Spring Security versions HttpSecurity is configured using SecurityFilterChain and no longer extend WebSecurityConfigurerAdapater.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                // CSRF is enabled  csrf.ignoringRequestMatchers("/api/auth"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/auth")))
                .httpBasic(Customizer.withDefaults())
                // use JWT token
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                // session is defined as stateless
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );
        return http.build();
    }
    
     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // We define encode and decoder using asymmetric keys. That means the private key is used to encode the token and the public key to decode.
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // The JWT contains a claim “scope” that contains the user roles.
        // By default, the JWT decoder adds a SCOPE_ suffix to the role names, and we want to avoid that.
        // Therefore we have to set the authority prefix to an empty string.
        // Remove the SCOPE_ prefix
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
    
  
    
    
}
 /*   
@Bean
UserDetailsService users() {
    return new InMemoryUserDetailsManager(
            User.withUsername("usuario")
                    .password(passwordEncoder().encode("pass"))
                    .roles(Roles.USER)
                    .build()
    );
} 

@Bean
public JwtEncoder jwtEncoder() {
    // Reemplaza "https://example.com/jwks" con la URI real de tu conjunto de claves JWK
    return NimbusJwtEncoder.withJwkSetUri("https://mi-servidor.com/jwks").build();
}


   @Bean
UserDetailsService users() {
        Persona miPersona = new Persona();
        miPersona.setUsuario("Elias Pilon");
        miPersona.setPass("Yoprogramo");
        
        String miUsuario = "nombreDeUsuario";
        String miContraseña = "contraseña";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String miContraseñaCodificada = passwordEncoder.encode(miContraseña);
        
         UserDetails user = User.withUsername(miUsuario)
                .password(miContraseñaCodificada)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);

    
}


  
  @Bean
public UserDetailsService userDetailsService(DataSource dataSource) {
    JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
    manager.setDataSource(dataSource);
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(manager);
    authProvider.setPasswordEncoder(encoder);
    manager.setAuthenticationManager(new ProviderManager(authProvider));
    return manager;
}
@Autowired
private JdbcUserDetailsManager userDetailsService;

public void createUser(String username, String password) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    String encodedPassword = encoder.encode(password);
    UserDetails user = User.withUsername(username)
            .password(encodedPassword)
            .roles("USER")
            .build();
    userDetailsService.createUser(user);
}


*/