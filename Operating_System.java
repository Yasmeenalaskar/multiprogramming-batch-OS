import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException; // Import this class to handle FileNotFoundException error
import java.io.IOException;
import java.util.Scanner; // Import the Scanner to read and write form the files
import java.util.logging.Level;
import java.util.logging.Logger;


public class Operating_System {

   public int JID = 0; // ID of job
   double RAM_Size = 192000; 
   double SSD = 2000000; //  hard disk for user program
   LinkedPQ q = new LinkedPQ(); // linked queue of JOB Scedeular
   LinkedPQ readyQ = new LinkedPQ(); // Ready queue 
   LinkedPQ IOQ = new LinkedPQ(); // I/O queue 
   boolean read = false; // check to decide read from job.txt or remaining job.txt 


// ==========================================create a job.txt file, generate 150 jobs and write their info on the file
   public Operating_System() {
   
      try {
         File o1 = new File("Job.txt"); 
         FileWriter fw = new FileWriter(o1);
      
         for (int i = 0; i < 150; i++) { 
            int ECU = (int) Math.round(Math.random() * (512 - 16 + 1) + 16); 
            int EMR =(int) Math.round(Math.random() * (256 - 16+1) + 16);
            ++JID;
            fw.write("" + JID);
            fw.write("\n");
            fw.write("" + ECU);
            fw.write("\n");
            fw.write("" + EMR);
            fw.write("\n");
         }
         fw.close();      
      } catch (Exception ex) {
         System.out.print("Error while genrating process");
      }
   }


// ==========================================To Fill The RAM from The hard disk by JOB Scedeular
   public LinkedPQ CPUSecdular() {
    
      PQNodeRAM job;
      LinkedPQ jobs = JOBsecdular();
      System.out.println("CPUsecdular enque processs now if avalible ");
      while (jobs.length() != 0) {
         job = jobs.serveRAM();
         
         if (job.data.EMR > RAM_Size) { // return the process that's not fit ram into q of jobs
            q.enqueueTOSSD(job.data); 
            break;}
         System.out.println("process with ID "+job.data.PID+" join Ready Queue");
         RAM_Size-= job.data.EMR;
         job.data.setState("Ready");
         readyQ.enqueueTORAM(job.data);
        
      }
      if(readyQ.length()==0){
         System.out.println("No remaining process to join ready queue");
      }
      return readyQ;
   }


// ========================================== read the jobs from job.txt, create PCB and set state to NEW
   public LinkedPQ JOBsecdular() {
      System.out.println("JOBsecdular enque jobs now if avalible ");
      File remaining = new File("remaining job.txt"); // write the remaining jobs on it
      File myObj = new File("Job.txt");
      try {
      
         Scanner myReader = new Scanner(myObj);
         try {
            remaining.createNewFile();
         } catch (IOException ex) {
            Logger.getLogger(Operating_System.class.getName()).log(Level.SEVERE, null, ex);
         }                     
         Scanner remainingjob = new Scanner(remaining);
         if (read) { // is the file job read first time ? if yes we read from remaining
               
            if(remaining.length() == 0) {
               System.out.println("No remaining jobs");
               return new LinkedPQ();}
            while (remainingjob.hasNextLine()) {
               String JIDF = remainingjob.nextLine();
               String ECUF = remainingjob.nextLine();
               String EMRF = remainingjob.nextLine();
               try {
                  int JID = Integer.parseInt(JIDF.trim());
                  int ECU = Integer.parseInt(ECUF.trim());
                  int EMR = Integer.parseInt(EMRF.trim());
                
                  if (EMR > SSD) {  // dosn't fit the SSD
                     try{
                        FileWriter fw = new FileWriter(remaining);
                        fw.write("" + JIDF);
                        fw.write("\n");
                        fw.write("" + ECUF);
                        fw.write("\n");
                        fw.write("" + EMRF);
                        fw.write("\n");
                        while (remainingjob.hasNextLine()) {
                           JIDF = remainingjob.nextLine();
                           ECUF = remainingjob.nextLine();
                           EMRF = remainingjob.nextLine();
                        
                           fw.write("" + JIDF);
                           fw.write("\n");
                           fw.write("" + ECUF);
                           fw.write("\n");
                           fw.write("" + EMRF);
                           fw.write("\n");
                        }//end while
                     }catch (Exception e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                     } 
                  } // end if
                  SSD-=EMR;
                  PCB p = new PCB(JID, ECU, EMR); 
                
                  q.enqueueTOSSD(p); 
                  System.out.println("job with ID "+JID+" is admitted");
               } catch (NumberFormatException nfe) {
                  System.out.println("NumberFormatException: " + nfe.getMessage());
               }
            }// end while
         } else {
         
            while (myReader.hasNextLine()) {
               String JIDF = myReader.nextLine();
               String ECUF = myReader.nextLine();
               String EMRF = myReader.nextLine();
               try {
                  int JID = Integer.parseInt(JIDF.trim());
                  int ECU = Integer.parseInt(ECUF.trim());
                  int EMR = Integer.parseInt(EMRF.trim());
                  if (EMR > SSD) {
                     read = true;
                     try {
                     
                        FileWriter fw = new FileWriter(remaining);
                        fw.write("" + JIDF);
                        fw.write("\n");
                        fw.write("" + ECUF);
                        fw.write("\n");
                        fw.write("" + EMRF);
                        fw.write("\n");
                        while (myReader.hasNextLine()) {
                           JIDF = myReader.nextLine();
                           ECUF = myReader.nextLine();
                           EMRF = myReader.nextLine();
                           try {
                              fw.write("" + JIDF);
                              fw.write("\n");
                              fw.write("" + ECUF);
                              fw.write("\n");
                              fw.write("" + EMRF);
                              fw.write("\n");
                           } catch (Exception e) {
                              System.out.println("An error occurred.");
                              e.printStackTrace();
                           }		
                        }// end while
                        fw.close();}
                     catch (Exception e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                     }	
                     break;
                  }
                  SSD=SSD-EMR;
                  PCB p = new PCB(JID, ECU, EMR); 
                  q.enqueueTOSSD(p); 
                  System.out.println("job with ID "+JID+" is admitted");
               } catch (NumberFormatException nfe) {
                  System.out.println("NumberFormatException: " + nfe.getMessage());
               }
            }// end while
            myReader.close();
         }// end else
         read=true;
      } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
      return q;
   }


// ==========================================Process is Terminate Normally
   public boolean terminatesNormally() {
   
      double TimeN = Math.random() * (1 - 0 + 1) + 0;
      if (TimeN >= 0 && TimeN <= 0.1) {
         return true;
      }
      return false;
   }


// ==========================================Process is Terminate Abnormally
   public boolean terminatesAbNormally() {
   
      double TimeA = Math.random() * (1 - 0 + 1) + 0;
      if (TimeA >= 0 && TimeA <= 0.05) {  
         return true;
      }
      return false;
   }


// ==========================================Process has an I/O request
   public boolean OI() {
   
      double TimeA = Math.random() * (1 - 0 + 1) + 0;
      if (TimeA >= 0 && TimeA <= 0.20) {
         return true;
      }
      return false;
   }
   
// ==========================================Process finish an I/O request
   public boolean FinshOI() {
   
      double TimeA = Math.random() * (1 - 0 + 1) + 0;
      if (TimeA >= 0 && TimeA <= 0.20) {
         return true;
      }
      return false;
   }
   
   
// ==========================================cpu stop and I/O routine start 
   public boolean IOR(){
   
      double TimeA = Math.random() * (1 - 0 + 1) + 0;
      if (TimeA >= 0 && TimeA <= 0.20) {
         return true;
      }
      return false;
   }
}