package org.leetcode.solutions;

import java.util.HashMap;
import java.util.Optional;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap();
        for(int index = 0; index < nums.length; index++){
            map.put(nums[index], index);   // put (each num, key) in a map
            int expectedNum = target - nums[index];
            int expectedIndex = map.getOrDefault(expectedNum, null);
            if( Optional.ofNullable(expectedIndex).isPresent() && index != expectedIndex )
                return new int[]{index, map.get(expectedNum)};
        }

        return null;
    }
}
