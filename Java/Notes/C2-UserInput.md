# Java User Input - Placement Revision Notes

## 📚 Core Concepts

### Scanner Class
```java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);
```

### Input Methods
| Method | Description | Example |
|--------|-------------|---------|
| `nextInt()` | Reads integer | `int num = scanner.nextInt();` |
| `nextDouble()` | Reads double | `double val = scanner.nextDouble();` |
| `nextFloat()` | Reads float | `float f = scanner.nextFloat();` |
| `nextLine()` | Reads entire line | `String line = scanner.nextLine();` |
| `next()` | Reads single word | `String word = scanner.next();` |
| `nextBoolean()` | Reads boolean | `boolean flag = scanner.nextBoolean();` |

### BufferedReader Class
```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String input = br.readLine();
```

## 🎯 Key Points for Interviews

1. **Scanner vs BufferedReader**: Performance and usage differences
2. **Exception Handling**: IOException for BufferedReader
3. **Resource Management**: Always close Scanner/BufferedReader
4. **Input Validation**: Check for valid input types
5. **nextLine() Problem**: Buffer issues with mixed input types

## 💼 Placement Questions & Answers

### Q1: Difference between Scanner and BufferedReader (TCS, Infosys)
**Answer:**
| Feature | Scanner | BufferedReader |
|---------|---------|----------------|
| Performance | Slower | Faster |
| Parsing | Built-in methods | Manual parsing needed |
| Exception | No checked exceptions | IOException |
| Buffer Size | Small (1KB) | Large (8KB) |
| Synchronization | Not synchronized | Synchronized |

### Q2: What is the nextLine() problem in Scanner? (Wipro, Accenture)
**Answer:**
```java
Scanner sc = new Scanner(System.in);
System.out.println("Enter number:");
int num = sc.nextInt(); // Doesn't consume newline
System.out.println("Enter name:");
String name = sc.nextLine(); // Reads the leftover newline

// Solution:
int num = sc.nextInt();
sc.nextLine(); // Consume the newline
String name = sc.nextLine();
```

### Q3: How to read multiple integers in one line? (Cognizant, HCL)
**Answer:**
```java
// Method 1: Using Scanner
Scanner sc = new Scanner(System.in);
String line = sc.nextLine();
String[] parts = line.split(" ");
int[] numbers = new int[parts.length];
for(int i = 0; i < parts.length; i++) {
    numbers[i] = Integer.parseInt(parts[i]);
}

// Method 2: Using Scanner with hasNext()
while(sc.hasNextInt()) {
    int num = sc.nextInt();
    // Process number
}
```

### Q4: How to validate user input? (Tech Mahindra, Capgemini)
**Answer:**
```java
Scanner sc = new Scanner(System.in);
int age = 0;
boolean validInput = false;

while(!validInput) {
    try {
        System.out.println("Enter your age:");
        age = sc.nextInt();
        if(age > 0 && age < 150) {
            validInput = true;
        } else {
            System.out.println("Invalid age. Try again.");
        }
    } catch(InputMismatchException e) {
        System.out.println("Invalid input. Enter a number.");
        sc.next(); // Clear invalid input
    }
}
```

### Q5: Reading input without using Scanner or BufferedReader (L&T Infotech, Mindtree)
**Answer:**
```java
// Using System.in directly
import java.io.IOException;

public class DirectInput {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter a character:");
        int ch = System.in.read();
        System.out.println("You entered: " + (char)ch);
    }
}

// Using Console class
import java.io.Console;

Console console = System.console();
if(console != null) {
    String input = console.readLine("Enter text: ");
    char[] password = console.readPassword("Enter password: ");
}
```

### Q6: How to read from file vs console? (IBM, Oracle)
**Answer:**
```java
// Reading from console
Scanner consoleScanner = new Scanner(System.in);

// Reading from file
Scanner fileScanner = new Scanner(new File("input.txt"));

// Can switch source easily
Scanner scanner = new Scanner(args.length > 0 ? 
    new File(args[0]) : System.in);
```

### Q7: What happens if user enters wrong data type? (Amazon, Microsoft)
**Answer:**
```java
Scanner sc = new Scanner(System.in);
try {
    System.out.println("Enter integer:");
    int num = sc.nextInt(); // InputMismatchException if not integer
} catch(InputMismatchException e) {
    System.out.println("Invalid input format");
    sc.next(); // Clear the invalid input from buffer
}
```

### Q8: How to read password securely? (Google, Adobe)
**Answer:**
```java
import java.io.Console;

Console console = System.console();
if(console == null) {
    System.out.println("Console not available");
    return;
}

char[] password = console.readPassword("Enter password: ");
String passwordStr = new String(password);
// Clear the array for security
Arrays.fill(password, ' ');
```

### Q9: Performance comparison of input methods (Flipkart, Paytm)
**Answer:**
**Speed Ranking (Fastest to Slowest):**
1. **BufferedReader** - Fastest for large inputs
2. **Scanner with custom delimiter** 
3. **Scanner with default settings**
4. **System.in.read()** - Character by character

```java
// Fast input template for competitive programming
class FastReader {
    BufferedReader br;
    StringTokenizer st;
    
    public FastReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }
    
    int nextInt() { return Integer.parseInt(next()); }
    long nextLong() { return Long.parseLong(next()); }
    double nextDouble() { return Double.parseDouble(next()); }
}
```

### Q10: How to handle EOF (End of File)? (Swiggy, Zomato)
**Answer:**
```java
// Method 1: Using hasNext()
Scanner sc = new Scanner(System.in);
while(sc.hasNext()) {
    String input = sc.next();
    // Process input
}

// Method 2: Using try-catch
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String line;
try {
    while((line = br.readLine()) != null) {
        // Process line
    }
} catch(IOException e) {
    e.printStackTrace();
}
```

## 🔥 Advanced Interview Questions

### Q11: Custom delimiter in Scanner (BYJU'S, Unacademy)
**Answer:**
```java
Scanner sc = new Scanner("apple,banana,orange");
sc.useDelimiter(",");
while(sc.hasNext()) {
    System.out.println(sc.next());
}

// For multiple delimiters
sc.useDelimiter("[,;\\s]+"); // comma, semicolon, or whitespace
```

### Q12: Reading large files efficiently (Freshworks, Razorpay)
**Answer:**
```java
// For very large files
try(BufferedReader br = Files.newBufferedReader(Paths.get("large.txt"))) {
    String line;
    while((line = br.readLine()) != null) {
        // Process line by line without loading entire file
    }
}

// For structured data
try(Scanner sc = new Scanner(Files.newBufferedReader(Paths.get("data.csv")))) {
    sc.useDelimiter("[,\\r\\n]+");
    while(sc.hasNext()) {
        String field = sc.next();
    }
}
```

### Q13: Thread-safe input reading (Ola, Uber)
**Answer:**
```java
// BufferedReader is synchronized
public class ThreadSafeInput {
    private static final BufferedReader br = 
        new BufferedReader(new InputStreamReader(System.in));
    
    public static synchronized String readLine() throws IOException {
        return br.readLine();
    }
}

// Scanner is not thread-safe
// Need external synchronization for Scanner
```

## 💡 Quick Tips for Coding Rounds

1. **Use BufferedReader** for fast input in competitive programming
2. **Always validate input** in production code
3. **Handle exceptions** properly
4. **Close resources** using try-with-resources
5. **Use hasNext()** methods to avoid exceptions
6. **Clear buffer** after mixed input types

## 🎯 Practice Problems

1. Create a calculator that handles invalid input gracefully
2. Read a matrix of integers from console
3. Implement a program that reads until EOF
4. Parse CSV data from console input
5. Create input validation for email format

## 📝 Common Input Patterns

```java
// Reading array of integers
Scanner sc = new Scanner(System.in);
int n = sc.nextInt();
int[] arr = new int[n];
for(int i = 0; i < n; i++) {
    arr[i] = sc.nextInt();
}

// Reading 2D array
int rows = sc.nextInt();
int cols = sc.nextInt();
int[][] matrix = new int[rows][cols];
for(int i = 0; i < rows; i++) {
    for(int j = 0; j < cols; j++) {
        matrix[i][j] = sc.nextInt();
    }
}

// Reading until specific input
String input;
while(!(input = sc.nextLine()).equals("exit")) {
    // Process input
}
```

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy