package org.tsp.Banking_System.helper;

import lombok.Data;

@Data

public class ResponceStructure<T>
{
int code;
String msg;
T data;
}
