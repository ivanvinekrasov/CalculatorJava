import java.io.IOException;

enum RomanNumeral {
    I((byte)1), II((byte)2), III((byte)3), IV((byte)4), V((byte)5), VI((byte)6), VII((byte)7),
    VIII((byte)8), IX((byte)9), X((byte)10), XL((byte)40), L((byte)50), C((byte)100); //M(1000)


    final byte arabic;

    //кастомный конструктор для констант данного enum
    RomanNumeral(byte arabic){
        this.arabic = arabic;
    }


    //метод для получения римского числа из арабского
    static String getRoman(byte arabic) throws IOException {

        if (arabic <= 0) {
            throw new IOException("В римской системе нет отрицательных чисел или нуля");
        }

        StringBuilder buf = new StringBuilder();

        //конструктор римских чисел. задействуя цикл for мы начинаем с самой большой константы C и заканчиваем I,
        //во вложенном цикле while, пока данное арабское число >= значению данной константы, мы добавляем ее в строку
        //buf и вычитаем ее значение из начального арабского числа и тд,
        //после мы переходим к следующей с конца константе L и повторяем это все аналогично
        //для последующих констант. при завершении циклов мы получаем необходимое представление в римской системе
        RomanNumeral[] values = RomanNumeral.values();
        for (int i = values.length-1; i>=0; i--) {
            while (arabic >= values[i].arabic) {
                buf.append(values[i]);
                arabic -= values[i].arabic;
            }
        }
        return buf.toString();
    }

    //метод для получения арабского значения из константы enum
    byte getArabic(){
        return arabic;
    }
}