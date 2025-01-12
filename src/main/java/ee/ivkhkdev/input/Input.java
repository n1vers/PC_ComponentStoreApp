package ee.ivkhkdev.input;

import java.util.Scanner;

public interface Input {
    default String nextLine(){
        return new Scanner(System.in).nextLine();
    }
}
