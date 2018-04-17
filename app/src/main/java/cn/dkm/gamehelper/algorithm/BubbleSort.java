package cn.dkm.gamehelper.algorithm;

/**
 * Created by 丁坤明 on 2018/2/20.
 */

public class BubbleSort {


    public static int[] bubbleSort(int[] array){

        if(array == null || array.length < 2){
            return array;
        }

        for(int i = 0; i < array.length; i++){
            for (int j = i; j < array.length - 1; j++){

                if(array[j+1] > array[j]){
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }

        return array;
    }

    public static void printArray(int[] array){
        if(array == null || array.length == 0){
            return;
        }

        for (int i =0 ; i < array.length; i++){
            System.out.print(array[i]+" ");
        }

        System.out.println();
    } 


    public static void main(String[] args){
        int[] array = new int[]{1,2,4,3,5,6};

        bubbleSort(array);

        printArray(array);

    }
}

