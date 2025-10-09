# Operating System Syllabus

## 1. Introduction to Operating Systems
- [ ] What is an Operating System?
- [ ] Functions and Goals of OS
- [ ] Types of Operating Systems (Batch, Time-Sharing, Distributed, Real-Time)
- [ ] OS Structure (Monolithic, Layered, Microkernel, Modular)
- [ ] System Calls and System Programs

## 2. Process Management
- [ ] Process Concept and States
- [ ] Process Control Block (PCB)
- [ ] Process Creation and Termination
- [ ] Context Switching
- [ ] Inter-Process Communication (IPC)
- [ ] Shared Memory and Message Passing
- [ ] Process Scheduling Queues
- [ ] Operations on Processes

## 3. CPU Scheduling
- [ ] CPU Scheduling Criteria (CPU Utilization, Throughput, Turnaround Time, Waiting Time, Response Time)
- [ ] First-Come, First-Served (FCFS)
- [ ] Shortest Job First (SJF)
- [ ] Shortest Remaining Time First (SRTF)
- [ ] Priority Scheduling
- [ ] Round Robin (RR)
- [ ] Multilevel Queue Scheduling
- [ ] Multilevel Feedback Queue Scheduling

## 4. Threads and Concurrency
- [ ] Thread Concepts and Benefits
- [ ] User Threads vs Kernel Threads
- [ ] Multithreading Models (Many-to-One, One-to-One, Many-to-Many)
- [ ] Thread Libraries (Pthreads, Java Threads, Windows Threads)
- [ ] Threading Issues (fork(), exec(), Signal Handling, Thread Cancellation)

## 5. Process Synchronization
- [ ] Critical Section Problem
- [ ] Peterson's Solution
- [ ] Synchronization Hardware (Test-and-Set, Compare-and-Swap)
- [ ] Mutex Locks
- [ ] Semaphores (Binary and Counting)
- [ ] Monitors
- [ ] Classical Synchronization Problems (Producer-Consumer, Readers-Writers, Dining Philosophers)
- [ ] Atomic Transactions
- [ ] Priority Inversion

## 6. Deadlocks
- [ ] Deadlock Characterization (Mutual Exclusion, Hold and Wait, No Preemption, Circular Wait)
- [ ] Resource Allocation Graph
- [ ] Deadlock Prevention
- [ ] Deadlock Avoidance (Banker's Algorithm)
- [ ] Deadlock Detection and Recovery

## 7. Memory Management
- [ ] Logical vs Physical Address Space
- [ ] Memory Allocation (Contiguous, Non-Contiguous)
- [ ] Fixed and Variable Partitioning
- [ ] Internal and External Fragmentation
- [ ] Paging and Page Tables
- [ ] Segmentation
- [ ] Virtual Memory Concepts
- [ ] Demand Paging
- [ ] Page Replacement Algorithms (FIFO, LRU, Optimal, LFU)
- [ ] Thrashing and Working Set Model
- [ ] Memory Allocation Algorithms (First Fit, Best Fit, Worst Fit)

## 8. File System Management
- [ ] File Concepts and Attributes
- [ ] File Operations and Access Methods
- [ ] Directory Structure (Single-Level, Two-Level, Tree-Structured, Acyclic Graph)
- [ ] File Allocation Methods (Contiguous, Linked, Indexed)
- [ ] Free Space Management
- [ ] Disk Structure and Formatting
- [ ] File System Implementation

## 9. I/O Systems
- [ ] I/O Hardware and Devices
- [ ] Polling, Interrupts, and DMA
- [ ] I/O Software Layers
- [ ] Disk Structure and Scheduling
- [ ] Disk Scheduling Algorithms (FCFS, SSTF, SCAN, C-SCAN, LOOK, C-LOOK)
- [ ] RAID Levels
- [ ] Disk Management and Formatting

## 10. Security and Protection
- [ ] Security Goals (Confidentiality, Integrity, Availability)
- [ ] Authentication and Authorization
- [ ] Access Control (Access Matrix, Access Control Lists, Capabilities)
- [ ] Security Threats (Malware, Viruses, Worms, Trojans)
- [ ] Cryptography Basics
- [ ] Protection Domains and Rings
- [ ] Security Mechanisms

## 11. Distributed and Advanced OS
- [ ] Distributed System Concepts
- [ ] Client-Server Model
- [ ] Remote Procedure Call (RPC)
- [ ] Distributed File Systems
- [ ] Real-Time Operating Systems
- [ ] Virtualization and Hypervisors

---

## Study Progress Tracker

### Overall Completion
- [ ] Introduction to OS (5 topics)
- [ ] Process Management (8 topics)
- [ ] CPU Scheduling (8 topics)
- [ ] Threads & Concurrency (5 topics)
- [ ] Process Synchronization (9 topics)
- [ ] Deadlocks (5 topics)
- [ ] Memory Management (11 topics)
- [ ] File System Management (7 topics)
- [ ] I/O Systems (7 topics)
- [ ] Security & Protection (7 topics)
- [ ] Distributed & Advanced OS (6 topics)

**Total Topics: 78**

---

## Exam-Specific Notes

### High-Weightage Topics for GATE/UGC-NET
1. **CPU Scheduling Algorithms** - Practice numerical problems
2. **Deadlock (Banker's Algorithm)** - Very important for calculations
3. **Page Replacement Algorithms** - Frequently asked with examples
4. **Disk Scheduling** - Calculate seek time for different algorithms
5. **Process Synchronization** - Classic problems are common
6. **Memory Management** - Paging, segmentation calculations
7. **File Allocation Methods** - Compare advantages/disadvantages

### Practice Tips
- Solve numerical problems for scheduling algorithms
- Draw diagrams for process synchronization problems
- Practice Banker's algorithm step-by-step
- Understand time complexity of page replacement algorithms
- Learn to calculate average seek time for disk scheduling

---

*Last Updated: 2025*
