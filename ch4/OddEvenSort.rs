// Odd-Even (transposition) sort.

fn odd_even_sort(a: &mut [i32]) {
    let n = a.len();
    let mut changed = true;
    while changed {
        changed = false;
        let mut j = 1;
        while j + 1 < n {
            if a[j] > a[j + 1] { a.swap(j, j + 1); changed = true; }
            j += 2;
        }
        let mut j = 0;
        while j + 1 < n {
            if a[j] > a[j + 1] { a.swap(j, j + 1); changed = true; }
            j += 2;
        }
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    odd_even_sort(&mut a);
    println!("{:?}", a);
}
