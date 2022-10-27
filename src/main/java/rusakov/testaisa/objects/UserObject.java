package rusakov.testaisa.objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserObject
{
    @Schema(description = "Имя пользователя", type = "String")
    private String username;
    @Schema(description = "Пароль", type = "String")
    private String password;

    public UserObject()
    {
    }

    public UserObject(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
