// Huffman coding: repeatedly merge the two least-probable nodes into a binary tree.

import java.util.*;

public class Huffman {
  static class Node implements Comparable<Node> {
    double prob;
    int symbol;
    Node left, right;
    Node(double prob, int symbol) { this.prob = prob; this.symbol = symbol; }
    Node(double prob, Node left, Node right) {
      this.prob = prob;
      this.symbol = -1;
      this.left = left;
      this.right = right;
    }
    public int compareTo(Node other) { return Double.compare(this.prob, other.prob); }
  }

  public String[] huffmanCodes(double[] p) {
    int n = p.length;
    PriorityQueue<Node> Q = new PriorityQueue<>();
    for (int i = 0; i < n; i++) {
      Q.add(new Node(p[i], i));
    }
    for (int i = 1; i <= n - 1; i++) {
      Node x = Q.poll();
      Node y = Q.poll();
      Node z = new Node(x.prob + y.prob, x, y);
      Q.add(z);
    }
    Node root = Q.poll();
    String[] codes = new String[n];
    assignCodes(root, "", codes);
    return codes;
  }

  private void assignCodes(Node node, String prefix, String[] codes) {
    if (node.symbol != -1) {
      codes[node.symbol] = prefix.isEmpty() ? "0" : prefix;
      return;
    }
    assignCodes(node.left, prefix + "0", codes);
    assignCodes(node.right, prefix + "1", codes);
  }

  public static void main(String[] args) {
    double[] p = new double[] {0.45, 0.13, 0.12, 0.16, 0.09, 0.05};
    Huffman prog = new Huffman();
    String[] result = prog.huffmanCodes(p);
    System.out.println(Arrays.toString(result));
  }
}
