# Java Exception Handling - Placement Revision Notes

## 📚 Core Concepts

### Exception Hierarchy
```
Throwable
├── Error (Unchecked)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── VirtualMachineError
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── IllegalArgumentException
    │   └── NumberFormatException
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        ├── ClassNotFoundException
        └── InterruptedException
```

### Exception Handling Keywords
- **try**: Block containing risky code
- **catch**: Handle specific exceptions
- **finally**: Always executes (cleanup code)
- **throw**: Manually throw exception
- **throws**: Declare exceptions method might throw

### Basic Syntax
```java
try {
    // Risky code
} catch (SpecificException e) {
    // Handle specific exception
} catch (Exception e) {
    // Handle general exception
} finally {
    // Cleanup code (always executes)
}
```

## 🎯 Key Points for Interviews

1. **Checked vs Unchecked**: Compile-time vs Runtime exceptions
2. **Exception Propagation**: How exceptions move up the call stack
3. **Custom Exceptions**: Creating application-specific exceptions
4. **Best Practices**: Specific catch blocks, proper resource cleanup
5. **Performance**: Exception handling overhead

## 💼 Placement Questions & Answers

### Q1: Difference between Checked and Unchecked exceptions (TCS, Infosys)
**Answer:**
| Feature | Checked Exception | Unchecked Exception |
|---------|------------------|-------------------|
| Compile-time handling | Must handle or declare | Optional handling |
| Inheritance | Extends Exception | Extends RuntimeException |
| Examples | IOException, SQLException | NullPointerException, ArrayIndexOutOfBoundsException |
| Recovery | Usually recoverable | Usually programming errors |
| Performance | Better (known at compile-time) | Can impact performance |

```java
// Checked Exception - Must handle
public void readFile(String fileName) throws IOException {
    FileReader file = new FileReader(fileName); // IOException possible
}

// Unchecked Exception - Optional handling
public void divide(int a, int b) {
    int result = a / b; // ArithmeticException possible but not mandatory to handle
}
```

### Q2: Exception vs Error (Wipro, Accenture)
**Answer:**
| Feature | Exception | Error |
|---------|-----------|-------|
| Recovery | Can be handled and recovered | Usually unrecoverable |
| Cause | Application logic issues | JVM/System issues |
| Handling | Should be caught and handled | Should not be caught |
| Examples | IOException, SQLException | OutOfMemoryError, StackOverflowError |

```java
// Exception - Should handle
try {
    int result = Integer.parseInt("abc");
} catch (NumberFormatException e) {
    System.out.println("Invalid number format");
}

// Error - Don't catch, fix the root cause
public void recursiveMethod() {
    recursiveMethod(); // Eventually causes StackOverflowError
}
```

### Q3: finally block execution scenarios (Cognizant, HCL)
**Answer:**
```java
// Scenario 1: Normal execution
public int test1() {
    try {
        return 1;
    } finally {
        System.out.println("Finally executed"); // Executes before return
    }
}

// Scenario 2: Exception thrown
public int test2() {
    try {
        int result = 1/0; // ArithmeticException
        return 1;
    } catch (ArithmeticException e) {
        return 2;
    } finally {
        System.out.println("Finally executed"); // Always executes
    }
}

// Scenario 3: Finally overrides return value (Avoid this!)
public int test3() {
    try {
        return 1;
    } finally {
        return 2; // Bad practice - overrides try block return
    }
    // Returns 2, not 1
}

// Scenario 4: Finally doesn't execute
public int test4() {
    try {
        System.exit(0); // JVM terminates
        return 1;
    } finally {
        System.out.println("Won't execute"); // Never executes
    }
}
```

**Finally block doesn't execute only in these cases:**
- System.exit() is called
- JVM crashes
- Infinite loop in try/catch block
- Thread is killed

