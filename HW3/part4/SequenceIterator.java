class SequenceIterator {
	Sequence s;

	SequenceIterator(Sequence dest) {
		s = dest;
	}

	SequenceIterator advance() {
		s = s.rest();
		return this;
	}

	Element get() {
		return s.first();
	}

	public boolean equal(SequenceIterator other) {
		return (s.first() == other.s.first());
	}
}