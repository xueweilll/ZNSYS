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
	private static String COM;	//���Ŷ˿�
	
	private static Service srv;
	private static OutboundMessage msg;
	static OutboundNotification outboundNotification;// = new OutboundNotification();
	
	static SerialModemGateway gateway;
	
	public static int Send(String PhoneNumber, String Content){
		int successnumber = 0;
		try {
			String[] phones = PhoneNumber.split(",");	//	�������ſ����Զ��Ÿ�����ѭ������
			for (int i = 0; i < phones.length; i++) {
				msg = new OutboundMessage(phones[i], Content);
				msg.setEncoding(MessageEncodings.ENCUCS2); // ����
				srv.sendMessage(msg);
				//�жϷ���״̬
				if(msg.getMessageStatus().toString().trim().equals("SENT")||msg.getMessageStatus().toString().trim().equals("send")){
					successnumber++;
				}else{
					System.out.println("SendMessage-->��"+(successnumber+1)+"�����ŷ��Ͷ���ʧ�ܣ�");
				}
			}
			System.out.println("SendMessage-->���Ͷ��Ž������ɹ�������"+successnumber+"�����������ݣ�"+Content);
			//���سɹ�����
			return successnumber;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SendMessage-->�����쳣����");
			return 0;
		}
	}
	
	public static void Close(){
		try {
			srv.stopService();
	        srv.removeGateway(gateway);
	        System.out.println("SendMessage-->�رն��Žӿڳɹ���");
		} catch (GatewayException e) {
			e.printStackTrace();
			System.out.println("SendMessage-->�رն˿ڳ�����");
		} catch (SMSLibException e) {
			e.printStackTrace();
			System.out.println("SendMessage-->�ر�SMSLib����");
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
		gateway = new SerialModemGateway("modem." + COM.toLowerCase(), COM, 9600, "wavecom", ""); // ���ö˿��벨����
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("1234");
//		gateway.setOutboundNotification(outboundNotification);
		srv.setOutboundMessageNotification(outboundNotification);//���Ͷ��ųɹ���Ļص�������
		try {
			srv.addGateway(gateway);
			System.out.println("SendMessage-->���ŷ��͹��ܳ�ʼ���ɹ���׼����������");
			srv.startService();
			System.out.println("SendMessage-->���������ɹ�");
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