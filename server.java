package java_experience_server;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class Word {
	private String chinese;
	private String english;
	
	public Word(String eng , String chi) {
		this.chinese = chi;
		this.english = eng;
	}
	public String getChinese() {
        return chinese;
    }

    public String getEnglish(){
        return english;
    }
}

public class server{
	//保存单词
	public static List<Word> words = new ArrayList();
	//保存答案
	public static List<Word> answers = new ArrayList();
	
	public static List<String> similar = new ArrayList();
	
	public static List<String> finalsimilar = new ArrayList();
	
	public static String[][] similarity = new String[2492][2];
	
	public static String[] back = new String[4];
	
	public static void main(String[] args) throws IOException{
		
		String address = "C:\\Users\\Lucky\\Desktop\\java\\java_experinece\\单词表.txt";
		wordsread(address);
		int port = 2005;
		//System.out.println(words.size());
		
			// 创建ServerSocket对象，指定监听的端口号
			ServerSocket serversocket = new ServerSocket(port);
			System.out.println("Connecting...");
			while (true) {
				// 监听客户端的连接请求
				Socket clientSocket = serversocket.accept();
				System.out.println("Connected");

            // 获取输入流和输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            

            String message;
            message = in.readLine();
            //System.out.println(message);
            	// 读取客户端发送的信息
            if (message.equals("0")) {
            	System.out.println("收到客户端消息：" + message);
            	String question = GetRandomWord();
            	out.println(question);
            }
            if (message.equals("1")) {
            	System.out.println("收到客户端消息：" + message);
            	Word question = answers.get(answers.size()-1);
            	out.println(question.getEnglish());
            }
            if (message.equals("2")) {
            	System.out.println("收到客户端消息：" + message);
            	out.println(back[0] + ";" + back[1] + ";" + back[2] + ";" + back[3]);
            }
            if (message.equals("3")) {
            	System.out.println("收到客户端消息：" + message);
            	Word content = answers.get(answers.size()-1);
            	write_1("C:\\Users\\Lucky\\Desktop\\java\\java_experinece\\未掌握单词.txt",content.getEnglish() + " " + content.getChinese() + "\n", true);
            }
            if (message.equals("4")) {
            	System.out.println("收到客户端消息：" + message);
            	Word content = answers.get(answers.size()-1);
            	write_1("C:\\Users\\Lucky\\Desktop\\java\\java_experinece\\已掌握单词.txt",content.getEnglish() + " " + content.getChinese() + "\n", true);
            }
			}
            
            //clientSocket.close();
            //serversocket.close();
}
	public static void wordsread(String address) throws IOException {
		File file = new File(address);
		FileReader reader = new FileReader(file);
		BufferedReader bReader = new BufferedReader(reader);
		String word_s;
		while ((word_s = bReader.readLine())!=null) {
			String[] Words = word_s.split(" ");
			if (Words.length >= 2) {
				Word word = new Word(Words[0],Words[1]);
				words.add(word);
			}
		}
	}
	
	public static String GetRandomWord() {
		Random random = new Random();
		Word Randomword = words.get(random.nextInt(words.size()));
		Word word = new Word(Randomword.getEnglish(),Randomword.getChinese());
		System.out.println(word.getEnglish());
		answers.add(word);
		findsword();
		return Randomword.getChinese();
	}
	
	public static double calculate(String w1 , String w2) {
		double same = 0;
		String w1_1;
		if (w1.length() < w2.length()) {
			w1_1 = w1;
			w1 = w2;
			w2 = w1_1;
		}
		double length_1 = w1.length();
		double length_2 = w2.length();
		
		char[] ever = new char[(int) Math.max(length_1, length_2)];
		int help = 0;
		int exist = 0;
		//System.out.println(w1);
		//System.out.println(w2);
		for (int i = 0 ; i < length_1 ; i++) {
			for (int j = 0 ; j < length_2 ; j++) {
				char c = w1.charAt(i);
				char h = w2.charAt(j);
				if (c == h) {
					for (int x = 0;x < help;x++) {
						if (ever[x] == c) {
							exist = 1;
							break;
						}
					}
					if (exist == 0) {
						same++;
						ever[help] = c;
						help++;
						break;
					}
					else {
						exist = 1;
					}
					
				}
			}
		}
		double length = Math.max(w1.length(),w2.length());
		//System.out.println(same/length);
		return same/length;
	}
	
	public static void findsword() {
		int i = 0;
		int quantity = 0;
		int[] max_1 = new int[2492];
		double max = 0;
		while (i < 2492) {
			Word word = words.get(i);
			Word answer = answers.get(answers.size()-1);
			if ((calculate(answer.getEnglish() , word.getEnglish())) >= 0.3 && (calculate(answer.getEnglish() , word.getEnglish())) < 1 && answer.getEnglish().charAt(0) == word.getEnglish().charAt(0)) {
				//similar.add(quantity , word.getEnglish());
				similarity[quantity][1] = word.getEnglish();
				similarity[quantity][0] =Double.toString(calculate(answer.getEnglish() , word.getEnglish())) ;
				quantity++;
				//System.out.println(word.getEnglish());
				i++;
			}
			else {
				i++;
			}
		}
		//System.out.println(similarity.length-1);
		for(int x = 0;x<similarity.length-1;x++){
            for(int j=0;j<similarity.length-1-i;j++){

                if(Double.parseDouble(similarity[j][1])>Double.parseDouble(similarity[j+1][1])){
                    String temp=similarity[j][1];
                    similarity[j][1]=similarity[j+1][1];
                    similarity[j+1][1]=temp;
                }
            }
        }
		back[0] =  similarity[quantity-1][1];
		back[1] =  similarity[quantity-2][1];
		back[2] =  similarity[quantity-3][1];
		Word que = answers.get(answers.size()-1);
		back[3] =  que.getEnglish();
	}
	public static void write_1(String address, String content, boolean append){
		FileWriter fileWriter;
		 try{
	            fileWriter = new FileWriter(address, append);
	            fileWriter.write(content);
	            fileWriter.flush();
	            fileWriter.close();
	        }
	        catch (IOException e){
	            e.printStackTrace();
	        }

	}
}