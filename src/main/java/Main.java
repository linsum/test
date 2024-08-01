import command.TreeUtils;
import command.Utils;

import java.util.List;

public class Main {
  public static void main(String args[]) {
//    int preorder{} = new int{} {3,5,6,2,7,4,1,0,9,10,8}; // 前序
//    int inorder{} = new int{} {6,5,7,2,4,3,9,0,10,1,8}; // 中序
//    TreeNode<Integer> node = Utils.buildTree1(preorder, inorder);

//    int inorder{} = new int{} {9,3,15,20,7};
//    int postorder{} = new int{} {9,15,7,20,3};
//    TreeNode<Integer> node = Utils.buildTree2(inorder, postorder);

//    Integer level{} = new Integer{} {10,5,15,3,7,13,18,1,null,6};
//    TreeNode<Integer> node = Utils.buildTree3(level);
//    int sum = Utils.rangeSumBST(node, 6 , 10);

//    System.out.println(node.val);
//    int cost{} = new int{} {6,7,4,4,4,5,6};
//    boolean flag = Utils.validPartition(cost);
//    int ans = Utils.minIncrements(7, cost);

//    int nums1{} = new int{} {1,2,3,0,0,0};
//    int nums2{} = new int{} {1};
//    int length = Utils.removeElement(nums2, 1);
//    int nextVisit{} = new int{} {5,4,8,7,10,2};
//    int res = Utils.firstDayBeenInAllRooms(nextVisit);


//    int nums{} = new int{} {5,4,8,7,10,2};
//    int res = Utils.minimumSum(nums);

//    String binary = "000110";
//    String res = Utils.maximumBinaryString(binary);
//
//    int nums[] = new int[]{9,16,30,23,33,35,9,47,39,46,16,38,5,49,21,44,17,1,6,37,49,15,23,46,38,9,27,3,24,1,14,17,12,23,43,38,12,4,8,17,11,18,26,22,49,14,9};
//    int edges[][] = new int[][]{{17,0},{30,17},{41,30},{10,30},{13,10},{7,13},{6,7},{45,10},{2,10},{14,2},{40,14},{28,40},{29,40},{8,29},{15,29},{26,15},{23,40},{19,23},{34,19},{18,23},{42,18},{5,42},{32,5},{16,32},{35,14},{25,35},{43,25},{3,43},{36,25},{38,36},{27,38},{24,36},{31,24},{11,31},{39,24},{12,39},{20,12},{22,12},{21,39},{1,21},{33,1},{37,1},{44,37},{9,44},{46,2},{4,46}};
//    int ans[] = Utils.getCoprimes(nums, edges);

//    int grid[][] = new int[][]{{0, 1},{0,0}};
//    int ans = Utils.findChampion(grid);

    int matrix[][] = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
    List<Integer> order = Utils.spiralOrder(matrix);
    System.out.println(order);
  }
}
