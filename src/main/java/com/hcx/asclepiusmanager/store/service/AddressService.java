package com.hcx.asclepiusmanager.store.service;

import com.hcx.asclepiusmanager.store.domain.Address;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/28 21:34
 */
public interface AddressService {
    Integer saveAddress(Address address);

    Integer updateAddress(Address address);

    List<Address> findAllAddress();

    Integer deleteAddress(Integer addressId);

    Address findAddressById(Integer addressId);
}
