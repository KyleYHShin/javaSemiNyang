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
    
    
//time 과 관련된 모든 long은 모두 같은 종류....
    if(sc.next().equals("stop")){
    	timer.cancel();
    	System.out.println("일시정지");
    }
    if(sc.next().equals("go")){
    	timer.purge();
    	System.out.println("재시작");
    }
    
  }




  class RemindTask extends TimerTask {
    public void run() {
      System.out.println("게임 끝");
      toolkit.beep();
      System.exit(0); //게임 완료부분
    }
  }
  
 
  public static void main(String args[]) {
    System.out.println("게임 시작");
    new TimerDemo(10);
    
    
  }
}  