### Q4: throw vs throws (Tech Mahindra, Capgemini)
**Answer:**
| Feature | throw | throws |
|---------|-------|--------|
| Purpose | Explicitly throw exception | Declare possible exceptions |
| Usage | Inside method body | Method signature |
| Number | Can throw one exception at a time | Can declare multiple exceptions |
| Type | Instance of Throwable | Class names |

```java
// throws - Declaration
public void readFile(String fileName) throws IOException, FileNotFoundException {
    if (fileName == null) {
        throw new IllegalArgumentException("File name cannot be null"); // throw
    }
    
    FileReader file = new FileReader(fileName); // May throw FileNotFoundException
    // File operations that may throw IOException
}

// Custom exception throwing
public void validateAge(int age) throws InvalidAgeException {
    if (age < 0 || age > 150) {
        throw new InvalidAgeException("Age must be between 0 and 150"); // throw
    }
}
```

### Q5: Custom Exception creation (L&T Infotech, Mindtree)
**Answer:**
```java
// Custom Checked Exception
class InvalidAccountException extends Exception {
    private String accountNumber;
    
    public InvalidAccountException(String message) {
        super(message);
    }
    
    public InvalidAccountException(String message, String accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }
    
    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}

// Custom Unchecked Exception
class InsufficientFundsException extends RuntimeException {
    private double requestedAmount;
    private double availableBalance;
    
    public InsufficientFundsException(String message, double requested, double available) {
        super(message);
        this.requestedAmount = requested;
        this.availableBalance = available;
    }
    
    public double getRequestedAmount() { return requestedAmount; }
    public double getAvailableBalance() { return availableBalance; }
}

// Usage
public class BankAccount {
    private double balance;
    private String accountNumber;
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds for withdrawal", amount, balance);
        }
        balance -= amount;
    }
    
    public void validateAccount() throws InvalidAccountException {
        if (accountNumber == null || accountNumber.length() != 10) {
            throw new InvalidAccountException("Invalid account number", accountNumber);
        }
    }
}
```

