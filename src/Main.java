import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws CalcInputException {
        Scanner scanner = new Scanner(System.in);
        String stringExpression = scanner.nextLine();
        String finalAnswer;
        finalAnswer = calc(stringExpression);
        System.out.println(finalAnswer);
    }

    public static String calc(String stringExpression) throws CalcInputException {
        String[] partsOfExpression = stringExpression.split(" ");
        String operators = "+-/*";
        if (partsOfExpression.length < 3 || partsOfExpression[1].length() != 1 || !operators.contains(partsOfExpression[1]))
            throw new CalcInputException("Запись не является операцией.");
        Number leftOperand = new Number(partsOfExpression[0]);
        Number rightOperand = new Number(partsOfExpression[2]);
        String strAnswer;
        String numberSystem = checkExpression(partsOfExpression, leftOperand, rightOperand); //Проверка правильности выражения и определение системы счисления
        int intAnswer = switch (partsOfExpression[1]) {
            case "+" -> leftOperand.getValue() + rightOperand.getValue();
            case "-" -> leftOperand.getValue() - rightOperand.getValue();
            case "*" -> leftOperand.getValue() * rightOperand.getValue();
            case "/" -> leftOperand.getValue() / rightOperand.getValue();
            default -> 0;
        };
        if (numberSystem.equals("Roman")) {
            if (intAnswer > 0)
                strAnswer = arabicToRoman(intAnswer);
            else
                throw new CalcInputException("Невозможно представить ответ \"" + intAnswer + "\" в римской системе счисления, т.к. в ней нет отрицательных чисел, а также числа \"0\".");
        }
        else
            strAnswer = String.valueOf(intAnswer);
        return strAnswer;
    }

    public static String checkExpression(String[] partsOfExpression, Number leftOperand, Number rightOperand) throws CalcInputException{
        if (partsOfExpression.length > 3)
            throw new CalcInputException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *).");
        if (leftOperand.checkRoman() && rightOperand.checkRoman()) // левый и правый операнды принадлежат римской системе счисления
            return "Roman";
        if (leftOperand.checkArabic() && rightOperand.checkArabic())// левый и правый операнды принадлежат арабской системе счисления
            return "Arabic";
        if (leftOperand.checkRoman() && rightOperand.checkArabic()    ||   leftOperand.checkArabic() && rightOperand.checkRoman())
            throw new CalcInputException("Левый и правый операнды принадлежат разным системам счисления.");
        throw new CalcInputException("Запись не является операцией, т.к. левый или правый операнд(или оба) не является арабским или римским числом, или его значение лежит вне диапазона [1;10].");
    }

    public static String arabicToRoman(int number){
        int num = 0;
        StringBuilder str = new StringBuilder();
        int cnt = String.valueOf(number).length() - 1;
        while (cnt != -1){
            num = number / (int) Math.pow(10,cnt) * (int) Math.pow(10,cnt); //получение страшего разряда числа и заполнение нулями младших (Например 165: -> 100).
            number = number % (int) Math.pow(10,cnt); //новое число - старое число без страшего разряда (Например: 165 -> 65)
            for(RomanNumber romanNumber: RomanNumber.values()) { //нахождение римского обозначения для полученного числа(num)
                if(romanNumber.inArabic() == num) {
                    str.append(romanNumber.name());
                }
            }
            cnt--;
        }
        return str.toString();
    }
}