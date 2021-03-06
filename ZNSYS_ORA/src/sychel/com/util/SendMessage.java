package sychel.com.util;

import java.io.IOException;

import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.Message.MessageEncodings;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

public class SendMessage {
	private static String COM;	//短信端口
	
	private static Service srv;
	private static OutboundMessage msg;
	static OutboundNotification outboundNotification;// = new OutboundNotification();
	
	static SerialModemGateway gateway;
	
	public static int Send(String PhoneNumber, String Content){
		int successnumber = 0;
		try {
			String[] phones = PhoneNumber.split(",");	//	多条短信可以以逗号隔开，循环发送
			for (int i = 0; i < phones.length; i++) {
				msg = new OutboundMessage(phones[i], Content);
				msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
				srv.sendMessage(msg);
				//判断发送状态
				if(msg.getMessageStatus().toString().trim().equals("SENT")||msg.getMessageStatus().toString().trim().equals("send")){
					successnumber++;
				}else{
					System.out.println("SendMessage-->第"+(successnumber+1)+"条短信发送短信失败！");
				}
			}
			System.out.println("SendMessage-->发送短信结束，成功条数："+successnumber+"条，短信内容："+Content);
			//返回成功数量
			return successnumber;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SendMessage-->发送异常！！");
			return 0;
		}
	}
	
	public static void Close(){
		try {
			srv.stopService();
	        srv.removeGateway(gateway);
	        System.out.println("SendMessage-->关闭短信接口成功！");
		} catch (GatewayException e) {
			e.printStackTrace();
			System.out.println("SendMessage-->关闭端口出错！");
		} catch (SMSLibException e) {
			e.printStackTrace();
			System.out.println("SendMessage-->关闭SMSLib错误！");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public class OutboundNotification implements IOutboundMessageNotification {
		public void process(AGateway agateway, OutboundMessage outboundmessage) {
			System.out.println("SendMessage-->Outbound handler called from Gateway: " + agateway);
			System.out.println("SendMessage-->"+outboundmessage);
			
		}
	}
	
	static{
		COM = "COM4";
		srv = Service.getInstance();
		gateway = new SerialModemGateway("modem." + COM.toLowerCase(), COM, 9600, "wavecom", ""); // 设置端口与波特率
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("1234");
//		gateway.setOutboundNotification(outboundNotification);
		srv.setOutboundMessageNotification(outboundNotification);//发送短信成功后的回调函方法
		try {
			srv.addGateway(gateway);
			System.out.println("SendMessage-->短信发送功能初始化成功，准备开启服务");
			srv.startService();
			System.out.println("SendMessage-->服务启动成功");
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (GatewayException e) {
			e.printStackTrace();
		} catch (SMSLibException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}