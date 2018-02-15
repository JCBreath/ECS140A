class Map extends Sequence {

	public MapIterator begin() {
		MapIterator itr = new MapIterator(this);
		return itr;
	}

	public MapIterator end() {
		// use Sequence end() to construct an end iterator
		MapIterator itr = new MapIterator(super.end().s);
		return itr;
	}

	public void Print() {
		// use Sequence Print()
		super.Print();
	}

	public void add(Pair inval) {		
		int i=0;
		// place pair in ascending order of key
		for(MapIterator itr = begin(); itr.get() != null && itr.get().key.Get() <= inval.key.Get(); ) {
			i++;
			// go to next pair and check
			if(itr.advance().s == null)
				break;
		}
		super.add(inval, i);
	}

	public MapIterator find(MyChar key) {
		MapIterator itr = begin();
		// until key matches or reaches end
		while(itr.get() != null && key.Get() != itr.get().key.Get())
			itr.advance();
		return itr;
	}


}