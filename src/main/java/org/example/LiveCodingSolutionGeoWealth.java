package org.example;

public class LiveCodingSolutionGeoWealth {

    private static boolean solution(int targetSum, int[] numbers){
        int firstIndex = 0;
        int lastIndex = 0;
        int sum = numbers[0];

        while (firstIndex < numbers.length){
            if (sum == targetSum){
                return true;
            }
            if (sum > targetSum){
                sum -= numbers[firstIndex];
                firstIndex++;
                if (firstIndex > lastIndex){
                    lastIndex = firstIndex;
                    sum = numbers[lastIndex];
                }
            } else
            if(sum < targetSum){
                lastIndex++;
                sum += numbers[lastIndex];
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Should be true, but it is: " + solution(9, new int[]{ 2, 2, 2, 2, 2, 3}));
        System.out.println("Should be true, but it is: " + solution(3, new int[]{ 9, 8, 1, 2, 2, 3}));
    }

}


