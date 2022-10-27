package rusakov.testaisa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rusakov.testaisa.objects.UserObject;
import rusakov.testaisa.repositories.AdminRepository;
import rusakov.testaisa.repositories.ClientRepository;

@Service
public class UserService
{
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public UserService(AdminRepository adminRepository, ClientRepository clientRepository)
    {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    public boolean isAdmin(UserObject userObject)
    {
        return adminRepository.existsByNameAndPassword(userObject.getUsername(), userObject.getPassword());
    }

    public boolean isClient(UserObject userObject)
    {
        return clientRepository.existsByNameAndPassword(userObject.getUsername(), userObject.getPassword());
    }
}
