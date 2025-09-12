package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
