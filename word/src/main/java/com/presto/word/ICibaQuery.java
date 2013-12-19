package com.presto.word;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.presto.word.bean.Word;
import com.presto.word.bean.WordRoot;
import com.presto.word.tools.DB;
import com.presto.word.tools.Util;

public class ICibaQuery {
	Gson gson;

	public static void main(String[] args) throws Exception {
		ICibaQuery q = new ICibaQuery();
		Word query = q.Query("TV");
		System.out.println(q.gson.toJson(query));
		DB db = new DB();
		// db.insertWord(query);
	}

	public ICibaQuery() {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.setPrettyPrinting();
		gson = builder.create();
	}

	public Word Query(String word, int cnt) throws Exception {
		Document parse = null;
		try {
			// 两秒超时如果超时重新执行
			parse = Jsoup.parse(new URL("http://www.iciba.com/" + word), 5000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (cnt > 5) {
				System.out.println(word + " failed too many times, jump it!");
				return null;
			}
			// 等待2秒
			Thread.sleep(2000);
			e.printStackTrace();
			System.out.println(word + " reexecution try number:" + cnt++);
			return this.Query(word, cnt);

		}
		// String url = "http://www.iciba.com/" + word;
		// InputStream input = new URL(url).openStream();
		// Document parse = Jsoup.parse(input, "UTF-8", url);

		Word rt = new Word();
		this.getMeanings(rt, parse);
		this.getOtherTypeAndDeritives(rt, parse);
		this.getWordRoot(rt, parse);
		this.getSameAndOppositeMean(rt, parse);
		// System.out.println(gson.toJson(rt));
		return rt;
	}

	public Word Query(String word) throws Exception {
		return this.Query(word, 1);
	}

	private void getSameAndOppositeMean(Word word, Document parse) {
		Elements select = parse
				.select("div[class=dict_content tongyi] > div > div");
		for (Element el : select) {

			Elements types = el.select("div[class*=pos_box]");
			Elements means = el.select("ul");
			for (int i = 0; i < types.size(); i++) {
				String type = types.get(i).text();
				if (type.equals("")) {
					type = "other";
				}
				Element meanBox = means.get(i);
				Elements dls = meanBox.select("dl");
				for (Element dl : dls) {
					String meanStr = dl.select("dt > span").text();
					if (meanStr.equals(""))
						meanStr = "other";
					Elements words = dl.select("dd > a");
					for (Element wordEl : words) {
						String wd = wordEl.text();
						if (el.select("h4").text().contains("反义词")) {
							word.addOpposite(type, meanStr, wd);
						} else {
							word.addSynonym(type, meanStr, wd);
						}
					}
				}
			}
		}

	}

	private void getWordRoot(Word word, Document parse) {
		Elements root = parse
				.select("div[class=dict_content vCigen] > div > div > h4 > span");
		if (root.size() > 0) {
			WordRoot rt = new WordRoot();
			rt.setRoot(root.text());
			Elements mean = parse
					.select("div[class=dict_content vCigen] > div > div > div[class=vCigen_h4]");
			rt.setMeaning(mean.get(0).text());
			word.setRoot(rt);
		}
		// 找到同根词，并找到和该单词一致的那个作为解释
		Elements lis = parse
				.select("div[class=dict_content vCigen] > div > div > ul[class=dl_show] > li");
		for (Element li : lis) {
			Element element = li.select("a").get(0);
			if (element.text().toLowerCase().equals(word.getWord())) {
				Element mean = li.select("span").get(0);
				word.setRootExplain((mean.html().replaceAll("\\(", "")
						.replaceAll("\\)", "")));
				break;
			}
		}
	}

	/**
	 * 解析其他单词形式以及派生词
	 * 
	 * @param rt
	 * @param parse
	 */
	private void getOtherTypeAndDeritives(Word word, Document parse) {
		Elements derr = parse
				.select("div[class=dictbar] > div[class=group_prons]").get(1)
				.select("div[class=group_inf] > ul");
		for (int i = 0; i < derr.size(); i++) {
			Element ul = derr.get(i);
			if (ul.text().contains("派生词")) {
				// 查找派生词
				Elements select = ul.select("li > a");
				for (Element li : select) {
					word.getDerivative().add(li.text());
				}
			} else if (!ul.text().contains("大家都在背")) {
				Elements lis = ul.select("li");
				for (Element li : lis) {
					String result = li.text().replaceAll("\\s", "")
							.replaceAll("\"", "");
					// System.out.println(result);
					String[] split = result.split("：");
					word.getOtherTypes().put(split[0], split[1]);
				}
			}
		}
	}

	private void getMeanings(Word word, Document parse) throws Exception {
		// 找到音标
		String lowerCase = parse.select("h1[id=word_name_h1]").text();
		if (lowerCase == null || lowerCase.equals("")) {
			throw new Exception("word not found!");
		}
		word.setWord(lowerCase);
		Elements select = parse.select("div[class=dict_title]");
		Elements pronounce = select.get(0).select("span[class=fl]");
		for (int i = 0; i < pronounce.size(); i++) {
			Element element = pronounce.get(i);
			if (element.text().contains("英")) {
				word.setEnglishSoundmark((element.select("strong[lang=EN-US]")
						.get(0).text()));
			} else if (element.text().contains("美")) {
				word.setAmericanSoundmark((element.select("strong[lang=EN-US]")
						.get(0).text()));
			}
		}
		// 找到种类
		Elements gen = parse.select("div[class=wd_genre]");
		if (gen.size() > 0) {
			Elements elementsByTag = gen.get(0).getElementsByTag("a");
			for (int i = 0; i < elementsByTag.size(); i++) {
				word.getCategories().add(
						elementsByTag.get(i).text().replaceAll("\\s", ""));
			}
		}
		// 找到meaning
		Elements means = parse
				.select("div[class=dictbar] > div > div[class=group_pos] > p");
		for (int i = 0; i < means.size(); i++) {
			Element element = means.get(i);
			String type = element.select("strong[class=fl]").text();
			String mean = element.select("span[class=label_list]").text()
					.replaceAll("\\s", "");
			word.setMeaning(type, mean);
		}
	}
}
