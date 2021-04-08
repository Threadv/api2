package com;

import java.util.Arrays;

/**
 * @Date: 2019/3/13 09:42
 * @Description:
 */
public class quickSortTest {

    private static void quickSort(int[] arr, int low, int heigh) {
        int l = low;
        int h = heigh;

        int start = arr[low];

        while (l < h) {

            while (l < h && start <= arr[h])
                h--;
            if (l < h) {
                swap(arr, l, h);
                l++;
            }

            while (l < h && start >= arr[l])
                l++;
            if (l < h) {
                swap(arr, l, h);
                h--;
            }

        }

        if(l>low)quickSort(arr,low,l-1);
        if(h<heigh)quickSort(arr,l+1,heigh);
    }

    private static void swap(int[] arr, int l, int h) {

        int temp = arr[h];
        arr[h] = arr[l];
        arr[l] = temp;
    }

    private static boolean numberIsPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {

        boolean b = numberIsPrime(5);
        System.out.println(b);


    }
    static int getonly(int num){
        int number=0;
        String s=num+"";
        int len=s.length();
        if(len!=0){

            for(int i=0;i < len; i++){
                char a=s.charAt(i);
                if(a=='1'){
                    number++;
                }
            }
        }
        return number;
    }




}
