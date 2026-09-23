// Recursive binary search on a sorted slice within [low..=high].

fn binary_search(a: &[i32], key: i32, low: i32, high: i32) -> i32 {
    if low > high { return -1; }
    let mid = (low + high) / 2;
    let m = a[mid as usize];
    if m == key { mid }
    else if m > key { binary_search(a, key, low, mid - 1) }
    else { binary_search(a, key, mid + 1, high) }
}

fn main() {
    let a = [1, 3, 5, 7, 9, 11, 13, 15];
    for &key in &[7, 1, 15, 4, 16] {
        println!("key={}: index = {}", key, binary_search(&a, key, 0, a.len() as i32 - 1));
    }
}
