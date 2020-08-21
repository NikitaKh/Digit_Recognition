package recognition;
import java.util.Scanner;

class NetworkLayers{

    private char[][] charArray = new char[5][3];
    private int[][] intArray = new int[5][3];
    private static int[] bias = {-2, -1, 0, 1, 2, 3, 6};
    private int[] resultsArray = new int[10];

    private static int[][] weights = {
            {1, 1, 1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, 1, 1}, //0
            {-1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1}, //1
            {1, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, 1}, //2
            {1, 1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1}, //3
            {1, -1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1}, //4
            {1, 1, 1, 1, -1, -1, 1, 1, 1, -1, -1, 1, 1, 1, 1}, //5
            {1, 1, 1, 1, -1, -1, 1, 1, 1, 1, -1, 1, 1, 1, 1}, //6
            {1, 1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1}, //7
            {1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1}, //8
            {1, 1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1} //9
    };

    private void input(){
        System.out.println("Input grid:");
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < charArray.length; i++){
            String input = sc.nextLine();
            for(int j = 0; j < charArray[i].length; j++){
                charArray[i][j] = input.charAt(j);
            }
        }
        compareArrays();
    }

    private void compareArrays(){
        for(int i = 0; i < charArray.length; i++){
            for(int j = 0; j < charArray[i].length; j++) {
                if (charArray[i][j] == '_') {
                    intArray[i][j] = 0;
                } else
                    intArray[i][j] = 1;
            }
        }
    }

    public void calc(){
        input();
        int out_neuron = 0;
        int biggest_neuron = 0;
        int position = 0;
        int wcol = 0;

        for(int wrow = 0; wrow < 10; wrow++){
            for(int nrow = 0; nrow < intArray.length; nrow++){
                for(int ncol =0; ncol < intArray[nrow].length; ncol++){
                    out_neuron += intArray[nrow][ncol] * weights[wrow][wcol];
                    wcol++;
                }
            }

            if(wrow == 0 || wrow == 6 || wrow == 9){
                out_neuron += bias[1];
            } else if (wrow == 1){
                out_neuron += bias[6];
            } else if (wrow == 2){
                out_neuron += bias[3];
            } else if (wrow == 3 || wrow == 5){
                out_neuron += bias[2];
            } else if (wrow == 4){
                out_neuron += bias[4];
            } else if (wrow == 7){
                out_neuron += bias[5];
            } else if (wrow == 8){
                out_neuron += bias[0];
            }

            if(out_neuron > biggest_neuron){
                biggest_neuron = out_neuron;
                position = wrow;
            }
            wcol = 0;
            out_neuron = 0;
        }
        System.out.println("This number is " + position);
    }
}

public class Main {
    public static void main(String[] args) {
        // write your code here
        NetworkLayers neural = new NetworkLayers();
        neural.calc();
    }
}
