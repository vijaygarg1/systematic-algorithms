// Parallel-style QuickSort (composed with `;` for the sequential build).

fn partition(lo: isize, hi: isize, g: &mut [i32]) -> isize {
    let pivot = g[hi as usize];
    let mut i: isize = lo - 1;
    for j in lo..hi {
        if g[j as usize] <= pivot {
            i += 1;
            g.swap(i as usize, j as usize);
        }
    }
    g.swap((i + 1) as usize, hi as usize);
    i + 1
}

fn quick_sort(lo: isize, hi: isize, g: &mut [i32]) {
    if lo < hi {
        let p = partition(lo, hi, g);
        quick_sort(lo, p - 1, g);
        quick_sort(p + 1, hi, g);
    }
}

fn main() {
    let mut g = vec![5, 2, 4, 6, 1, 3];
    let n = g.len() as isize;
    quick_sort(0, n - 1, &mut g);
    println!("{:?}", g);
}
