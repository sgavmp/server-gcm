package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class RegIdManager {
	private static File REG_ID_STORE;
	public static void writeToFile(String regId) throws IOException {
		REG_ID_STORE = new File(Thread.currentThread().getContextClassLoader().getResource("GCMRegId.txt").getPath());
		Set<String> regIdSet = readFromFile();

		if (!regIdSet.contains(regId)) {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(REG_ID_STORE, true)));
			out.println(regId);
			out.close();
			Files.write(null, regId.getBytes());
		}
	}

	public static Set<String> readFromFile() throws IOException {
		REG_ID_STORE = new File(Thread.currentThread().getContextClassLoader().getResource("GCMRegId.txt").getPath());
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
