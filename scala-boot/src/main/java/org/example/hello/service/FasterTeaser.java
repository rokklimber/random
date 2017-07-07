package org.example.hello.service;

import org.example.hello.TeaserServiceWrapper;

import scala.collection.Iterator;
import scala.collection.immutable.List;

public class FasterTeaser extends TeaserServiceWrapper {

	@Override
	public String style()
	{
		return "faster";
	}

	public int[] getMaxNumbers(List digits, int outputs)
	{
		// create sorted array objects
		int sortedDigits[] = new int[digits.length()];
		int sortedIdx = 0, temp;
		
		// sort into single sorted array
		for (Iterator i = digits.iterator();i.hasNext();)
		{
			int next = (int) i.next();
			if(sortedIdx > 0)
			{
				int location = getIndex(next, sortedDigits, 0, sortedIdx);
				for(int j = location;j <= sortedIdx;j++)
				{
					temp = sortedDigits[j];
					sortedDigits[j] = next;
					next = temp;
				}
				sortedIdx++;
			}
			else sortedDigits[sortedIdx++] = next;
		}
		
		// produce output values
		int num1 = 0, num2 = 0;
		for(int i = 0;i < ((sortedDigits.length / 2) + (sortedDigits.length % 2));i++)
		{
			num1 += (int) (sortedDigits[2*i] * Math.pow(10, i));
			num2 += (int) ( (((2*i) + 1) < sortedDigits.length) ?  (sortedDigits[(2*i) + 1] * Math.pow(10, i)) : 0);
		}
		return new int[] {num1, num2};
	}
	
	/**
	 * find the proper array index to insert next digit in sorted array, using a
	 * recursive binary search against a slice of the array between supplied indices
	 * 
	 * @param digit value to add to sorted list
	 * @param lookup current sorted list
	 * @param min smallest array index to search (inclusive)
	 * @param max largest array index to search (inclusive)
	 * @return correct index of array to insert digit
	 */
	protected int getIndex(int digit, int lookup[], int min, int max)
	{
		// default case
		if(max - min == 1)
		{
			if(digit > lookup[min]) return max;
			else return min;
		}
		
		// recursive case, choose new partition using pivot value as the center
		else
		{
			int pivot = min + ((max - min) / 2);
			if(digit > lookup[pivot]) return getIndex(digit, lookup, pivot, max);
			else return getIndex(digit, lookup, min , pivot);
		}
	}
}
