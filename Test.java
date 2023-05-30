import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("a");
        test1.add("e");

        ArrayList<String> test2 = new ArrayList<>();
        test2.add("b");
        test2.add("c");
        test2.add("d");

        System.out.println(test1);
        test1.addAll(1, test2);

        
        System.out.println(test1);
    }
}
