/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamaahmad
 */
public class PQNodeRAM {
	
   public PCB data;
   public PQNodeRAM next;
		
   public PQNodeRAM() {
      next = null;
   }
   
   public PQNodeRAM(PCB e) {
      data = e;
   }
}