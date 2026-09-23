// Dutch-flag (Three-Way) Partition: less / equal / greater regions.

fn three_way_partition(a: &mut [i32], pivot: i32) {
    let (mut lo, mut mid, mut hi) = (0usize, 0usize, a.len() as isize - 1);
    while (mid as isize) <= hi {
        if a[mid] < pivot       { a.swap(lo, mid); lo += 1; mid += 1; }
        else if a[mid] > pivot  { a.swap(mid, hi as usize); hi -= 1; }
        else                    { mid += 1; }
    }
}

fn main() {
    let mut a = [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5];
    three_way_partition(&mut a, 5);
    println!("{:?}", a);
}
