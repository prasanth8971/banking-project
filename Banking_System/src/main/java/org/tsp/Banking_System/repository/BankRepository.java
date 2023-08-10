package org.tsp.Banking_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tsp.Banking_System.dto.BankAccount;

public interface BankRepository extends JpaRepository<BankAccount, Long>
{

}
