// BubbleSort: repeated passes of adjacent compare-and-swap.

fn bubble_sort(a: &mut [i32]) {
    let mut swapped = true;
    while swapped {
        swapped = false;
        for i in 0..a.len().saturating_sub(1) {
            if a[i] > a[i + 1] { a.swap(i, i + 1); swapped = true; }
        }
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    bubble_sort(&mut a);
    println!("{:?}", a);
}
