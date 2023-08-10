package org.tsp.Banking_System.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
@Entity
@Data
@Component
public class Customer 
{
	@Id
	@SequenceGenerator(initialValue = 425881040,allocationSize = 1,name="cust_id",sequenceName = "cust_id")
	@GeneratedValue(generator = "cust_id")
    int cust_id;
	String name;
	long mobile;
	String email;
	String password;
	Date dob;
	int age;
	boolean status;
	int otp;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<BankAccount> accounts;
   
}
