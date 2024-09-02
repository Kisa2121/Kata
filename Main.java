import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*([+\\-*/])\\s*(\"?([^\"\\s]+)\"?)");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            String firstWord = matcher.group(1);
            String sign = matcher.group(2);
            String secondWord = matcher.group(3);

            try {
                areWordsQuoted(firstWord, secondWord, sign);
                stringCalculate(firstWord, sign, secondWord);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Ошибка: Неверный формат ввода");
        }
    }

    public static void areWordsQuoted(String firstWord, String secondWord, String sign) throws Exception {
        if (firstWord.length() > 10) {
            throw new Exception("Длина первого слова превышает 10 символов");
        }

        if (sign.equals("*") || sign.equals("/")) {
            if (isNumeric(firstWord)) {
                throw new Exception("Первое слово не должно быть числом");
            }
            try {
                int secondInt = Integer.parseInt(secondWord);
                if (secondInt < 1 || secondInt > 10) {
                    throw new Exception("Вторая строка, являющаяся числом, должна быть от 1 до 10");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Вторая строка должна быть числом для операций '*' и '/'");
            }
        } else {
            if (!secondWord.startsWith("\"") || !secondWord.endsWith("\"")) {
                throw new Exception("Вторая строка, не являющаяся числом, не выделена кавычками");
            }

            secondWord = secondWord.substring(1, secondWord.length() - 1); // Убираем кавычки для проверки длины

            if (secondWord.length() > 10) {
                throw new Exception("Длина второго слова превышает 10 символов");
            }
        }
    }

    public static void stringCalculate(String firstWord, String sign, String secondWord) throws Exception {
        switch (sign) {
            case "+":
                if (secondWord.startsWith("\"") && secondWord.endsWith("\"")) {
                    secondWord = secondWord.substring(1, secondWord.length() - 1); // Убираем кавычки для операции
                }
                System.out.println("\"" + firstWord + secondWord + "\"");
                break;
            case "-":
                if (secondWord.startsWith("\"") && secondWord.endsWith("\"")) {
                    secondWord = secondWord.substring(1, secondWord.length() - 1); // Убираем кавычки для операции
                }
                String diff = firstWord.replace(secondWord, "");
                System.out.println("\"" + diff + "\"");
                break;
            case "*":
                try {
                    int multiplier = Integer.parseInt(secondWord);
                    if (multiplier < 1 || multiplier > 10) {
                        throw new Exception("Множитель должен быть от 1 до 10");
                    }
                    String result = firstWord.repeat(multiplier);
                    if (result.length() > 40) {
                        System.out.println("\"" + result.substring(0, 40) + "..." + "\"");
                    } else {
                        System.out.println("\"" + result + "\"");
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Вторая строка должна быть числом для операции '*'");
                }
                break;
            case "/":
                try {
                    int divisor = Integer.parseInt(secondWord);
                    if (divisor < 1 || divisor > 10) {
                        throw new Exception("Делитель должен быть от 1 до 10");
                    }
                    int division = firstWord.length() / divisor;
                    System.out.println("\"" + firstWord.substring(0, division) + "\"");
                } catch (NumberFormatException e) {
                    throw new Exception("Вторая строка должна быть числом для операции '/'");
                }
                break;
            default:
                throw new Exception("Неподдерживаемая операция");
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}