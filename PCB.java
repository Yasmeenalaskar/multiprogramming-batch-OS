/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamaahmad
 */
public class PCB {

   int ECU;// Expected CPU usage
   int EMR;// Expected memory requirement 
   int PID;// process ID
   int WT; // Wating time
   int IRT;// Time in I/O
   private String state; // State of process
   private int CUT;// Actual CPU usage 
   int totalWT;// total wating time

   public PCB(int iD, int ECU,int EMR) {
      this.PID = iD;
      this.ECU = ECU;
      this.EMR = EMR;
      WT=0;
      totalWT=0;
      IRT=0;
      state ="New";
      CUT = 0;
   }

   public int getCUT() {
      return CUT;
   }

   public void setCUT(int countCPU) {
      this.CUT = countCPU;
   }

   public void setState(String s) {
      state = s;
   }

   public String getState() {
      return state;
   }
}