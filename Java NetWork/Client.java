import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


import javax.swing.JFrame;


public class Client extends JFrame { 

	private static final long serialVersionUID = 1L;
	Graphics g;//定义了一个画笔
	private Integer x;//坐标x
	private Integer y;//坐标y
	private String flag;//标志flag判断是圆还是正方形
	private DatagramSocket socket=null;//定义了一个Socket套接字
	
	private String flag1;//标志flag1判断是何种颜色
	private int color1;
	private int color2;
	private int color3;
	private String	[] string=new String [3];
	private Color color;

/**
 * 以上为服务 画笔 颜色的变量
 */

	public Client(){
		
		this.setTitle("Client");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.addMouseListener();//添加鼠标事件监听

		
		int i=0;
		string[0]="灰色";
		string[1]="蓝色";
		string[2]="红色";
        i=(int)(Math.random()*3);
        System.out.println(i);
        System.out.println(string[i]);
		if("红色".equals(string[i])) {
			color1=255;
			color2=0;
			color3=0;
		}
		
		if("蓝色".equals(string[i])) {
			color1=0;
			color2=0;
			color3=255;
		}
		
		if("灰色".equals(string[i])) {
			color1=128;
			color2=128;
			color3=128;
		}
		
        flag1=string[i];
        System.out.println(flag1);
        color=new Color(color1, color2, color3,255);

 /*
 以上为调制画笔颜色
 */
        
	}
	
	public void addMouseListener() {
//鼠标事件响应程序
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
				if(me.getButton()==MouseEvent.BUTTON1){//判断是否为鼠标左键
					x=me.getX();
					y=me.getY();
					flag="Rectangle";//形状为正方形
					print();//画图函数
					sendUdp();
				}
				else if(me.getButton()==MouseEvent.BUTTON3){//判断是否为鼠标右键
					g=getGraphics();
					x=me.getX();
					y=me.getY();
					flag="Circle";//形状为圆
					print();
					sendUdp();
					
				}
			}
		});
		
	}
	
	//画图函数
	public void print(){
		g=getGraphics();
		g.setColor(color);
		if("Rectangle".equals(flag)){	//判断形状是否为正方形		
			g.drawRect(x, y, 20, 20);//画正方形 长20 宽20
		}
		else if("Circle".equals(flag)){//判断形状是否为圆	
			g.drawOval(x, y, 40, 40);//画圆 直径40
		}
	}
	
	public void sendUdp() {
		try {			
			socket = new DatagramSocket();//创建一个数据报
			byte[] Send1=(""+x).getBytes();//将坐标x转化为byte数组
			byte[] Send2=(""+y).getBytes();//将坐标y转化为byte数组
			byte[] Send3=flag.getBytes();//将flag转化为byte数组
			byte[] Send4=flag1.getBytes();//将flag1转化为byte数组
			DatagramPacket pack1=new DatagramPacket(Send1, 0, Send1.length, 
					InetAddress.getByName("127.0.0.1"), 9090);		
			DatagramPacket pack2=new DatagramPacket(Send2, 0, Send2.length, 
					InetAddress.getByName("127.0.0.1"), 9090);
			DatagramPacket pack3=new DatagramPacket(Send3, 0, Send3.length, 
					InetAddress.getByName("127.0.0.1"), 9090);
			DatagramPacket pack4=new DatagramPacket(Send4, 0, Send4.length, 
					InetAddress.getByName("127.0.0.1"), 9090);
			//将坐标x，坐标y，flag，flag1封装成数据报；  ip地址为本地回环地址  端口号9090
			socket.send(pack1);
			socket.send(pack2);
			socket.send(pack3);
			socket.send(pack4);
			//将数据报发送给服务器端
			System.out.println(x);
			System.out.println(y);
			System.out.println(flag);
			
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if(socket!=null)
		{
			socket.close();
		}
	}
	}
			
	public static void main(String[] args){
		//事件分派线程
		EventQueue.invokeLater(() ->{
			Client client=new Client();
		});
	}		
	
}