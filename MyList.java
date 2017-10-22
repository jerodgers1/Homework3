import java.io.*;
import java.util.*;
import java.lang.*;

public class MyList
{
    public static void main(String[] args)
    {
		/*
		CommandLineParser parser = new DefaultPaser();
		try{
			CommsandLine cmd = parser.parse(options, args);
		}
		catch(ParseException exp)
		{
			System.out.println(exp);
		}
				
		if(cmd.hasOption("type") || cmd.hasOption("key") || cmd.hasOption("list"))
		{
			System.out.println("has options!!!");
			String tempOption = cmd.getOptionValue("type");
			System.out.println(tempOption);
			tempOption = cmd.getOptionValue("key");
			System.out.println(tempOption);
			tempOption = cmd.getOptionValue("list");
			System.out.println(tempOption);
		}
		*/
	     //if there is no command line args the program will return -1
	     if(args.length!=0)
	      {
		       String key = "";
		        int j=0;
		        boolean keyFound = false;
		        //sets the lenght of the array aList
        		ArrayList<item> list = new ArrayList<item>(args.length);
        		//creates a buffer to read the key from user input
        		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        		try{
              //reads the key
        			key = reader.readLine();//Double.parseDouble(reader.readLine());
        		}
        		catch(IOException err)
        		{
        			System.out.println(err);
        		}
				
				for (int i =0; i < args.length; i++)
        		{
        		  list.add(new item(args[i], i));
				}
			     		
				MyList sortObject = new MyList();
				sortObject.sortArray(list, 0, list.size()-1);
				/*
				for (int i =0; i < args.length; i++)
        		{
        		  System.out.println("testing " + list.get(i).index + " " + list.get(i).value);
				}
				*/
				//runs binary search on the list and returns true or false if not found it prints not found.
	    		if(!binSearch(list, key))
	    		{
	    			System.out.println("The key was not found!");
	    		}      
      	}
      	else
      	{
      		  System.out.println(-1);
        }
    }

    public static boolean binSearch(ArrayList<item> list, String key)
    {
        int upperBound = list.size()-1;
        int lowerBound = 0;
		int iterator = (upperBound + lowerBound) / 2;
		int compareValue = 0;
        //searches through the list for the key
        while(((list.get(iterator).value.compareTo(key)) != 0)&&(lowerBound<upperBound))
        {
		  compareValue = list.get(iterator).value.compareTo(key);
          if(compareValue > 0)
          {
            upperBound--;
          }
          else
          {
            lowerBound++;
          }
          iterator = (upperBound + lowerBound) / 2;
        }
        //returns true and prints the index of the key or returns false
        if((list.get(iterator).value.compareTo(key)) == 0)
        {
          System.out.println(list.get(iterator).index);
          return true;
        }
        else
        {
          return false;
        }
      }

    public void sortArray(ArrayList<item> list, int leftBound, int rightBound)
    {
        if(leftBound < rightBound)
        {
          int center = (leftBound + rightBound)/2;
          sortArray(list, leftBound, center);
          sortArray(list, center+1, rightBound);
          merge(list, leftBound,center,rightBound);
        }
    }

    public void merge(ArrayList<item> list, int leftBound,int center, int rightBound)
    {
        int leftSize = center - leftBound + 1;
        int rightSize = rightBound - center;
        ArrayList<item> leftList = new ArrayList<item>(leftSize);
		ArrayList<item> rightList = new ArrayList<item>(rightSize);
		
        for(int i=0; i < leftSize; i++)
        {
		  leftList.add(new item(list.get(leftBound+i).value, list.get(leftBound+i).index));
          
        }
        for(int i=0; i < rightSize; i++)
        {
		  rightList.add(new item(list.get(center + i + 1).value, list.get(center + i + 1).index));
          
        }
        //go through and merge the two arrays in order back into main array
        int i =0, j=0, k= leftBound, compareValue=0;
		if(isAString(leftList.get(0).value))
		{
			while(i < leftSize && j < rightSize)
			{
				compareValue = leftList.get(i).value.compareTo(rightList.get(j).value);
				if(compareValue < 0)
				{
					list.set(k++, leftList.get(i++));
				}
				else if(compareValue >0)
				{
					list.set(k++, rightList.get(j++));
				}
				else
				{
					list.set(k++, leftList.get(i++));
				}
			}
		}
		else
		{
			while(i < leftSize && j < rightSize)
			{
				double tempLeft = Double.parseDouble(leftList.get(i).value);
				double tempRight = Double.parseDouble(rightList.get(j).value);
				if(tempLeft <= tempRight)
				{
					list.set(k++, leftList.get(i++));
				}
				else
				{
					list.set(k++, rightList.get(j++));
				}
			}
		}
        //after merger the two what ever is left in one array is place in the main alist array
        while(i < leftSize)
        {
          list.set(k++, leftList.get(i++));
        }
        while(j < rightSize)
        {
          list.set(k++, rightList.get(j++));
        }
    }
	
	public static boolean isAString(String temp)
	{
		try{
			double val = Double.parseDouble(temp);
		}
		catch(NumberFormatException e)
		{
			return true;
		}
		return false;
	}
	
}
class item{
	String value;
	int index;
	
	public item(String value, int index)
	{
		this.value = value;
		this.index =index;
	}
	
	public String getValue()
	{
		return value;
	}
	public int getIndex()
	{
		return index;
	}
	
	public void setValue(String value)
	{
		
		this.value = value;
	}
	
	public void setIndex(int index)
	{
		this.index=index;
	}
}
