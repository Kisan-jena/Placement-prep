# Java OOPs Concepts - Placement Revision Notes

## 📚 Core Concepts

### Four Pillars of OOP
1. **Encapsulation** - Data hiding
2. **Inheritance** - Code reusability
3. **Polymorphism** - One interface, multiple implementations
4. **Abstraction** - Hiding implementation details

### Class and Object
```java
// Class definition
class Student {
    private String name;
    private int age;
    
    // Constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getter and Setter methods
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// Object creation
Student student = new Student("John", 20);
```

## 🎯 Key Points for Interviews

1. **Encapsulation**: Private fields + Public methods
2. **Inheritance**: IS-A relationship, single inheritance in Java
3. **Polymorphism**: Method overloading + Method overriding
4. **Abstraction**: Abstract classes and interfaces
5. **Constructor**: Special method for object initialization

## 💼 Placement Questions & Answers

### Q1: What is the difference between class and object? (TCS, Infosys)
**Answer:**
| Class | Object |
|-------|--------|
| Blueprint/Template | Instance of class |
| Logical entity | Physical entity |
| No memory allocated | Memory allocated |
| Defined once | Can create multiple |
| Contains methods & variables | Contains actual values |

### Q2: Explain encapsulation with real-world example (Wipro, Accenture)
**Answer:**
```java
class BankAccount {
    private double balance; // Hidden from outside
    private String accountNumber;
    
    // Controlled access through methods
    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    public double getBalance() {
        return balance; // Read-only access
    }
}
```
**Benefits:** Data security, Flexibility, Code maintainability

### Q3: Types of inheritance in Java (Cognizant, HCL)
**Answer:**
```java
// Single Inheritance
class Animal {
    void eat() { System.out.println("Eating"); }
}
class Dog extends Animal {
    void bark() { System.out.println("Barking"); }
}

// Multilevel Inheritance
class Animal { }
class Mammal extends Animal { }
class Dog extends Mammal { }

// Hierarchical Inheritance
class Animal { }
class Dog extends Animal { }
class Cat extends Animal { }

// Note: Multiple inheritance not supported in Java (Diamond Problem)
// Use interfaces for multiple inheritance
```

### Q4: Method overloading vs Method overriding (Tech Mahindra, Capgemini)
**Answer:**
| Feature | Overloading | Overriding |
|---------|-------------|------------|
| Definition | Same method name, different parameters | Same signature in parent and child |
| Binding | Compile-time | Runtime |
| Inheritance | Not required | Required |
| Access modifier | Can be different | Cannot be more restrictive |
| Example | `add(int, int)` vs `add(double, double)` | Parent `draw()` vs Child `draw()` |

```java
// Method Overloading
class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}

// Method Overriding
class Animal {
    void makeSound() { System.out.println("Some sound"); }
}
class Dog extends Animal {
    @Override
    void makeSound() { System.out.println("Woof"); }
}
```

### Q5: What is constructor? Types of constructors (L&T Infotech, Mindtree)
**Answer:**
```java
class Student {
    private String name;
    private int age;
    
    // Default Constructor
    public Student() {
        this.name = "Unknown";
        this.age = 0;
    }
    
    // Parameterized Constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Copy Constructor (Java doesn't have built-in)
    public Student(Student other) {
        this.name = other.name;
        this.age = other.age;
    }
}
```
**Key Points:**
- Constructor name = Class name
- No return type
- Called automatically when object is created
- Can be overloaded
- If no constructor defined, Java provides default constructor

### Q6: Abstract class vs Interface (IBM, Oracle)
**Answer:**
| Feature | Abstract Class | Interface |
|---------|----------------|-----------|
| Multiple inheritance | No | Yes |
| Constructor | Yes | No |
| Access modifiers | All | public, default (Java 9+) |
| Variables | All types | public static final |
| Methods | Abstract + Concrete | Abstract + Default + Static |
| Keyword | `extends` | `implements` |

```java
// Abstract Class
abstract class Shape {
    protected String color; // Instance variable allowed
    
    public Shape(String color) { // Constructor allowed
        this.color = color;
    }
    
    abstract double calculateArea(); // Abstract method
    
    public void displayColor() { // Concrete method
        System.out.println("Color: " + color);
    }
}

// Interface
interface Drawable {
    int MAX_SIZE = 100; // public static final by default
    
    void draw(); // public abstract by default
    
    default void print() { // Default method (Java 8+)
        System.out.println("Printing...");
    }
    
    static void info() { // Static method (Java 8+)
        System.out.println("Drawing interface");
    }
}
```

### Q7: What is polymorphism? Explain with example (Amazon, Microsoft)
**Answer:**
**Runtime Polymorphism (Method Overriding):**
```java
class Animal {
    void makeSound() { System.out.println("Some sound"); }
}
class Dog extends Animal {
    void makeSound() { System.out.println("Woof"); }
}
class Cat extends Animal {
    void makeSound() { System.out.println("Meow"); }
}

public class Test {
    public static void main(String[] args) {
        Animal animal1 = new Dog(); // Upcasting
        Animal animal2 = new Cat();
        
        animal1.makeSound(); // Prints: Woof
        animal2.makeSound(); // Prints: Meow
    }
}
```

