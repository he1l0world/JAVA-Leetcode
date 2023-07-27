package org.leetcode.solutions;

import java.util.PriorityQueue;

public class FindKthLargest {
    public static int findKthLargest(int[] nums, int k) {
        //Time complexity is O(nlogk)
        //Space complexity is O(k)
        PriorityQueue<Integer> minHeap = new PriorityQueue(); //default priorityQueue is min Heap unless we define the comparator
        //when using priorityQueue for maxHeap we need to give the comparator and also note to check capacity larger than 1
        for(int num : nums){
            minHeap.add(num);
            if(minHeap.size() > k){
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }

}
