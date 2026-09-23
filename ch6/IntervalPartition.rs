// Interval partition: greedy by earliest start time.

fn partition(s: &[i32], f: &[i32]) -> Vec<i32> {
    let n = s.len();
    let mut g = vec![0i32; n];
    let mut room_finish = vec![0i32; n];
    let mut num_rooms = 0;
    for j in 0..n {
        let mut r: isize = -1;
        for i in 0..num_rooms {
            if r == -1 && room_finish[i] <= s[j] { r = i as isize; }
        }
        if r == -1 { r = num_rooms as isize; num_rooms += 1; }
        g[j] = r as i32 + 1;
        room_finish[r as usize] = f[j];
    }
    g
}

fn main() {
    let s = [1, 4, 7];
    let f = [2, 5, 8];
    println!("rooms: {:?}", partition(&s, &f));
}
