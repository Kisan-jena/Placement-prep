# Java Multithreading & Concurrency - Placement Revision Notes

## 📚 Core Concepts

### Thread Lifecycle
```
NEW → RUNNABLE → BLOCKED/WAITING/TIMED_WAITING → TERMINATED
```

### Thread Creation Methods
```java
// Method 1: Extending Thread class
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

// Method 2: Implementing Runnable interface
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}

// Method 3: Using Callable interface (with return value)
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "Callable result: " + Thread.currentThread().getName();
    }
}

// Usage
MyThread thread1 = new MyThread();
Thread thread2 = new Thread(new MyRunnable());
thread1.start();
thread2.start();
```

### Synchronization Mechanisms
1. **synchronized keyword**
2. **Lock interface (ReentrantLock)**
3. **Atomic classes**
4. **volatile keyword**
5. **ThreadLocal**

## 🎯 Key Points for Interviews

1. **Thread vs Process**: Memory sharing, creation cost
2. **Synchronization**: Preventing race conditions
3. **Deadlock**: Circular dependency, prevention strategies
4. **Thread Pool**: Executor framework for efficient thread management
5. **Concurrent Collections**: Thread-safe alternatives

## 💼 Placement Questions & Answers

### Q1: Thread vs Process (TCS, Infosys)
**Answer:**
| Feature | Thread | Process |
|---------|--------|---------|
| Memory | Shared memory space | Separate memory space |
| Creation | Faster to create | Slower to create |
| Communication | Direct (shared variables) | IPC mechanisms |
| Context switching | Faster | Slower |
| Independence | Dependent on process | Independent |
| Crash impact | Can affect entire process | Isolated |

```java
// Thread example
class SharedCounter {
    private int count = 0;
    
    public synchronized void increment() {
        count++; // Shared memory - all threads see same variable
    }
    
    public int getCount() { return count; }
}

// Multiple threads sharing same object
SharedCounter counter = new SharedCounter();
Thread t1 = new Thread(() -> counter.increment());
Thread t2 = new Thread(() -> counter.increment());
```

### Q2: synchronized vs Lock interface (Wipro, Accenture)
**Answer:**
| Feature | synchronized | Lock |
|---------|--------------|------|
| Type | Keyword | Interface |
| Lock acquisition | Automatic | Manual (lock/unlock) |
| Timeout | No | Yes (tryLock with timeout) |
| Interruptible | No | Yes |
| Condition variables | wait/notify | Condition objects |
| Performance | Good | Better (sometimes) |
| Fairness | No control | Can be fair |

```java
// synchronized keyword
class SynchronizedExample {
    private int count = 0;
    
    // Synchronized method
    public synchronized void increment() {
        count++;
    }
    
    // Synchronized block
    public void decrement() {
        synchronized(this) {
            count--;
        }
    }
    
    public int getCount() { return count; }
}

// Lock interface
class LockExample {
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock(); // Must unlock in finally
        }
    }
    
    public boolean tryIncrement() {
        if (lock.tryLock()) {
            try {
                count++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
    
    public boolean tryIncrementWithTimeout() throws InterruptedException {
        if (lock.tryLock(2, TimeUnit.SECONDS)) {
            try {
                count++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}
```

### Q3: What is deadlock? How to prevent it? (Cognizant, HCL)
**Answer:**
**Deadlock occurs when two or more threads wait for each other indefinitely.**

```java
// Deadlock example
class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void method1() {
        synchronized(lock1) {
            System.out.println("Thread1: Holding lock1");
            
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            synchronized(lock2) {
                System.out.println("Thread1: Holding lock1 & lock2");
            }
        }
    }
    
    public void method2() {
        synchronized(lock2) {
            System.out.println("Thread2: Holding lock2");
            
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            synchronized(lock1) {
                System.out.println("Thread2: Holding lock2 & lock1");
            }
        }
    }
}

// Deadlock prevention - Always acquire locks in same order
class DeadlockPrevention {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void method1() {
        synchronized(lock1) {          // Always acquire lock1 first
            synchronized(lock2) {      // Then lock2
                // Critical section
            }
        }
    }
    
    public void method2() {
        synchronized(lock1) {          // Same order: lock1 first
            synchronized(lock2) {      // Then lock2
                // Critical section
            }
        }
    }
}

// Using timeout to prevent deadlock
class TimeoutPrevention {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    
    public boolean safeMethod() throws InterruptedException {
        boolean acquired1 = lock1.tryLock(1, TimeUnit.SECONDS);
        if (!acquired1) return false;
        
        try {
            boolean acquired2 = lock2.tryLock(1, TimeUnit.SECONDS);
            if (!acquired2) return false;
            
            try {
                // Critical section
                return true;
            } finally {
                lock2.unlock();
            }
        } finally {
            lock1.unlock();
        }
    }
}
```

