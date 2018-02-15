class Pair extends Element {
	MyChar key;
	Element val;

	Pair(MyChar key, Element val) {
		this.key = key;
		this.val = val;
	}

	// print Pair in "(key, val)"
	public void Print() {
		System.out.print("(");
		key.Print();
		System.out.print(" ");
		val.Print();
		System.out.print(")");
	}
}