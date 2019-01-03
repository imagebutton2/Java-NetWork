import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JFrame;

public class Serve extends JFrame {
	private static final long serialVersionUID = 1L;
	private DatagramSocket socket;//定义了一个Socket套接字
	private Graphics g;//画笔
	
	private Integer x;//坐标x
	private Integer y;//坐标y
	private String flag;//标志flag判断是圆还是正方形
	
	private String flag1;//标志flag1判断是何种颜色
	private int color1;
	private int color2;
	private int color3;
	private String	[] string=new String [3];
	private Color color;
/**
以上为服务 画笔 颜色的变量
 */
public Serve(){
	string[0]="灰色";
	string[1]="蓝色";
	string[2]="红色";
}

	public void udpReceive() {

		// 接收客户端发来的数据（核心）
		try {
			socket = new DatagramSocket(9090);//创建一个数据报端口号为9090
			while (true) {
				byte[] b1 = new byte[100];
				byte[] b2 = new byte[100];
				byte[] b3 = new byte[100];
				byte[] b4 = new byte[100];
				
				DatagramPacket packet1 = new DatagramPacket(b1, b1.length);
				DatagramPacket packet2 = new DatagramPacket(b2, b2.length);
				DatagramPacket packet3 = new DatagramPacket(b3, b3.length);
				DatagramPacket packet4 = new DatagramPacket(b4, b4.length);
				socket.receive(packet1);
				socket.receive(packet2);
				socket.receive(packet3);
				socket.receive(packet4);
				
				flag  = new String(packet3.getData(), 0, packet3.getLength());
				flag1  = new String(packet4.getData(), 0, packet4.getLength());
				x = Integer.parseInt(new String(packet1.getData(), 0, packet1.getLength()));
				y = Integer.parseInt(new String(packet2.getData(), 0, packet2.getLength()));
				 System.out.println(x);
				 System.out.println(y);
				 System.out.println(flag);
				 System.out.println(flag1);
				 
				 if("红色".equals(flag1)) {
						color1=255;
						color2=0;
						color3=0;
					}
				
				 if("蓝色".equals(flag1)) {
						color1=0;
						color2=0;
						color3=255;
					}
				
				 if("灰色".equals(flag1)) {
						color1=128;
						color2=128;
						color3=128;
					}
				color=new Color(color1, color2, color3,255);
				
				print(); //画图函数
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(socket!=null) {
				socket.close();
			}
			
		}
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
	
	public static void main(String[] args) throws SocketException {
		// TODO Auto-generated method stub
		//事件分派线程
		EventQueue.invokeLater(() ->{

		Serve serve=new Serve();
		serve.setTitle("Serve");
		serve.setSize(800, 800);
		serve.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serve.setVisible(true);		
		serve.udpReceive();
		});
	}	
		
}