### Q6: Exception chaining and cause (IBM, Oracle)
**Answer:**
```java
public class ExceptionChaining {
    
    public void method1() throws CustomException {
        try {
            method2();
        } catch (SQLException e) {
            // Chain the original exception
            throw new CustomException("Database operation failed", e);
        }
    }
    
    public void method2() throws SQLException {
        try {
            // Database operation
            throw new SQLException("Connection timeout");
        } catch (SQLException e) {
            // Log and re-throw
            System.err.println("SQL Error: " + e.getMessage());
            throw e;
        }
    }
    
    public void demonstrateChaining() {
        try {
            method1();
        } catch (CustomException e) {
            System.out.println("Main exception: " + e.getMessage());
            
            Throwable cause = e.getCause();
            if (cause != null) {
                System.out.println("Root cause: " + cause.getMessage());
            }
            
            // Print full stack trace
            e.printStackTrace();
        }
    }
}

class CustomException extends Exception {
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Q7: Try-with-resources (Java 7+) (Amazon, Microsoft)
**Answer:**
```java
// Old way - Manual resource management
public void readFileOldWay() {
    FileReader file = null;
    BufferedReader reader = null;
    
    try {
        file = new FileReader("test.txt");
        reader = new BufferedReader(file);
        String line = reader.readLine();
        System.out.println(line);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (reader != null) reader.close();
            if (file != null) file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// New way - Try-with-resources (Automatic resource management)
public void readFileNewWay() {
    try (FileReader file = new FileReader("test.txt");
         BufferedReader reader = new BufferedReader(file)) {
        
        String line = reader.readLine();
        System.out.println(line);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    // Resources automatically closed, even if exception occurs
}

// Custom AutoCloseable resource
class CustomResource implements AutoCloseable {
    private String name;
    
    public CustomResource(String name) {
        this.name = name;
        System.out.println("Opening " + name);
    }
    
    public void doSomething() throws Exception {
        System.out.println("Working with " + name);
        // throw new Exception("Something went wrong"); // Test exception
    }
    
    @Override
    public void close() throws Exception {
        System.out.println("Closing " + name);
    }
}

// Usage
public void useCustomResource() {
    try (CustomResource resource = new CustomResource("MyResource")) {
        resource.doSomething();
    } catch (Exception e) {
        System.out.println("Caught: " + e.getMessage());
    }
    // close() automatically called
}
```

### Q8: Multiple catch blocks and exception hierarchy (Google, Adobe)
**Answer:**
```java
public void handleMultipleExceptions(String input) {
    try {
        // Multiple potential exceptions
        int number = Integer.parseInt(input);    // NumberFormatException
        int result = 100 / number;               // ArithmeticException
        int[] array = new int[result];           // NegativeArraySizeException
        array[10] = 5;                          // ArrayIndexOutOfBoundsException
        
    } catch (NumberFormatException e) {
        System.out.println("Invalid number format: " + e.getMessage());
        
    } catch (ArithmeticException e) {
        System.out.println("Division by zero: " + e.getMessage());
        
    } catch (NegativeArraySizeException e) {
        System.out.println("Negative array size: " + e.getMessage());
        
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Array index out of bounds: " + e.getMessage());
        
    } catch (RuntimeException e) {
        System.out.println("Other runtime exception: " + e.getMessage());
        
    } catch (Exception e) {
        System.out.println("General exception: " + e.getMessage());
    }
}

// Multi-catch (Java 7+)
public void multiCatch(String input) {
    try {
        int number = Integer.parseInt(input);
        int result = 100 / number;
        
    } catch (NumberFormatException | ArithmeticException e) {
        System.out.println("Number-related error: " + e.getMessage());
        // Note: 'e' is effectively final, cannot be reassigned
        
    } catch (Exception e) {
        System.out.println("Other error: " + e.getMessage());
    }
}

// Important: Order matters - specific to general
public void wrongOrder() {
    try {
        // Some code
    } catch (Exception e) {           // Too general - catches everything
        // Handle
    } catch (IOException e) {         // Unreachable code - compile error!
        // This will never execute
    }
}
```

### Q9: Exception handling best practices (Flipkart, Paytm)
**Answer:**
```java
public class ExceptionBestPractices {
    
    private static final Logger logger = LoggerFactory.getLogger(ExceptionBestPractices.class);
    
    // 1. Be specific with exceptions
    public void goodPractice(String fileName) throws FileNotFoundException, IOException {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            // Process file
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", fileName, e);
            throw e; // Re-throw if caller needs to handle
        } catch (IOException e) {
            logger.error("Error reading file: {}", fileName, e);
            throw new ProcessingException("Failed to process file: " + fileName, e);
        }
    }
    
    // 2. Don't swallow exceptions
    public void badPractice() {
        try {
            riskyOperation();
        } catch (Exception e) {
            // Silent failure - BAD!
        }
    }
    
    public void betterPractice() {
        try {
            riskyOperation();
        } catch (Exception e) {
            logger.error("Error in risky operation", e);
            // Either handle properly or re-throw
            throw new ServiceException("Operation failed", e);
        }
    }
    
    // 3. Use try-with-resources for cleanup
    public String readFileContent(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }
        // Scanner automatically closed
        
        return content.toString();
    }
    
    // 4. Validate inputs early
    public void processData(List<String> data) {
        Objects.requireNonNull(data, "Data list cannot be null");
        
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be empty");
        }
        
        for (String item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Data list cannot contain null items");
            }
            // Process item
        }
    }
    
    // 5. Don't use exceptions for control flow
    public boolean isValidNumber(String input) {
        // BAD - using exception for control flow
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean isValidNumberBetter(String input) {
        // BETTER - check first
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        
        return input.matches("-?\\d+");
    }
    
    private void riskyOperation() throws Exception {
        throw new Exception("Something went wrong");
    }
}

