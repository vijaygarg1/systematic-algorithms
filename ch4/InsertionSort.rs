// Insertion Sort: walk each new element into its place via swaps.

fn insertion_sort(a: &mut [i32]) {
    for i in 1..a.len() {
        let mut j = i;
        while j > 0 && a[j - 1] > a[j] { a.swap(j - 1, j); j -= 1; }
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    insertion_sort(&mut a);
    println!("{:?}", a);
}
