// Interval partition: for each interval (sorted by start time), reuse
// the earliest-free room via a min-heap of (finishTime, roomNumber)
// pairs -- extract-min when its finish time is <= the current start
// time, else open a new room.

use std::cmp::Reverse;
use std::collections::BinaryHeap;

fn partition(s: &[i32], f: &[i32]) -> Vec<i32> {
    let n = s.len();
    let mut g = vec![0i32; n];
    let mut heap: BinaryHeap<Reverse<(i32, i32)>> = BinaryHeap::new();
    let mut num_rooms: i32 = 0;
    for j in 0..n {
        let r = match heap.peek() {
            Some(Reverse((finish, _))) if *finish <= s[j] => {
                let Reverse((_, room)) = heap.pop().unwrap();
                room
            }
            _ => { let room = num_rooms; num_rooms += 1; room }
        };
        g[j] = r + 1;
        heap.push(Reverse((f[j], r)));
    }
    g
}

fn main() {
    let s = [1, 4, 7];
    let f = [2, 5, 8];
    println!("rooms: {:?}", partition(&s, &f));
}
