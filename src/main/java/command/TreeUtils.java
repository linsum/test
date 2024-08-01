package command;

import pojo.TreeNode;

import java.util.*;

public class TreeUtils {
  // 根据前序、中序构造二叉树
  public static TreeNode<Integer> buildTree1(int[] preorder, int[] inorder) {
    if (preorder == null || preorder.length == 0) {
      return null;
    }
    TreeNode<Integer> root = new TreeNode<>(preorder[0]);
    Deque<TreeNode<Integer>> stack = new LinkedList<TreeNode<Integer>>();
    stack.push(root);
    int inorderIndex = 0;
    for (int i = 1; i < preorder.length; i++) {
      int preorderVal = preorder[i];
      TreeNode<Integer> node = stack.peek();
      if (node.val != inorder[inorderIndex]) {
        node.left = new TreeNode(preorderVal);
        stack.push(node.left);
      } else {
        while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
          node = stack.pop();
          inorderIndex++;
        }
        node.right = new TreeNode(preorderVal);
        stack.push(node.right);
      }
    }
    return root;
  }

  // 根据中序、后序构造二叉树
  public static TreeNode buildTree2(int[] inorder, int[] postorder) {
    if (postorder == null || postorder.length == 0) return null;
    Deque<TreeNode<Integer>> stack = new ArrayDeque<TreeNode<Integer>>();
    int length = postorder.length - 1;
    TreeNode<Integer> root = new TreeNode<Integer>(postorder[length]);
    stack.push(root);
    int inorderIndex = length;
    for (int i = length - 1; i >= 0; i--) {
      TreeNode<Integer> node = stack.peek();
      int postorderVal = postorder[i];
      if (node.val != inorder[inorderIndex]) {
        node.right = new TreeNode(postorderVal);
        stack.push(node.right);
      } else {
        while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
          node = stack.pop();
          inorderIndex--;
        }
        node.left = new TreeNode(postorderVal);
        stack.push(node.left);
      }
    }
    return root;
  }

  // 根据层序构造二叉树，返回根节点，数组自顶向下
  public static TreeNode buildTree3(Integer[] level) {
    if (level == null || level.length == 0) return null;
    Queue<TreeNode> queue = new LinkedList<>();
    TreeNode<Integer> root = new TreeNode<Integer>(level[0]);
    queue.offer(root);
    int index = 0;
    while (index < level.length) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        if (++index >= level.length) break;
        node.left = createNode(level[index], false);
        if (node.left != null) queue.offer(node.left);
        if (++index >= level.length) break;
        node.right = createNode(level[index], false);
        if (node.right != null) queue.offer(node.right);
      }
    }
    return root;
  }

  public static <T> TreeNode<T> createNode(T val, boolean bNull) {
    if ((val != null && !bNull) || bNull) {
      return new TreeNode<T>(val);
    }
    return null;
  }

  public static int rangeSumBST(TreeNode<Integer> root, int low, int high) {
    if (root == null) return 0;
    if (root.val > high) return rangeSumBST(root.left, low, high);
    if (root.val < low) return rangeSumBST(root.right, low, high);
    return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
  }


  // ------------------------------------------------------------------------------------------

  // leetcode-94. 二叉树的中序遍历
  // 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
  public static List<Integer> inorderTraversal(TreeNode<Integer> root) {
    List<Integer> list = new ArrayList<Integer>();
    Deque<TreeNode> stk = new LinkedList<TreeNode>();
    while (root != null || !stk.isEmpty()) {
      while (root != null) {
        stk.push(root);
        root = root.left;
      }
      root = stk.pop();
      list.add(root.val);
      root = root.right;
    }
    return list;
  }

}
