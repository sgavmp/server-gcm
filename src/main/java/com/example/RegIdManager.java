package com.example;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RegIdManager {
	private static File REG_ID_STORE;
	private static Set<String> REG_ID_SET = null;
	
	private static void init() {
		REG_ID_SET = new HashSet<String>();
	}
	
	public static void writeToFile(String regId) throws IOException {
//		REG_ID_STORE = new File(Thread.currentThread().getContextClassLoader().getResource("GCMRegId.txt").getPath());
//		Set<String> regIdSet = readFromFile();
//
//		if (!regIdSet.contains(regId)) {
//			PrintWriter out = new PrintWriter(new BufferedWriter(
//					new FileWriter(REG_ID_STORE, true)));
//			out.println(regId);
//			out.close();
//			Files.write(null, regId.getBytes());
//		}
		
		if (REG_ID_SET==null)
			init();
		
		if (!REG_ID_SET.contains(regId))
			REG_ID_SET.add(regId);
	}

	public static Set<String> readFromFile() throws IOException {
//		REG_ID_STORE = new File(Thread.currentThread().getContextClassLoader().getResource("GCMRegId.txt").getPath());
//		BufferedReader br = new BufferedReader(new FileReader(REG_ID_STORE));
//		String regId = "";
//		Set<String> regIdSet = new HashSet<String>();
//		while ((regId = br.readLine()) != null) {
//			regIdSet.add(regId);
//		}
//		br.close();
//		return regIdSet;
		
		if (REG_ID_SET==null)
			init();
		
		return REG_ID_SET;
	}
}
