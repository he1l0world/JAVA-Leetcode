package org.leetcode.solutions;

import java.util.HashMap;
import java.util.Optional;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap();
        for(int currentIndex = 0; currentIndex < nums.length; currentIndex++){
            Integer previouslyIndex = map.putIfAbsent(nums[currentIndex], currentIndex);   // put (each num, key) in a map
            if(Optional.ofNullable(previouslyIndex).isPresent() && nums[currentIndex] + nums[previouslyIndex] == target)
                return new int[]{previouslyIndex, currentIndex};
            int expectedNum = target - nums[currentIndex];
            Integer expectedIndex = map.getOrDefault(expectedNum, null);
            if( Optional.ofNullable(expectedIndex).isPresent() && currentIndex != expectedIndex )
                return new int[]{currentIndex, map.get(expectedNum)};
        }

        return null;
    }
}
