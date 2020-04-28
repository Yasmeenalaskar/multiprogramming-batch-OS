import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CPU {

   public static double CountNormally = 0;
   public static double CountAbNormally = 0;
   public static double CountIO= 0;
   public static double cutcpu= 0;// count cpu usage 
   static LinkedPQ proccedjobsQ  = new LinkedPQ();
      static LinkedPQ readyQ  = new LinkedPQ();
     static LinkedPQ IOQ  = new LinkedPQ();
   static int cstime =0; // context switch time
   static int sumsize=0;
   static int maxsize=0;
   static int minsize=100000000;
   static int avg=0;
   static double totalwaitTime = 0;
   static double totalspeandTime = 0;
   static Operating_System OS ;
  static  boolean happen = false ; 

   
   
   public static void main(String[] args) {
    
      System.out.println("Start OS");
      OS = new Operating_System();
        
      cpu();
    
   }
 
  
   public static void cpu(){
       
      System.out.println("CPU start processing ");
      boolean needIO ;
     
     // OS.CPUSecdular();//to fill ready Queue
       OS.CPUSecdular();//to fill ready Queue
    
      while (OS.readyQ.length() > 0) {// generate interrupt for current process and set its state to TERMINATE
          
         needIO = false ; 
         PQNodeRAM job = OS.readyQ.serveRAM();
         System.out.println("process with ID "+job.data.PID+" is dispatched from Ready Queue");
         job.data.setState("Running");
         cstime++;
         if(maxsize <job.data.EMR) {
            maxsize=job.data.EMR;
         }
         if(minsize >job.data.EMR) {
            minsize=job.data.EMR;
         }
         sumsize+= job.data.EMR;// to calculate the avarage job size 
         
         while (true) {
            if (OS.OI()) {	
               CountIO++;//change
               System.out.println("The PID : " + job.data.PID +  " Interrupt by I/O request ");
               job.data.WT = cstime; // current time
               
               job.data.setState("Waiting");
               OS.IOQ.enqueueTORAM(job.data);
               needIO = true ;
               break;
            }
            if (OS.terminatesNormally()) {	// check terminates normally interrupt
               CountNormally++;
               System.out.println("The PID : " + job.data.PID + "	The CUT :" + job.data.getCUT()+ "     The IRT : "+job.data.IRT +"   The WT :"+job.data.totalWT+ "	Termination status :" + "Terminate normal");	
               break;
            } else if (OS.terminatesAbNormally()) {// check terminates abnormally interrupt
               CountAbNormally++;	
               System.out.println("The PID : " + job.data.PID + "	The CUT :" + job.data.getCUT()+ "     The IRT : "+job.data.IRT +"   The WT :"+job.data.totalWT+ "	Termination status :" + "Terminate abnormal");	
               break;
            }  
            if (job.data.getCUT() > job.data.ECU) { // check if actual CPU usage term exceeds expected CPU usage
               CountAbNormally++;	
               System.out.println("The PID : " + job.data.PID + "	The CUT :" + job.data.getCUT()+ "     The IRT : "+job.data.IRT +"   The WT :"+job.data.totalWT+"	Termination status :" + "Terminate abnormal");	
               break;
            }
            if(OS.IOR()){ /// interrupt for doning I/O routine 
               job.data.setState("Ready");
              OS.readyQ.enqueueTORAM(job.data);
               needIO = true ;
               if(OS.IOQ.length() > 0)
                 IOroutine(); 
              break;
            }
            job.data.setCUT(job.data.getCUT() + 1); // increment CPU usage of current procces
         }// End while
         if(!needIO){
            cutcpu+=job.data.getCUT(); // CPU usage for calculate CPU utlization 
            OS.RAM_Size+=job.data.EMR;
            OS.SSD+=job.data.EMR;
            job.data.setState("Terminate");
            OS.CPUSecdular(); // invoke CPUSecdular to admit a new job.
            proccedjobsQ.enqueueTORAM(job.data);
            
         }
      } // End while 
   
      if(OS.IOQ.length() > 0) // if there's remaining procces in I/O queue
         IOroutine();
      else{
         if(!happen) 
         result(); 
      }
   }
   public static void IOroutine(){
      int totalspenttime=0; // for this itration
      System.out.println("IOroutine start");
      while(OS.IOQ.length() > 0){
         PQNodeRAM jobNeedIO = OS.IOQ.serveRAM();
         int spendTime = 0;
         if(spendTime==0)
         while (true){
            spendTime++;
            if(OS.FinshOI()){
               jobNeedIO.data.IRT  += spendTime;
               jobNeedIO.data.WT = (cstime + totalspenttime) - jobNeedIO.data.WT;
               totalwaitTime += jobNeedIO.data.WT ;
               jobNeedIO.data.totalWT+=jobNeedIO.data.WT;
               totalspeandTime +=spendTime;
               jobNeedIO.data.setState("Ready");
               OS.readyQ.enqueueTORAM(jobNeedIO.data);
               System.out.println("process with ID "+jobNeedIO.data.PID+" finsh from I/O routine and join Ready Queue");
               
               break;
            }
         }
        totalspenttime+=spendTime; 
      }
      cpu(); // return to exucute the remaining procses  in cpu
   }
   public static void result(){
      happen = true ;
      File basicFile = new File("Results.txt");
      try {
         basicFile.createNewFile();
      } catch (IOException ex) {
         Logger.getLogger(CPU.class.getName()).log(Level.SEVERE, null, ex);
      }
       
      try {
                    
         FileWriter FileW = new FileWriter(basicFile);
                      
         FileW.write("The number of processed jobs is " + proccedjobsQ.length() + " jobs");
         FileW.write("\n"); 
      	
         FileW.write("The minimum job size	" + minsize + " KB");
         FileW.write("\n");
      	
         FileW.write("The maximum job size	" + maxsize + " KB");
         FileW.write("\n");
      	
         FileW.write("The Average job size	" + sumsize/proccedjobsQ.length() + " KB");
         FileW.write("\n");
      	
         FileW.write("The number of jobs when the execution done normally	" + (int)CountNormally + " jobs");
         FileW.write("\n");
      
         FileW.write("The number of jobs when the execution done Abnormally	" + (int)CountAbNormally + " jobs");
         FileW.write("\n");
      	
         FileW.write("CPU utilization " + Math.round((cstime/(totalspeandTime+cstime))*100 )+ "%");
         FileW.write("\n"); 
         FileW.write("The number of jobs serviced by I/O device  " + (int)CountIO + " jobs");
         FileW.write("\n");
         FileW.write("I/O device utilization " + Math.round((totalwaitTime/(totalspeandTime+totalwaitTime))*100) + "%");
         FileW.write("\n"); 
         FileW.write("The Average waiting time	" + Math.round(totalwaitTime/CountIO) + " units");
         FileW.write("\n");
         FileW.close();
      } catch (Exception A) {
                    
         System.out.println("There is an Error");
      }
      System.out.println("Results are generated into Results.tax file ");
   
   
   
   }
}