# Recursive FFT: evaluate a coefficient vector at the N-th roots of unity.
import cmath

def fft(a, omega):
    N = len(a)
    if N == 1:
        return [a[0]]
    a_even = a[0::2]
    a_odd = a[1::2]
    fft_even = fft(a_even, omega * omega)
    fft_odd = fft(a_odd, omega * omega)
    result = [0] * N
    z = 1
    for k in range(N // 2):
        result[k] = fft_even[k] + z * fft_odd[k]
        result[k + N // 2] = fft_even[k] - z * fft_odd[k]
        z *= omega
    return result

def fft_multiply(a, b):
    """Multiply two coefficient vectors (polynomials) via FFT."""
    n = len(a) + len(b) - 1
    N = 1
    while N < n:
        N *= 2
    a_pad = a + [0] * (N - len(a))
    b_pad = b + [0] * (N - len(b))
    omega = cmath.exp(2j * cmath.pi / N)
    fa = fft(a_pad, omega)
    fb = fft(b_pad, omega)
    fc = [fa[k] * fb[k] for k in range(N)]
    c = fft(fc, 1 / omega)
    return [round((v / N).real) for v in c[:n]]

if __name__ == "__main__":
    omega = 1j  # 4th root of unity
    a = [1, 2, 0, 0]
    print([round(v.real, 6) + round(v.imag, 6) * 1j for v in fft(a, omega)])
    # Polynomial multiplication: (1 + 2x)(1 + 3x) = 1 + 5x + 6x^2
    print(fft_multiply([1, 2], [1, 3]))
