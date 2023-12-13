package com.softsalud.software.persistence.service;

import com.softsalud.software.persistence.entity.Address;
import com.softsalud.software.persistence.repository.IAddressRepository;
import com.softsalud.software.persistence.service.interfaces.IAddressService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gonzalez Ismael
 */
@Service
public class AddressService implements IAddressService{
    @Autowired IAddressRepository iAddressRepos;

    @Override
    public List<Address> getAddresses() {
        return iAddressRepos.findAll();
    }

    @Override
    public void saveAddress(String district, String street, Integer number) {
        Address a = new Address();
        a.setDistrict(district);
        a.setStreet(street);
        a.setNumber(number);
        iAddressRepos.save(a);
    }

    @Override
    public void deleteAddress(Long id) {
        iAddressRepos.deleteById(id);
    }

    @Override
    public void updateAddress(Long id, String district, String street, Integer number) {
        Address a = findAddress(id);
        a.setDistrict(district);
        a.setStreet(street);
        a.setNumber(number);
        iAddressRepos.save(a);
    }

    @Override
    public Address findAddress(Long id) {
        return iAddressRepos.findById(id).orElse(null);
    }
}
