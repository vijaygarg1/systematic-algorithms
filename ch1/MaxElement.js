/*
 * Return the largest element of A.
 *
 * Auto-generated from MaxElement.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class MaxElement {
  MaxElement(A) {
    this.A = A;
    this.n = this.A.length;
    let max_ = this.A[0];
    for (let i = 1; i <= ((this.A.length - 1)); i++) {
        if ((this.A[i] > max_)) {
            max_ = this.A[i];
        }
    }
    return max_;
  }
}

if (typeof module !== 'undefined') module.exports = MaxElement;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new MaxElement();
    const result = prog.MaxElement(A);
    console.log(JSON.stringify(result));
}
