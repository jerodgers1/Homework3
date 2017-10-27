package com.group1.HW3;

import java.io.*;
import java.util.*;
import java.lang.*;
import org.apache.commons.cli.*;
public class MyList
{
    public static void main(String[] args)
    {
		Options options = new Options();
		Option listValues = OptionBuilder.withArgName( "list")
									.hasArgs(Option.UNLIMITED_VALUES)
									.withDescription("sorted list of integers or strings" )
									.withValueSeparator(' ')
									.create("list");
		CommandLine cmd = null;
		options.addOption( "type", true, "specify types of input: 'i' for integer and 's' for string  " );
		options.addOption( "key", true, "value of the search key" );
		options.addOption(listValues);
		CommandLineParser parser = new DefaultParser();
		try{
			 cmd = parser.parse(options, args);
		}
		catch(ParseException exp)
		{
			System.out.println(exp);
		}
		if(cmd.hasOption("type") || cmd.hasOption("key") || cmd.hasOption("list"))
		{
			String typeOfList = cmd.getOptionValue("type");
			String key = cmd.getOptionValue("key");
			String[] tempList = cmd.getOptionValues("list");
			int j=0;
		    boolean keyFound = false;
		    //sets the lenght of the array aList
        	ArrayList<item> list = new ArrayList<item>(tempList.length);
 
			for (int i =0; i < tempList.length; i++)
        	{
        	  list.add(new item(tempList[i], i));
			}
			
			MyList sortObject = new MyList();
			sortObject.sortArray(list, 0, list.size()-1, typeOfList);
			/*
			for (int i =0; i < tempList.length; i++)
        	{
        	  System.out.println("testing " + list.get(i).index + " " + list.get(i).value);
			}
			*/
			//runs binary search on the list and returns true or false if not found it prints not found.
	    	if(!binSearch(list, key, typeOfList))
	    	{
	    		System.out.println("The key was not found!");
	    	}      
      	}
      	else
      	{
      		  System.out.println(-1);
        }
    }

    public static boolean binSearch(ArrayList<item> list, String key, String type)
    {
        int upperBound = list.size()-1;
        int lowerBound = 0;
		int iterator = (upperBound + lowerBound) / 2;
		int compareValue = 0;
		String s = "s";
		if(type.equals(s))
		{
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
		else
		{
		 while((Double.parseDouble(list.get(iterator).value)!=Double.parseDouble(key))&&(lowerBound<upperBound))
         {
           if(Double.parseDouble(list.get(iterator).value)>Double.parseDouble(key))
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
         if(Double.parseDouble(list.get(iterator).value) == Double.parseDouble(key))
         {
           System.out.println(list.get(iterator).index);
           return true;
         }
         else
		 {
		   return false;
		 }
		}
      }

    public void sortArray(ArrayList<item> list, int leftBound, int rightBound, String type)
    {
        if(leftBound < rightBound)
        {
          int center = (leftBound + rightBound)/2;
          sortArray(list, leftBound, center, type);
          sortArray(list, center+1, rightBound, type);
          merge(list, leftBound,center,rightBound, type);
        }
    }

    public void merge(ArrayList<item> list, int leftBound,int center, int rightBound, String type)
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
		String s = "s";
		if(type.equals(s))
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
