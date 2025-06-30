import java.util.*;
import java.io.*;
class Program1
{	
	public static void Welcoming()
	{
		System.out.println("Welcome to the FIA F1 Data Entry Program. \n");
	}
	
	public static String [] TeamName(int team)
	{
		Scanner sc=new Scanner(System.in);
		String [] teamname= new String[team];
		String UserInputName;
		do
		{
			System.out.println("Team Name: ");
			UserInputName= sc.nextLine().trim(); //removes any leading whitespaces
		}while(UserInputName.isEmpty());//compare if the string is empty
		for(int i=0; i<team; i++)
		{
			teamname[i]=UserInputName;
		}
		for(int i=0; i<team-1; i++)
		{
			System.out.println("Team Name: " + teamname[i]);
		}
		return teamname;
	}
	
	public static String [] CarCode(int team)
	{
		Scanner sc=new Scanner(System.in);
		String [] carcode= new String [team];
		System.out.println("Since the team name will be the same for the following, the car code will also have to be same.");
		System.out.println("Car code: ");
		String UserInputCode=sc.nextLine().trim();
		for(int i=0; i<team; i++)
		{
			carcode[i]= UserInputCode;
		}
		
		for(int i=0; i<team-1; i++)
		{
			System.out.println("CarCode: " + carcode[i]);
		}
		return carcode;
	}
	
	public static String [] DriverName(int team)
	{
		Scanner sc=new Scanner(System.in);
		String [] drivername = new String [team];
		for(int i=0; i<team; i++)
		{
		do
		{
			System.out.println("Driver Names: ");
			drivername[i]= sc.nextLine().trim(); //don't include the space before a string is entered
		}while(drivername[i].isEmpty());
		}	
		return drivername;
	}
	
	public static String [] GrandPrix(int team)
	{
		Scanner sc=new Scanner(System.in);
		String [] grandPrix= new String[team];
		for(int i=0; i<team; i++)
		{
		do
		{
			System.out.println("Grand Prix: ");
			grandPrix[i]=sc.nextLine().trim();
		}while(grandPrix[i].isEmpty());
		}
		return grandPrix;
	}
	
	public static int [] PositionFinished(int team)
	{
		Scanner sc=new Scanner(System.in);
		int [] Pfinished= new int[team];
		for(int i=0; i<team; i++)
		{
		do
		{
			try
			{
				System.out.println("Position Finished(enter -1 for imcompleted race): ");
				do
				{
				Pfinished[i]=sc.nextInt();
				sc.nextLine();
				}while(Pfinished[i] <-1);
				break; //exit the loop body
			}
			catch(Exception e)
			{
				System.out.println("Please enter an integer");
				sc.nextLine();
			}
		}while(true);
		}
		return Pfinished;
	}
	
	public static double [] FastestLap(int team)
	{
		Scanner sc=new Scanner(System.in);
		double [] fastestlap= new double[team];
		for(int i=0; i<team; i++)
		{
		  do
		  {
			try
			{
				do
				{
					System.out.println("Fastest Lap: ");
					fastestlap[i]=sc.nextDouble();
					sc.nextLine();
					if(fastestlap[i]<0)
					{
						System.out.println("Please enter a non-negative value ");
					}
				}while(fastestlap[i]<0);
				break;
			}
			catch(Exception e)
			{
				sc.nextLine();
				System.out.println("Input Mismatch Error.");
			}
		  }while(true);
		}
		return fastestlap;
	}
		
	public static int CheckValidTeam()
	{
		int team;
		Scanner sc= new Scanner(System.in);
		System.out.println("How many F1 Teams are there? Please enter 2 only. " );
		System.out.println("Disclaimer: the team name entered will be applied to the following details. ");
		System.out.println("After finishing enter the information for this team. You will be prompted to enter the information for another team if you want. ");
		while(true)
		{
			try
			{
				do
				{
				System.out.println("Please enter a valid number of team. ");
				team=sc.nextInt();
				}while(team!=2);
				break;
			}
			
			catch(Exception e)
			{
				sc.nextLine();
				System.out.println(e.getMessage());
			}
		}
		return team;
	}
	
