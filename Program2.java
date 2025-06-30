import java.io.*;
import java.util.*;
public class Program2
{
	public static int CalculateLineNum()
	{
		String line;
		int lineNum=0;
		try
		{
			FileInputStream F=new FileInputStream("Data.csv");
			InputStreamReader R=new InputStreamReader(F);
			BufferedReader BufRdr=new BufferedReader(R);
			line=BufRdr.readLine();
			line=BufRdr.readLine();
			while(line!=null)
			{
				lineNum++;
				line=BufRdr.readLine();
			}
			F.close();
			System.out.println("You have "+ lineNum + " lines in the csv file.");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return lineNum;
	}
	
	public static char Analysis()
	{
		char choice;
		Scanner sc=new Scanner(System.in);
		do
		{
			System.out.println("Do you want to do data analysis or team analysis?");
			System.out.println("Choose D for data analysis or T for team analysis. ");
			System.out.println("Your choice: ");
			choice=sc.next().charAt(0);
			if(choice== 'D' || choice== 'd')
			{
				break;
			}
			
			else if(choice== 'T' || choice== 't')
			{
				break;
			}
			
			else
			{
				System.out.println("Invalid choice.");
				System.out.println("Please enter again. ");
			}
		}while(choice!='D' && choice!='d' && choice!='T' && choice!='t');
		return choice;
	}
	
	public static String[] [] DataAnalysis(int pNumOfRows)
	{
		String line;
		String [] [] values=new String [pNumOfRows][6]; //splited String value from the variable 'line'
		int [] iValuesPF=new int [pNumOfRows];
		HashSet<String> TeamsWithCompletion=new HashSet<String>(); //creating a HashSet to store team names with completion of race
		HashSet<String> TeamsWithoutCompletion= new HashSet<String>();
		int j;
		int i;
		try
		{
			FileReader F=new FileReader("Data.csv");
			BufferedReader bufRdr=new BufferedReader(F);
			line=bufRdr.readLine();
			System.out.println("The following teams have at least one driver completing the race: ");
			for(i=0; i<pNumOfRows; i++)
			{
				line=bufRdr.readLine();
				for(j=0; j<6; j++)
				{
			
					values[i][j]=line.split(",")[j];
					if(j==4)
					{
						iValuesPF[i]=Integer.parseInt(values[i][j]); //convert String to integer
					}
				}
				if(iValuesPF[i]!=-1 && TeamsWithCompletion.add(values[i][0]))
				{
					System.out.println(values[i][0]);
				}
			}
			F.close();
			System.out.println("The following teams have no driver completed the race: ");
			for(int R=0; R<pNumOfRows; R++)
			{	
				if(iValuesPF[R]==-1 && !TeamsWithCompletion.contains(values[R][0])&& TeamsWithoutCompletion.add(values[R][0])) //check if the team name is contained in the HashSet
				{
					System.out.println(values[R][0]);
				}
			}
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		return values;
		
	}
	
	public static double [] FindFastestTeam(String [] [] values, int pNumOfRows)
	{
		double [] FastestLap=new double[pNumOfRows];
		double [] SumTime=new double [pNumOfRows];
		double FastestTime=0.0;
		String FastestTeam=null;
		boolean isSwapped;
		System.out.println("The following fastest time for each team is as below: ");
		for(int R=0; R<pNumOfRows; R++)
		{
			if(values[R][5]!=null)
			{
				FastestLap[R]=Double.parseDouble(values[R][5]);
				if(!values[R][4].equals("-1"))
				{
				System.out.println(values[R][0] + " : " + FastestLap[R]);
				}
				if(values[R][4].equals("-1"))
				{
				FastestLap[R]=205.5;
				System.out.println(values[R][0] + " : " + FastestLap[R]);
				}
			}
		}
		
		for(int x=0; x<pNumOfRows-1; x++)
		{
			if(values[x][0].equals(values[x+1][0]) && !values[x][4].equals("-1") && !values[x+1][4].equals("-1"))
			{
				SumTime[x]=FastestLap[x]+FastestLap[x+1];
				System.out.println("The total time of the team "+ values[x][0] + " is " + SumTime[x]);
			}
			
			if(values[x][0].equals(values[x+1][0]) && values[x][4].equals("-1") && !values[x+1][4].equals("-1"))
			{
				FastestLap[x]=205.50;
				SumTime[x]=FastestLap[x]+FastestLap[x+1];
				System.out.println("The total time of the team "+ values[x][0] + " is " + SumTime[x]);
			}
			
			if(values[x][0].equals(values[x+1][0]) && !values[x][4].equals("-1") && values[x+1][4].equals("-1"))
			{
				FastestLap[x+1]=205.50;
				SumTime[x]=FastestLap[x]+FastestLap[x+1];
				System.out.println("The total time of the team "+ values[x][0] + " is " + SumTime[x]);
			}
			
			if(values[x][0].equals(values[x+1][0]) && values[x][4].equals("-1") && values[x+1][4].equals("-1"))
			{
				FastestLap[x]=205.50;
				FastestLap[x+1]=205.50;
				SumTime[x]=FastestLap[x]+FastestLap[x+1];
				System.out.println("The total time of the team "+ values[x][0] + " is " + SumTime[x]);
			}
		}
		
		for(int x=0; x<pNumOfRows; x++)
		{
			isSwapped=false;
			for(int y=0; y<pNumOfRows-x-1; y++)
			{
				if(SumTime[y]>=SumTime[y+1])
				{
					double TimeA=SumTime[y];
					SumTime[y]=SumTime[y+1];
					SumTime[y+1]=TimeA;
					
					String TeamA=values[y][0];
					values[y][0]=values[y+1][0];
					values[y+1][0]=TeamA;
					
					isSwapped=true;
				}
			}
			
			if(isSwapped==false)
			{
				break;
			}
		}
		for(int i=0; i<pNumOfRows; i++)
		{	
			if(SumTime[i]!=0)
			{
				FastestTime=SumTime[i];
				FastestTeam=values[i][0];
				break;
			}
		}
		System.out.println("The fastest team is " + FastestTeam + " which used " + FastestTime + " amount of time. ");
		for(int y=0; y<pNumOfRows-1; y++)
		{	
			if(values[y][4].equals("-1")&& values[y][0].equals(values[y+1][0]) && values[y+1][4].equals("-1"))
			{
				System.out.println("The team whose drivers didn't complete the race: " + values[y][0]);
			}
		}
		return FastestLap;
	}
	
	public static double [] SortDriversAscending(String [] [] pValues, double [] pFastestLap)
	{
		boolean isSwapped;
		for(int a=0; a<pValues.length; a++)
		{
			isSwapped=false;
			for(int b=0; b<pValues.length-1-a; b++)
			{
				if(pFastestLap[b]>pFastestLap[b+1])
				{
					double A=pFastestLap[b];
					pFastestLap[b]=pFastestLap[b+1];
					pFastestLap[b+1]=A;
					
					String Driver=pValues[b][2];
					pValues[b][2]=pValues[b+1][2];
					pValues[b+1][2]=Driver;
					isSwapped=true;
				}
			}
			
			if(isSwapped==false)
			{
				break;
			}
		}
		System.out.println("The following drivers are sorted in ascending order: ");
		for(int y=0; y<pValues.length; y++)
		{
			System.out.println(pValues[y][2] + " : " + pFastestLap[y]);
		}
		return pFastestLap;
	}
	
	public static void SortDriversDescending(double [] pFastestLap, String[] [] pValues)
	{
		boolean isSwapped;
		for(int x=0; x<pFastestLap.length; x++)
		{
			isSwapped=false;
			for(int y=0; y<pFastestLap.length-1-x; y++)
			{
				if(pFastestLap[y]<pFastestLap[y+1])
				{
					double A=pFastestLap[y];
					pFastestLap[y]=pFastestLap[y+1];
					pFastestLap[y+1]=A;
					
					String Driver= pValues[y][2];
					pValues[y][2]=pValues[y+1][2];
					pValues[y+1][2]=Driver;
					isSwapped=true;
				}
			}
			
			if(isSwapped== false)
			{
				break;
			}
		}
		System.out.println("The following drivers are sorted in descending order: ");
		for(int y=0; y<pValues.length; y++)
		{
			System.out.println(pValues[y][2] + " : " + pFastestLap[y]);
		}
	}
	
	public static void TeamAnalysis(String [] [] pValues)
	{
		Scanner sc=new Scanner(System.in);
		String [] drivernames=new String[2];
		double [] FastestLap= new double[2];
		String carcode;
		System.out.println("Please enter the car code of the team that you want to analyse: ");
		carcode=sc.nextLine();
		for(int x=0; x<pValues.length-1; x++)
		{
			if(carcode.equals(pValues[x][1]))
			{
				drivernames[0]=pValues[x][2];
				drivernames[1]=pValues[x+1][2];
				FastestLap[0]=Double.parseDouble(pValues[x][5]);
				FastestLap[1]=Double.parseDouble(pValues[x+1][5]);
				break;
			}
		}
		
		if(FastestLap[0]>FastestLap[1])
		{
			double A=FastestLap[0];
			FastestLap[0]=FastestLap[1];
			FastestLap[1]=A;
			
			String B=drivernames[0];
			drivernames[0]=drivernames[1];
			drivernames[1]=B;
		}
		
		System.out.println("The name of the drivers from the team is as below. ");
		for(int x=0; x<2; x++)
		{
			System.out.println(drivernames[x]);
		}
	}
	
	public static String[] [] ReadingValues(int pNumOfRows)
	{
		String line;
		String [] [] values=new String[pNumOfRows][6];
		try
		{
			FileReader F=new FileReader("Data.csv");
			BufferedReader bufRdr=new BufferedReader(F);
			line=bufRdr.readLine();
			for(int x=0; x<pNumOfRows; x++)
			{
				line=bufRdr.readLine();
				for(int y=0; y<6; y++)
				{
					values[x][y]=line.split(",")[y];
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		System.out.println("The following is the content of the csv file. ");
		for(int i=0; i<pNumOfRows; i++)
		{
			for(int j=0; j<6; j++)
			{
				System.out.print(values[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		return values;
	}
	
	public static void RemovingContent()
	{
		try
		{
			FileWriter F=new FileWriter("Data.csv", false);
			F.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}