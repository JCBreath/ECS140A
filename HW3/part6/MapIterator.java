class MapIterator extends SequenceIterator {

	MapIterator(Sequence dest) {
		super(dest);
	}

	Pair get() {
		return (Pair)s.first();
	}
}