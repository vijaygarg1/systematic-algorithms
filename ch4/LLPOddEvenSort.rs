// LLP-OddEven-Sort: forbidden(j) iff A[j] > A[j+1]; alternate odd/even rounds.

fn forbidden(j: usize, a: &[i32]) -> bool {
    j + 1 < a.len() && a[j] > a[j + 1]
}

fn llp_odd_even_sort(a: &mut [i32]) {
    let n = a.len();
    let mut changed = true;
    while changed {
        changed = false;
        let mut j = 1;
        while j + 1 < n {
            if forbidden(j, a) { a.swap(j, j + 1); changed = true; }
            j += 2;
        }
        let mut j = 0;
        while j + 1 < n {
            if forbidden(j, a) { a.swap(j, j + 1); changed = true; }
            j += 2;
        }
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    llp_odd_even_sort(&mut a);
    println!("{:?}", a);
}
