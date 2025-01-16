# Introdution And Description

This is a project for Operating Systems lecture. In this project we were supposed to provide a solution to readers-writers problem in which several processes (readers and writers) are trying to access shared variables.
Two operations on the semaphore were allowed; ***acquire()*** and ***release()*** (they correspond wait and signal functions)
The problem involves two types of threads: readers and writers. Readers can access the resource simultaneously, but writers require exclusive access. If a writer is writing, no other reader or writer should be allowed to access the resource. The challenge is to ensure that readers do not block each other, while writers are given priority over readers when necessary.


## Solution Explanation

This solution uses three semaphores to manage access to the resource:
- **Semaphore 'S':** This protects the readersCount and writersCount variables to ensure that they are updated safely.
- **Semaphore 'readerBlock':** This blocks new readers if a writer is currently writing.
- **Semaphore 'writerBlock':** This ensures that writers proceed one by one into the critical section and blocks other writers from entering while one is writing.
The solution defines the following functions to manage locking and unlocking of the resource:
- **readLock():** This function allows a reader to acquire access to the shared resource. It ensures that the first reader blocks writers and other readers can enter concurrently.
- **writeLock():** This function allows a writer to acquire exclusive access to the resource. It ensures that no other writer or reader can enter the critical section while the writer is writing.
- **readUnLock():** This function releases the reader's lock and, if it is the last reader, allows writers to proceed.
- **writeUnLock():** This function releases the writer's lock and allows new readers or writers to enter.



## Implementation Details
The implementation utilizes Java's concurrency model to manage multiple reader and writer threads. Each thread either acquires a read lock or a write lock depending on its type, performs its task (reading or writing), and then releases the appropriate lock when finished. The program randomly generates a number of readers and writers and simulates their execution. To make it more realistic, sleep functions are called for simulating reading and writing processes.



## Sample Test
In the sample test, a random number of readers (between 2 and 5) and writers (between 1 and 3) are created. Each reader thread acquires the read lock, simulates reading for a short period, and then releases the read lock. Each writer thread acquires the write lock, simulates writing for a longer period, and then releases the write lock.




## Conclusion
This solution demonstrates an effective way to manage access to a shared resource using semaphores, ensuring that multiple readers can access the resource concurrently, while writers are given exclusive access when needed. The solution ensures that no writer is starved, though additional mechanisms could be introduced to guarantee fairness. The use of semaphores provides an efficient way to synchronize multiple threads while avoiding issues like deadlocks or race conditions.



### Some Outputs:  

![image](https://github.com/user-attachments/assets/ed70b313-264d-45ad-8d13-11da5247168e) 

![image](https://github.com/user-attachments/assets/922e138c-d95b-4c6c-9842-7cc88100be55)

