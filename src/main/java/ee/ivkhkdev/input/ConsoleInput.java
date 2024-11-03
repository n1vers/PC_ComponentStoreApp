package ee.ivkhkdev.input;

import ee.ivkhkdev.interfaces.Input;

import java.io.Serializable;
import java.util.Scanner;

public class ConsoleInput implements Input, Serializable {
    private Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String nextLine(){
        return scanner.nextLine();
    };
}
