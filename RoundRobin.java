/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osprojectfinal;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

class RoundRobin {
public static void main(String args[]) throws IOException {
  DataInputStream in = new DataInputStream(System. in );
  int i, j, k, timeQuantum, sum = 0;
  
  System.out.println("Number of processes:");
  int process = Integer.parseInt(in.readLine());
  Date date = new Date();
  
  for (i = 0; i < process; i++){
	  System.out.println("Process " + (i + 1) + " was created at " + new Timestamp(date.getTime()));  
  }
  
  int burstTime[] = new int[process];
  int waitingTime[] = new int[process];
  int totalAroundTime[] = new int[process];
  int temp[] = new int[process];
  
  for (i = 0; i < process; i++) {
   System.out.println("Enter burst time for Process " + (i + 1) + ": ");
   burstTime[i] = Integer.parseInt( in .readLine());
  }
  
  System.out.println("Enter time quantum:");
  timeQuantum = Integer.parseInt(in.readLine());
  for (i = 0; i < process; i++){
  temp[i] = burstTime[i];
  }
  
  for (i = 0; i < process; i++){
  waitingTime[i] = 0;
  }
  do {
   for (i = 0; i < process; i++) {
    if (burstTime[i] > timeQuantum) {
     burstTime[i] -= timeQuantum;
     for (j = 0; j < process; j++) 
     {  
      if ((j != i) && (burstTime[j] != 0)) 
       waitingTime[j] += timeQuantum;
     }
    } else {
     for (j = 0; j < process; j++) 
     {
      if ((j != i) && (burstTime[j] != 0)) 
       waitingTime[j] += burstTime[i];
     }
     burstTime[i] = 0;
    }
   }
   sum = 0;
   for (k = 0; k < process; k++)
   sum = sum + burstTime[k];
  }
  while (sum != 0);
  for (i = 0; i < process; i++)
	  totalAroundTime[i] = waitingTime[i] + temp[i];
  System.out.println("Process    BurstTime    WaitingTime    ArrivalTime");
  for (i = 0; i < process; i++) {
   System.out.println("P" + (i + 1) + "         " + temp[i] + "            " + waitingTime[i] + "              " + totalAroundTime[i]);
  }
  float avg_waitingTime = 0;
  float avg_turnAroundTime = 0;
  for (j = 0; j < process; j++) {
   avg_waitingTime += waitingTime[j];
  }
  for (j = 0; j < process; j++) {
   avg_turnAroundTime += totalAroundTime[j];
  }
    
  System.out.println("Average Waiting Time " + (avg_waitingTime / process) + "\nAverage Turn Around Time " + (avg_turnAroundTime / process));
  
  for (i = 0; i < process; i++){
	  System.out.println( (i + 1) + " was terminated at " + new Timestamp(date.getTime()));  
  }
  
 }
}
