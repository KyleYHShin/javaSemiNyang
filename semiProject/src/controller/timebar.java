package controller;

import javax.swing.*;  
public class timebar extends JFrame{  
JProgressBar timeBar;  
int i=1000;  
  
public timebar(){  
timeBar=new JProgressBar(0,1000);  
timeBar.setBounds(40,40,200,30);  
      
timeBar.setValue(1000);  
timeBar.setStringPainted(true);  
      
add(timeBar);  
setSize(400,400);  
setLayout(null);  
}  
  
public void timbarRun(){  
while(i>=0){  
  timeBar.setValue(i);  
  i=i-20;  
  try{Thread.sleep(1000);}//1000�� �ѹ��� ������...
 
  catch(Exception e){}  
}  
}  
public static void main(String[] args) {  
    timebar m=new timebar();  
    m.setVisible(true);  
   m.timbarRun();  
}  
}  