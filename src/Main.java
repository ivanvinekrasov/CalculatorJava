import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in); //инициализируем Scanner для считывания данных с клавиатуры
        String problem = scanner.nextLine();  //записываем введенные пользователем данных в переменную problem

        System.out.println(calc(problem));
    }

    public static String calc(String input) throws IOException {
        byte a;
        byte b;
        boolean isRoman;
        byte result = 0;
        byte romanSignsCount = (byte)input.replaceAll("[0-9+\\-*/\\s]", "").length();
        byte arabicSignsCount = (byte)input.replaceAll("[IVXLC+\\-*/\\s]", "").length();

        isRoman = romanSignsCount > 0;  //если количество римских символов больше 0, то работаем с римской системой счисления


        String sign = input.replaceAll("[0-9IVXLC\\s]", "");  //создаем переменную, которая сожержит знак операции


        //обрабатываем исключения
        if (sign.contains(",") | sign.contains(".")) {
            throw new IOException("Калькулятор умеет работать только с целыми числами");
        }

        if (sign.length() == 0) {
            throw new IOException("Строка не является математической операцией");
        }

        if (sign.length() > 1) {
            throw new IOException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if (romanSignsCount !=0 && arabicSignsCount!= 0 ) {
            throw new IOException("Используются одновременно разные системы счисления");
        }


        input = input.replaceAll("\\s", "");
        String[] data = input.split("[+\\-*/]");

        //присваиваем значения a и b типа byte. У римских чисел обрабатываем исключение, если таких констант нет в enum
        if (isRoman) {
            try {
                a = RomanNumeral.valueOf(data[0]).getArabic();
                b = RomanNumeral.valueOf(data[1]).getArabic();
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
            }
        } else {
            a = Byte.parseByte(data[0]);
            b = Byte.parseByte(data[1]);
        }

        //обрабатываем еще одно исключение для арабских чисел и возможно попавших констант enum RomanNumerical (40, 50, 100)
        if (a > 10 | a < 1 | b > 10 | b < 1) {
            throw new IOException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }


        //производим вычисление исходя из знака операции
        switch (sign){
            case "+" -> result = (byte) (a + b);
            case "-" -> result = (byte) (a - b);
            case "*" -> result = (byte) (a * b);
            case "/" -> result = (byte) (a / b);
        }

        //в зависимости от системы счисления возвращаем результат в требуемом виде
        if (isRoman) {
            return RomanNumeral.getRoman(result);
        } else {
            return String.valueOf(result);
        }
    }
}
