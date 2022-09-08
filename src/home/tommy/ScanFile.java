package home.tommy;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ScanFile {

	private static final Scanner scan = new Scanner(System.in);

	public static void main(String args[]) throws Exception {
		// read the string filename
//		String filename;
//		filename = scan.nextLine();

		String filename = "hosts_access_log_00.txt";
		Set<String> checkResult = new HashSet<String>();
		Set<String> result = new HashSet<String>();
		try (Scanner fileScan = new Scanner(new File(filename))) {
			while (fileScan.hasNextLine()) {
				String content = fileScan.nextLine();

				Pattern pattern = Pattern.compile("GET.+GIF.+200", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					String matchStr = matcher.group();
					int lastIndex = matchStr.toLowerCase().lastIndexOf("gif");
					matchStr = matchStr.substring(0, lastIndex + 3);
					matchStr = matchStr.substring(matchStr.lastIndexOf("/") + 1);
					// eliminate duplicate result (case insensitive)
					if (!checkResult.contains(matchStr.toLowerCase())) {
						checkResult.add(matchStr.toLowerCase());
						result.add(matchStr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("gif_" + filename))) {
			result.forEach((str) -> {
				try {
					System.out.println(str);
					writer.write(str);
					writer.newLine();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
