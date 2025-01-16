import java.util.concurrent.Semaphore;
import java.util.Random;

class ReadWriteLock {

    private final Semaphore S = new Semaphore(1);
    private final Semaphore readerBlock = new Semaphore(1);
    private final Semaphore writerBlock = new Semaphore(1);

    private int readersCount = 0;
    private int writersCount = 0;

    public void readLock() {
        try {
            readerBlock.acquire();
            S.acquire();
            readersCount++;
            if (readersCount == 1) {
                writerBlock.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            S.release();
            readerBlock.release();
        }
    }

    public void writeLock() {
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting to write.");
            S.acquire();
            if (writersCount == 0) {
                readerBlock.acquire();
            }
            writersCount++;
            S.release();

            writerBlock.acquire();
            System.out.println(Thread.currentThread().getName() + " has entered the critical section to write.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readUnLock() {
        try {
            S.acquire();
            readersCount--;
            if (readersCount == 0) {
                writerBlock.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            S.release();
        }
    }

    public void writeUnLock() {
        try {
            System.out.println(Thread.currentThread().getName() + " has exited the critical section after writing.");
            S.acquire();
            writersCount--;
            if (writersCount == 0) {
                readerBlock.release();
            }
            S.release();

            writerBlock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Sample test class
class Main {
    public static void main(String[] args) {
        ReadWriteLock rwLock = new ReadWriteLock();
        Random random = new Random();

        Runnable reader = () -> {
            rwLock.readLock();
            System.out.println(Thread.currentThread().getName() + " is reading.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rwLock.readUnLock();
            System.out.println(Thread.currentThread().getName() + " finished reading.");
        };

        Runnable writer = () -> {
            rwLock.writeLock();
            System.out.println(Thread.currentThread().getName() + " is writing data.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rwLock.writeUnLock();
        };

        int numReaders = random.nextInt(5) + 2;
        int numWriters = random.nextInt(3) + 1;

        System.out.println("Number of Readers: " + numReaders);
        System.out.println("Number of Writers: " + numWriters);

        Thread[] threads = new Thread[numReaders + numWriters];

        for (int i = 0; i < numReaders; i++) {
            threads[i] = new Thread(reader, "Reader " + (i + 1));
        }

        for (int i = 0; i < numWriters; i++) {
            threads[numReaders + i] = new Thread(writer, "Writer " + (i + 1));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
