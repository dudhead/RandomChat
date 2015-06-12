package com.javapapers.java.gcm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class RegIdManager {
	static final String REG_ID_STORE = "/Users/Priya/Downloads/GCM-CCS-App-Server/src/com/javapapers/java/gcm/GCMRegId.txt";
	public static void writeToFile(String regId) throws IOException {
		Set<String> regIdSet = readFromFile();

		if (!regIdSet.contains(regId)) {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(REG_ID_STORE, true)));
			out.println(regId);
			out.close();
		}
	}

	public static Set<String> readFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(REG_ID_STORE));
		String regId = "";
		Set<String> regIdSet = new HashSet<String>();
		while ((regId = br.readLine()) != null) {
			regIdSet.add(regId);
		}
		br.close();
		return regIdSet;
	}
}
