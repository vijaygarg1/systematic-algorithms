// Min-heap on an int[]: build, heapify, insert, extract-min.

import java.util.*;

public class Heap {
  public void build(int[] A, int n) {
    int i = ((n / 2) - 1);
    while ((i >= 0)) {
      heapify(A, i, n);
      i = (i - 1);
    }
  }

  public void heapify(int[] A, int i, int n) {
    int smallest = i;
    int left = ((2 * i) + 1);
    int right = ((2 * i) + 2);
    if (((left < n) && (A[left] < A[smallest]))) {
      smallest = left;
    }
    if (((right < n) && (A[right] < A[smallest]))) {
      smallest = right;
    }
    if ((smallest != i)) {
      { int tmp = A[i]; A[i] = A[smallest]; A[smallest] = tmp; }
      heapify(A, smallest, n);
    }
  }

  public int insert(int[] A, int key, int n) {
    n = (n + 1);
    int i = (n - 1);
    while (((i > 0) && (A[((i - 1) / 2)] > key))) {
      A[i] = A[((i - 1) / 2)];
      i = ((i - 1) / 2);
    }
    A[i] = key;
    return n;
  }

  public int extractMin(int[] A, int n) {
    if ((n < 1)) {
      return (-2147483648);
    }
    int min = A[0];
    A[0] = A[(n - 1)];
    n = (n - 1);
    heapify(A, 0, n);
    return min;
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    Heap prog = new Heap();
    prog.build(A, A.length);
    System.out.println(Arrays.toString(A));
  }
}