**Compile-time Polymorphism (Method Overloading):**
```java
class MathOperations {
    int multiply(int a, int b) { return a * b; }
    double multiply(double a, double b) { return a * b; }
    int multiply(int a, int b, int c) { return a * b * c; }
}
```

### Q8: What is the diamond problem? How Java solves it? (Google, Adobe)
**Answer:**
```java
// Diamond Problem in Multiple Inheritance
//     A
//   /   \
//  B     C
//   \   /
//     D

// Java's Solution: Interfaces with default methods
interface A {
    default void print() { System.out.println("A"); }
}
interface B extends A {
    default void print() { System.out.println("B"); }
}
interface C extends A {
    default void print() { System.out.println("C"); }
}

// Class implementing both B and C must override print()
class D implements B, C {
    @Override
    public void print() {
        B.super.print(); // Explicitly call B's implementation
        // or provide custom implementation
    }
}
```

### Q9: Can we override static methods? (Flipkart, Paytm)
**Answer:**
```java
class Parent {
    static void staticMethod() {
        System.out.println("Parent static method");
    }
    
    void instanceMethod() {
        System.out.println("Parent instance method");
    }
}

class Child extends Parent {
    // This is method hiding, NOT overriding
    static void staticMethod() {
        System.out.println("Child static method");
    }
    
    @Override
    void instanceMethod() {
        System.out.println("Child instance method");
    }
}

public class Test {
    public static void main(String[] args) {
        Parent p = new Child();
        p.staticMethod(); // Prints: Parent static method (method hiding)
        p.instanceMethod(); // Prints: Child instance method (overriding)
    }
}
```
**Answer:** No, static methods cannot be overridden. They can be hidden.

### Q10: What is super keyword? (Swiggy, Zomato)
**Answer:**
```java
class Vehicle {
    String brand = "Generic";
    
    Vehicle(String brand) {
        this.brand = brand;
    }
    
    void start() {
        System.out.println("Vehicle starting");
    }
}

class Car extends Vehicle {
    String brand = "Car Brand";
    
    Car(String brand) {
        super(brand); // Call parent constructor
    }
    
    void start() {
        super.start(); // Call parent method
        System.out.println("Car starting");
    }
    
    void printBrands() {
        System.out.println("Child brand: " + this.brand);
        System.out.println("Parent brand: " + super.brand);
    }
}
```
**Uses of super:**
1. Call parent constructor: `super()`
2. Call parent method: `super.methodName()`
3. Access parent variable: `super.variableName`

## 🔥 Advanced Interview Questions

### Q11: Covariant return types (BYJU'S, Unacademy)
**Answer:**
```java
class Animal {
    Animal reproduce() {
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    Dog reproduce() { // Covariant return type
        return new Dog();
    }
}
```
**Covariant return type:** Child class method can return subtype of parent's return type.

### Q12: Can constructor be private? (Freshworks, Razorpay)
**Answer:**
```java
class Singleton {
    private static Singleton instance;
    
    // Private constructor
    private Singleton() {
        // Initialization code
    }
    
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```
**Use cases:** Singleton pattern, Factory pattern, Utility classes

### Q13: Multiple inheritance through interfaces (Ola, Uber)
**Answer:**
```java
interface Flyable {
    void fly();
    default void takeOff() {
        System.out.println("Taking off...");
    }
}

interface Swimmable {
    void swim();
    default void dive() {
        System.out.println("Diving...");
    }
}

class Duck implements Flyable, Swimmable {
    @Override
    public void fly() {
        System.out.println("Duck flying");
    }
    
    @Override
    public void swim() {
        System.out.println("Duck swimming");
    }
}
```

## 💡 Quick Tips for Coding Rounds

1. **Always use @Override annotation** for method overriding
2. **Remember access modifier rules** in inheritance
3. **Understand IS-A vs HAS-A** relationships
4. **Know when to use abstract class vs interface**
5. **Constructor chaining**: Child constructor calls parent constructor

## 🎯 Practice Problems

1. Create a banking system using encapsulation
2. Implement shape hierarchy with area calculation
3. Design vehicle inheritance with method overriding
4. Create interface for multiple inheritance scenario
5. Implement singleton pattern with private constructor

## 📝 Design Patterns Using OOP

```java
// Factory Pattern
abstract class Animal {
    abstract void makeSound();
}

class AnimalFactory {
    static Animal createAnimal(String type) {
        switch(type.toLowerCase()) {
            case "dog": return new Dog();
            case "cat": return new Cat();
            default: return null;
        }
    }
}

// Observer Pattern
interface Observer {
    void update(String message);
}

class Subject {
    private List<Observer> observers = new ArrayList<>();
    
    void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    void notifyObservers(String message) {
        for(Observer observer : observers) {
            observer.update(message);
        }
    }
}
```

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy