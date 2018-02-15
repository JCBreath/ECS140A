class Matrix extends Sequence {

	int row, col; // dimension
	Sequence s; // store matrix elements

	Matrix(int rowsize, int colsize) {
		row = rowsize;
		col = colsize;
		s = new Sequence();
		// initialize empty matrix
		for(int i = 0; i < row*col; i++) {
			MyInteger num = new MyInteger();
			s.add(num, i);
		}

	}

	void Set(int rowsize, int colsize, int value) {
		((MyInteger)s.index(rowsize*col+colsize)).Set(value);
	}

	int Get(int rowsize, int colsize) {
		return ((MyInteger)s.index(rowsize*col+colsize)).Get();
	}

	Matrix Sum(Matrix mat) {
		Matrix result = new Matrix(row, col);
		// result.s[i] = this.s[i] + mat.s[i]
		for(int i = 0; i < row * col; i++)
			((MyInteger)result.s.index(i)).Set(((MyInteger)s.index(i)).Get() + ((MyInteger)mat.s.index(i)).Get());
		return result;
	}

	Matrix Product(Matrix mat) {
		Matrix result = new Matrix(row, mat.col);
		// check compatibility
		if(col == mat.row) {
			// construct rows of result
			for(int i = 0; i < result.row; i++) {
				// construct columns of result
				for(int j = 0; j < result.col; j++) {
					int sum = 0;
					//result.s[i, j] = this.s[i, k] * mat.s[k, j];
					for(int k = 0; k < col; k++) {
						sum += Get(i, k) * mat.Get(k, j);
					}
					result.Set(i, j, sum);
				}
			}
		}
		else {
			System.out.println("Matrix dimensions incompatible for Product");
			System.exit(1);
		}
		return result;
	}

	public void Print() {
		for(int i = 0; i < row; i++) {
			System.out.print("[ ");
			for(int j = 0; j < col; j++) {
				System.out.print(Get(i, j) + " ");
			}
			System.out.println("]");
		}
	}
}