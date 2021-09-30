# MMU
Memory Management Unit - JAVA project

## Summery

This project implements a system to manage a memory unit. The task of the memory unit, is to identify if a requested page is not in the RAM. In case the page is not there, the MMU will get it from the hard drive.

Simulate RAM (client), Hard disc(server) and MMU work when page-fault or page-replacement accure

When you first initialize the unit, you choose a replacement algorithm for the MMU (implemented with Java-Generics).

## Technologies

This project uses Client-Server communication, OOP, Multi-threaded, GUI (Swing) in Java.

## Description

Paging is a memory manage method that allows the operating system to transfer memory segments from the main memory to the secondary memory. The data transfer is performed in memory segments have a equal size, which called **pages**. The paging is an important step in the virtual memory implementation in all modern operating systems, and allows them to use the hard disk for store too large data without save them in the main memory.

In order to perform the paging process the operating system uses the MMU unit, which is an integral part of it and its function is to translate the virtual addresses space, which the process "know" into the physical addresses space (represents main and secondary memory).

If the memory controller detects that the requested page is not mapped to the main memory, a page fault is created, and the memory controller raises appropriate interrupt in rder to load the requested page from the secondary memory. The operating system executes the following:

- Determine the location of the data in the secondary storage devices.
- If the main memory is full, select a page to remove from the main memory.
- Load the requested data into the main memory.
- Update the page table with the new data.
- Ending the interrupt handling.

The need to refer to a particular address in memory stems from two main sources:

- Access to the next program instruction to execute.
- Access data by instruction from the program

