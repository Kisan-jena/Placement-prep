# Java Data Types - Placement Revision Notes

## 📚 Core Concepts

### Primitive Data Types
| Data Type | Size | Range | Default Value |
|-----------|------|-------|---------------|
| `byte` | 1 byte | -128 to 127 | 0 |
| `short` | 2 bytes | -32,768 to 32,767 | 0 |
| `int` | 4 bytes | -2³¹ to 2³¹-1 | 0 |
| `long` | 8 bytes | -2⁶³ to 2⁶³-1 | 0L |
| `float` | 4 bytes | ~±3.4E+38 | 0.0f |
| `double` | 8 bytes | ~±1.8E+308 | 0.0d |
| `char` | 2 bytes | 0 to 65,535 | '\u0000' |
| `boolean` | 1 bit | true/false | false |

### Non-Primitive Data Types
- **String** - Reference type, immutable
- **Arrays** - Collection of similar elements
- **Classes** - User-defined types
- **Interfaces** - Contract for classes

## 🎯 Key Points for Interviews

1. **Wrapper Classes**: Each primitive has a corresponding wrapper class
2. **Autoboxing/Unboxing**: Automatic conversion between primitive and wrapper
3. **Type Casting**: Implicit (widening) vs Explicit (narrowing)
4. **String Pool**: Strings are stored in heap memory's string pool

## 💼 Placement Questions & Answers

### Q1: What is the difference between int and Integer? (TCS, Infosys, Wipro)
**Answer:**
- `int` is a primitive data type (4 bytes)
- `Integer` is a wrapper class for int
- `int` stores actual value, `Integer` stores reference
- `int` has default value 0, `Integer` has default value null
- `Integer` provides utility methods like parseInt(), valueOf()

### Q2: Explain autoboxing and unboxing with example (Accenture, Cognizant)
**Answer:**
```java
// Autoboxing - primitive to wrapper
int a = 10;
Integer b = a; // Automatic conversion

// Unboxing - wrapper to primitive  
Integer c = 20;
int d = c; // Automatic conversion
```

### Q3: What happens when you compare Integer objects? (HCL, Tech Mahindra)
**Answer:**
```java
Integer a = 128;
Integer b = 128;
System.out.println(a == b); // false

Integer c = 127;
Integer d = 127;
System.out.println(c == d); // true (cached -128 to 127)
```

### Q4: Size of char in Java and why? (Capgemini, L&T Infotech)
**Answer:**
- `char` is 2 bytes (16 bits) in Java
- Java uses Unicode (UTF-16) encoding
- Can represent 65,536 different characters
- Supports international characters

### Q5: Can we change the size of an array after declaration? (Mindtree, Mphasis)
**Answer:**
- No, array size is fixed once created
- Use ArrayList or other dynamic collections for variable size
- Arrays are objects in Java stored in heap memory

### Q6: What is the range of byte data type? (IBM, Oracle)
**Answer:**
- Range: -128 to 127 (8 bits, signed)
- Formula: -2^(n-1) to 2^(n-1)-1 where n=8
- Useful for saving memory in large arrays

### Q7: Difference between float and double (Amazon, Microsoft)
**Answer:**
| Feature | float | double |
|---------|-------|--------|
| Size | 4 bytes | 8 bytes |
| Precision | 7 decimal digits | 15 decimal digits |
| Suffix | f or F | d or D (optional) |
| Default | Not default | Default for decimals |

### Q8: What is type casting? Types with examples (Google, Adobe)
**Answer:**
**Implicit Casting (Widening):**
```java
int a = 10;
double b = a; // Automatic conversion
```

**Explicit Casting (Narrowing):**
```java
double a = 10.5;
int b = (int) a; // Manual conversion, data loss possible
```

### Q9: Can we store different data types in an array? (Deloitte, EY)
**Answer:**
- No, arrays in Java are homogeneous
- Can use Object[] array to store different types
- Use ArrayList<Object> for dynamic storage
- Generics provide type safety

### Q10: What is the default value of local variables? (Freshworks, Razorpay)
**Answer:**
- Local variables have NO default values
- Must be initialized before use
- Compiler throws error for uninitialized local variables
- Only instance and static variables have default values

## 🔥 Advanced Interview Questions

### Q11: Memory allocation for primitives vs objects (Paytm, Flipkart)
**Answer:**
- **Primitives**: Stored in stack memory (method area)
- **Objects**: Stored in heap memory, reference in stack
- **String literals**: Stored in string pool (heap)

### Q12: Why is String immutable in Java? (Swiggy, Zomato)
**Answer:**
1. **Security**: Prevents modification of sensitive data
2. **String Pool**: Enables string interning for memory efficiency
3. **Thread Safety**: Immutable objects are inherently thread-safe
4. **Hashcode Caching**: Hashcode calculated once and cached

### Q13: Stack vs Heap memory (BYJU'S, Unacademy)
**Answer:**
| Stack | Heap |
|-------|------|
| Stores primitives & references | Stores objects |
| Faster access | Slower access |
| LIFO structure | Random access |
| Thread-specific | Shared among threads |
| Limited size | Larger size |

## 💡 Quick Tips for Coding Rounds

1. **Remember precedence**: byte → short → int → long → float → double
2. **char arithmetic**: char + int = int
3. **Boolean operations**: Only true/false, no 0/1
4. **Array declaration**: `int[] arr` or `int arr[]`
5. **String comparison**: Always use `.equals()` not `==`

## 🎯 Practice Problems

1. Write a program to demonstrate all primitive data types
2. Create examples of implicit and explicit type casting
3. Implement autoboxing and unboxing scenarios
4. Compare string creation methods (literal vs new)
5. Show integer cache behavior (-128 to 127)

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy