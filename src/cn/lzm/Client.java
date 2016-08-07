package cn.lzm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private Socket socket;
	public Client() throws Exception{
		socket = new Socket("localhost",8088);
	}
	public void start(){
		try {
			ServerHandler handler = new ServerHandler();
			Thread t = new Thread(handler);
			t.start();
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
			PrintWriter pw = new PrintWriter(osw,true);
			Scanner scan = new Scanner(System.in);
			String message = null;
			System.out.println(" ‰»Îƒ⁄»›£∫");
			while((message=scan.nextLine())!=null){
				pw.println(message);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try{
			Client client = new Client();
			client.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class ServerHandler implements Runnable{
		public void run(){
			try{
				InputStream in = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String message = null;
				while((message=br.readLine())!=null){
					System.out.println(message);
				}
			}catch(Exception e){
				
			}
		}
	}
}
