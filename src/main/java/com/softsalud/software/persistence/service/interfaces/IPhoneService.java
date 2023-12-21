package com.softsalud.software.persistence.service.interfaces;

import com.softsalud.software.persistence.entity.Phone;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IPhoneService {
    public List<Phone> getPhones();
    public void savePhone(Long phone);
    public Phone savePhone2(Long phone);
    public void deletePhone(Long id);
    public void updatePhone(Long id, Long phone);
    public Phone findPhone(Long id);
}
