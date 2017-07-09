package com.ziyang.filecompare;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
public class FileContentCompare {
	public static void main(String[] args) throws Exception {
		try {
			String filePath1 = "C:/Documents and Settings/20131106/";
//			String filePath2 = "c:/work/abc/data/";
			String filePath2 = filePath1;
			String fileName1 = filePath1 + "abc.green.dat";
//			String fileName2 = filePath2 + "abc.txt";
			String fileName2 = filePath2 + "abc.dat";
			
			SortedSet<String> fSet1 = getStringSetOfFile(fileName1);
			SortedSet<String> fSet2 = getStringSetOfFile(fileName2);
			
			boolean exceptFirst = false;
			if(exceptFirst) {
				fSet1.remove(fSet1.first());
				fSet1.remove(fSet1.first());
				
				fSet2.remove(fSet2.first());
				fSet2.remove(fSet2.first());
			}

			printSet(fileName1, fSet1);
			printSet(fileName2, fSet2);

			if ((fSet1.isEmpty() && !fSet2.isEmpty()) || (!fSet1.isEmpty() && fSet2.isEmpty())) {
				System.out.println("Different!");
			} else if (fSet1.equals(fSet2)) {
				System.out.println("Same!");
			} else {
				System.out.println("Different!");
			}

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static void printSet(String fileName, Set<String> fileSet) {
		System.out.println("################## " + fileName + " ####################");
		for (String line : fileSet) {
			System.out.println(line);
		}
		System.out.println("#################################################");
	}

	private static SortedSet<String> getStringSetOfFile(String fileName) throws Exception {
		SortedSet<String> result = new TreeSet<String>();
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			result.add(strLine);
		}
		// Close the input stream
		in.close();

		return result;
	}
}
