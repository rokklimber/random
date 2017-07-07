package org.example.hello.service;

import java.util.Arrays;

import org.example.hello.TeaserServiceWrapper;

import scala.collection.Iterator;
import scala.collection.immutable.List;

public class SimpleTeaser extends TeaserServiceWrapper {

	@Override
	public String style() {
		return "simple";
	}

	public int[] getMaxNumbers(List digits, int outputs) {
		
		// get in int[] form
		int[] digitList = new int[digits.size()];
		int idx = 0;
		for (Iterator i = digits.iterator();i.hasNext();)
		{
			int j = (int) i.next();
			//System.out.println("idx: " + idx + " Object: " + j);
			digitList[idx++] = (j);
		}
		
		// multiply by -1 for reverse order
		for (int i = 0; i < digitList.length; i++)
			digitList[i] = -digitList[i];

		// sort digits
		Arrays.sort(digitList);

		// undo multiply by -1
		for (int i = 0; i < digitList.length; i++)
			digitList[i] = -digitList[i];

		// create output arrays
		int num1[] = new int[digitList.length / 2];
		int num2[] = new int[digitList.length - num1.length];

		// split digits into digit arrays
		idx = 0;
		for (int digit : digitList) {
			if (idx % 2 == 0)
				num2[(idx++ / 2)] = digit;
			else
				num1[(idx++ / 2)] = digit;
		}

		int num1Val = 0, num2Val = 0, digPos = 0;
		for (int num1Digit : num1) {
			int pow = num1.length - (++digPos);
			num1Val += (num1Digit * Math.pow(10, pow));
		}
		digPos = 0;
		for (int num2Digit : num2) {
			int pow = num2.length - (++digPos);
			num2Val += (num2Digit * Math.pow(10, pow));
		}

		return new int[] { num1Val, num2Val };
	}

}
