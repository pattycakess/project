package osprojectfinal;

import java.io.*;
import java.util.*;

public class reader 
{
	public int processNum=0;
	public int timeQuantum;
	public int[] burstArray = new int[100];
	public int[] arrival = new int[100];
	public int[] waitTime = new int[100];
	public int[] turnAroundTime = new int[100];
	float avg_waitTime=0;
	float avg_turnAroundTime=0;
	public int burstNum;
	int i,g,q,k,z,sum=0;
	
	public void runCSVFile(String userFile)
	{
		String csvFile = userFile;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();

			while ((line = br.readLine()) != null) 
			{
				String[] burst = line.split(cvsSplitBy);
				burstNum = Integer.parseInt(burst[2]);
				makeArray(burstNum,i);
				i++;
				processNum++;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void makeArray(int burstNum2, int i){
		burstArray[i] = burstNum2;
	}	
	public int timeQuautum(int timeQ){
		timeQuantum = timeQ;
		return timeQuantum;
	}
	public void roundRobinAlgorithm()
	{
	for(g=0;g<processNum;g++)
		arrival[g]=burstArray[g];
	
		for(g=0;g<processNum;g++)
			waitTime[g]=0;
		do
		{
			for(g=0;g<processNum;g++)
			{
				if(burstArray[g]>timeQuantum)
				{
					burstArray[g]-=timeQuantum;
					
					for(k=0;k<processNum;k++)
					{
						if((k!=g)&&(burstArray[k]!=0))
							waitTime[k]+=timeQuantum;
					}
				}
				else
				{
					for(k=0;k<processNum;k++)
					{
						if((k!=i)&&(burstArray[k]!=0))
							waitTime[k]+=burstArray[g];
					}
					burstArray[g]=0;
				}
			}
			sum=0;
			for(z=0;z<processNum;z++)
				sum=sum+burstArray[z];
	}
		while(sum!=0);
		for(g=0;g<processNum;g++)
			turnAroundTime[g]=waitTime[g]+arrival[g];
		
		for(k=0;k<processNum;k++)
		{
			avg_waitTime+=waitTime[k];
		}
		for(k=0;k<processNum;k++)
		{
			avg_turnAroundTime+=turnAroundTime[k];
		}
	
		System.out.println();
		System.out.println("Process\t\tB.T.\tW.T.\tT.A.T.");
		System.out.println("--------------------------------------");
		for(g=0;g<processNum;g++)
		{
			System.out.println((g+1)+"\t "+arrival[g]+"\t "+waitTime[g]+"\t  "+turnAroundTime[g]);
		}
		System.out.println();
		System.out.println("Average Waiting Time: "+(avg_waitTime/processNum)+"\nAverage Turnaround Time: "+(avg_turnAroundTime/processNum));
	}
	
	public static void main(String[] args) 
	 {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Enter a time quantum: ");
			int userTime = keyboard.nextInt();
		 	String path = "/Users/patriciahidalgo/NetBeansProjects/osprojectfinal/build/classes/osprojectfinal/processes.csv";
			reader obj = new reader();
			obj.runCSVFile(path);
			obj.timeQuautum(userTime);
			obj.roundRobinAlgorithm();
			
		  }
}
