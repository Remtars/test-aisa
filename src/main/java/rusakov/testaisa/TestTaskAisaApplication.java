package rusakov.testaisa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rusakov.testaisa.authentication.UserAuthorizationFilter;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class TestTaskAisaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TestTaskAisaApplication.class, args);
    }

    // Вся авторизация отключена, потому что у меня не вышло настроить Swagger ...
    // ... таким образом, чтобы он добавлял необходимый заголовок к запросам

    /*
    @Autowired
    private UserAuthorizationFilter filter;

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter
    {
        @Override public void configure(WebSecurity web) throws Exception
        {
            web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
            web.ignoring().mvcMatchers(
                    "/swagger-ui/**",
                    "/configuration/**",
                    "/swagger-resources/**",
                    "/v3/api-docs/**",
                    "/webjars/**");
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/data/enroll").hasAuthority("ROLE_USER")
                    .antMatchers(HttpMethod.POST, "/api/data/enrollFree").hasAuthority("ROLE_USER")
                    .antMatchers(HttpMethod.GET, "/api/data/getTimeFree").hasAuthority("ROLE_USER")
                    .antMatchers(HttpMethod.GET, "/api/data/clients").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/data/addService").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/data/deleteService").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated();
        }
    }
    */
}