	public static void PrintHeader()
	{
		try
		{
			FileWriter F=new FileWriter("Data.csv");
			PrintWriter P=new PrintWriter(F);
			P.println("TeamName, CarCode, DriverName, GrandPrix, PositionFinished, FastestLap");
			P.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static void Data_Storage(String [] TeamName, String [] CarCode, String [] DriverName, String [] GrandPrix, int [] PositionFinished, double[] FastestLap)
	{
		try
		{
			FileWriter F=new FileWriter("Data.csv", true);	//turn on append mode
			PrintWriter P= new PrintWriter(F);
			for(int i=0; i<TeamName.length; i++)
			{	
			P.println(TeamName[i] + "," + CarCode[i] + "," + DriverName[i] + "," + GrandPrix[i] + "," + PositionFinished[i] + "," + FastestLap[i]);
			}
			P.close();
			System.out.println("Data has been stored successfully.");
		}
		
		catch(Exception e)
		{
			System.out.println("File not created. " + e.getMessage());
		}
	}	
}

public class F1_1
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		int SumTeam=0;
		int teamNumber;
		char Ans;
		String[] TN;
		String[] CC;
		String[] DN;
		String[] GP;
		int[] PF;
		double[] FL;
		String [] [] values=null;
		char UserChoice;
		Program1.Welcoming();//accessing the static method from the "Program1" class
		Program1.PrintHeader();
		do
		{
			teamNumber=Program1.CheckValidTeam();
			SumTeam=teamNumber+SumTeam;
			System.out.println();
			System.out.println("Enter your data according to the following sequence of data: ");
			TN=Program1.TeamName(teamNumber);
			CC=Program1.CarCode(teamNumber);
			DN=Program1.DriverName(teamNumber);
			GP=Program1.GrandPrix(teamNumber);
			PF=Program1.PositionFinished(teamNumber);
			FL=Program1.FastestLap(teamNumber);
			Program1.Data_Storage(TN, CC, DN, GP, PF, FL);
			do
			{
				System.out.println("Would you like to enter more data? (Y/N) : ");
				Ans=sc.next().charAt(0);
			}while(Ans!='N' && Ans!='n' && Ans!='Y' && Ans!='y');
		}while(Ans=='Y' || Ans=='y');
		int NumOfRows=Program2.CalculateLineNum();
		do
		{
			UserChoice=Program2.Analysis();	
			switch (UserChoice)
			{
				case 'D':
				case 'd':
				{
					values=Program2.DataAnalysis(NumOfRows);
					double [] FastestLap=Program2.FindFastestTeam(values, NumOfRows);
					double [] SortedFastestLap=Program2.SortDriversAscending(values, FastestLap);
					Program2.SortDriversDescending(SortedFastestLap,values);
					System.out.println("Would you like to do team analysis? (Y/N)");
					char choice=sc.next().charAt(0);
					do
					{
						if(choice=='Y' || choice=='y')
						{
							values=Program2.ReadingValues(NumOfRows);
							Program2.TeamAnalysis(values);
							char iChoice;
							do
							{
								System.out.println("Would you like to do team analysis for other teams? (Y/N)");
								iChoice=sc.next().charAt(0);
								if(iChoice=='Y' || iChoice=='y')
								{
									values=Program2.ReadingValues(NumOfRows);
									Program2.TeamAnalysis(values);
								}
								
								else if(iChoice=='N' || iChoice=='n')
								{
									System.out.println("Thank you for using the program.");
								}
								
								else
								{
									System.out.println("Invalid choice. Please enter again.");
								}
							}while(iChoice!='n' && iChoice!='N');
						}
						
						else if(choice=='N' || choice=='n')
						{
							System.out.println("Thank you for using the program.");
							break;
						}
						
						else
						{
							System.out.println("Invalid input. Please enter Y or N. ");
						}
					}while(choice!='Y' && choice!='y' && choice!='N' && choice!='n');
					break;
				}
				
				case 'T':
				case 't':
				{
					values=Program2.ReadingValues(NumOfRows);
					Program2.TeamAnalysis(values);
					System.out.println("Would you like to do data analysis? (Y/N)");
					char choice=sc.next().charAt(0);
					do
					{
						if(choice=='Y' || choice=='y')
						{
							values=Program2.DataAnalysis(NumOfRows);
							double [] FastestLap=Program2.FindFastestTeam(values, NumOfRows);
							double [] SortedFastestLap=Program2.SortDriversAscending(values,FastestLap);
							Program2.SortDriversDescending(FastestLap,values);
						}
						
						else if(choice=='N' || choice=='n')
						{
							System.out.println("Thank you for using the program.");
							break;
						}
						
						else
						{
							System.out.println("Invalid input.");
							System.out.println("Please enter again.");
						}
					}while(choice!='Y' && choice!='y' && choice!='N' && choice!='n');
					break;
				}
				
				default: 
				{
					System.out.println("Invalid input. ");
					break;
				}
			}
		}while(UserChoice!='T' && UserChoice!='t' && UserChoice!='D' && UserChoice!='d');
		Program2.RemovingContent();	
	}
}