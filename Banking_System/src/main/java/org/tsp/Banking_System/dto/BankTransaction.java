package org.tsp.Banking_System.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Component
public class BankTransaction 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)

 int id;
 LocalDateTime datetime;
 double deposite;
 double withdraw;
 double balance;
 
}
