package controller;

import java.awt.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo extends Thread{
  Toolkit toolkit;
  Timer timer;
  Scanner sc = new Scanner(System.in);
  
  public TimerDemo(int limitTime) {
    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(), limitTime * 1000);
    
    
//time �� ���õ� ��� long�� ��� ���� ����....
    if(sc.next().equals("stop")){
    	timer.cancel();
    	System.out.println("�Ͻ�����");
    }
    if(sc.next().equals("go")){
    	timer.purge();
    	System.out.println("�����");
    }
    
  }




  class RemindTask extends TimerTask {
    public void run() {
      System.out.println("���� ��");
      toolkit.beep();
      System.exit(0); //���� �Ϸ�κ�
    }
  }
  
 
  public static void main(String args[]) {
    System.out.println("���� ����");
    new TimerDemo(10);
    
    
  }
}  