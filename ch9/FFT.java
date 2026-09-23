// Recursive FFT: evaluate a coefficient vector at the N-th roots of unity.

public class FFT {
  static class Complex {
    double re, im;
    Complex(double re, double im) { this.re = re; this.im = im; }
    Complex add(Complex o) { return new Complex(re + o.re, im + o.im); }
    Complex sub(Complex o) { return new Complex(re - o.re, im - o.im); }
    Complex mul(Complex o) { return new Complex(re * o.re - im * o.im, re * o.im + im * o.re); }
    public String toString() {
      return String.format("%.3f%+.3fi", re, im);
    }
  }

  public Complex[] fft(Complex[] a, Complex omega) {
    int N = a.length;
    if (N == 1) return new Complex[] { a[0] };
    Complex[] aEven = new Complex[N / 2], aOdd = new Complex[N / 2];
    for (int i = 0; i < N / 2; i++) {
      aEven[i] = a[2 * i];
      aOdd[i] = a[2 * i + 1];
    }
    Complex omega2 = omega.mul(omega);
    Complex[] fEven = fft(aEven, omega2);
    Complex[] fOdd = fft(aOdd, omega2);
    Complex[] result = new Complex[N];
    Complex z = new Complex(1, 0);
    for (int k = 0; k < N / 2; k++) {
      result[k] = fEven[k].add(z.mul(fOdd[k]));
      result[k + N / 2] = fEven[k].sub(z.mul(fOdd[k]));
      z = z.mul(omega);
    }
    return result;
  }

  public static void main(String[] args) {
    FFT prog = new FFT();
    Complex[] a = { new Complex(1, 0), new Complex(2, 0), new Complex(0, 0), new Complex(0, 0) };
    Complex omega = new Complex(0, 1); // i, a 4th root of unity
    for (Complex c : prog.fft(a, omega)) System.out.println(c);
  }
}
