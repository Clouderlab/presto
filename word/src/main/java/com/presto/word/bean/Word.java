package com.presto.word.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Word {
	private String word;
	// 音标
	private String americanSoundmark = "";
	private String englishSoundmark = "";
	// 及物动词 vt.
	private String vtMeaning = "";
	// 不及物动词 vi.
	private String viMeaning = "";
	// 既是及物动词也是非及物动词 vt.& vi.
	private String vMeaning = "";
	// 形容词 adj.
	private String adjMeaning = "";
	// 名词 n.
	private String nMeaning = "";
	// 副词 adv.
	private String advMeaning = "";
	// 感叹词 int.
	private String intMeaning = "";
	// 连词 conj.
	private String conjMeaning = "";
	// 缩写 abbr.
	private String abbrMeaning = "";
	// 代词 pron.
	private String pronMeaning = "";
	// 冠词 art. 例如 the
	private String artMeaning = "";
	// 介词 prep. 例如 on
	private String prepMeaning = "";
	// 助词 aux. 例如 am is are
	private String auxMeaning = "";
	// 数字 num.
	private String numMeaning = "";
	// 其他
	private String otherMeaning = "";
	// 属于类型 CET4 CET6
	private List<String> categories = new ArrayList<String>();
	// 词根信息
	private WordRoot root;
	// 其他形式单词，key是关系，value是单词
	private HashMap<String, String> otherTypes = new HashMap<String, String>();
	// 派生词
	private List<String> derivative = new ArrayList<String>();
	// 同义词，key是词性，value是<意思，单词>
	private HashMap<String, HashMap<String, List<String>>> synonyms = new HashMap<String, HashMap<String, List<String>>>();
	// 反义词，key是词性，value是<意思，单词>
	private HashMap<String, HashMap<String, List<String>>> opposites = new HashMap<String, HashMap<String, List<String>>>();

	// 词根解释
	private String rootExplain = "";

	// 例句另外再抓
	public String getWord() {
		return word;
	}

	public String toString() {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		// builder.setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(this);
	}

	public void setMeaning(String type, String value) throws Exception {
		String t = type.toLowerCase();
		if (t.equals("vt.")) {
			this.setVtMeaning(value);
		} else if (t.equals("vi.")) {
			this.setViMeaning(value);
		} else if (t.equals("v.") || (t.contains("vt.") && t.contains("vi."))) {
			this.setvMeaning(value);
		} else if (t.equals("adj.")) {
			this.setAdjMeaning(value);
		} else if (t.equals("n.")) {
			this.setnMeaning(value);
		} else if (t.equals("adv.")) {
			this.setAdvMeaning(value);
		} else if (t.equals("int.")) {
			this.setIntMeaning(value);
		} else if (t.equals("conj.")) {
			this.setConjMeaning(value);
		} else if (t.equals("abbr.")) {
			this.setAbbrMeaning(value);
		} else if (t.equals("pron.")) {
			this.setPronMeaning(value);
		} else if (t.equals("art.")) {
			this.setArtMeaning(value);
		} else if (t.equals("prep.")) {
			this.setPrepMeaning(value);
		} else if (t.equals("aux.")) {
			this.setAuxMeaning(value);
		} else if (t.equals("num.")) {
			this.setNumMeaning(value);
		} else {
			this.setOtherMeaning(this.getOtherMeaning() + type + " " + value);
			System.out.println("Unrecognized type:" + type);
			// throw new Exception("Unrecognized type:" + type);
		}
	}

	public String getOtherMeaning() {
		return otherMeaning;
	}

	public void setOtherMeaning(String otherMeaning) {
		this.otherMeaning = otherMeaning;
	}

	public String getvMeaning() {
		return vMeaning;
	}

	public void setvMeaning(String vMeaning) {
		this.vMeaning = vMeaning;
	}

	public String getnMeaning() {
		return nMeaning;
	}

	public void setnMeaning(String nMeaning) {
		this.nMeaning = nMeaning;
	}

	public String getNumMeaning() {
		return numMeaning;
	}

	public void setNumMeaning(String numMeaning) {
		this.numMeaning = numMeaning;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getAmericanSoundmark() {
		return americanSoundmark;
	}

	public void setAmericanSoundmark(String americanSoundmark) {
		this.americanSoundmark = americanSoundmark;
	}

	public String getEnglishSoundmark() {
		return englishSoundmark;
	}

	public void setEnglishSoundmark(String englishSoundmark) {
		this.englishSoundmark = englishSoundmark;
	}

	public String getVtMeaning() {
		return vtMeaning;
	}

	public void setVtMeaning(String vtMeaning) {
		this.vtMeaning = vtMeaning;
	}

	public String getViMeaning() {
		return viMeaning;
	}

	public void setViMeaning(String viMeaning) {
		this.viMeaning = viMeaning;
	}

	public String getAdjMeaning() {
		return adjMeaning;
	}

	public void setAdjMeaning(String adjMeaning) {
		this.adjMeaning = adjMeaning;
	}

	public String getAdvMeaning() {
		return advMeaning;
	}

	public void setAdvMeaning(String advMeaning) {
		this.advMeaning = advMeaning;
	}

	public String getIntMeaning() {
		return intMeaning;
	}

	public void setIntMeaning(String intMeaning) {
		this.intMeaning = intMeaning;
	}

	public String getConjMeaning() {
		return conjMeaning;
	}

	public void setConjMeaning(String conjMeaning) {
		this.conjMeaning = conjMeaning;
	}

	public String getAbbrMeaning() {
		return abbrMeaning;
	}

	public void setAbbrMeaning(String abbrMeaning) {
		this.abbrMeaning = abbrMeaning;
	}

	public String getPronMeaning() {
		return pronMeaning;
	}

	public void setPronMeaning(String pronMeaning) {
		this.pronMeaning = pronMeaning;
	}

	public String getArtMeaning() {
		return artMeaning;
	}

	public void setArtMeaning(String artMeaning) {
		this.artMeaning = artMeaning;
	}

	public String getPrepMeaning() {
		return prepMeaning;
	}

	public void setPrepMeaning(String prepMeaning) {
		this.prepMeaning = prepMeaning;
	}

	public String getAuxMeaning() {
		return auxMeaning;
	}

	public void setAuxMeaning(String auxMeaning) {
		this.auxMeaning = auxMeaning;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public WordRoot getRoot() {
		return root;
	}

	public void setRoot(WordRoot root) {
		this.root = root;
	}

	public HashMap<String, String> getOtherTypes() {
		return otherTypes;
	}

	public void setOtherTypes(HashMap<String, String> otherTypes) {
		this.otherTypes = otherTypes;
	}

	public List<String> getDerivative() {
		return derivative;
	}

	public void setDerivative(List<String> derivative) {
		this.derivative = derivative;
	}

	public void addSynonym(String type, String mean, String word) {
		this.addRelativeMean(this.synonyms, type, mean, word);
	}

	public void addOpposite(String type, String mean, String word) {
		this.addRelativeMean(this.opposites, type, mean, word);
	}

	private void addRelativeMean(
			HashMap<String, HashMap<String, List<String>>> map, String type,
			String mean, String word) {
		if (!map.containsKey(type)) {
			map.put(type, new HashMap<String, List<String>>());
		}
		HashMap<String, List<String>> typeValue = map.get(type);
		if (!typeValue.containsKey(mean)) {
			typeValue.put(mean, new ArrayList<String>());
		}
		List<String> list = typeValue.get(mean);
		list.add(word);
	}

	public HashMap<String, HashMap<String, List<String>>> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(
			HashMap<String, HashMap<String, List<String>>> synonyms) {
		this.synonyms = synonyms;
	}

	public HashMap<String, HashMap<String, List<String>>> getOpposites() {
		return opposites;
	}

	public void setOpposites(
			HashMap<String, HashMap<String, List<String>>> opposites) {
		this.opposites = opposites;
	}

	public String getRootExplain() {
		return rootExplain;
	}

	public void setRootExplain(String rootExplain) {
		this.rootExplain = rootExplain;
	}

}
