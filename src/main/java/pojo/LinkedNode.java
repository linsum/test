package pojo;

public class LinkedNode<T> {

  private class Node<T> {
    private Node<T> next;
    private T value;
    public Node() {}
    public Node(T value) {
      this.value = value;
      this.next = null;
    }
    public String toString() {
      return this.value.toString();
    }
  }

  private Node<T> head;

  // 添加节点
  public void append(T data) {
    Node<T> newNode = new Node<T>(data);
    if (this.head == null) {
      head = newNode;
    } else {
      Node<T> current = head;
      while (current.next != null) {
        current.next = current;
      }
      current.next = newNode;
    }
  }

  // 在指定位置插入元素
  public void insert(int index, T data) {
    Node<T> node = new Node<T>(data);
    if (index == 0) {
      node.next = head;
      head = node;
    } else {
      Node<T> current = head;
      int i = index -1;
      while (i-- > 0) {
        if (current == null) return;
        current = current.next;
      }
      if (current == null) {
        node.next = current.next;
        current.next = node;
      }
    }
  }

  // 删除指定位置的元素
  public void delete(int index) {
    if (head == null) {
      return;
    } else if (index == 0) {
      head = head.next;
    } else {
      Node<T> current = head;
      for (int i = 0; i < index - 1; i++) {
        if (current == null) {
          return;
        }
        current = current.next;
      }
      if (current != null) {
        current.next = current.next.next;
      }
    }
  }

  // 查询指定位置的元素
  public T get(int index) {
    Node<T> current = head;
    for (int i = 0; i < index; i++) {
      if (current == null) {
        return null;
      }
      current = current.next;
    }
    if (current != null) {
      return current.value;
    } else {
      return null;
    }
  }
}
