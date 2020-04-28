/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamaahmad
 */
public class LinkedPQ {

   private int size;
   private PQNodeRAM head;
   private PQNodeRAM Tail;

   public LinkedPQ() {
      head = null;
      size = 0;
   }
   
   public int length() {
      return size;
   }

   public boolean full() {
      return false;
   }

   public void enqueueTORAM(PCB e) {
      PQNodeRAM tmp = new PQNodeRAM(e);
      if(size == 0) {
         tmp.next = head;
         head =Tail= tmp;}
      else{
         Tail.next = tmp ;
         Tail=tmp;
      }
      size++;
   }
   
   public void enqueueTOSSD(PCB e) {
      PQNodeRAM start = head;  
      PQNodeRAM temp = new PQNodeRAM(e);
      if(size == 0) {
         temp.next = head;
         head =Tail= temp;
      } 
      else if ((head).data.EMR > e.EMR) {  
      
         temp.next = head;  
         head = temp;  
      }  
      else {  
      
         while (start.next != null && start.next.data.EMR < e.EMR) {  
            start = start.next;  
         }   
         temp.next = start.next;  
         start.next = temp;  
      }
               size++;  
   
   } 

   public PQNodeRAM serveRAM() {
      PQNodeRAM Node = head;
      if(head.next != null)
         head = head.next;
      size--;
     
      return Node;
   }
}
