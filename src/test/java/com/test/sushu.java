package com.test;

public class sushu {



    public static void main(String[] args) {


        for (int i = 1; i < 201; i++) {
            boolean prime = isPrime(i);
            if (prime) System.out.println(i);
        }

    }

    public static boolean isPrime(int n) {

        String s;
        if(n < 2) return false;

        for(int i = 2; i < n; ++i)

            if(n%i == 0) return false;

        return true;

    }
}
