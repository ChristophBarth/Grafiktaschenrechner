package Parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

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
                else if (zeichen >= '1' && zeichen <= '9'){
                    return 5;
                }
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
                else if(zeichen == '-' || zeichen == '*' || zeichen == '/' || zeichen == '.' || zeichen == '+' || zeichen == '^' || (zeichen >= '0' && zeichen <= '9')) return 1; //TODO: Fallunterscheidung!
                else return -1;
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
                else if(zeichen == 'x') return 2;
                else return -1;
            case 9:
                if (zeichen == '+' || zeichen == '-' || zeichen == '*' || zeichen == '/' || zeichen == '.' || zeichen == '0' || zeichen == '^' || zeichen == 'x')
                    return 1;
                else if (zeichen >= '1' && zeichen <= '8') return 16;
                else return -1;
        }
        return -1;

    }

    public static double[] parseStringToCoefficients(String s){
        s = s.replace("*", "");

        double[] polynomial = new double[16];

        List<String> list = new ArrayList<>();
        String parts[] = s.split("((?=-))|((?=\\+))"); //splittet string, behaelt Delimiter

        for(int i = 0; i<parts.length; i++) { //konstante wird um x^0 erweitert

            if(!(parts[i].contains("x^"))) {
                if(parts[i].contains("x"))parts[i] = parts[i] + "^1";
                else parts[i] = parts[i] + "x^0";
            }
            if(parts[i].substring(0,1).equals("x")) parts[i] = "1"+parts[i];
            else if(parts[i].substring(0,2).equals("-x")){
                parts[i] = "-1" + parts[i].substring(1, parts[i].length());
            }
            if(parts[i].contains("/")){
                double zaehler = Double.parseDouble(parts[i].substring(0,parts[i].indexOf('/')));
                double nenner = Double.parseDouble(parts[i].substring(parts[i].indexOf('/')+1, parts[i].indexOf('x')));
            }
        }//Konstante Formatieren



        if(!parts[0].substring(0,1).equals("+") && !parts[0].substring(0,1).equals("-")){ //fügt + am Anfang hinzu
            parts[0] = "+" + parts[0];
        }

        int biggest_exponent = 0;
        for(int i = 0; i<parts.length; i++) {
            if(Integer.parseInt(parts[i].substring(parts[i].length() - 1)) > biggest_exponent) {
                biggest_exponent = Integer.parseInt(parts[i].substring(parts[i].length() - 1));
            }
        }

        boolean is_exponent = false;
        for(int j = biggest_exponent; j>=0;j--){
            for(int i = 0; i<parts.length; i++) {
                if(Integer.parseInt(parts[i].substring(parts[i].length() - 1)) == j) {
                    list.add(parts[i]);
                    is_exponent = true;
                }
            }
            if(is_exponent == false){
                list.add("+0x^" + Integer.toString(j));
            }
            is_exponent = false;
        }
        for(int k = list.size()-1; k>=0;k--){
            String coefficient = list.get(k).split("x")[0];
            if(coefficient.contains("/")){
                double zaehler = Double.parseDouble(coefficient.substring(0,coefficient.indexOf('/')));
                double nenner = Double.parseDouble(coefficient.substring(coefficient.indexOf('/')+1, coefficient.length()));
                polynomial[list.size()-1 - k] = zaehler/nenner;
            }else{
                polynomial[list.size()-1 - k] = Double.parseDouble(coefficient);
            }
        }


        return polynomial;
    }

    public static void main(String[] args) {
        parseStringToCoefficients("-1/2x^3");
    }

}
