package com.softsalud.software.persistence.service;

import com.softsalud.software.persistence.entity.Phone;
import com.softsalud.software.persistence.repository.IPhoneRepository;
import com.softsalud.software.persistence.service.interfaces.IPhoneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gonzalez Ismael
 */
@Service
public class PhoneService implements IPhoneService {
    @Autowired IPhoneRepository iPhoneRepos;

    @Override
    public List<Phone> getPhones() {
        return iPhoneRepos.findAll();
    }

    @Override
    public void savePhone(Long phone) {
        Phone p = new Phone();
        p.setPhone(phone);
        iPhoneRepos.save(p);
    }

    @Override
    public void deletePhone(Long id) {
        iPhoneRepos.deleteById(id);
    }

    @Override
    public void updatePhone(Long id, Long phone) {
        Phone p = findPhone(id);
        p.setPhone(phone);
        iPhoneRepos.save(p);
    }

    @Override
    public Phone findPhone(Long id) {
        return iPhoneRepos.findById(id).orElse(null);
    }
    
}
