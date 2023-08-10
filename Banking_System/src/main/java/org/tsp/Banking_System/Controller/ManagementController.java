package org.tsp.Banking_System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.Management;
import org.tsp.Banking_System.exception.MyException;
import org.tsp.Banking_System.helper.ResponceStructure;
import org.tsp.Banking_System.service.ManagementService;

@RestController
@RequestMapping("management")
public class ManagementController 
{
	@Autowired
	ManagementService service;
	 
	@PostMapping("add")
	public ResponceStructure<Management> save(@RequestBody Management manegement)
	{
		return service.save(manegement);
	}
	@PostMapping("login")
	public ResponceStructure<Management> login(@RequestBody Management management) throws MyException
	{
		return service.login(management);
	}
	@GetMapping("accounts")
	public ResponceStructure<List<BankAccount>> fetchAllAccounts() throws MyException
	{
		return service.fetchAllAccounts();
	}
	
	@PutMapping("accountchange/{acno}")
	public ResponceStructure<BankAccount> changeStatus(@PathVariable long acno)
	{
		return service.changeStatus(acno);
	}
}