class ProcessingException extends RuntimeException {
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Q10: Exception propagation and stack trace (Swiggy, Zomato)
**Answer:**
```java
public class ExceptionPropagation {
    
    public void method1() {
        System.out.println("method1 start");
        try {
            method2();
        } catch (RuntimeException e) {
            System.out.println("Caught in method1: " + e.getMessage());
            System.out.println("Stack trace:");
            e.printStackTrace();
        }
        System.out.println("method1 end");
    }
    
    public void method2() {
        System.out.println("method2 start");
        method3();
        System.out.println("method2 end"); // Won't execute if exception thrown
    }
    
    public void method3() {
        System.out.println("method3 start");
        method4();
        System.out.println("method3 end"); // Won't execute if exception thrown
    }
    
    public void method4() {
        System.out.println("method4 start");
        throw new RuntimeException("Exception from method4");
        // System.out.println("method4 end"); // Unreachable code
    }
    
    // Stack trace analysis
    public void analyzeStackTrace() {
        try {
            method1();
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            
            System.out.println("Exception details:");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Class: " + e.getClass().getName());
            
            System.out.println("\nStack trace elements:");
            for (StackTraceElement element : stackTrace) {
                System.out.printf("Class: %s, Method: %s, Line: %d%n",
                    element.getClassName(),
                    element.getMethodName(),
                    element.getLineNumber());
            }
        }
    }
    
    // Checked exception propagation
    public void checkedExceptionDemo() throws IOException {
        // Method that throws checked exception must declare it
        readFile("nonexistent.txt");
    }
    
    private void readFile(String fileName) throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        // Read file
    }
}
```

## 🔥 Advanced Interview Questions

### Q11: Exception handling in lambda expressions (BYJU'S, Unacademy)
**Answer:**
```java
public class LambdaExceptionHandling {
    
    // Problem: Checked exceptions in lambdas
    public void problematicLambda() {
        List<String> fileNames = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        
        // This won't compile - lambda doesn't handle IOException
        // fileNames.stream().map(fileName -> Files.readAllLines(Paths.get(fileName)));
    }
    
    // Solution 1: Wrapper method
    public void solution1() {
        List<String> fileNames = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        
        List<List<String>> contents = fileNames.stream()
            .map(this::readFileUnchecked)
            .collect(Collectors.toList());
    }
    
    private List<String> readFileUnchecked(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    // Solution 2: Functional interface with checked exception
    @FunctionalInterface
    interface CheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }
    
    public static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    public void solution2() {
        List<String> fileNames = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        
        List<List<String>> contents = fileNames.stream()
            .map(unchecked(fileName -> Files.readAllLines(Paths.get(fileName))))
            .collect(Collectors.toList());
    }
    
    // Solution 3: Optional for graceful handling
    public void solution3() {
        List<String> fileNames = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        
        List<List<String>> contents = fileNames.stream()
            .map(this::readFileSafely)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
    
    private Optional<List<String>> readFileSafely(String fileName) {
        try {
            return Optional.of(Files.readAllLines(Paths.get(fileName)));
        } catch (IOException e) {
            System.err.println("Failed to read file: " + fileName + " - " + e.getMessage());
            return Optional.empty();
        }
    }
}
```

### Q12: Performance impact of exceptions (Freshworks, Razorpay)
**Answer:**
```java
public class ExceptionPerformance {
    
    // Expensive: Exception creation and stack trace generation
    public void expensiveExceptionDemo() {
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 10000; i++) {
            try {
                throw new RuntimeException("Test exception");
            } catch (RuntimeException e) {
                // Catching exception
            }
        }
        
        long endTime = System.nanoTime();
        System.out.println("Exception handling time: " + (endTime - startTime) / 1_000_000 + " ms");
    }
    
    // Cheap: Normal control flow
    public void normalControlFlow() {
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 10000; i++) {
            if (shouldThrow()) {
                // Normal handling
                handleError();
            }
        }
        
        long endTime = System.nanoTime();
        System.out.println("Normal control flow time: " + (endTime - startTime) / 1_000_000 + " ms");
    }
    
    // Custom exception without stack trace (performance optimization)
    static class LightweightException extends RuntimeException {
        public LightweightException(String message) {
            super(message);
        }
        
        @Override
        public synchronized Throwable fillInStackTrace() {
            // Don't fill stack trace for performance
            return this;
        }
    }
    
    // Exception pooling for high-frequency scenarios
    private static final RuntimeException CACHED_EXCEPTION = 
        new RuntimeException("Cached exception");
    
    public void useCachedException() {
        // For very high-frequency exception scenarios
        throw CACHED_EXCEPTION; // Reuse same exception instance
    }
    
    private boolean shouldThrow() {
        return true;
    }
    
    private void handleError() {
        // Handle error without exception
    }
    
    // Benchmark comparison
    public void performanceBenchmark() {
        int iterations = 100_000;
        
        // Test 1: Exception-based error handling
        long start1 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            try {
                errorProneOperation(true);
            } catch (RuntimeException e) {
                // Handle error
            }
        }
        long time1 = System.nanoTime() - start1;
        
        // Test 2: Return-code based error handling
        long start2 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            ErrorResult result = safeOperation(true);
            if (!result.isSuccess()) {
                // Handle error
            }
        }
        long time2 = System.nanoTime() - start2;
        
        System.out.println("Exception-based: " + time1 / 1_000_000 + " ms");
        System.out.println("Return-code based: " + time2 / 1_000_000 + " ms");
        System.out.println("Exception overhead: " + (time1 - time2) / 1_000_000 + " ms");
    }
    
    private void errorProneOperation(boolean shouldFail) {
        if (shouldFail) {
            throw new RuntimeException("Operation failed");
        }
    }
    
    private ErrorResult safeOperation(boolean shouldFail) {
        if (shouldFail) {
            return new ErrorResult(false, "Operation failed");
        }
        return new ErrorResult(true, null);
    }
    
    static class ErrorResult {
        private final boolean success;
        private final String errorMessage;
        
        public ErrorResult(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }
        
        public boolean isSuccess() { return success; }
        public String getErrorMessage() { return errorMessage; }
    }
}
```

### Q13: Exception handling in concurrent environments (Ola, Uber)
**Answer:**
```java
public class ConcurrentExceptionHandling {
    
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    // Handling exceptions in threads
    public void threadExceptionHandling() {
        // Bad: Exception swallowed
        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("Exception in thread");
        });
        thread1.start(); // Exception lost!
        
        // Good: Custom exception handler
        Thread thread2 = new Thread(() -> {
            throw new RuntimeException("Exception in thread");
        });
        thread2.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Uncaught exception in thread " + t.getName() + ": " + e.getMessage());
            e.printStackTrace();
        });
        thread2.start();
        
        // Global exception handler
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.err.println("Global handler - Thread " + t.getName() + " died: " + e.getMessage());
        });
    }
    
    // Exception handling with CompletableFuture
    public void completableFutureExceptions() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random failure");
            }
            return "Success";
        });
        
        // Handle exceptions
        CompletableFuture<String> handled = future.handle((result, exception) -> {
            if (exception != null) {
                System.err.println("Exception occurred: " + exception.getMessage());
                return "Default value";
            }
            return result;
        });
        
        // Exceptional handling
        CompletableFuture<String> exceptional = future.exceptionally(exception -> {
            System.err.println("Handling exception: " + exception.getMessage());
            return "Fallback value";
        });
        
        // Async exception handling
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                System.err.println("Async error handling: " + exception.getMessage());
            } else {
                System.out.println("Async success: " + result);
            }
        });
    }
    
    // Exception aggregation in parallel processing
    public void parallelExceptionHandling() {
        List<String> items = Arrays.asList("item1", "item2", "item3", "item4");
        List<Exception> exceptions = new ArrayList<>();
        List<String> results = new ArrayList<>();
        
        List<CompletableFuture<Void>> futures = items.stream()
            .map(item -> CompletableFuture.runAsync(() -> {
                try {
                    String result = processItem(item);
                    synchronized (results) {
                        results.add(result);
                    }
                } catch (Exception e) {
                    synchronized (exceptions) {
                        exceptions.add(e);
                    }
                }
            }, executor))
            .collect(Collectors.toList());
        
        // Wait for all to complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0]));
        
        try {
            allFutures.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("Error waiting for completion: " + e.getMessage());
        }
        
        // Process results and exceptions
        System.out.println("Successful results: " + results.size());
        System.out.println("Exceptions: " + exceptions.size());
        
        if (!exceptions.isEmpty()) {
            // Create aggregate exception
            RuntimeException aggregateException = new RuntimeException(
                "Multiple operations failed: " + exceptions.size() + " errors");
            exceptions.forEach(aggregateException::addSuppressed);
            throw aggregateException;
        }
    }
    
    private String processItem(String item) throws Exception {
        if (item.equals("item2")) {
            throw new RuntimeException("Processing failed for " + item);
        }
        return "Processed " + item;
    }
    
    // Circuit breaker pattern for exception handling
    public class CircuitBreaker {
        private int failures = 0;
        private long lastFailureTime = 0;
        private final int threshold = 5;
        private final long timeout = 60000; // 1 minute
        
        public String callService() throws Exception {
            if (isCircuitOpen()) {
                throw new RuntimeException("Circuit breaker is OPEN");
            }
            
            try {
                String result = unreliableService();
                reset();
                return result;
            } catch (Exception e) {
                recordFailure();
                throw e;
            }
        }
        
        private boolean isCircuitOpen() {
            return failures >= threshold && 
                   (System.currentTimeMillis() - lastFailureTime) < timeout;
        }
        
        private void recordFailure() {
            failures++;
            lastFailureTime = System.currentTimeMillis();
        }
        
        private void reset() {
            failures = 0;
            lastFailureTime = 0;
        }
        
        private String unreliableService() throws Exception {
            if (Math.random() > 0.7) {
                throw new Exception("Service unavailable");
            }
            return "Service response";
        }
    }
}
```

## 💡 Quick Tips for Coding Rounds

1. **Always handle specific exceptions** before general ones
2. **Use try-with-resources** for automatic resource management
3. **Don't swallow exceptions** - at minimum log them
4. **Validate inputs early** to prevent exceptions
5. **Use exceptions for exceptional cases**, not control flow

## 🎯 Practice Problems

1. Create a robust file reader with proper exception handling
2. Implement retry mechanism with exponential backoff
3. Design exception hierarchy for banking application
4. Handle exceptions in multi-threaded environment
5. Create circuit breaker pattern implementation

## 📝 Exception Handling Patterns

```java
// Retry pattern with exponential backoff
public class RetryPattern {
    public <T> T executeWithRetry(Supplier<T> operation, int maxRetries) {
        int attempt = 0;
        long delay = 1000; // 1 second
        
        while (attempt < maxRetries) {
            try {
                return operation.get();
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Max retries exceeded", e);
                }
                
                try {
                    Thread.sleep(delay);
                    delay *= 2; // Exponential backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during retry", ie);
                }
            }
        }
        
        throw new RuntimeException("Should not reach here");
    }
}

// Template method for exception handling
public abstract class ExceptionHandlingTemplate {
    
    public final void execute() {
        try {
            beforeExecution();
            doExecute();
            afterExecution();
        } catch (Exception e) {
            handleException(e);
        } finally {
            cleanup();
        }
    }
    
    protected void beforeExecution() {}
    protected abstract void doExecute() throws Exception;
    protected void afterExecution() {}
    protected void handleException(Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
    protected void cleanup() {}
}
```

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy