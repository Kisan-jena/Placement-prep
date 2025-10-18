# Java String & String Handling - Placement Revision Notes

## 📚 Core Concepts

### String Classes in Java
1. **String** - Immutable
2. **StringBuilder** - Mutable, non-synchronized
3. **StringBuffer** - Mutable, synchronized

### String Creation Methods
```java
// Method 1: String literal (stored in String Pool)
String str1 = "Hello";

// Method 2: Using new keyword (stored in Heap)
String str2 = new String("Hello");

// Method 3: Using char array
char[] chars = {'H', 'e', 'l', 'l', 'o'};
String str3 = new String(chars);
```

### Important String Methods
| Method | Description | Example |
|--------|-------------|---------|
| `length()` | Returns string length | `"Hello".length()` → 5 |
| `charAt(int index)` | Character at index | `"Hello".charAt(1)` → 'e' |
| `substring(int start)` | Substring from start | `"Hello".substring(2)` → "llo" |
| `indexOf(String str)` | First occurrence index | `"Hello".indexOf("l")` → 2 |
| `replace(char old, char new)` | Replace characters | `"Hello".replace('l', 'x')` → "Hexxo" |
| `toLowerCase()` | Convert to lowercase | `"Hello".toLowerCase()` → "hello" |
| `trim()` | Remove leading/trailing spaces | `" Hello ".trim()` → "Hello" |
| `split(String regex)` | Split into array | `"a,b,c".split(",")` → ["a","b","c"] |

## 🎯 Key Points for Interviews

1. **String Immutability**: Once created, cannot be changed
2. **String Pool**: Memory optimization for string literals
3. **Performance**: StringBuilder > StringBuffer > String concatenation
4. **Memory**: String literals in Method Area, new String() in Heap
5. **Comparison**: Use `.equals()` for content, `==` for reference

## 💼 Placement Questions & Answers

### Q1: Why are Strings immutable in Java? (TCS, Infosys)
**Answer:**
1. **String Pool**: Enables string interning for memory efficiency
2. **Security**: Prevents modification of sensitive data (URLs, usernames)
3. **Thread Safety**: Immutable objects are inherently thread-safe
4. **Hashcode Caching**: Hashcode calculated once and cached
5. **Class Loading**: Class names are strings, immutability ensures security

```java
String str1 = "Hello";
String str2 = str1.toUpperCase(); // Creates new string
System.out.println(str1); // Still "Hello" (immutable)
System.out.println(str2); // "HELLO" (new string)
```

### Q2: String vs StringBuilder vs StringBuffer (Wipro, Accenture)
**Answer:**
| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| Mutability | Immutable | Mutable | Mutable |
| Thread Safety | Yes | No | Yes |
| Performance | Slow (for concatenation) | Fast | Medium |
| Memory | Creates new object | Modifies existing | Modifies existing |
| Since | JDK 1.0 | JDK 1.5 | JDK 1.0 |

```java
// Performance comparison
String str = "";
StringBuilder sb = new StringBuilder();
StringBuffer sbf = new StringBuffer();

// Slow - creates new String object each time
for(int i = 0; i < 1000; i++) {
    str += i; // Don't do this!
}

// Fast - modifies existing buffer
for(int i = 0; i < 1000; i++) {
    sb.append(i);
}
```

### Q3: String Pool concept and memory allocation (Cognizant, HCL)
**Answer:**
```java
String str1 = "Hello";        // String Pool
String str2 = "Hello";        // Points to same object in pool
String str3 = new String("Hello"); // Heap memory
String str4 = new String("Hello"); // Different object in heap

System.out.println(str1 == str2);     // true (same reference)
System.out.println(str1 == str3);     // false (different memory)
System.out.println(str3 == str4);     // false (different objects)
System.out.println(str1.equals(str3)); // true (same content)

// Force string to pool
String str5 = str3.intern(); // Moves to string pool
System.out.println(str1 == str5);     // true (now same reference)
```

### Q4: String comparison methods (Tech Mahindra, Capgemini)
**Answer:**
```java
String str1 = "Hello";
String str2 = "hello";
String str3 = "Hello";
String str4 = null;

// equals() - case sensitive
System.out.println(str1.equals(str2));     // false
System.out.println(str1.equals(str3));     // true

// equalsIgnoreCase() - case insensitive
System.out.println(str1.equalsIgnoreCase(str2)); // true

// compareTo() - lexicographic comparison
System.out.println(str1.compareTo(str2));  // negative (H < h in ASCII)
System.out.println(str1.compareTo(str3));  // 0 (equal)

// Null safe comparison
System.out.println(Objects.equals(str1, str4)); // false (handles null)

// Common mistake
// str4.equals(str1); // NullPointerException
// Solution: "Hello".equals(str4) or Objects.equals()
```

### Q5: How to reverse a string? (L&T Infotech, Mindtree)
**Answer:**
```java
// Method 1: Using StringBuilder
String original = "Hello";
String reversed = new StringBuilder(original).reverse().toString();

// Method 2: Using char array
public static String reverseString(String str) {
    char[] chars = str.toCharArray();
    int left = 0, right = chars.length - 1;
    
    while(left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        left++;
        right--;
    }
    return new String(chars);
}

// Method 3: Using recursion
public static String reverseRecursive(String str) {
    if(str.length() <= 1) return str;
    return reverseRecursive(str.substring(1)) + str.charAt(0);
}

// Method 4: Using Java 8 Streams
String reversed = str.chars()
    .mapToObj(c -> String.valueOf((char) c))
    .reduce("", (a, b) -> b + a);
```

### Q6: How to check if string is palindrome? (IBM, Oracle)
**Answer:**
```java
// Method 1: Simple approach
public static boolean isPalindrome(String str) {
    String cleaned = str.toLowerCase().replaceAll("[^a-z0-9]", "");
    String reversed = new StringBuilder(cleaned).reverse().toString();
    return cleaned.equals(reversed);
}

// Method 2: Two pointer approach (efficient)
public static boolean isPalindromeOptimal(String str) {
    str = str.toLowerCase().replaceAll("[^a-z0-9]", "");
    int left = 0, right = str.length() - 1;
    
    while(left < right) {
        if(str.charAt(left) != str.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}

// Method 3: Recursive approach
public static boolean isPalindromeRecursive(String str, int start, int end) {
    if(start >= end) return true;
    if(str.charAt(start) != str.charAt(end)) return false;
    return isPalindromeRecursive(str, start + 1, end - 1);
}
```

### Q7: String tokenization and splitting (Amazon, Microsoft)
**Answer:**
```java
String data = "apple,banana;orange:grape";

// Method 1: Using split()
String[] fruits1 = data.split("[,;:]"); // Regex for multiple delimiters
// Result: ["apple", "banana", "orange", "grape"]

// Method 2: Using StringTokenizer
StringTokenizer st = new StringTokenizer(data, ",;:");
List<String> fruits2 = new ArrayList<>();
while(st.hasMoreTokens()) {
    fruits2.add(st.nextToken());
}

// Method 3: Using Scanner
Scanner scanner = new Scanner(data);
scanner.useDelimiter("[,;:]");
List<String> fruits3 = new ArrayList<>();
while(scanner.hasNext()) {
    fruits3.add(scanner.next());
}

// Handling empty strings
String text = "a,,b,c,";
String[] parts = text.split(",", -1); // Include empty strings
// Result: ["a", "", "b", "c", ""]
```

### Q8: String formatting and manipulation (Google, Adobe)
**Answer:**
```java
// String formatting
String name = "John";
int age = 25;
double salary = 50000.75;

// Method 1: String.format()
String formatted1 = String.format("Name: %s, Age: %d, Salary: %.2f", 
                                 name, age, salary);

// Method 2: printf()
System.out.printf("Name: %s, Age: %d, Salary: %.2f%n", name, age, salary);

// Method 3: MessageFormat
String pattern = "Name: {0}, Age: {1}, Salary: {2,number,#.##}";
String formatted2 = MessageFormat.format(pattern, name, age, salary);

// StringBuilder formatting
StringBuilder sb = new StringBuilder();
sb.append("Name: ").append(name)
  .append(", Age: ").append(age)
  .append(", Salary: ").append(String.format("%.2f", salary));

// String manipulation
String text = "  Hello World  ";
String result = text.trim()                    // Remove spaces
                   .toLowerCase()              // Convert to lowercase
                   .replace(" ", "_")          // Replace spaces with underscore
                   .substring(0, 10);          // Take first 10 characters
```

### Q9: Regular expressions with strings (Flipkart, Paytm)
**Answer:**
```java
// Email validation
public static boolean isValidEmail(String email) {
    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    return email.matches(regex);
}

// Phone number validation
public static boolean isValidPhone(String phone) {
    String regex = "^\\+?[1-9]\\d{1,14}$"; // International format
    return phone.matches(regex);
}

// Extract numbers from string
public static List<Integer> extractNumbers(String text) {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(text);
    List<Integer> numbers = new ArrayList<>();
    
    while(matcher.find()) {
        numbers.add(Integer.parseInt(matcher.group()));
    }
    return numbers;
}

// Replace using regex
String text = "Hello123World456";
String result = text.replaceAll("\\d+", ""); // Remove all digits
// Result: "HelloWorld"
```

### Q10: String performance optimization (Swiggy, Zomato)
**Answer:**
```java
// Bad: String concatenation in loop
public String concatenateStrings(String[] strings) {
    String result = "";
    for(String str : strings) {
        result += str; // Creates new String object each time - O(n²)
    }
    return result;
}

// Good: Using StringBuilder
public String concatenateStringsOptimal(String[] strings) {
    StringBuilder sb = new StringBuilder();
    for(String str : strings) {
        sb.append(str); // Modifies existing buffer - O(n)
    }
    return sb.toString();
}

// Best: Using String.join() for simple concatenation
public String concatenateStringsBest(String[] strings) {
    return String.join("", strings); // Most efficient
}

// Memory efficient string comparison
public boolean compareStrings(String str1, String str2) {
    // Check length first (early exit)
    if(str1.length() != str2.length()) {
        return false;
    }
    
    // Use equals() for content comparison
    return str1.equals(str2);
}
```

## 🔥 Advanced Interview Questions

### Q11: String interning and memory leaks (BYJU'S, Unacademy)
**Answer:**
```java
// String interning
String str1 = new String("Hello").intern(); // Forces to string pool
String str2 = "Hello"; // From string pool
System.out.println(str1 == str2); // true

// Memory leak scenario (before Java 7)
// String pool was in PermGen, could cause OutOfMemoryError
for(int i = 0; i < 1000000; i++) {
    String str = ("String" + i).intern(); // Don't do this!
}

// Solution: Avoid unnecessary interning
// Use interning only when you need to optimize memory for frequently used strings
```

### Q12: Custom string class implementation (Freshworks, Razorpay)
**Answer:**
```java
public class CustomString {
    private final char[] value;
    private final int length;
    
    public CustomString(String str) {
        this.value = str.toCharArray();
        this.length = str.length();
    }
    
    public int length() {
        return length;
    }
    
    public char charAt(int index) {
        if(index < 0 || index >= length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }
    
    public CustomString substring(int beginIndex, int endIndex) {
        if(beginIndex < 0 || endIndex > length || beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException();
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = beginIndex; i < endIndex; i++) {
            sb.append(value[i]);
        }
        return new CustomString(sb.toString());
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof CustomString)) return false;
        
        CustomString other = (CustomString) obj;
        if(this.length != other.length) return false;
        
        for(int i = 0; i < length; i++) {
            if(this.value[i] != other.value[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return new String(value);
    }
}
```

### Q13: String encoding and Unicode (Ola, Uber)
**Answer:**
```java
// String encoding
String text = "Hello नमस्ते";

// Convert to bytes using different encodings
byte[] utf8Bytes = text.getBytes(StandardCharsets.UTF_8);
byte[] utf16Bytes = text.getBytes(StandardCharsets.UTF_16);
byte[] asciiBytes = text.getBytes(StandardCharsets.US_ASCII);

// Create string from bytes
String fromUtf8 = new String(utf8Bytes, StandardCharsets.UTF_8);
String fromUtf16 = new String(utf16Bytes, StandardCharsets.UTF_16);

// Unicode handling
String unicode = "\\u0048\\u0065\\u0932\\u0932\\u006F"; // Hello in Unicode
String decoded = StringEscapeUtils.unescapeJava(unicode);

// Check if string contains only ASCII characters
public static boolean isAscii(String str) {
    return str.chars().allMatch(c -> c < 128);
}
```

## 💡 Quick Tips for Coding Rounds

1. **Use StringBuilder** for multiple string concatenations
2. **Always use .equals()** for string comparison, not ==
3. **Handle null strings** gracefully in methods
4. **Use String.join()** for simple concatenation with delimiter
5. **Consider regex** for complex string validation/manipulation

## 🎯 Practice Problems

1. Find all anagrams of a string
2. Implement string compression (aabcccccaaa → a2b1c5a3)
3. Find longest palindromic substring
4. Check if strings are rotations of each other
5. Implement efficient string search algorithm (KMP)

## 📝 Common String Patterns

```java
// Check if two strings are anagrams
public boolean areAnagrams(String str1, String str2) {
    if(str1.length() != str2.length()) return false;
    
    char[] chars1 = str1.toLowerCase().toCharArray();
    char[] chars2 = str2.toLowerCase().toCharArray();
    
    Arrays.sort(chars1);
    Arrays.sort(chars2);
    
    return Arrays.equals(chars1, chars2);
}

// Remove duplicates from string
public String removeDuplicates(String str) {
    Set<Character> seen = new LinkedHashSet<>();
    for(char c : str.toCharArray()) {
        seen.add(c);
    }
    
    StringBuilder sb = new StringBuilder();
    for(char c : seen) {
        sb.append(c);
    }
    return sb.toString();
}

// Find first non-repeating character
public char firstNonRepeating(String str) {
    Map<Character, Integer> charCount = new HashMap<>();
    
    // Count frequencies
    for(char c : str.toCharArray()) {
        charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }
    
    // Find first character with count 1
    for(char c : str.toCharArray()) {
        if(charCount.get(c) == 1) {
            return c;
        }
    }
    return '\0'; // Not found
}
```

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy