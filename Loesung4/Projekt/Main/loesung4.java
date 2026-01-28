    public class loesung4 {

        // Aufgabe 1: Datentypen
        public static void aufgabe1() {
            int myInt = 10;
            double myDouble = 3.14;
            char myChar = 'A';
            boolean myBoolean = true;

            System.out.println("int: " + myInt);
            System.out.println("double: " + myDouble);
            System.out.println("char: " + myChar);
            System.out.println("boolean: " + myBoolean);
        }

        // Aufgabe 2: String
        public static void aufgabe2() {
            String greeting = "Hello, World!";
            greeting += " How are you?";
            System.out.println(greeting);
        }

        // Aufgabe 3: Aufzählungen (enum)
        enum Day {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }

        public static void aufgabe3() {
            Day today = Day.WEDNESDAY;
            System.out.println("Heute ist: " + today);
        }

        // Aufgabe 4: return-Anweisung
        public static int add(int a, int b) {
            return a + b;
        }

        public static void aufgabe4() {
            int result = add(5, 7);
            System.out.println("Summe: " + result);
        }

        // Aufgabe 5: Lokale Konstanten und Variablen
        public static double calculateArea(double radius) {
            final double PI = 3.14159;
            return PI * radius * radius;
        }

        public static void aufgabe5() {
            double area = calculateArea(5.0);
            System.out.println("Fläche des Kreises: " + area);
        }

        // Aufgabe 6: Methode, Signatur und Methodenaufruf
        public static void greet(String name) {
            System.out.println("Hallo, " + name + "!");
        }

        public static int multiply(int a, int b) {
            return a * b;
        }

        public static void aufgabe6() {
            greet("Alice");
            int product = multiply(4, 6);
            System.out.println("Produkt: " + product);
        }

        // Main-Methode zum Aufruf aller Aufgaben
        public static void main(String[] args) {
            System.out.println("Aufgabe 1:");
            aufgabe1();

            System.out.println("\nAufgabe 2:");
            aufgabe2();

            System.out.println("\nAufgabe 3:");
            aufgabe3();

            System.out.println("\nAufgabe 4:");
            aufgabe4();

            System.out.println("\nAufgabe 5:");
            aufgabe5();

            System.out.println("\nAufgabe 6:");
            aufgabe6();
        }
    }

