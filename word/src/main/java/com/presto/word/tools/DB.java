package com.presto.word.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;

import com.presto.word.bean.Word;

public class DB {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		DB db = new DB();
		Statement st = db.conn.createStatement();
		st.execute("set names 'utf8'");
		st.executeUpdate("UPDATE presto_word set nummeaning='哈哈!'");
	}

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/presto?useUnicode=true&amp;characterEncoding=UTF-8";
	String user = "root";
	String password = "root";
	Connection conn;

	public DB() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);
		Statement createStatement = conn.createStatement();
		createStatement.execute("set names 'utf8'");
	}

	public void finalize() {
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}

	private long insertOrGetRootID(Word word) throws SQLException {
		String sql = "SELECT rid FROM presto_wordroot WHERE root='"
				+ word.getRoot().getRoot() + "'";
		Statement stm = conn.createStatement();
		boolean execute = stm.execute(sql);
		ResultSet resultSet = stm.getResultSet();
		if (resultSet.next()) {
			return resultSet.getLong(1);
		}
		String root = "INSERT INTO presto_wordroot(root,meaning) VALUES('"
				+ word.getRoot().getRoot() + "','"
				+ word.getRoot().getMeaning() + "')";

		stm.executeUpdate(root, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stm.getGeneratedKeys();
		rs.next();
		return rs.getLong(1);
	}

	private void deleteExists(Word word) throws SQLException {
		String sql = "DELETE FROM presto_word WHERE word='" + word.getWord()
				+ "'";
		Statement stm = conn.createStatement();
		stm.execute(sql);
	}

	public void insertWord(Word word) throws SQLException {
		this.deleteExists(word);
		Statement createStatement = conn.createStatement();
		if (word.getRoot() != null) {
			long rid = insertOrGetRootID(word);
			// boolean execute = createStatement.execute(root);
			String sql = "INSERT INTO presto_word (word,americansound,englishsound,vtmeaning,vimeaning,vmeaning,adjmeaning,nmeaning,advmeaning,intmeaning,conjmeaning,abbrmeaning,pronmeaning, artmeaning,prepmeaning,auxmeaning,nummeaning,categories,rid,rootexplain) VALUES('"
					+ word.getWord()
					+ "',\""
					+ word.getAmericanSoundmark()
					+ "\",\""
					+ word.getEnglishSoundmark()
					+ "\",'"
					+ word.getVtMeaning()
					+ "','"
					+ word.getViMeaning()
					+ "','"
					+ word.getvMeaning()
					+ "','"
					+ word.getAdjMeaning()
					+ "','"
					+ word.getnMeaning()
					+ "','"
					+ word.getAdvMeaning()
					+ "','"
					+ word.getIntMeaning()
					+ "','"
					+ word.getConjMeaning()
					+ "','"
					+ word.getAbbrMeaning()
					+ "','"
					+ word.getPronMeaning()
					+ "','"
					+ word.getArtMeaning()
					+ "','"
					+ word.getPrepMeaning()
					+ "','"
					+ word.getAuxMeaning()
					+ "','"
					+ word.getNumMeaning()
					+ "','"
					+ word.getCategories()
					+ "',"
					+ rid
					+ ",\""
					+ word.getRootExplain() + "\")";
			createStatement.execute(sql);
		} else {
			String sql = "INSERT INTO presto_word (word,americansound,englishsound,vtmeaning,vimeaning,vmeaning,adjmeaning,nmeaning,advmeaning,intmeaning,conjmeaning,abbrmeaning,pronmeaning, artmeaning,prepmeaning,auxmeaning,nummeaning,categories) VALUES('"
					+ word.getWord()
					+ "',\""
					+ word.getAmericanSoundmark()
					+ "\",\""
					+ word.getEnglishSoundmark()
					+ "\",'"
					+ word.getVtMeaning()
					+ "','"
					+ word.getViMeaning()
					+ "','"
					+ word.getvMeaning()
					+ "','"
					+ word.getAdjMeaning()
					+ "','"
					+ word.getnMeaning()
					+ "','"
					+ word.getAdvMeaning()
					+ "','"
					+ word.getIntMeaning()
					+ "','"
					+ word.getConjMeaning()
					+ "','"
					+ word.getAbbrMeaning()
					+ "','"
					+ word.getPronMeaning()
					+ "','"
					+ word.getArtMeaning()
					+ "','"
					+ word.getPrepMeaning()
					+ "','"
					+ word.getAuxMeaning()
					+ "','"
					+ word.getNumMeaning()
					+ "','" + word.getCategories() + "')";
			createStatement.execute(sql);
		}
		// this.insertDerivative(word);
		// this.insertSynAndOpposite(word);

	}

	private void insertSynAndOpposite(Word word) {
		// TODO Auto-generated method stub

	}

	// private void insertDerivative(Word word) {
	// if (word.getOtherTypes().size() > 0) {
	// for (Entry<String, String> ent : word.getOtherTypes().entrySet()) {
	// String
	// sql="INSERT INTO presto_wordderative(word,wid,dword,dwid,relation)"
	// }
	// }
	// if (word.getDerivative().size() > 0) {
	//
	// }
	// }
}
