import java.util.Scanner;

class Main {
    private static final String[] ROMAN = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private static final int[] ARABIC = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static String calc(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("throws Exception"); //формат ввода дожен соответствовать 2 операндам и 1 оператору
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isArabic1 = isArabicNumeral(operand1);
        boolean isArabic2 = isArabicNumeral(operand2);

        if ((isArabic1 && !isArabic2) || (!isArabic1 && isArabic2)) {
            throw new IllegalArgumentException("throws Exception"); //запрет на использование одновременно римских и арабских чисел
        }

        int num1 = isArabic1 ? Integer.parseInt(operand1) : convertToArabic(operand1);
        int num2 = isArabic2 ? Integer.parseInt(operand2) : convertToArabic(operand2);

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("throws Exception"); //исключаем деление на 0
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("throws Exception");
        }

        return isArabic1 && isArabic2 ? String.valueOf(result) : convertToRoman(result);
    }

    private static boolean isArabicNumeral(String input) {
        try {
            int num = Integer.parseInt(input);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int convertToArabic(String input) {
        for (int i = 0; i < ROMAN.length; i++) {
            if (ROMAN[i].equals(input)) {
                return ARABIC[i];
            }
        }
        throw new IllegalArgumentException("throws Exception"); //исключаем римские числа не входящие  диапазон
    }
    private static String convertToRoman(int number){      // Перевод араб в рим
        String result = "";
        if (number <= 0) {
            throw new IllegalArgumentException("throws Exception"); //исключение 0 и отрицательных чисел для результата в римском формате
            }
        int value = 0;
        int[] arab = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arab.length; i++){
            value = number / arab[i];
            for (int j = 0; j < value; j++){
                result = result.concat(roman[i]);
            }

            number = number % arab[i];
        }

        return result;
        }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input:");

        boolean exitFlag = false;

        while (!exitFlag) {
            String input = scanner.nextLine();
            try {
                String result = calc(input);
                System.out.println("Output:\n" + result);
                exitFlag = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Output:\n" + e.getMessage());
                exitFlag = true;
            }
        }
        scanner.close();
    }
}