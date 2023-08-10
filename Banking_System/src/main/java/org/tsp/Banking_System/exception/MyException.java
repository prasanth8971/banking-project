package org.tsp.Banking_System.exception;

public class MyException extends Exception
{
String msg="";

public MyException(String msg) 
{
	this.msg = msg;
}

@Override
public String toString()
{
	return msg;
}

}


////if we create an object without passing parameter then default constrictor will called
//public MyException()
//{
//
//}