### Q4: Producer-Consumer problem (Tech Mahindra, Capgemini)
**Answer:**
```java
// Using wait/notify
class ProducerConsumerWaitNotify {
    private final List<Integer> buffer = new ArrayList<>();
    private final int capacity = 5;
    
    public synchronized void produce(int item) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait(); // Buffer full, wait for consumer
        }
        
        buffer.add(item);
        System.out.println("Produced: " + item);
        notifyAll(); // Notify waiting consumers
    }
    
    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Buffer empty, wait for producer
        }
        
        int item = buffer.remove(0);
        System.out.println("Consumed: " + item);
        notifyAll(); // Notify waiting producers
        return item;
    }
}

// Using BlockingQueue (recommended)
class ProducerConsumerBlockingQueue {
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
    
    public void produce(int item) throws InterruptedException {
        queue.put(item); // Blocks if queue is full
        System.out.println("Produced: " + item);
    }
    
    public int consume() throws InterruptedException {
        int item = queue.take(); // Blocks if queue is empty
        System.out.println("Consumed: " + item);
        return item;
    }
}

// Producer and Consumer classes
class Producer implements Runnable {
    private ProducerConsumerBlockingQueue pc;
    
    public Producer(ProducerConsumerBlockingQueue pc) {
        this.pc = pc;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                pc.produce(i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private ProducerConsumerBlockingQueue pc;
    
    public Consumer(ProducerConsumerBlockingQueue pc) {
        this.pc = pc;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                pc.consume();
                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### Q5: Thread Pool and Executor Framework (L&T Infotech, Mindtree)
**Answer:**
```java
// Different types of thread pools
class ThreadPoolExample {
    
    public void demonstrateThreadPools() {
        // 1. Fixed Thread Pool
        ExecutorService fixedPool = Executors.newFixedThreadPool(4);
        
        // 2. Cached Thread Pool (creates threads as needed)
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        
        // 3. Single Thread Executor
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        
        // 4. Scheduled Thread Pool
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        
        // Submit tasks
        for (int i = 0; i < 10; i++) {
            final int taskNumber = i;
            fixedPool.submit(() -> {
                System.out.println("Task " + taskNumber + " executed by " + 
                                 Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Scheduled task
        scheduledPool.scheduleAtFixedRate(() -> {
            System.out.println("Scheduled task at " + new Date());
        }, 0, 5, TimeUnit.SECONDS);
        
        // Proper shutdown
        fixedPool.shutdown();
        try {
            if (!fixedPool.awaitTermination(60, TimeUnit.SECONDS)) {
                fixedPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            fixedPool.shutdownNow();
        }
    }
    
    // Custom ThreadPoolExecutor
    public void customThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                                    // Core pool size
            4,                                    // Maximum pool size
            60L, TimeUnit.SECONDS,               // Keep alive time
            new LinkedBlockingQueue<>(100),      // Work queue
            new ThreadFactory() {                // Custom thread factory
                private int threadNumber = 1;
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "CustomWorker-" + threadNumber++);
                    t.setDaemon(false);
                    return t;
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy() // Rejection policy
        );
        
        // Submit tasks
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
    }
}

// CompletableFuture for asynchronous programming
class CompletableFutureExample {
    
    public void asyncProgramming() {
        // Simple async task
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "World";
        });
        
        // Combine results
        CompletableFuture<String> combined = future1.thenCombine(future2, 
            (result1, result2) -> result1 + " " + result2);
        
        // Chain operations
        CompletableFuture<String> chained = combined
            .thenApply(String::toUpperCase)
            .thenApply(s -> s + "!")
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " From Java"));
        
        // Handle completion
        chained.thenAccept(System.out::println);
        
