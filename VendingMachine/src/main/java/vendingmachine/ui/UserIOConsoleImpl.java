package vendingmachine.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String prompt) {
        this.print(prompt);
        return console.nextLine();
    }

    @Override
    public double readDouble(String prompt) {
        while(true) {
            try {
                return Double.parseDouble(this.readString(prompt));
            } catch(NumberFormatException e) {
                this.print("Need to be Double");
            }
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result;
        do {
            result = this.readDouble(prompt);
        } while(result < min || result > max);

        return result;
    }

    @Override
    public float readFloat(String prompt) {
        while(true) {
            try {
                return Float.parseFloat(this.readString(prompt));
            } catch(NumberFormatException e) {
                this.print("Need to be Float");
            }
        }
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result;
        do {
            result = this.readFloat(prompt);
        } while(result < min || result > max);

        return result;
    }

    @Override
    public int readInt(String prompt) {
        while(true) {
            try {
                return Integer.parseInt(this.readString(prompt));
            } catch(NumberFormatException e) {
                this.print("Need to be Int");
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = this.readInt(prompt);
        } while(result < min || result > max);

        return result;
    }

    @Override
    public long readLong(String prompt) {
        while(true) {
            try {
                return Long.parseLong(this.readString(prompt));
            } catch(NumberFormatException e) {
                this.print("Need to be Long");
            }
        }
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result;
        do {
            result = this.readLong(prompt);
        } while(result < min || result > max);

        return result;
    }

}