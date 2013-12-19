package com.presto.word;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.presto.word.bean.Word;
import com.presto.word.tools.DB;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		FileWriter data = new FileWriter("F:/prestoWord/dict/data.js");
		FileWriter log = new FileWriter("F:/prestoWord/dict/log.txt");

		DB db = new DB();
		Statement stm = db.conn.createStatement();
		boolean execute = stm.execute("select wid,word from dict");
		ResultSet rs = stm.getResultSet();
		ICibaQuery qu = new ICibaQuery();
		while (rs.next()) {
			long id = rs.getLong(1);
			String word = rs.getString(2);
			System.out.println("getting id:" + id + " word:" + word + "..");
			Word query = qu.Query(word);
			
			// 每3秒钟搞一次
			Thread.sleep(3000);
			log.append("id:" + id + " word:" + word + " complete" + "\n");
			data.append(query.toString()).append('\n');
			data.flush();
			log.flush();
		}
		data.close();
		log.close();
	}
}
