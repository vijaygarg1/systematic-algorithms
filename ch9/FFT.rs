// Recursive FFT: evaluate a coefficient vector at the N-th roots of unity.
use std::ops::{Add, Sub, Mul};

#[derive(Clone, Copy, Debug)]
struct Complex { re: f64, im: f64 }

impl Add for Complex { type Output = Complex; fn add(self, o: Complex) -> Complex { Complex { re: self.re + o.re, im: self.im + o.im } } }
impl Sub for Complex { type Output = Complex; fn sub(self, o: Complex) -> Complex { Complex { re: self.re - o.re, im: self.im - o.im } } }
impl Mul for Complex { type Output = Complex; fn mul(self, o: Complex) -> Complex { Complex { re: self.re * o.re - self.im * o.im, im: self.re * o.im + self.im * o.re } } }

fn fft(a: &[Complex], omega: Complex) -> Vec<Complex> {
    let n = a.len();
    if n == 1 { return vec![a[0]]; }
    let a_even: Vec<Complex> = a.iter().step_by(2).cloned().collect();
    let a_odd: Vec<Complex> = a.iter().skip(1).step_by(2).cloned().collect();
    let omega2 = omega * omega;
    let f_even = fft(&a_even, omega2);
    let f_odd = fft(&a_odd, omega2);
    let mut result = vec![Complex { re: 0.0, im: 0.0 }; n];
    let mut z = Complex { re: 1.0, im: 0.0 };
    for k in 0..n / 2 {
        result[k] = f_even[k] + z * f_odd[k];
        result[k + n / 2] = f_even[k] - z * f_odd[k];
        z = z * omega;
    }
    result
}

fn main() {
    let a = vec![
        Complex { re: 1.0, im: 0.0 },
        Complex { re: 2.0, im: 0.0 },
        Complex { re: 0.0, im: 0.0 },
        Complex { re: 0.0, im: 0.0 },
    ];
    let omega = Complex { re: 0.0, im: 1.0 }; // i, a 4th root of unity
    for c in fft(&a, omega) {
        println!("{:.3}{:+.3}i", c.re, c.im);
    }
}
