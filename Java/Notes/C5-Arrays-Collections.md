# Java Arrays & Collections - Placement Revision Notes

## 📚 Core Concepts

### Arrays in Java
```java
// Array declaration and initialization
int[] arr1 = new int[5];           // Default values (0)
int[] arr2 = {1, 2, 3, 4, 5};      // Array literal
int[] arr3 = new int[]{1, 2, 3};   // Anonymous array

// 2D Arrays
int[][] matrix = new int[3][4];    // 3 rows, 4 columns
int[][] jaggedArray = new int[3][]; // Jagged array
```

### Collections Framework Hierarchy
```
Collection (Interface)
├── List (Interface)
│   ├── ArrayList (Class)
│   ├── LinkedList (Class)
│   └── Vector (Class)
├── Set (Interface)
│   ├── HashSet (Class)
│   ├── LinkedHashSet (Class)
│   └── TreeSet (Class)
└── Queue (Interface)
    ├── PriorityQueue (Class)
    └── Deque (Interface)
        └── ArrayDeque (Class)

Map (Interface)
├── HashMap (Class)
├── LinkedHashMap (Class)
├── TreeMap (Class)
└── Hashtable (Class)
```

## 🎯 Key Points for Interviews

1. **Arrays**: Fixed size, homogeneous elements, index-based access
2. **Collections**: Dynamic size, type-safe with generics, rich API
3. **Performance**: ArrayList vs LinkedList vs Vector
4. **Thread Safety**: Vector/Hashtable (synchronized) vs ArrayList/HashMap
5. **Ordering**: LinkedHashMap/LinkedHashSet maintain insertion order

## 💼 Placement Questions & Answers

### Q1: Array vs ArrayList (TCS, Infosys)
**Answer:**
| Feature | Array | ArrayList |
|---------|-------|-----------|
| Size | Fixed | Dynamic |
| Type | Can store primitives & objects | Only objects (autoboxing for primitives) |
| Performance | Faster | Slightly slower |
| Memory | Less overhead | More overhead |
| Methods | Length property | Rich API (add, remove, etc.) |
| Generics | No | Yes (type safety) |

```java
// Array
int[] arr = new int[5];
arr[0] = 10;
int length = arr.length; // Property

// ArrayList
ArrayList<Integer> list = new ArrayList<>();
list.add(10);
int size = list.size(); // Method
```

### Q2: ArrayList vs LinkedList vs Vector (Wipro, Accenture)
**Answer:**
| Feature | ArrayList | LinkedList | Vector |
|---------|-----------|------------|--------|
| Data Structure | Dynamic array | Doubly linked list | Dynamic array |
| Access Time | O(1) | O(n) | O(1) |
| Insertion/Deletion | O(n) at middle | O(1) at middle | O(n) at middle |
| Memory | Less overhead | More overhead | Less overhead |
| Thread Safety | Not synchronized | Not synchronized | Synchronized |
| Performance | Fast | Slow for access | Slow (synchronization) |

```java
// ArrayList - Best for frequent access
List<Integer> arrayList = new ArrayList<>();
arrayList.add(1);
int element = arrayList.get(0); // O(1)

// LinkedList - Best for frequent insertion/deletion
List<Integer> linkedList = new LinkedList<>();
linkedList.add(1);
linkedList.add(0, 2); // O(1) at beginning

// Vector - Legacy, synchronized
Vector<Integer> vector = new Vector<>();
vector.add(1); // Thread-safe but slower
```

### Q3: HashMap vs Hashtable vs LinkedHashMap vs TreeMap (Cognizant, HCL)
**Answer:**
| Feature | HashMap | Hashtable | LinkedHashMap | TreeMap |
|---------|---------|-----------|---------------|---------|
| Null values | Allows | Not allowed | Allows | Not allowed |
| Thread Safety | No | Yes | No | No |
| Ordering | No | No | Insertion order | Sorted order |
| Performance | O(1) average | O(1) average | O(1) average | O(log n) |
| Since | Java 1.2 | Java 1.0 | Java 1.4 | Java 1.2 |

```java
// HashMap - Most commonly used
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("key1", 1);
hashMap.put(null, 2); // Allows null keys

// Hashtable - Legacy, synchronized
Hashtable<String, Integer> hashtable = new Hashtable<>();
hashtable.put("key1", 1);
// hashtable.put(null, 2); // Throws NullPointerException

// LinkedHashMap - Maintains insertion order
Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
linkedHashMap.put("second", 2);
linkedHashMap.put("first", 1);
// Iteration order: second, first

// TreeMap - Sorted by keys
Map<String, Integer> treeMap = new TreeMap<>();
treeMap.put("zebra", 1);
treeMap.put("apple", 2);
// Iteration order: apple, zebra
```

