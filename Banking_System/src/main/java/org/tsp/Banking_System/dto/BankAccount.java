package org.tsp.Banking_System.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
@Component
public class BankAccount 
{
	@Id
	@GeneratedValue(generator = "acno")
	@SequenceGenerator(name = "acno",sequenceName = "acno",initialValue = 10210001,allocationSize = 1)
    long number;
	String type;
	double banklimit;
	double amount;
	boolean status;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<BankTransaction> bankTransaction;
	
}
