public class sea {
    public static void main(String[] args) {
        System.out.println("Java is set up! ✅");
        System.out.println("Args length: " + args.length);
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")");
        // Simple computation
        int sum = 0;
        for (int i = 1; i <= 5; i++) sum += i; // 1+2+3+4+5 = 15
        System.out.println("Sum 1..5 = " + sum);
    }
}