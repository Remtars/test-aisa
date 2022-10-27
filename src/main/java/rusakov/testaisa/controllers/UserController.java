package rusakov.testaisa.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rusakov.testaisa.objects.ServiceObject;
import rusakov.testaisa.objects.UserObject;
import rusakov.testaisa.services.UserService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController
{
    private static final String EXAMPLE_LOGIN = "{\n" +
            "  \"username\": \"admin\",\n" +
            "  \"password\": \"admin\"\n" +
            "}";

    @Value("${auth.secret}")
    private String SECRET;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @Operation(description = "Получение токена для пользователя")
    @PostMapping("/api/user")
    public ResponseEntity<String> login(
            @Parameter(description = "Объект, описывающий пользователя")
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        content = @Content(examples = {
                        @ExampleObject(name = "Получение токена", value = EXAMPLE_LOGIN)}
                        )
                    )
            @RequestBody UserObject userObject)
    {

        StringBuilder roles = new StringBuilder();

        if (userService.isAdmin(userObject))
        {
            roles.append("ROLE_ADMIN");
        }
        if (userService.isClient(userObject))
        {
            if (!roles.isEmpty()) roles.append(",");
            roles.append("ROLE_USER");
        }
        if (roles.isEmpty())
        {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        String token = getJWTToken(userObject, roles.toString());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private String getJWTToken(UserObject user, String roles)
    {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

        String token = Jwts
                .builder()
                .setId("JWT")
                .setSubject(user.getUsername())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

        return "Bearer " + token;
    }
}