        // Wait for completion
        try {
            String result = chained.get(5, TimeUnit.SECONDS);
            System.out.println("Final result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Parallel processing with CompletableFuture
    public void parallelProcessing() {
        List<String> urls = Arrays.asList(
            "http://example1.com", "http://example2.com", "http://example3.com"
        );
        
        List<CompletableFuture<String>> futures = urls.stream()
            .map(url -> CompletableFuture.supplyAsync(() -> downloadContent(url)))
            .collect(Collectors.toList());
        
        // Wait for all to complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0]));
        
        CompletableFuture<List<String>> allResults = allFutures.thenApply(v ->
            futures.stream()
                   .map(CompletableFuture::join)
                   .collect(Collectors.toList())
        );
        
        allResults.thenAccept(results -> {
            System.out.println("All downloads completed:");
            results.forEach(System.out::println);
        });
    }
    
    private String downloadContent(String url) {
        try {
            Thread.sleep(1000); // Simulate download
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Content from " + url;
    }
}
```

### Q6: volatile keyword and memory model (IBM, Oracle)
**Answer:**
```java
class VolatileExample {
    private volatile boolean running = true;  // volatile ensures visibility
    private int counter = 0;
    
    public void start() {
        new Thread(() -> {
            while (running) {
                counter++;
                // Without volatile on 'running', this loop might never end
                // even if another thread sets running = false
            }
            System.out.println("Thread stopped. Counter: " + counter);
        }).start();
    }
    
    public void stop() {
        running = false;  // This change is immediately visible to other threads
    }
}

// volatile vs synchronized
class VolatileVsSynchronized {
    private volatile int volatileVar = 0;
    private int synchronizedVar = 0;
    
    // volatile - only ensures visibility, not atomicity
    public void incrementVolatile() {
        volatileVar++;  // Not atomic! Read-modify-write operation
    }
    
    // synchronized - ensures both visibility and atomicity
    public synchronized void incrementSynchronized() {
        synchronizedVar++;  // Atomic operation
    }
    
    // Atomic operations for better performance
    private AtomicInteger atomicVar = new AtomicInteger(0);
    
    public void incrementAtomic() {
        atomicVar.incrementAndGet();  // Atomic and high-performance
    }
}

// Double-checked locking with volatile
class Singleton {
    private static volatile Singleton instance;  // volatile is crucial here
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {                    // First check (no locking)
            synchronized (Singleton.class) {      // Enter critical section
                if (instance == null) {           // Second check (with locking)
                    instance = new Singleton();   // Create instance
                }
            }
        }
        return instance;
    }
}
```

### Q7: Atomic classes and CAS operations (Amazon, Microsoft)
**Answer:**
```java
class AtomicExample {
    private AtomicInteger atomicInt = new AtomicInteger(0);
    private AtomicReference<String> atomicRef = new AtomicReference<>("initial");
    private AtomicBoolean atomicBool = new AtomicBoolean(false);
    
    public void demonstrateAtomicOperations() {
        // Basic operations
        int oldValue = atomicInt.getAndIncrement();  // Returns old value, then increments
        int newValue = atomicInt.incrementAndGet();  // Increments, then returns new value
        
        // Compare and Swap (CAS)
        boolean success = atomicInt.compareAndSet(5, 10);  // If value is 5, set to 10
        
        // Atomic reference operations
        String oldRef = atomicRef.getAndSet("new value");
        boolean refSuccess = atomicRef.compareAndSet("new value", "newer value");
        
        // Update with function
        int result = atomicInt.updateAndGet(x -> x * 2);
        int accumulate = atomicInt.accumulateAndGet(5, Integer::sum);
        
        System.out.println("Final atomic int: " + atomicInt.get());
        System.out.println("Final atomic ref: " + atomicRef.get());
    }
    
    // Lock-free counter implementation
    class LockFreeCounter {
        private AtomicInteger count = new AtomicInteger(0);
        
        public int increment() {
            return count.incrementAndGet();
        }
        
        public int decrement() {
            return count.decrementAndGet();
        }
        
        public int get() {
            return count.get();
        }
        
        // Custom atomic operation
        public int multiplyBy(int factor) {
            int current, updated;
            do {
                current = count.get();
                updated = current * factor;
            } while (!count.compareAndSet(current, updated));
            
            return updated;
        }
    }
    
    // Atomic field updaters for better memory efficiency
    class OptimizedClass {
        private volatile int value;
        private static final AtomicIntegerFieldUpdater<OptimizedClass> updater =
            AtomicIntegerFieldUpdater.newUpdater(OptimizedClass.class, "value");
        
        public int incrementValue() {
            return updater.incrementAndGet(this);
        }
        
        public boolean compareAndSetValue(int expect, int update) {
            return updater.compareAndSet(this, expect, update);
        }
    }
}

// ABA problem demonstration and solution
class ABAExample {
    private AtomicReference<Node> head = new AtomicReference<>();
    
    static class Node {
        int value;
        Node next;
        
        Node(int value) {
            this.value = value;
        }
    }
    
    // Potential ABA problem
    public void problematicOperation() {
        Node current = head.get();          // Read A
        // ... some other thread changes A to B then back to A
        // This CAS might succeed even though the structure changed
        head.compareAndSet(current, current.next);
    }
    
    // Solution using AtomicStampedReference
    private AtomicStampedReference<Node> stampedHead = 
        new AtomicStampedReference<>(null, 0);
    
    public void safeOperation() {
        int[] stamp = new int[1];
        Node current = stampedHead.get(stamp);
        int currentStamp = stamp[0];
        
        // ... perform operation
        
        // CAS with stamp - fails if stamp changed
        boolean success = stampedHead.compareAndSet(
            current, current.next, currentStamp, currentStamp + 1);
    }
}
```

### Q8: Thread communication: wait, notify, notifyAll (Google, Adobe)
**Answer:**
```java
class ThreadCommunication {
    private final Object lock = new Object();
    private boolean condition = false;
    
    // Producer thread
    public void producer() {
        synchronized (lock) {
            System.out.println("Producer: Working...");
            
            // Produce something
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            
            condition = true;
            System.out.println("Producer: Work done, notifying consumer");
            lock.notify();  // Wake up waiting consumer
        }
    }
    
    // Consumer thread
    public void consumer() {
        synchronized (lock) {
            while (!condition) {
                try {
                    System.out.println("Consumer: Waiting for condition...");
                    lock.wait();  // Release lock and wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.println("Consumer: Condition met, consuming...");
            // Consume the resource
        }
    }
    
    // Multiple workers coordination
    class WorkerCoordination {
        private final Object lock = new Object();
        private int completedTasks = 0;
        private final int totalTasks = 5;
        
        public void worker(int workerId) {
            // Do some work
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            
            synchronized (lock) {
                completedTasks++;
                System.out.println("Worker " + workerId + " completed task. " +
                                 "Total completed: " + completedTasks);
                
                if (completedTasks == totalTasks) {
                    lock.notifyAll();  // Wake up all waiting threads
                }
            }
        }
        
        public void coordinator() {
            synchronized (lock) {
                while (completedTasks < totalTasks) {
                    try {
                        System.out.println("Coordinator: Waiting for all tasks to complete...");
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                
                System.out.println("Coordinator: All tasks completed!");
            }
        }
    }
}

// Modern alternative using CountDownLatch
class ModernCoordination {
    private final CountDownLatch latch = new CountDownLatch(5);
    
    public void worker(int workerId) {
        try {
            // Do work
            Thread.sleep(1000);
            System.out.println("Worker " + workerId + " completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();  // Signal completion
        }
    }
    
    public void coordinator() {
        try {
            System.out.println("Coordinator: Waiting for all workers...");
            latch.await();  // Wait for all workers to complete
            System.out.println("Coordinator: All workers completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### Q9: Concurrent Collections (Flipkart, Paytm)
**Answer:**
```java
class ConcurrentCollectionsExample {
    
    public void demonstrateConcurrentCollections() {
        // ConcurrentHashMap - Thread-safe HashMap
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        
        // Safe concurrent operations
        concurrentMap.put("key1", 1);
        concurrentMap.putIfAbsent("key2", 2);  // Atomic operation
        
        // Atomic update operations
        concurrentMap.compute("key1", (key, value) -> value == null ? 1 : value + 1);
        concurrentMap.merge("key3", 1, Integer::sum);
        
        // Bulk operations
        concurrentMap.forEach((key, value) -> 
            System.out.println(key + ": " + value));
        
        // CopyOnWriteArrayList - Thread-safe for read-heavy scenarios
        CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("item1");
        copyOnWriteList.add("item2");
        
        // Safe iteration even during modification
        for (String item : copyOnWriteList) {
            System.out.println(item);
            // Another thread can safely add/remove items
        }
        
        // BlockingQueue implementations
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<>();
        
        // Producer-consumer with BlockingQueue
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    arrayBlockingQueue.put("Item " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        new Thread(() -> {
            try {
                while (true) {
                    String item = arrayBlockingQueue.take();
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        // ConcurrentSkipListMap - Sorted concurrent map
        ConcurrentSkipListMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put(3, "three");
        skipListMap.put(1, "one");
        skipListMap.put(2, "two");
        
        // Always sorted
        skipListMap.forEach((key, value) -> 
            System.out.println(key + ": " + value));
    }
    
    // Performance comparison
    public void performanceComparison() {
        int numThreads = 10;
        int operationsPerThread = 100000;
        
        // Test HashMap vs ConcurrentHashMap
        Map<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        
        // ConcurrentHashMap test
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    concurrentMap.put(threadId * operationsPerThread + j, j);
                }
            });
            threads.add(thread);
            thread.start();
        }
        
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        long concurrentTime = System.currentTimeMillis() - startTime;
        System.out.println("ConcurrentHashMap time: " + concurrentTime + "ms");
        System.out.println("Final size: " + concurrentMap.size());
    }
}
```

### Q10: ThreadLocal and memory leaks (Swiggy, Zomato)
**Answer:**
```java
class ThreadLocalExample {
    // Simple ThreadLocal
    private static final ThreadLocal<String> threadLocalString = new ThreadLocal<>();
    
    // ThreadLocal with initial value
    private static final ThreadLocal<Integer> threadLocalInteger = 
        ThreadLocal.withInitial(() -> 0);
    
    // ThreadLocal with custom class
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();
    
    static class UserContext {
        private String userId;
        private String sessionId;
        
        public UserContext(String userId, String sessionId) {
            this.userId = userId;
            this.sessionId = sessionId;
        }
        
        // getters and setters
        public String getUserId() { return userId; }
        public String getSessionId() { return sessionId; }
    }
    
    public void demonstrateThreadLocal() {
        // Set values for current thread
        threadLocalString.set("Thread-" + Thread.currentThread().getId());
        threadLocalInteger.set(100);
        userContext.set(new UserContext("user123", "session456"));
        
        // Get values (only for current thread)
        System.out.println("String: " + threadLocalString.get());
        System.out.println("Integer: " + threadLocalInteger.get());
        System.out.println("User ID: " + userContext.get().getUserId());
        
        // Each thread has its own copy
        new Thread(() -> {
            threadLocalString.set("Different-Thread");
            System.out.println("Other thread string: " + threadLocalString.get());
            
            // Clean up to prevent memory leaks
            threadLocalString.remove();
        }).start();
    }
    
    // Memory leak prevention
    public void properCleanup() {
        try {
            // Use ThreadLocal
            threadLocalString.set("some value");
            userContext.set(new UserContext("user", "session"));
            
            // ... do work
            
        } finally {
            // Always clean up in finally block
            threadLocalString.remove();
            userContext.remove();
        }
    }
    
    // ThreadLocal in web applications
    class WebRequestContext {
        private static final ThreadLocal<HttpRequest> currentRequest = new ThreadLocal<>();
        
        public static void setRequest(HttpRequest request) {
            currentRequest.set(request);
        }
        
        public static HttpRequest getRequest() {
            return currentRequest.get();
        }
        
        public static void clear() {
            currentRequest.remove();  // Prevent memory leaks
        }
        
        // Dummy HttpRequest class
        static class HttpRequest {
            private String url;
            private String method;
            
            public HttpRequest(String url, String method) {
                this.url = url;
                this.method = method;
            }
            
            public String getUrl() { return url; }
            public String getMethod() { return method; }
        }
    }
    
    // Custom ThreadLocal with automatic cleanup
    static class AutoCleanupThreadLocal<T> extends ThreadLocal<T> {
        private final Set<AutoCleanupThreadLocal<T>> allInstances = 
            Collections.newSetFromMap(new WeakHashMap<>());
        
        public AutoCleanupThreadLocal() {
            allInstances.add(this);
        }
        
        @Override
        public void set(T value) {
            super.set(value);
            // Register for cleanup when thread terminates
        }
        
        public static void cleanupAll() {
            // Method to clean up all ThreadLocal instances
            // Can be called from servlet filters, etc.
        }
    }
    
    // InheritableThreadLocal example
    private static final InheritableThreadLocal<String> inheritableThreadLocal = 
        new InheritableThreadLocal<>();
    
    public void demonstrateInheritableThreadLocal() {
        inheritableThreadLocal.set("Parent thread value");
        
        Thread childThread = new Thread(() -> {
            // Child thread inherits parent's value
            System.out.println("Child thread value: " + inheritableThreadLocal.get());
            
            // Child can modify its own copy
            inheritableThreadLocal.set("Child thread value");
            System.out.println("Modified child value: " + inheritableThreadLocal.get());
        });
        
        childThread.start();
        
        try {
            childThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Parent value unchanged
        System.out.println("Parent thread value: " + inheritableThreadLocal.get());
    }
}
```

## 🔥 Advanced Interview Questions

### Q11: ForkJoinPool and parallel processing (BYJU'S, Unacademy)
**Answer:**
```java
class ForkJoinExample {
    
    // Recursive task for divide-and-conquer
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1000;
        private int[] array;
        private int start, end;
        
        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // Small enough to compute directly
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Split into subtasks
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);
                
                // Fork left task (submit to pool)
                leftTask.fork();
                
                // Compute right task directly
                Long rightResult = rightTask.compute();
                
                // Join left task result
                Long leftResult = leftTask.join();
                
                return leftResult + rightResult;
            }
        }
    }
    
    public void parallelSum() {
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        
        ForkJoinPool pool = new ForkJoinPool();
        
        long startTime = System.currentTimeMillis();
        Long result = pool.invoke(new SumTask(array, 0, array.length));
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Parallel sum: " + result);
        System.out.println("Parallel time: " + parallelTime + "ms");
        
        // Sequential comparison
        startTime = System.currentTimeMillis();
        long sequentialSum = 0;
        for (int value : array) {
            sequentialSum += value;
        }
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Sequential sum: " + sequentialSum);
        System.out.println("Sequential time: " + sequentialTime + "ms");
        
        pool.shutdown();
    }
    
    // Parallel streams use ForkJoinPool internally
    public void parallelStreams() {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000)
                                        .boxed()
                                        .collect(Collectors.toList());
        
        // Sequential processing
        long sequentialSum = numbers.stream()
                                   .mapToLong(Integer::longValue)
                                   .sum();
        
        // Parallel processing
        long parallelSum = numbers.parallelStream()
                                 .mapToLong(Integer::longValue)
                                 .sum();
        
        System.out.println("Sequential: " + sequentialSum);
        System.out.println("Parallel: " + parallelSum);
        
        // Custom parallel processing
        ForkJoinPool customPool = new ForkJoinPool(4);
        try {
            Long customResult = customPool.submit(() ->
                numbers.parallelStream()
                       .mapToLong(Integer::longValue)
                       .sum()
            ).get();
            
            System.out.println("Custom pool result: " + customResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            customPool.shutdown();
        }
    }
}
```

### Q12: Lock-free programming and CAS (Freshworks, Razorpay)
**Answer:**
```java
class LockFreeProgramming {
    
    // Lock-free stack implementation
    static class LockFreeStack<T> {
        private final AtomicReference<Node<T>> head = new AtomicReference<>();
        
        private static class Node<T> {
            final T data;
            final Node<T> next;
            
            Node(T data, Node<T> next) {
                this.data = data;
                this.next = next;
            }
        }
        
        public void push(T item) {
            Node<T> newNode;
            Node<T> currentHead;
            
            do {
                currentHead = head.get();
                newNode = new Node<>(item, currentHead);
            } while (!head.compareAndSet(currentHead, newNode));
        }
        
        public T pop() {
            Node<T> currentHead;
            Node<T> newHead;
            
            do {
                currentHead = head.get();
                if (currentHead == null) {
                    return null;  // Empty stack
                }
                newHead = currentHead.next;
            } while (!head.compareAndSet(currentHead, newHead));
            
            return currentHead.data;
        }
        
        public boolean isEmpty() {
            return head.get() == null;
        }
    }
    
    // Lock-free queue implementation (simplified)
    static class LockFreeQueue<T> {
        private final AtomicReference<Node<T>> head;
        private final AtomicReference<Node<T>> tail;
        
        private static class Node<T> {
            volatile T data;
            volatile AtomicReference<Node<T>> next;
            
            Node(T data) {
                this.data = data;
                this.next = new AtomicReference<>(null);
            }
        }
        
        public LockFreeQueue() {
            Node<T> dummy = new Node<>(null);
            head = new AtomicReference<>(dummy);
            tail = new AtomicReference<>(dummy);
        }
        
        public void enqueue(T item) {
            Node<T> newNode = new Node<>(item);
            
            while (true) {
                Node<T> currentTail = tail.get();
                Node<T> tailNext = currentTail.next.get();
                
                if (currentTail == tail.get()) {  // Consistent state
                    if (tailNext == null) {
                        // Try to link new node
                        if (currentTail.next.compareAndSet(null, newNode)) {
                            // Successfully linked, try to advance tail
                            tail.compareAndSet(currentTail, newNode);
                            break;
                        }
                    } else {
                        // Help advance tail
                        tail.compareAndSet(currentTail, tailNext);
                    }
                }
            }
        }
        
        public T dequeue() {
            while (true) {
                Node<T> currentHead = head.get();
                Node<T> currentTail = tail.get();
                Node<T> headNext = currentHead.next.get();
                
                if (currentHead == head.get()) {  // Consistent state
                    if (currentHead == currentTail) {
                        if (headNext == null) {
                            return null;  // Empty queue
                        }
                        // Help advance tail
                        tail.compareAndSet(currentTail, headNext);
                    } else {
                        if (headNext == null) {
                            continue;  // Inconsistent state, retry
                        }
                        
                        T data = headNext.data;
                        if (head.compareAndSet(currentHead, headNext)) {
                            return data;
                        }
                    }
                }
            }
        }
    }
    
    // Performance comparison: Lock-free vs Synchronized
    public void performanceComparison() {
        LockFreeStack<Integer> lockFreeStack = new LockFreeStack<>();
        Stack<Integer> synchronizedStack = new Stack<>();
        
        int numThreads = 4;
        int operationsPerThread = 100000;
        
        // Test lock-free stack
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    lockFreeStack.push(j);
                    lockFreeStack.pop();
                }
            });
            threads.add(thread);
            thread.start();
        }
        
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        long lockFreeTime = System.currentTimeMillis() - startTime;
        
        // Test synchronized stack
        startTime = System.currentTimeMillis();
        threads.clear();
        
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    synchronized (synchronizedStack) {
                        synchronizedStack.push(j);
                        if (!synchronizedStack.isEmpty()) {
                            synchronizedStack.pop();
                        }
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
        
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        long synchronizedTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Lock-free time: " + lockFreeTime + "ms");
        System.out.println("Synchronized time: " + synchronizedTime + "ms");
        System.out.println("Speedup: " + (double)synchronizedTime / lockFreeTime + "x");
    }
}
```

### Q13: Thread-safe singleton patterns (Ola, Uber)
**Answer:**
```java
class ThreadSafeSingletons {
    
    // 1. Synchronized method (simple but slower)
    static class SynchronizedSingleton {
        private static SynchronizedSingleton instance;
        
        private SynchronizedSingleton() {}
        
        public static synchronized SynchronizedSingleton getInstance() {
            if (instance == null) {
                instance = new SynchronizedSingleton();
            }
            return instance;
        }
    }
    
    // 2. Double-checked locking (efficient)
    static class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton instance;
        
        private DoubleCheckedSingleton() {}
        
        public static DoubleCheckedSingleton getInstance() {
            if (instance == null) {                    // First check
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) {           // Second check
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }
    
    // 3. Initialization-on-demand holder (lazy + thread-safe)
    static class HolderSingleton {
        private HolderSingleton() {}
        
        private static class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }
        
        public static HolderSingleton getInstance() {
            return Holder.INSTANCE;  // Thread-safe due to class loading mechanism
        }
    }
    
    // 4. Enum singleton (best approach)
    enum EnumSingleton {
        INSTANCE;
        
        public void doSomething() {
            System.out.println("Doing something...");
        }
        
        // Thread-safe, serialization-safe, reflection-proof
    }
    
    // 5. AtomicReference approach
    static class AtomicSingleton {
        private static final AtomicReference<AtomicSingleton> INSTANCE = 
            new AtomicReference<>();
        
        private AtomicSingleton() {}
        
        public static AtomicSingleton getInstance() {
            while (true) {
                AtomicSingleton current = INSTANCE.get();
                if (current != null) {
                    return current;
                }
                
                AtomicSingleton newInstance = new AtomicSingleton();
                if (INSTANCE.compareAndSet(null, newInstance)) {
                    return newInstance;
                }
                // If CAS failed, another thread created instance, retry
            }
        }
    }
    
    // 6. Thread-safe with lazy initialization using supplier
    static class SupplierSingleton {
        private static final Supplier<SupplierSingleton> INSTANCE_SUPPLIER = 
            Suppliers.memoize(SupplierSingleton::new);
        
        private SupplierSingleton() {}
        
        public static SupplierSingleton getInstance() {
            return INSTANCE_SUPPLIER.get();
        }
        
        // Simple memoizing supplier implementation
        static class Suppliers {
            public static <T> Supplier<T> memoize(Supplier<T> supplier) {
                return new MemoizingSupplier<>(supplier);
            }
        }
        
        static class MemoizingSupplier<T> implements Supplier<T> {
            private final Supplier<T> delegate;
            private volatile T value;
            
            MemoizingSupplier(Supplier<T> delegate) {
                this.delegate = delegate;
            }
            
            @Override
            public T get() {
                T result = value;
                if (result == null) {
                    synchronized (this) {
                        result = value;
                        if (result == null) {
                            value = result = delegate.get();
                        }
                    }
                }
                return result;
            }
        }
    }
    
    // Performance test
    public void testSingletonPerformance() {
        int numThreads = 10;
        int operationsPerThread = 1000000;
        
        // Test double-checked locking
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    DoubleCheckedSingleton.getInstance();
                }
            });
            threads.add(thread);
            thread.start();
        }
        
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        long doubleCheckedTime = System.currentTimeMillis() - startTime;
        
        // Test synchronized method
        startTime = System.currentTimeMillis();
        threads.clear();
        
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    SynchronizedSingleton.getInstance();
                }
            });
            threads.add(thread);
            thread.start();
        }
        
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        long synchronizedTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Double-checked locking: " + doubleCheckedTime + "ms");
        System.out.println("Synchronized method: " + synchronizedTime + "ms");
        System.out.println("Speedup: " + (double)synchronizedTime / doubleCheckedTime + "x");
    }
}
```

## 💡 Quick Tips for Coding Rounds

1. **Use thread pools** instead of creating threads manually
2. **Prefer CompletableFuture** over raw threading for async tasks
3. **Use concurrent collections** for thread-safe operations
4. **Always clean up ThreadLocal** to prevent memory leaks
5. **Consider lock-free alternatives** for high-performance scenarios

## 🎯 Practice Problems

1. Implement dining philosophers problem
2. Create thread-safe cache with TTL
3. Build rate limiter using multithreading
4. Implement parallel merge sort using ForkJoinPool
5. Create thread-safe event bus with subscribers

## 📝 Concurrency Best Practices

```java
// 1. Always use thread-safe collections in concurrent environments
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();

// 2. Use atomic operations for simple counters
AtomicInteger counter = new AtomicInteger(0);

// 3. Prefer CompletableFuture for async operations
CompletableFuture.supplyAsync(() -> expensiveOperation())
                 .thenApply(this::processResult)
                 .thenAccept(System.out::println);

// 4. Use proper exception handling in threads
Thread thread = new Thread(() -> {
    try {
        riskyOperation();
    } catch (Exception e) {
        logger.error("Error in thread", e);
    }
});

// 5. Always shutdown thread pools
ExecutorService executor = Executors.newFixedThreadPool(4);
try {
    // Submit tasks
} finally {
    executor.shutdown();
    try {
        if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    } catch (InterruptedException e) {
        executor.shutdownNow();
    }
}
```

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy