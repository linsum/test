package pojo;

public class TreeNode<T> {
  public T val;
  public TreeNode<T> left;
  public TreeNode<T> right;
  public TreeNode() {}
  public TreeNode(T val){
    this.val=val;
  }
  public TreeNode(T val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
  public String toString() {
    return this.val.toString();
  }
}