### Q4: HashSet vs LinkedHashSet vs TreeSet (Tech Mahindra, Capgemini)
**Answer:**
| Feature | HashSet | LinkedHashSet | TreeSet |
|---------|---------|---------------|---------|
| Duplicates | Not allowed | Not allowed | Not allowed |
| Null values | One null allowed | One null allowed | Not allowed |
| Ordering | No order | Insertion order | Sorted order |
| Performance | O(1) average | O(1) average | O(log n) |
| Interface | Set | Set | NavigableSet |

```java
// HashSet - No ordering
Set<String> hashSet = new HashSet<>();
hashSet.add("banana");
hashSet.add("apple");
hashSet.add("cherry");
// Iteration order: unpredictable

// LinkedHashSet - Insertion order
Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("banana");
linkedHashSet.add("apple");
linkedHashSet.add("cherry");
// Iteration order: banana, apple, cherry

// TreeSet - Sorted order
Set<String> treeSet = new TreeSet<>();
treeSet.add("banana");
treeSet.add("apple");
treeSet.add("cherry");
// Iteration order: apple, banana, cherry
```

### Q5: How HashMap works internally? (L&T Infotech, Mindtree)
**Answer:**
```java
// HashMap internal structure
// Array of Node<K,V>[] table (buckets)
// Each bucket can have linked list or tree (Java 8+)

public class MyHashMap<K, V> {
    private Node<K, V>[] buckets;
    private int size;
    private int capacity = 16;
    private double loadFactor = 0.75;
    
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        int hash;
        
        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    public V put(K key, V value) {
        int hash = hash(key);
        int index = hash & (capacity - 1); // hash % capacity
        
        Node<K, V> node = buckets[index];
        if (node == null) {
            buckets[index] = new Node<>(hash, key, value, null);
        } else {
            // Handle collision - chain or replace
            while (node.next != null) {
                if (node.key.equals(key)) {
                    V oldValue = node.value;
                    node.value = value;
                    return oldValue;
                }
                node = node.next;
            }
            node.next = new Node<>(hash, key, value, null);
        }
        
        size++;
        if (size > capacity * loadFactor) {
            resize(); // Double the capacity and rehash
        }
        
        return null;
    }
    
    private int hash(K key) {
        return key == null ? 0 : key.hashCode();
    }
}
```

**Key Points:**
- Uses array of buckets (default 16)
- Hash function determines bucket index
- Collision handling: Chaining (linked list) → Tree (Java 8+ when >8 elements)
- Load factor 0.75 triggers resize
- Rehashing when capacity doubles

### Q6: Implement ArrayList from scratch (IBM, Oracle)
**Answer:**
```java
public class MyArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;
    
    public MyArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }
    
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        elementData = new Object[initialCapacity];
    }
    
    public boolean add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
        return true;
    }
    
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        
        // Shift elements to right
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }
    
    @SuppressWarnings("unchecked")
    public E get(int index) {
        rangeCheck(index);
        return (E) elementData[index];
    }
    
    public E remove(int index) {
        rangeCheck(index);
        
        @SuppressWarnings("unchecked")
        E oldValue = (E) elementData[index];
        
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // Help GC
        
        return oldValue;
    }
    
    public int size() { return size; }
    
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            int newCapacity = elementData.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
    
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
```

### Q7: Find duplicate elements in array (Amazon, Microsoft)
**Answer:**
```java
// Method 1: Using HashSet - O(n) time, O(n) space
public List<Integer> findDuplicates1(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    Set<Integer> duplicates = new HashSet<>();
    
    for (int num : nums) {
        if (!seen.add(num)) {
            duplicates.add(num);
        }
    }
    
    return new ArrayList<>(duplicates);
}

// Method 2: Using frequency map - O(n) time, O(n) space
public List<Integer> findDuplicates2(int[] nums) {
    Map<Integer, Integer> frequencyMap = new HashMap<>();
    
    for (int num : nums) {
        frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
    }
    
    List<Integer> duplicates = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
        if (entry.getValue() > 1) {
            duplicates.add(entry.getKey());
        }
    }
    
    return duplicates;
}

// Method 3: For array with elements 1 to n - O(n) time, O(1) space
public List<Integer> findDuplicates3(int[] nums) {
    List<Integer> duplicates = new ArrayList<>();
    
    for (int i = 0; i < nums.length; i++) {
        int index = Math.abs(nums[i]) - 1;
        
        if (nums[index] < 0) {
            duplicates.add(Math.abs(nums[i]));
        } else {
            nums[index] = -nums[index];
        }
    }
    
    return duplicates;
}
```

