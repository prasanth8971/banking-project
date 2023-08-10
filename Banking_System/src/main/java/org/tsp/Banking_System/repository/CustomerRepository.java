package org.tsp.Banking_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{

	

}
