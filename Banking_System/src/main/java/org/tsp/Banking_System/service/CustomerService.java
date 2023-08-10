package org.tsp.Banking_System.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.BankTransaction;
import org.tsp.Banking_System.dto.Customer;
import org.tsp.Banking_System.dto.Login;
import org.tsp.Banking_System.exception.MyException;
import org.tsp.Banking_System.helper.MailVarification;
import org.tsp.Banking_System.helper.ResponceStructure;
import org.tsp.Banking_System.repository.BankRepository;
import org.tsp.Banking_System.repository.CustomerRepository;

@Service
public class CustomerService 
{
 @Autowired
 CustomerRepository repository;
 
 @Autowired
 Customer customer;
 
 @Autowired
 MailVarification mail;
 
 @Autowired
 BankAccount account;
 
 @Autowired
 BankRepository bankrepository;
 
 @Autowired
 BankTransaction transaction;
 
 public ResponceStructure<Customer> save(Customer customer)
 {
	 ResponceStructure<Customer> structure=new ResponceStructure<>();
	 int age=Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears(); 
	 if(age<18)
	 {
		 structure.setMsg(("You Should Be 18+ to Create The Account"));
		 //CONFLIT--->is not for save the data check condition and return some msg to the user
		 structure.setCode(HttpStatus.CONFLICT.value());
		 structure.setData(customer);
	 }
	 else
	 {
		 //for generating otp-->Math.random()*100000;  --->one method    (or)
		 //we can write this also 
		 Random random=new Random();
		 int otp=random.nextInt(100000,999999);
		 customer.setOtp(otp);
		 customer.setAge(age);
		 
		 //mail.sendmail(customer);
		 
		 structure.setMsg("verification mail sent");
		 structure.setCode(HttpStatus.PROCESSING.value());
		 structure.setData(repository.save(customer));
		 
	 }
	 return structure;
 }
 public ResponceStructure<Customer> OtpVerification(int cust_id,int otp)throws MyException
 {

	 ResponceStructure<Customer> structure=new ResponceStructure<>();
	 //Optional is similar to the collection but it is having only one set of data
	 Optional<Customer> optional=repository.findById(cust_id);
	 if(optional.isEmpty())
	 {
		 throw new MyException("check id and try again");
	 }
	 else
	 {
		 Customer customer=optional.get();
		 if(customer.getOtp()==otp)
		 {
			 customer.setStatus(true);
			 structure.setCode(HttpStatus.CREATED.value());
			 structure.setMsg("account created successfully");
			 structure.setData(repository.save(customer));
			 
		 }
		 else
		 {
			 throw new MyException("otp mismatch");
		 }
		 return structure;
	 }
	 
 }
public ResponceStructure<Customer> login(Login login) throws MyException
{
	 ResponceStructure<Customer> structure=new ResponceStructure<>();
	 
	 Optional<Customer> optional=repository.findById(login.getId());
	 if(optional.isEmpty())
	 {
		 throw new MyException("invalid customer id");
	 }
	 else
	 {
		 Customer customer=optional.get();
		if(customer.getPassword().equals(login.getPassword())) 
		{
			if(customer.isStatus())
			{
				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMsg("Login success");
				structure.setData(customer);
				
			}
			else
			{
				throw new MyException("verify your email first");
			}
		}
		else
		{
			throw new MyException("invalid password");
		}
	 }
	
		return  structure;
}
public ResponceStructure<Customer> CreateAccount(int cust_id, String type) throws MyException
{
	ResponceStructure<Customer> structure=new ResponceStructure<Customer>();
	 Optional<Customer> optional=repository.findById(cust_id);
	 if(optional.isEmpty())
	 {
		 throw new MyException("invalid customer id");
	 }
	 else
	 {
		 Customer customer=optional.get();
		List<BankAccount> list=customer.getAccounts();
		
		boolean flog=true;
		for(BankAccount account:list)
		{
			if(account.getType().equals(type))
			{
				flog=false;
				break;
			}
		}
		if(!flog)
		{
			throw new MyException(type+"Accounts Allredy exits");
		}
		else
		{
		   account.setType(type);
		   if(type.equals("savings"))
		   {
			 account.setBanklimit(5000);
		   }
		   else
		   {
			   account.setBanklimit(10000);
		   }
		   
		   
		    list.add(account);
			customer.setAccounts(list);
	    }
		structure.setCode(HttpStatus.ACCEPTED.value());
		structure.setMsg("Account created wait for management approved");
		structure.setData(repository.save(customer));
		
		
	 }
	
	return structure;
}
public ResponceStructure<List<BankAccount>> fetchAllTrue(int cust_id) throws MyException 
{
	ResponceStructure<List<BankAccount>> structure=new ResponceStructure<List<BankAccount>>();
    Optional<Customer> optional=repository.findById(cust_id);
    Customer customer=optional.get();
    List<BankAccount> list=customer.getAccounts();
    List<BankAccount> res=new ArrayList<BankAccount>();
    for(BankAccount account:list)
    {
    	if(account.isStatus())
    	{
    		res.add(account);
    	}
    }
    if(res.isEmpty())
    {
    	throw new MyException("No active accounts found");
    }
    else
    {
    	structure.setCode(HttpStatus.FOUND.value());
    	structure.setMsg("Accounts found");
    	structure.setData(res);
    }
	return structure;
}
public ResponceStructure<Double> checkBalance(long acno)
{
	ResponceStructure<Double> structure=new ResponceStructure<Double>();
	Optional<BankAccount> optional=bankrepository.findById(acno);
	BankAccount account=optional.get();
	
	structure.setCode(HttpStatus.FOUND.value());
	structure.setMsg("data found");
	structure.setData(account.getAmount());
	
	return structure;
}
public ResponceStructure<BankAccount> deposite(long acno, double amount)
{
	ResponceStructure<BankAccount> structure=new ResponceStructure<BankAccount>();
	BankAccount account=bankrepository.findById(acno).get();
	account.setAmount(account.getAmount()+amount);
	
	transaction.setDatetime(LocalDateTime.now());
	transaction.setDeposite(amount);
	transaction.setBalance(account.getAmount());
	
	List<BankTransaction> transactions=account.getBankTransaction();
	transactions.add(transaction);
	
	account.setBankTransaction(transactions);
	
	structure.setCode(HttpStatus.ACCEPTED.value());
	structure.setMsg("Amount added successfully");
	structure.setData(bankrepository.save(account));
	return structure;
}
public ResponceStructure<BankAccount> withdraw(long acno, double amount) throws MyException
{
	ResponceStructure<BankAccount> structure=new ResponceStructure<BankAccount>();
	BankAccount account=bankrepository.findById(acno).get();
	
	if(amount>account.getBanklimit())
	{
		throw new MyException("Out of Limit");
	}
	else
	{
		if(amount>account.getAmount())
		{
			throw new MyException("insufficient funds");
		}
		else
		{
			account.setAmount(account.getAmount()-amount);
			
			transaction.setDatetime(LocalDateTime.now());
			transaction.setWithdraw(amount);
			transaction.setBalance(account.getAmount());
			
			List<BankTransaction> transactions=account.getBankTransaction();
			transactions.add(transaction);
			
			account.setBankTransaction(transactions);
			
			structure.setCode(HttpStatus.ACCEPTED.value());
			structure.setMsg("Amount withdraw successfully");
			structure.setData(bankrepository.save(account));
		}
	}
			
	return structure;
}
public ResponceStructure<List<BankTransaction>> viewTransaction(long acno) throws MyException
{
	ResponceStructure<List<BankTransaction>> structure=new ResponceStructure<List<BankTransaction>>();
	BankAccount account=bankrepository.findById(acno).get();
	List<BankTransaction> list=account.getBankTransaction();
	
	if(list.isEmpty())
	{
		throw new MyException("Notranction");
	}
	else
	{
		structure.setCode(HttpStatus.FOUND.value());
		structure.setMsg("data found");
		structure.setData(list);
	}
	   return structure;
	
}

}
