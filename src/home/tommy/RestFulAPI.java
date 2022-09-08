package home.tommy;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.net.*;

class Result {

	/*
	 * Complete the 'topArticles' function below.
	 *
	 * The function is expected to return a STRING_ARRAY. The function accepts
	 * INTEGER limit as parameter. base url for copy/paste:
	 * https://jsonmock.hackerrank.com/api/articles?page=<pageNumber>
	 */

	private static final String BASE_URL = "https://jsonmock.hackerrank.com/api/articles";
	private static int totalPages = 0;
	private static ArrayList<Article> articles = new ArrayList<Article>();

	public static List<String> topArticles(int limit) {
		try {
			URL url = new URL(BASE_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			// get total pages
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				StringBuffer response = new StringBuffer();
				reader.lines().forEach((str) -> response.append(str));

				String totalPagesStr = response.substring(response.indexOf("total_pages\":") + 13);
				totalPages = Integer.parseInt(totalPagesStr.substring(0, totalPagesStr.indexOf(",")));
			} catch (Exception e2) {
				e2.printStackTrace();
				throw e2;
			}

			// get all articles by different page number
			for (int i = 1; i < totalPages + 1; i++) {
				url = new URL(BASE_URL + "?page=" + i);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.connect();

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuffer response = new StringBuffer();
					reader.lines().forEach((str) -> response.append(str));

					String totalPagesStr = response.substring(response.indexOf("total_pages\":") + 13);
					totalPages = Integer.parseInt(totalPagesStr.substring(0, totalPagesStr.indexOf(",")));

//					System.out.println(totalPages);
//					System.out.println(response);
					createArticles(response.toString());

				} catch (Exception e2) {
					e2.printStackTrace();
					throw e2;
				}
			}

			List<Article> sortedArticle = articles.stream()
					.filter((article) -> article.getTitle() != null || article.getStoryTitle() != null)
					.sorted(Comparator.comparingInt(Article::getNumComments).reversed()).limit(limit)
					.collect(Collectors.toList());

			sortedArticle.forEach(System.out::println);

			List<String> finalResult = sortedArticle.stream().map((article) -> article.getFinalTitle())
					.collect(Collectors.toList());

			finalResult.forEach(System.out::println);

			return finalResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	public static ArrayList<String> createArticles(String line) {
		try {
			List<String> lines = Arrays.asList(line.split("[{]"));
			lines.forEach((str) -> {
				if (str.indexOf("title") >= 0) {
					List<String> fields = Arrays.asList(str.split(","));
					Article article = new Article();
					fields.forEach((field) -> {
						if (field.indexOf("\"title\"") >= 0) {
							String title = field.replaceAll("\"title\":", "").replace("\"", "");
							if (title != null && !title.equalsIgnoreCase("null") && title.trim().length() > 0) {
								article.setTitle(title);
							}
						} else if (field.indexOf("\"story_title\"") >= 0) {
							String storyTitle = field.replaceAll("\"story_title\":", "").replace("\"", "");
							if (storyTitle != null && !storyTitle.equalsIgnoreCase("null")
									&& storyTitle.trim().length() > 0) {
								article.setStoryTitle(storyTitle);
							}

						} else if (field.indexOf("\"num_comments\"") >= 0) {
							String numComments = field.replaceAll("\"num_comments\":", "").replace("\"", "");
							if (numComments != null && !numComments.equalsIgnoreCase("null")
									&& numComments.trim().length() > 0) {
								try {
									article.setNumComments(Integer.parseInt(numComments));
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
							}
						}
						System.out.println(field);
					});
					articles.add(article);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

class Article {
	private String title;
	private String storyTitle;
	private int numComments;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStoryTitle() {
		return storyTitle;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	public String getFinalTitle() {
		return title != null ? title : storyTitle;
	}

	@Override
	public String toString() {
		return "Article:{title=" + title + ",storyTitle=" + storyTitle + ",numComments=" + numComments + "}";
	}
}

public class RestFulAPI {
	public static void main(String[] args) throws IOException {
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//		int limit = Integer.parseInt(bufferedReader.readLine().trim());

		List<String> result = Result.topArticles(4);

//		bufferedWriter.write(result.stream().collect(joining("\n")) + "\n");
//
//		bufferedReader.close();
//		bufferedWriter.close();
	}
}
