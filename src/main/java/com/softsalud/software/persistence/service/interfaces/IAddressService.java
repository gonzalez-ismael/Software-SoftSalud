package com.softsalud.software.persistence.service.interfaces;

import com.softsalud.software.persistence.entity.Address;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IAddressService {
    public List<Address> getAddresses();
    public void saveAddress(String district, String street, Integer number);
    public Address saveAddress2(String district, String street, Integer number);
    public void deleteAddress(Long id);
    public void updateAddress(Long id, String district, String street, Integer number);
    public Address findAddress(Long id);
}