### Q8: Merge two sorted arrays (Google, Adobe)
**Answer:**
```java
// Method 1: Using extra space - O(m+n) time, O(m+n) space
public int[] mergeSortedArrays1(int[] arr1, int[] arr2) {
    int[] merged = new int[arr1.length + arr2.length];
    int i = 0, j = 0, k = 0;
    
    while (i < arr1.length && j < arr2.length) {
        if (arr1[i] <= arr2[j]) {
            merged[k++] = arr1[i++];
        } else {
            merged[k++] = arr2[j++];
        }
    }
    
    // Copy remaining elements
    while (i < arr1.length) {
        merged[k++] = arr1[i++];
    }
    while (j < arr2.length) {
        merged[k++] = arr2[j++];
    }
    
    return merged;
}

// Method 2: In-place merge (if arr1 has extra space)
public void mergeSortedArrays2(int[] arr1, int m, int[] arr2, int n) {
    int i = m - 1;  // Last element in arr1
    int j = n - 1;  // Last element in arr2
    int k = m + n - 1;  // Last position in arr1
    
    while (i >= 0 && j >= 0) {
        if (arr1[i] > arr2[j]) {
            arr1[k--] = arr1[i--];
        } else {
            arr1[k--] = arr2[j--];
        }
    }
    
    // Copy remaining elements from arr2
    while (j >= 0) {
        arr1[k--] = arr2[j--];
    }
}
```

### Q9: Iterator vs ListIterator vs Enumeration (Flipkart, Paytm)
**Answer:**
| Feature | Iterator | ListIterator | Enumeration |
|---------|----------|--------------|-------------|
| Direction | Forward only | Bidirectional | Forward only |
| Modification | remove() | add(), remove(), set() | Read-only |
| Collections | All collections | Only List | Legacy (Vector, Hashtable) |
| Fail-fast | Yes | Yes | No |
| Since | Java 1.2 | Java 1.2 | Java 1.0 |

```java
List<String> list = Arrays.asList("a", "b", "c", "d");

// Iterator - Forward only
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String element = iterator.next();
    // iterator.remove(); // Allowed
}

// ListIterator - Bidirectional
ListIterator<String> listIterator = list.listIterator();
while (listIterator.hasNext()) {
    String element = listIterator.next();
    // listIterator.add("new"); // Allowed
    // listIterator.set("modified"); // Allowed
}

// Backward iteration
while (listIterator.hasPrevious()) {
    String element = listIterator.previous();
}

// Enumeration - Legacy
Vector<String> vector = new Vector<>(list);
Enumeration<String> enumeration = vector.elements();
while (enumeration.hasMoreElements()) {
    String element = enumeration.nextElement();
    // No modification allowed
}
```

### Q10: Collections utility methods (Swiggy, Zomato)
**Answer:**
```java
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);

// Sorting
Collections.sort(numbers);                    // [1, 1, 2, 3, 4, 5, 6, 9]
Collections.sort(numbers, Collections.reverseOrder()); // [9, 6, 5, 4, 3, 2, 1, 1]

// Searching (requires sorted list)
int index = Collections.binarySearch(numbers, 5); // Returns index of 5

// Min/Max
int min = Collections.min(numbers);
int max = Collections.max(numbers);

// Frequency
int frequency = Collections.frequency(numbers, 1); // Count of 1s

// Shuffle
Collections.shuffle(numbers); // Random order

// Reverse
Collections.reverse(numbers);

// Fill
Collections.fill(numbers, 0); // All elements become 0

// Copy
List<Integer> copy = new ArrayList<>(numbers);
Collections.copy(copy, numbers);

// Synchronization
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Unmodifiable collections
List<Integer> unmodifiableList = Collections.unmodifiableList(numbers);
// unmodifiableList.add(10); // Throws UnsupportedOperationException

// Empty collections
List<String> emptyList = Collections.emptyList();
Set<String> emptySet = Collections.emptySet();
Map<String, String> emptyMap = Collections.emptyMap();

// Singleton collections
List<String> singletonList = Collections.singletonList("only");
Set<String> singletonSet = Collections.singleton("only");
Map<String, String> singletonMap = Collections.singletonMap("key", "value");
```

## 🔥 Advanced Interview Questions

### Q11: Custom Comparator and Comparable (BYJU'S, Unacademy)
**Answer:**
```java
// Comparable - Natural ordering
class Student implements Comparable<Student> {
    private String name;
    private int age;
    private double gpa;
    
    // Constructor and getters
    
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name); // Sort by name
    }
}

// Comparator - Custom ordering
class StudentAgeComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getAge(), s2.getAge());
    }
}

// Using comparators
List<Student> students = new ArrayList<>();
// Add students...

// Natural ordering (by name)
Collections.sort(students);

// Custom ordering (by age)
Collections.sort(students, new StudentAgeComparator());

// Lambda expressions (Java 8+)
Collections.sort(students, (s1, s2) -> Double.compare(s1.getGpa(), s2.getGpa()));

// Method references
Collections.sort(students, Comparator.comparing(Student::getName));

// Multiple criteria
students.sort(Comparator.comparing(Student::getGpa).reversed()
                       .thenComparing(Student::getName));
```

### Q12: Concurrent Collections (Freshworks, Razorpay)
**Answer:**
```java
// ConcurrentHashMap - Thread-safe HashMap
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put("key", 1);
concurrentMap.putIfAbsent("key", 2); // Won't override existing value

// CopyOnWriteArrayList - Thread-safe ArrayList
CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
copyOnWriteList.add("item1");
// Safe for concurrent reads, expensive writes

// BlockingQueue - Thread-safe queue with blocking operations
BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
blockingQueue.put("item"); // Blocks if queue is full
String item = blockingQueue.take(); // Blocks if queue is empty

// Atomic operations
AtomicInteger atomicCounter = new AtomicInteger(0);
int newValue = atomicCounter.incrementAndGet(); // Thread-safe increment

// Producer-Consumer example with BlockingQueue
class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                queue.put(i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    
    public void run() {
        try {
            while (true) {
                Integer item = queue.take();
                System.out.println("Consumed: " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### Q13: Memory optimization in collections (Ola, Uber)
**Answer:**
```java
// ArrayList capacity optimization
List<Integer> list = new ArrayList<>(1000); // Pre-size if known

// HashMap load factor optimization
Map<String, Integer> map = new HashMap<>(16, 0.9f); // Higher load factor

// Use primitive collections libraries (Trove, Eclipse Collections)
// Instead of ArrayList<Integer> use TIntArrayList (no boxing overhead)

// Memory-efficient alternatives
// Instead of HashSet<String> for small sets
Set<String> smallSet = Set.of("a", "b", "c"); // Immutable, memory efficient

// For very large collections
// Consider off-heap solutions like Chronicle Map

// Avoid memory leaks
public class LeakyCache {
    private Map<String, Object> cache = new HashMap<>();
    
    // Bad: No cleanup, memory leak
    public void put(String key, Object value) {
        cache.put(key, value);
    }
    
    // Good: Size limit or TTL
    private static final int MAX_SIZE = 1000;
    public void putSafe(String key, Object value) {
        if (cache.size() >= MAX_SIZE) {
            // Remove oldest entries or implement LRU
            cache.clear(); // Simple approach
        }
        cache.put(key, value);
    }
}

// Use WeakHashMap for cache scenarios
Map<String, ExpensiveObject> cache = new WeakHashMap<>();
// Entries can be garbage collected when key is not strongly referenced
```

## 💡 Quick Tips for Coding Rounds

1. **Choose right collection**: ArrayList for frequent access, LinkedList for frequent insertion/deletion
2. **Initialize capacity**: When size is known, pre-size collections
3. **Use enhanced for loop**: More readable and less error-prone
4. **Handle null values**: Check for null before operations
5. **Use Collections utility**: Don't reinvent sorting, searching algorithms

## 🎯 Practice Problems

1. Rotate array by k positions
2. Find missing number in array (1 to n)
3. Implement LRU cache using HashMap + DoublyLinkedList
4. Two sum problem using HashMap
5. Group anagrams using HashMap

## 📝 Common Array/Collection Patterns

```java
// Two pointers technique
public boolean isPalindrome(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        if (arr[left] != arr[right]) return false;
        left++;
        right--;
    }
    return true;
}

// Sliding window technique
public int maxSumSubarray(int[] arr, int k) {
    int maxSum = 0, windowSum = 0;
    
    // First window
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    maxSum = windowSum;
    
    // Slide window
    for (int i = k; i < arr.length; i++) {
        windowSum = windowSum - arr[i - k] + arr[i];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}

// Frequency counting
public Map<Character, Integer> getCharFrequency(String str) {
    Map<Character, Integer> frequency = new HashMap<>();
    for (char c : str.toCharArray()) {
        frequency.put(c, frequency.getOrDefault(c, 0) + 1);
    }
    return frequency;
}
```

---
**Companies that frequently ask these questions:**
- Service-based: TCS, Infosys, Wipro, Accenture, Cognizant, HCL, Tech Mahindra
- Product-based: Amazon, Microsoft, Google, Adobe, Flipkart, Paytm, Swiggy