package java_experinece_client;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.net.*;
import java.util.Random;
import java.io.*;


public class client {
	public static class GUI extends JFrame{
		
		private Timer timer;
		Random rand = new Random();
		public String question;
		public String[] question_else = {"1","2","3","4"};
		public String[] question_else_1 = {"1","2","3","4"};
		
		private int x = 0 , y = rand.nextInt(191) , flag_1;
		
		public void Ready() {
			int res = JOptionPane.showConfirmDialog(null, "Are you ready?","START",JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) 
			{}
			else {
					System.exit(0);
				}
		}
		
		public GUI(int score ,String flag ,int win) {
			if (flag.equals("0")) {
				Ready();
			}

			this.setSize(1000, 500);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel jp0 = new JPanel();
			jp0.setLayout(null);
			//jp0.setBackground(Color.white);
			this.add(jp0);
			
			//显示分数
			JPanel jp1 = new JPanel();
			jp1.setLayout(null);
			jp1.setSize(1000, 20);
			jp1.setLocation(0, 0);
			jp0.add(jp1);
			
			JLabel jp1_1 = new JLabel("your score:" + Integer.toString(score));
			jp1_1.setSize(1000, 20);
			jp1_1.setLocation(0, 0);
			jp1_1.setForeground(Color.black);
			jp1_1.setOpaque(true);
			jp1_1.setBackground(Color.white);
			jp1_1.setHorizontalAlignment(JLabel.CENTER);
			jp1.add(jp1_1);
			
			//游戏背景
			JPanel jp4 = new JPanel();
			jp4.setLayout(null);
			jp4.setSize(1000, 240);
			jp4.setLocation(0, 20);
			jp4.setBackground(Color.black);
			jp4.setVisible(true);
			jp0.add(jp4);
			
			//题目位置
			JPanel que = new JPanel();
			que.setSize(300,50);
			que.setLocation(x, y + 20);
			que.setBackground(Color.black);
			
			question = GetQuestion();
			JLabel que_1 = new JLabel(question);
			que_1.setSize(300, 50);
			que_1.setLocation(0, 0);
			que_1.setForeground(Color.white);
			que_1.setBackground(Color.black);
			que_1.setOpaque(true);
			que.add(que_1);
			
			//定时器以达到移动效果
			timer = new Timer(100,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (x < 890) {
						x = x + 5;
						if (y >= 190)
							flag_1 = 1;
						if (y <= 0)
							flag_1 =0;
						if (flag_1 == 0) {
							y = y + 10;
						}
						else {
							y = y - 10;
						}
						que.setLocation(x, y);
						jp4.add(que);
					}
					else {
						String answer = GetAnswer();
						int newscore = score - 1;
						timer.stop();
						JOptionPane.showMessageDialog(null, "您没有回答，答案是：" + answer);
		                dispose();
		                GUI gui = new GUI(newscore , "1" , 1);
						}
					}
				}
			);
			timer.start();
			
			//显示选项
			//System.out.println("!");
			JPanel jp2 = new JPanel();
			jp2.setLayout(null);
			jp2.setSize(1000, 240);
			jp2.setLocation(0, 300);
			jp2.requestFocus();
			jp0.add(jp2);
			
			question_else_1 = GetSameAnswer();
			question_else = disorganize(question_else , question_else_1);
			JLabel jp2_1 = new JLabel(question_else[3]);
			JLabel jp2_2 = new JLabel(question_else[0]);
			JLabel jp2_3 = new JLabel(question_else[1]);
			JLabel jp2_4 = new JLabel(question_else[2]);
			
			jp2_1.setSize(100, 40);
			jp2_2.setSize(100, 40);
			jp2_3.setSize(100, 40);
			jp2_4.setSize(100, 40);
			
			jp2_1.setLocation(110, 10);
			jp2_2.setLocation(330, 10);
			jp2_3.setLocation(550, 10);
			jp2_4.setLocation(770, 10);
			
			jp2_1.requestFocus();
			jp2_2.requestFocus();
			jp2_3.requestFocus();
			jp2_4.requestFocus();
			
			jp2_1.setForeground(Color.white);
			jp2_2.setForeground(Color.white);
			jp2_3.setForeground(Color.white);
			jp2_4.setForeground(Color.white);
			
			jp2_1.setOpaque(true);
			jp2_2.setOpaque(true);
			jp2_3.setOpaque(true);
			jp2_4.setOpaque(true);
			
			jp2_1.setBackground(Color.pink);
			jp2_2.setBackground(Color.pink);
			jp2_3.setBackground(Color.pink);
			jp2_4.setBackground(Color.pink);
			
			jp2_1.setHorizontalAlignment(JLabel.CENTER);
			jp2_2.setHorizontalAlignment(JLabel.CENTER);
			jp2_3.setHorizontalAlignment(JLabel.CENTER);
			jp2_4.setHorizontalAlignment(JLabel.CENTER);
			
			jp2.add(jp2_1);
			jp2.add(jp2_2);
			jp2.add(jp2_3);
			jp2.add(jp2_4);
			
			//选项背景
			JPanel jp5 = new JPanel();
			jp5.setLayout(null);
			jp5.setSize(1000, 240);
			jp5.setLocation(0, 0);
			jp5.setBackground(Color.white);
			jp5.setVisible(true);
			jp5.requestFocus();
			jp2.add(jp5);			
			
			//输入答案
			JPanel jp3 = new JPanel();
			jp3.setLayout(null);
			jp3.setSize(1000, 100);
			jp3.setLocation(20, 250);
			jp3.setVisible(true);
			jp0.add(jp3);
			
			JLabel jp3_2 = new JLabel("输入答案：");
			jp3_2.setSize(400, 40);
			jp3_2.setLocation(0, 0);
			jp3_2.requestFocus();
			jp3.add(jp3_2);
			
			JTextField jp3_1 = new JTextField();
			jp3_1.setSize(400, 20);
			jp3_1.setLocation(0, 30);
			jp3.add(jp3_1);
			jp3_1.requestFocus();
			
			JButton jp3_3 = new JButton("提交答案");
			jp3_3.setSize(100, 20);
			jp3_3.setLocation(400, 30);
			jp3.add(jp3_3);
			jp3_3.requestFocus();
			
			if (flag.equals("0")) {
				timer.stop();
                dispose();
                GUI gui = new GUI(score , "1" , 1);
			}
			
			jp3_3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        //String input = jp3_1.getText();
			        String message = jp3_1.getText();
					//question_else = GetSameAnswer();
					int newscore = score;
					int win_1 = win;
					String answer = GetAnswer();
					if (message.equals("")) {
						newscore = newscore - 1;
						JOptionPane.showMessageDialog(null, "您没有回答，答案是：" + answer);
						Write_1();
						win_1 = 1;
					}
					else if (message.equals(answer)) {
						newscore = newscore + win_1;
						if (win_1 == 1) {
							JOptionPane.showMessageDialog(null, "恭喜回答正确");
							win_1++;
						}
						else {
							JOptionPane.showMessageDialog(null, "恭喜回答正确,你已经连续回答正确" + win_1 + "轮啦！");
							win_1++;
						}
						Write_2();
						
					}
					else {
						newscore = newscore - 2;
						JOptionPane.showMessageDialog(null, "很遗憾，您回答错误，答案是：" + answer);
						Write_1();
						win_1 = 1;
					}
					
					if (newscore < 0) {
						JOptionPane.showMessageDialog(null, "很遗憾，您失败了");
						System.exit(0);
						
					}
					else {
						timer.stop();
		                dispose();
		                GUI gui = new GUI(newscore , "1" , win_1);
					}
			    }
			});
		}
		public String[] disorganize(String[] string , String[] string1) {
			Random random = new Random();
			int number =random.nextInt(4);
			string[0] = string1[number%4];
			string[1] = string1[(number + 1)%4];
			string[2] = string1[(number + 2)%4];
			string[3] = string1[(number + 3)%4];
			return string;
		}
		
		public String GetAnswer(){
			String response = null;
			try {
				Socket socket = new Socket("localhost" , 2005);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
					
					timer.stop();
					out.println("1");
					response = in.readLine();
					System.out.println("server: " + response);
					socket.close();
					in.close();
					out.close();
				
				}catch (IOException e) {
					e.printStackTrace();
				}
				return response;
	}
		
		public String GetQuestion(){
			String response = null;
			try {
			Socket socket = new Socket("localhost" , 2005);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
				
				//timer.stop();
				out.println("0");
				response = in.readLine();

				System.out.println("server: " + response);
				socket.close();
				in.close();
				out.close();
			
			}catch (IOException e) {
				e.printStackTrace();
			}
			return response;
	}
		
		public String[] GetSameAnswer(){
			String response = null;
			String[] back = new String[4];
			try {
			Socket socket = new Socket("localhost" , 2005);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
				
				//timer.stop();
				out.println("2");
				response = in.readLine();
				System.out.println("server: " + response);
				socket.close();
				in.close();
				out.close();
				back = response.split(";",4);
			}catch (IOException e) {
				e.printStackTrace();
			}
			return back;
	}
		
		public void Write_1(){
			try {
			Socket socket = new Socket("localhost" , 2005);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
				
				//timer.stop();
				out.println("3");
				socket.close();
				in.close();
				out.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		public void Write_2(){
			try {
			Socket socket = new Socket("localhost" , 2005);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
				
				//timer.stop();
				out.println("4");
				socket.close();
				in.close();
				out.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	}
	
	public static void main (String[] args) {
		int default_score = 10;
		GUI gui = new GUI(default_score , "0" , 1);
	}
	
}