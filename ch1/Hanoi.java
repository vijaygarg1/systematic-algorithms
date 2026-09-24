// Move n disks from source to target through aux (Towers of Hanoi).

import java.util.*;

public class Hanoi {
  public void Hanoi(int n, int source, int aux, int target) {
    if ((n == 1)) {
    } else {
      Hanoi((n - 1), source, target, aux);
      Hanoi((n - 1), aux, source, target);
    }
  }

  public static void main(String[] args) {
    int n = 0;
    int aux = 0;
    int target = 0;
    Hanoi prog = new Hanoi();
    prog.Hanoi(n, 0, aux, target);
  }
}