package org.tsp.Banking_System.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.Customer;
import org.tsp.Banking_System.dto.Management;
import org.tsp.Banking_System.exception.MyException;
import org.tsp.Banking_System.helper.ResponceStructure;
import org.tsp.Banking_System.repository.BankRepository;
import org.tsp.Banking_System.repository.ManagementRepository;
import org.w3c.dom.css.CSSRule;

@Service
public class ManagementService 
{
	@Autowired
	ManagementRepository repository;
	
	
	@Autowired
	BankRepository repository2;
  public ResponceStructure<Management> save(Management management)
  {
    ResponceStructure< Management> structure=new ResponceStructure<>();
    structure.setCode(HttpStatus.CREATED.value());
    structure.setData(repository.save(management));
    structure.setMsg("Data created Successfully");
    return structure;  
  }
  
 // *********************************management login   ************************************
public ResponceStructure<Management> login(Management management) throws MyException 
{	

	 ResponceStructure<Management> structure=new ResponceStructure<>();
	 
     Management management1=repository.findByEmail(management.getEmail());
	 if(management1==null)
	 {
		 throw new MyException("invalid manaagement email");
	 }
	 else
	 {
		
		if(management1.getPassword().equals(management.getPassword())) 
		   {
				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMsg("Login success");
				structure.setData(management1);
				
			}
		else
		{
			throw new MyException("invalid password");
		}
	 }
	
		return  structure;
}
// *********************************FETCH ALLL************************************
public ResponceStructure<List<BankAccount>> fetchAllAccounts() throws MyException
{
	ResponceStructure<List<BankAccount>> structure=new ResponceStructure<>();
	List<BankAccount> list=repository2.findAll();
	if(list.isEmpty())
	{
		throw new MyException("no accounts presents");
	}
	else
	{
		structure.setCode(HttpStatus.FOUND.value());
		structure.setMsg("data found");
		structure.setData(list);
	}
	return structure;
}
//*********************************CHANGE ACCOUNT STATUS************************************

public ResponceStructure<BankAccount> changeStatus(long acno)
{
	ResponceStructure<BankAccount> structure=new ResponceStructure<>();
	Optional<BankAccount> optional=repository2.findById(acno);
	BankAccount account=optional.get();
	if(account.isStatus())
	{
		account.setStatus(false);
	}
	else
	{
		account.setStatus(true);
	}
	structure.setCode(HttpStatus.OK.value());
	structure.setMsg("change status success");
	structure.setData(repository2.save(account));
	return structure;

}
}
