class Number {
    private int value;
    private String operand;


    Number(String operand) {
        this.operand = operand;
    }


    public boolean checkArabic() throws CalcInputException{
        int int_1;
        try {
            int_1 = Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            return false; //Операнд не является арабским числом
        }
        if(int_1 > 10 || int_1 < 1)
            throw new CalcInputException("Число \""+ operand + "\" находится вне разрешенного диапазона [1;10].") ;
        else {
            this.value = int_1;
            return true; //Операнд является арабским числом
        }
    }

    public boolean checkRoman() {
        RomanNumber[] mas = RomanNumber.values();
        for(int i = 0; i < 10; i++){ // проверка является ли введенное число римским в диапазоне [1;10] и нахождение его значения в арабской системе счисления
            if(mas[i].toString().equals(operand)) {
                this.value = mas[i].inArabic();
                return true; // Операнд является римским числом
            }
        }
        return false; // операнд не является римским числом или является римским числом, которое лежит вне диапазона [1;10]
    }

    public int getValue() {
        return value;
    }
}
