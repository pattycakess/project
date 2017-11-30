package osproject;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.*;
import java.io.*;
public class Scheduler {
    public static void main(String [] args) throws FileNotFoundException{
        ArrayList <Process> file=null;
        String address=args[0];//The reading of the file
        int timeQuantum = Integer.parseInt(args[1]);
        
        try{ //read CSV file and storing it in temp ArrayList
            reader trial=new reader(address);
            trial.open();
            file=trial.processList();     
        }
        
        catch(IOException e){
            System.out.println("No file"); //In case there is no file 
        }
       
        System.out.println("Process    BurstTime    ArrivalTime");//prints out process in the queue
        for(int j=0;j<file.size();j++){
            System.out.println(file.get(j).getPID()+"         "+ file.get(j).getBurst()+"            "+file.get(j).getArrivalTime());
        }
        
        ArrayBlockingQueue readyQueue=new ArrayBlockingQueue(file.size());
        for(int i=0;i<file.size();i++){
            readyQueue.add(file.get(i));
        }

        Scanner in=new Scanner(System.in);
        int clock=0;
        Process current_cpu; 
        int contextSwitch=-1;
        int cpuCounter=0;
        int numProcess=readyQueue.size();
        ArrayBlockingQueue completedQueue=new ArrayBlockingQueue(numProcess);

        while(!readyQueue.isEmpty()){
            current_cpu=(Process) readyQueue.poll();
            System.out.println(" ");
            for (int cpuStartTime=clock;(clock-cpuStartTime)<timeQuantum;clock++){ //keeps running for the time quantum times
                if(current_cpu==null){ // finding out when the CPU is idle
                    cpuCounter++;
                }
                System.out.println("Clock time = "+clock+" "+ current_cpu.getPID() +" is running");
                if(current_cpu.getBurst()==current_cpu.getServiceTime()){ //beginning of the process
                }
                
                current_cpu.reduceServiceTime();//lowering the service time

                if(current_cpu.getServiceTime()==0){   //checking if the process finished executing
                    current_cpu.addCompletionTime(clock);
                    completedQueue.add(current_cpu);   //Process is sent to completedQueue
                    System.out.println(current_cpu.getPID()+" completed at clock time= "+current_cpu.getCompletionTime());
                    contextSwitch++; //Increments the context switch value
                    System.out.println("Context switch occurs");
                    clock++;
                    break;
                }
            }
            if(current_cpu.getServiceTime()>0){//Incomplete Processes gets added to the back to the end of the readyQ again
                readyQueue.add(current_cpu);
                contextSwitch++; //Increments context switch value
                System.out.println("Context switch occurs");
            }
        }

        System.out.println("");
        System.out.println("The Time Quantum used was: " + timeQuantum);
        
        //Calculate CPU Utilization 
        double cpuUtilization= ((clock-cpuCounter)/(clock))*100;
        System.out.println("The CPU Utilization is " + cpuUtilization+ " % ");
        
        //Calculating Throughput
        double clockDou=clock;
        double size= completedQueue.size();
        double throughPut=size/clockDou;
        System.out.printf("The through put is "+ "%.4f ",throughPut);
        System.out.println();
        
        //Calculating Average Turnaround Time
        int tTurnAroundTime=0;
        for(int i=0; i<numProcess;i++){
            Process temp1=(Process) completedQueue.poll();
            int turnAroundTime= temp1.getCompletionTime()-temp1.getArrivalTime();
            temp1.addTurnAroundTime(turnAroundTime);
            tTurnAroundTime=turnAroundTime+tTurnAroundTime;
            completedQueue.add(temp1);
        }
        double avgTAT= tTurnAroundTime/size;
        System.out.println("The average turnaround time is: "+ avgTAT);
        
        //Average Waiting Time
        int totalWaitTime=0;
        for(int j=0; j<numProcess;j++){
            Process temp2= (Process)completedQueue.poll();
            int waitTime=temp2.getTurnAroundTime()-temp2.getBurst();
            totalWaitTime=totalWaitTime+waitTime;
        }
        double avgWaiting= totalWaitTime/size;
        System.out.println("The average waiting time is: "+ avgWaiting);
        System.out.println("The number of context switches: "+contextSwitch);
    }
}
