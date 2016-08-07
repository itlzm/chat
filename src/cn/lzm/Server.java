package cn.lzm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private ServerSocket server;
	private List<PrintWriter> allout;
	public Server() throws Exception{
		server = new ServerSocket(8088);
		allout = new ArrayList<PrintWriter>();
	}
	public synchronized void addOut(PrintWriter out){
		allout.add(out);
	}
	public synchronized void removeOut(PrintWriter out){
		allout.remove(out);
	}
	public synchronized void sendMessage(String message){
		for(PrintWriter out:allout){
			out.println(message);
		}
	}
	public synchronized void sendTask(){
		int size = allout.size();
		PrintWriter out = allout.get(size-1);
		out.println("请输入你的姓名：");
	}
	public void start() throws Exception{
		while(true){
			System.out.println("等待连接");
			Socket socket = server.accept();
			System.out.println("连接成功");
			ClientHandler handler = new ClientHandler(socket);
			Thread t = new Thread(handler);
			t.start();
		}

	}
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	class ClientHandler implements Runnable{
		private Socket socket;
		private String host;
		public ClientHandler(Socket socket){
			this.socket = socket;
			InetAddress address = socket.getInetAddress();
			host = address.getHostAddress();
		}
		
		public void run(){
			PrintWriter pw = null;
			try {
				sendMessage("["+host+"]上线了！");
				OutputStream out = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
				pw = new PrintWriter(osw,true);
				addOut(pw);
				sendTask();
				
				InputStream	in = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String message = null;
				while((message = br.readLine())!=null){
					sendMessage(host+":"+message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try{
					removeOut(pw);
					sendMessage("["+host+"]下线了!");
					socket.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
