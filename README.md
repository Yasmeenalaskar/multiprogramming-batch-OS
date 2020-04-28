# multiprogramming-batch-OS
 Simulation of a simple multiprogramming batch operating system <br/>
 It is a program in Java that simulates the behavior of the multiprogramming operating system.
At the end of the simulation, there is an output some statistics regarding the behavior of the
system. In the following sections, we will introduce the hardware specification, the
multiprogramming OS features and the jobs requirements.
<br/><br/>
 The system hardware specifications assumed in the simulation are as follows: <br/>
1. A single- core CPU. <br/>
2. A hard disk with 2 GB available for user programs. <br/>
3. A RAM with 192 MB available for user programs. <br/>
4. A single IO device <br/>
<br/>
The simulation covers two features of the operating system:<br/>
1. Job scheduling: The operating system maintains a single job queue. Job Scheduler follows the
Smallest Storage Requirement (SSR) policy. <br/>
2. Process scheduling. The operating system maintains a single ready queue and a single I/O
queue. CPU Scheduler follows the First-Come, First-Served (FCFS) scheduling algorithm policy. <br/>

There will be no external input to the program. In the beginning of the simulation, the program
generates a file (Jobs.txt) containing 150 jobs <br/>
 Program specifications: Each program has two main requirements: A programsize in KB and an
expected execution time. Of course, the expected execution time is greater or equal to the exact
execution time. In addition, each program should have an Id and state. <br/>
• The ECU is generated randomly with a uniform distributed between 16 and 512 units.<br/>
• The EMR is generated randomly with a uniform distributed between 16 KB and 256<br/>

**HOW DOSE PROGRAM WORK:**<br/>
- Generate a file (Jobs.txt) containing 150 jobs each along with job id ,expected
CPU usage and expected memory requirement <br/>
- Fill the RAM from the hard disk by job scheduler ( jobs join ready queue as FIFO
until the RAM doesn’t fit any more) <br/>
- Dispatch a process from ready queue to enter CPU and change its state from
ready to running as well as increment cycle (iteration) unite. And define boolean
needIO to identify the process that Interrupted by IO request <br/>
- Generate interrupt for the current process.<br/>
- Increment CPU usage for the current process .<br/>
- Do termination routine if flag needIO not equals true and Update RAM size and
SSD because process is terminated (free RAM space and SSD space).Also update
process state to termination. <br/>
- the loop will continue until either I/O routine call or if the ready queue doesn’t
have process anymore.<br/>
- After the loop we check if there is process in I/O queue if yes call I/O routine else
represent generate result.<br/>
- The program writes the statistics in the output file Results.txt.<br/>


