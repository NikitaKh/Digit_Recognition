package recognition;
import java.util.Scanner;

class NetworkLayers{

    private int[] resultsArray = new int[10];
    private static double learningRateCoefficient = 0.5;
    private double[][] outputNeurons = new double[10][10];

    private static double[][] idealNeurons = {
            {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1},  //0
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1},  //1
            {1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1},  //2
            {1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1},  //3
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1},  //4
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1},  //5
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},  //6
            {1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1},  //7
            {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},  //8
            {1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1}   //9
    };

    private double weights[][] = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    private static double[][] idealOutput = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //0
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, //1
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, //2
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, //3
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, //4
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, //5
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, //6
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //7
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, //8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}  //9
    };

    /* Method to calculate output neurons with the Sigmoid function */

    private void makeOutputNeurons(){
        double outputNeuron = 0;

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                for (int k = 0; k < idealNeurons[j].length; k++){
                    outputNeuron += idealNeurons[j][k] * weights[i][k];
                }
                outputNeuron = 1 / (1 + Math.exp(-outputNeuron)); // Segmoid function implementation
                outputNeurons[i][j] = outputNeuron;
                outputNeuron = 0;
            }
        }
    }

    public void learningModel(){
        boolean neuronCheck;
        double[][] deltaRules = new double[10][15];

        for(int position = 0; position < idealNeurons.length; position++){
            neuronCheck = checkValid(idealNeurons[position], position);

            while(neuronCheck == false){
                for(int i = 0; i < deltaRules.length; i++){
                    for (int  j = 0; j < deltaRules[i].length; j++){
                        deltaRules[i][j] = deltaRule(idealNeurons[i][j], idealNeurons[position][j], outputNeurons[position][i]);
                    }
                }
                //Adjust Weights
                adjustWeights(deltaRules,position);
                //re-calculate output o for each test from number 0 to 9
                makeOutputNeurons();
                //Check if we need to stop for that particular output o
                neuronCheck = checkValid(idealNeurons[position], position);
                //System.out.println("MY WEIGHTS--> "+ Arrays.toString(outputs));
                System.out.println("NEXT NEURON");
            }
            //now next neuron
            System.out.println("NEXT NEURON");
        }
    }

    public boolean checkValid(double[] outputNeuron, int outputNeuronPosition){
        boolean isIdeal = true;

        for(int i = 0; i < 10; i++){
            if(outputNeuron[i] != idealOutput[outputNeuronPosition][i]){
                isIdeal = false;
                break;
            }
        }
        return isIdeal;
    }

    public double deltaRule(double ai, double ojIdeal, double oj){
        return learningRateCoefficient * ai * (ojIdeal - oj);
    }

    public void adjustWeights(double[][] delta, int positionOutputBeingTested){
        double mean = 0;
        for(int j = 0; j < 15; j++){
            for(int i = 0; i < 10; i++){
                mean += delta[i][j];
            }
            mean = mean / 10;
            weights[positionOutputBeingTested][j] += mean;
            mean = 0;
        }
    }
}

class Menue {

    private Scanner sc = new Scanner(System.in);
    private char[][] charArray = new char[5][3];
    private int[][] intArray = new int[5][3];

    public void input(){
        System.out.println("Input grid:");
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

        for(int i = 0; i < intArray.length; i++){
            for(int j = 0; j < intArray[i].length; j++){
                System.out.print(intArray[i][j]);
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NetworkLayers newLayer = new NetworkLayers();
        newLayer.learningModel();
    }
}
