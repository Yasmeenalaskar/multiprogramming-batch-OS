# multiprogramming-batch-OS
 Simulation of a simple multiprogramming batch operating system <br/>
 The system hardware specifications assumed in the simulation are as follows: <br/>
1. A single- core CPU. <br/>
2. A hard disk with 2 GB available for user programs. <br/>
3. A RAM with 192 MB available for user programs. <br/>
4. A single IO device <br/>
The simulation covers two features of the operating system:<br/>
1. Job scheduling: The operating system maintains a single job queue. Job Scheduler follows the
Smallest Storage Requirement (SSR) policy. <br/>
2. Process scheduling. The operating system maintains a single ready queue and a single I/O
queue. CPU Scheduler follows the First-Come, First-Served (FCFS) scheduling algorithm policy. <br/>

There will be no external input to the program. In the beginning of the simulation, the program
generates a file (Jobs.txt) containing 150 jobs <br/>
 The ECU is generated randomly with a uniform distributed
between 16 and 512 units <br/>
The EMR is
generated randomly with a uniform distributed between 16 KB and 256 KB. <br/>

