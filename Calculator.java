import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Напишите свой пример: ");
        String example  = in.nextLine();

        System.out.println(calc(example));
        in.close();

    }

    //калькулятор
    public static String calc(String input){
        input = input.replace(" ", "");
        int indexSymCalc = -1;
        char[] listSymbolCalculation = {'+', '-', '*', '/'};
        int i = listSymbolCalculation.length-1;
        //нахождение индекса символа вычисления
        while (indexSymCalc < 0 && i >= 0){
            indexSymCalc  = input.indexOf(listSymbolCalculation[i]);
            i--;
        }
        //проверка на мат операцию
        if (indexSymCalc < 0){
            throw new IllegalArgumentException("т.к. строка не является математической операцией");
        }
        i = listSymbolCalculation.length-1;
        int testIndex = -1;
        while (testIndex < 0 && i >= 0){
            testIndex  = input.indexOf(listSymbolCalculation[i],indexSymCalc+1);
            i--;
        }
        if (testIndex>-1){
            throw new IllegalArgumentException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String strFirstNum = input.substring(0,indexSymCalc);
        String strSecondNum = input.substring(indexSymCalc + 1);
        String symbolCalculation = input.substring(indexSymCalc, indexSymCalc+1);

        if (strSecondNum.equals("0") && symbolCalculation.equals("/")){
            throw new ArithmeticException("т.к. вы пытаетесь поделить на ноль");
        }

        int firstNum;
        int secondNum;
        //проверка на разные системы счисления
        if (isNumeric(strFirstNum) && !isNumeric(strSecondNum) || (!isNumeric(strFirstNum) & isNumeric(strSecondNum))){
            throw new ArithmeticException("т.к. используются одновременно разные системы счисления");
        }

        if (isNumeric(strFirstNum) && isNumeric(strSecondNum)){
            firstNum = Integer.parseInt(strFirstNum);
            secondNum = Integer.parseInt(strSecondNum);
        }else {
            firstNum = romToArab(strFirstNum.toUpperCase());
            secondNum = romToArab(strSecondNum.toUpperCase());
        }
        if (firstNum > 10 || secondNum > 10){
            throw new IllegalArgumentException("т.к. введены слишком большие числа (>10)");
        }
        if (symbolCalculation.equals("-") && secondNum >= firstNum || symbolCalculation.equals("/") && secondNum >= firstNum){
            throw new ArithmeticException("т.к. в римской системе нет отрицательных и нулевых чисел");
        }
        if (isNumeric(strFirstNum) & isNumeric(strSecondNum)){
            return Integer.toString(calculatorNumbers(firstNum,secondNum,symbolCalculation));
        }else {
            return arabToRom(calculatorNumbers(firstNum,secondNum,symbolCalculation));
        }

    }

    //вычисление
    public static int calculatorNumbers(int firstNum, int secondNum, String symbolCalculation){
        return switch (symbolCalculation) {
            case "+" -> firstNum + secondNum;
            case "-" -> firstNum - secondNum;
            case "*" -> firstNum * secondNum;
            case "/" -> firstNum / secondNum;
            default -> throw new NumberFormatException("неверный формат");
        };
    }

    //проверка, чем является число
    public static boolean isNumeric(String number){
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    //перевод элемента римского числа в целочисленное значение
    public static int convertRoman(char element){
        return switch (element) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            default -> throw new NumberFormatException("неверный формат");
        };
    }

    //перевод римского числа в целочисленное значение
    public static int romToArab(String romanNumber){
        int finalNumber = 0;
        int lastNumber = 0;
        for (int x = romanNumber.length()-1; x >= 0; x--){
            int number = convertRoman(romanNumber.charAt(x));
            if (number >= lastNumber){
                finalNumber += number;
            } else {
                finalNumber -= number;
            }
            lastNumber = number;
        }
        return finalNumber;
    }

    //перевод целого числа в римское число
    public static String arabToRom(int intNumber){
        int  []numCollection = {1, 4, 5, 9, 10, 40,50, 90, 100};
        String []romNumCollection = {"I","IV","V","IX","X", "XL","L","XC", "C"};
        int i = numCollection.length -1;
        StringBuilder result = new StringBuilder();
        while (intNumber > 0){
            while (numCollection[i] > intNumber){
                i--;
            }
            result.append(romNumCollection[i]);
            intNumber -= numCollection[i];
        }
        return result.toString();
    }
}
