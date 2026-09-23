// Recursive FFT: evaluate a coefficient vector at the N-th roots of unity.
#include <complex>
#include <vector>
#include <iostream>
using namespace std;
typedef complex<double> cd;

vector<cd> fft(const vector<cd>& a, cd omega) {
  int N = a.size();
  if (N == 1) return a;
  vector<cd> aEven(N / 2), aOdd(N / 2);
  for (int i = 0; i < N / 2; i++) {
    aEven[i] = a[2 * i];
    aOdd[i] = a[2 * i + 1];
  }
  cd omega2 = omega * omega;
  vector<cd> fEven = fft(aEven, omega2);
  vector<cd> fOdd = fft(aOdd, omega2);
  vector<cd> result(N);
  cd z(1, 0);
  for (int k = 0; k < N / 2; k++) {
    result[k] = fEven[k] + z * fOdd[k];
    result[k + N / 2] = fEven[k] - z * fOdd[k];
    z *= omega;
  }
  return result;
}

int main() {
  vector<cd> a = {cd(1, 0), cd(2, 0), cd(0, 0), cd(0, 0)};
  cd omega(0, 1); // i, a 4th root of unity
  for (cd c : fft(a, omega)) cout << c.real() << (c.imag() >= 0 ? "+" : "") << c.imag() << "i\n";
  return 0;
}
