// Linear search for `key` in a slice.  First-occurrence index, or -1.

fn linear_search(a: &[i32], key: i32) -> i32 {
    for (i, &x) in a.iter().enumerate() {
        if x == key { return i as i32; }
    }
    -1
}

fn main() {
    let a = [4, 2, 7, 1, 9, 3];
    for &key in &[7, 4, 5, 9] {
        println!("key={}: index = {}", key, linear_search(&a, key));
    }
}
