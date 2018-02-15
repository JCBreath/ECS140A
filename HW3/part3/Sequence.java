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

	public Element index(int pos) {
		if(pos >= 0 && pos <= length()) {
			Sequence cur = this;
			for(int i = 0; i<pos; i++)
				cur = cur.next;
			return cur.elm;
		} else {
			System.err.println("Error: Invalid pos value");
			System.exit(1);
		}
		return null;
	}

	public Sequence flatten() {
		Sequence s = new Sequence();
		
		// Traverse sequence
		for(Sequence cur = this; cur != null; cur = cur.next) {
			// element is a sequence
			if(cur.elm instanceof Sequence) {
				Sequence sub = (Sequence) cur.elm;
				sub = sub.flatten();
				// traverse sub-sequence
				while(sub != null) {
					// if first element
					if(s.elm == null)
						s.add(sub.elm, 0);
					else
						s.add(sub.elm, s.length());
					sub = sub.next;
				}
			}
			// element is a char or int
			else {
				// if first element
				if(s.elm == null)
					s.add(cur.elm, 0);
				else
					s.add(cur.elm, s.length());
			}
		}

		return s;
	} 

	public Sequence copy() {
		Sequence s = new Sequence();
		for(Sequence cur = this; cur != null; cur = cur.next) {
			// element is a sequence
			if(cur.elm instanceof Sequence) {
				// if first element
				if(s.elm==null)
					s.add(((Sequence)cur.elm).copy(), 0);
				else
					s.add(((Sequence)cur.elm).copy(), s.length());
			}
			// element is a char
			else if(cur.elm instanceof MyChar) {
				MyChar c = new MyChar();
				c.Set(((MyChar)cur.elm).Get());
				if(s.elm==null)
					s.add(c, 0);
				else
					s.add(c, s.length());

			}
			// element is an int
			else {
				MyInteger i = new MyInteger();
				i.Set(((MyInteger)cur.elm).Get());
				if(s.elm==null)
					s.add(i, 0);
				else
					s.add(i, s.length());
			}
		}
		return s;
	}

}