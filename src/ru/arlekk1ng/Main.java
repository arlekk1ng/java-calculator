package ru.arlekk1ng;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("выражение / стоп: ");
            input = scanner.nextLine();

            if (input.equals("стоп")) {
                break;
            }

            System.out.print("ответ: ");
            try {
                System.out.println(calc(input));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public static String calc(String input) throws Exception {
        String[] data = input.trim().split(" ");

        if (data.length != 3) {
            throw new Exception("строка не удовлетворяет заданию");
        }

        boolean isRoman = isRomanNumeral(data[0]);
        int number1, number2;

        if (isRoman) {
            number1 = romanToArabic(data[0]);
            number2 = romanToArabic(data[2]);
        } else {
            number1 = parseInt(data[0]);
            number2 = parseInt(data[2]);
        }

        int result;

        switch (data[1]) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                result = number1 / number2;
                break;
            default:
                throw new Exception("оператор не удовлетворяет заданию");
        }

        if (isRoman) {
            return arabicToRoman(result);
        }
        return String.valueOf(result);
    }

    private static final String[] ROMAN_NUMERALS = {"X", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    private static boolean isRomanNumeral(String input) {
        input = input.toUpperCase();

        for (String number: ROMAN_NUMERALS) {
            if (input.equals(number)) {
                return true;
            }
        }
        return false;
    }

    private static int romanToArabic(String input) throws Exception {
        input = input.toUpperCase();

        for (int i = 1; i < 10; i++) {
            if (input.equals(ROMAN_NUMERALS[i])) {
                return i;
            }
        }
        if (input.equals(ROMAN_NUMERALS[0])) {
            return 10;
        }
        throw new Exception("операнд не удовлетворяет заданию");
    }

    private static String arabicToRoman(int number) throws Exception {
        if (number < 1) {
            throw new Exception("в римской системе только положительные числа");
        }

        RomanNumeral[] romanNumerals = RomanNumeral.values();

        int i = romanNumerals.length - 1;
        String result = "";

        while ((number > 0) && (i >= 0)) {
            RomanNumeral currentSymbol = romanNumerals[i];
            if (currentSymbol.getValue() <= number) {
                result += currentSymbol.name();
                number -= currentSymbol.getValue();
            } else {
                i -= 1;
            }
        }

        return result;
    }

    private static int parseInt(String input) throws Exception {
        for (int number = 1; number <= 10; number++) {
            if (input.equals(String.valueOf(number))) {
                return number;
            }
        }
        throw new Exception("операнд не удовлетворяет заданию");
    }
}


enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
