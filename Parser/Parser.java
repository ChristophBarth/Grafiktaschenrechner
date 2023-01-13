package Parser;

public class Parser {
    public static int[] parseStringToCoefficients(String input){
        return(null);
    }

    public static int getValidity(String input){
        int zustand =0;

        int[] endzustaende = {10, 15, 12, 2, 14, 6, 16, 5};

        for(int i=0;i<input.length();i++){
           zustand = folgeZustand(zustand, input.charAt(i));
        }

        for(int i=0;i<endzustaende.length;i++){
            if(zustand == endzustaende[i])return 0;
        }
        return zustand;

    }

    public static int folgeZustand(int zustand, char zeichen){

        switch (zustand) {
            case 0:
                if (zeichen == '*') return 11;
                else if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '.' || zeichen == '/' || zeichen == '^') return 1;
                else if (zeichen == '0') return 15;
                else if (zeichen >= '1' && zeichen <= '9') return 5;
                else if (zeichen == 'x') return 2;
                else return -1;
            case 1:
                return 1;
            case 10:
                if (zeichen == '*') return 11;
                else if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '.' || zeichen == '/' || zeichen == '^') return 1;
                else if (zeichen == '0') return 4;
                else if (zeichen >= '1' && zeichen <= '9') return 10;
                else if (zeichen == 'x') return 2;
                else return -1;
            case 11:
                if (zeichen == 'x') return 2;
                else return -1; //TODO: Fallunterscheidung!
            case 12:
                if (zeichen == '*' || zeichen == '^' || zeichen == 'x' || zeichen == '.' || zeichen == '/') return 1;
                else if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen >= '0' && zeichen <= '9') return 6;
            case 13:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '/' || zeichen == '.' || zeichen == 'x' || zeichen == '^')
                    return 1;
                else if (zeichen == '0') return 6;
                else if (zeichen >= '1' && zeichen <= '9') return 12;
                else return -1;
            case 14:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '^') return 1;
                else if (zeichen == '.') return 7;
                else if (zeichen == '/') return 9;
                else if (zeichen >= '0' && zeichen <= '9') return 3;
                else return -1;
            case 15:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '^' || (zeichen >= '0' && zeichen <= '9'))
                    return 1;
                else if (zeichen == '.') return 7;
                else if (zeichen == '/') return 9;
                else if (zeichen == 'x') return 2;
                else return -1;
            case 16:
                if (zeichen == '*') return 11;
                else if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '.' || zeichen == '/' || zeichen == '^') return 1;
                else if (zeichen >= '0' && zeichen <= '9') return 16;
                else if (zeichen == 'x') return 2;
                else return -1;
            case 2:
                if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '^') return 13;
                else if (zeichen == '*' || zeichen == '.' || zeichen == 'x' || (zeichen >= '0' && zeichen <= '9'))
                    return 1;
                else return -1;
            case 3:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '.' || zeichen == '^' || zeichen == 'x')
                    return 1;
                else if (zeichen == '/') return 9;
                else if (zeichen >= '0' && zeichen <= '9') return 3;
                else return -1;
            case 4:
                if (zeichen == '*' || zeichen == '^' || zeichen == 'x' || zeichen == '.' || zeichen == '/') return 1;
                else if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '0') return 4;
                else if (zeichen >= '1' && zeichen <= '9') return 10;
                else return -1;
            case 5:
                if (zeichen == '*') return 11;
                else if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '.') return 7;
                else if (zeichen == '/') return 9;
                else if (zeichen >= '0' && zeichen <= '9') return 5;
                else if (zeichen == '^') return 1;
                else if (zeichen == 'x') return 2;
                else return -1;
            case 6:
                if (zeichen == '+' || zeichen == '-') return 8;
                else if (zeichen == '*' || zeichen == '.' || zeichen == '/' || (zeichen >= '0' && zeichen <= '9') || zeichen == '^' || zeichen == 'x')
                    return 1;
                else return -1;
            case 7:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '/' || zeichen == '.' || zeichen == '^' || zeichen == 'x')
                    return 1;
                else if (zeichen == '0') return 0;
                else if (zeichen >= '1' && zeichen <= '9') return 10;
                else return -1;
            case 8:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '/' || zeichen == '.' || zeichen == '^')
                    return 1;
                else if (zeichen == '0') return 14;
                else if (zeichen >= '1' && zeichen <= '9') return 5;
                else return -1;
            case 9:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '/' || zeichen == '.' || zeichen == '0' || zeichen == '^' || zeichen == 'x')
                    return 1;
                else if (zeichen >= '1' && zeichen <= '8') return 16;
                else return -1;
        }
        return -1;

    }

}
