class Sequence extends Element {
	public Element elm;
	public Sequence next;

	public Sequence() {
		elm = null;
		next = null;
	}

	public void Print() {
		System.out.print("[ ");
		Sequence cur = this;
		while(cur != null) {
			cur.elm.Print();
			System.out.print(" ");
			cur = cur.next;
		}
		System.out.print("]");
	}

	public Element first() {
		return elm;
	}

	public Sequence rest() {
		return next;
	}

	public int length() {
		int len = 0;
		for(Sequence cur = this; cur != null; cur = cur.next)
			len++;
		return len;
	}

	public void add(Element elm, int pos) {
		// pos varification
		if(pos >= 0 && pos <= length()) {
			Sequence cur = this;
			Sequence pre = null;
			// go to pos
			for(int i = 0; i<pos; i++) {
				pre = cur;
				cur = cur.next;
			}
			// add element at the end of array
			if(cur == null) {
				cur = new Sequence();
				cur.elm = elm;
				cur.next = null;
				pre.next = cur;
			}
			// no element at pos
			else if (cur.elm == null) {
				cur.elm = elm;
				cur.next = null;
			}
			// insert
			else {
				Sequence s = new Sequence();
				s.elm = cur.elm;
				s.next = cur.next;
				cur.elm = elm;
				cur.next = s;
			}
		} else {
			System.err.println("Error: Invalid pos value");
			System.exit(1);
		}
	}

	public  void delete(int pos) {
		if(pos >= 0 && pos <= length()) {
			Sequence cur = this;
			for(int i = 0; i<pos; i++)
				cur = cur.next;
			if(cur.next != null) {
				cur.elm = cur.next.elm;
				cur.next = cur.next.next;
			}
		}
	}
}