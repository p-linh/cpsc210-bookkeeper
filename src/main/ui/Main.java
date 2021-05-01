package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

// runs the Bookkeeper Application
public class Main {
    public static void main(String[] args) {
        try {
            new BookkeeperApp();
        } catch (IOException e) {
            //
        }
    }
}
