import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String firstWord = sc.next();
        String sign = sc.next();
        String secondWord = sc.next();
        try {
            areWordsQuoted(firstWord, secondWord);
            firstWord = firstWord.replace("\"", "");
            secondWord = secondWord.replace("\"", "");
            stringCalculate(firstWord, sign, secondWord);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static void areWordsQuoted(String firstWord, String secondWord) throws Exception {
        String regex = "\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(firstWord);
        Matcher matcher2 = pattern.matcher(secondWord);

        if (!matcher1.find()) {
            throw new Exception("Первое слово не выделено кавычками");
        }

        if (firstWord.length() > 12) {
            throw new Exception("Длина первого слова превышает 10 символов");
        }

        try {
            int secondInt = Integer.parseInt(secondWord);
            if (matcher2.find()) {
                throw new Exception("Вторая строка, являющаяся числом, выделена кавычками");
            }
            if (secondInt <= 1 || secondInt >= 10) {
                throw new Exception("Вторая строка, являющаяся числом, должна быть от 1 до 10");
            }
        } catch (NumberFormatException e) {
            if (!matcher2.find()) {
                throw new Exception("Вторая строка, не являющаяся числом, не выделена кавычками");
            }
            if (secondWord.length() > 10) {
                throw new Exception("Длина второго слова превышает 10 символов");
            }
        }
    }

    public static void stringCalculate(String firstWord, String sign, String secondWord) throws Exception {
        switch (sign) {
            case "+":
                System.out.println("\"" + firstWord + secondWord + "\"");
                break;
            case "-":
                String diff = firstWord.replace(secondWord, "");
                System.out.println("\"" + diff + "\"");
                break;
            case "*":
                if (!(firstWord.matches(".*\\d.*"))) {
                    if (secondWord.matches("\\d+")) {
                        int secondInt = Integer.parseInt(secondWord);
                        StringBuilder multiply = new StringBuilder();
                        for (int i = 0; i < secondInt; i++) {
                            multiply.append(firstWord);
                        }
                        if (multiply.length() > 40) {
                            System.out.println("\"" + multiply.substring(0, 40) + "..." + "\"");
                        } else {
                            System.out.println("\"" + multiply + "\"");
                        }
                    }
                }
                break;
            case "/":
                if (!(firstWord.matches(".*\\d.*"))) {
                    if (secondWord.matches("\\d+")) {
                        int secondInt = Integer.parseInt(secondWord);
                        int division = firstWord.length() / secondInt;
                        System.out.println("\"" + firstWord.substring(0, division) + "\"");
                        break;
                    }
                }
                break;
            default:
                throw new Exception("Неподдерживаемая операция");
        }
    }
}