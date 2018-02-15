;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defun all-length (l)
	(cond
		; if (l == null) return 0;
		((null l) 0)
		; if (l == atom) return 1;
		((atom l) 1)
		; else return (length(list.first()) + length(list.rest()));
		(t (+ (all-length(car l)) (all-length(cdr l))))	
	)
)

(defun range (l)
	; return list(min max)
	(list (lmin l) (lmax l))
)

(defun lmin(l)
	(cond
		; if(l.rest() == null) return l.first();
		((null (cdr l)) (car l))
		(t (min (car l) (lmin (cdr l))))
	)
)

(defun lmax(l)
	(cond
		((null (cdr l)) (car l))
		(t (max (car l) (lmax (cdr l))))
	)
)

; list before(begin, end, list) {
;	if(list == null) return null;
;	else if(list.first() == begin) return(append(list.first(), after(e, list.rest()));
;	else return before(b, e, list.rest());
;}

(defun before(begin end l)
	(cond
		; if(list == null) return null
		((null l) nil)
		; else if(list.first() == begin) return(append(list.first(), after(e, list.rest()))
		((equal begin (car l)) (append (list (car l)) (after end (cdr l))))
		; else return before(b, e, list.rest());
		(t (before begin end (cdr l)))
	)
)

(defun after(end l)
	(cond
		; if(list == null) return null
		((null l) nil)
		; else if(list.first()) == end) return list.first() // discard rest()
		((equal (car l) end) (list (car l)))
		; else return (append(first(), after(e, list.rest())) // keep front list
		(t (append (list (car l)) (after end (cdr l))))
	)
)

(defun split-if(f l)
	(list (lsplit f l nil) (lsplit f l t))
)

; list split(func, list, func_result) {
;	if(list is empty) return null;
;	else if (func(list.first()) == func_result)  
;		return list.first() + split(f, list.rest(), f_res);
;	else return split(f, list.rest(), f_res);
;}

(defun lsplit(f l f_res)
	(cond
		((null l) nil)
		((equal (funcall f (car l)) f_res) (append (list (car l)) (lsplit f (cdr l) f_res)))
		(t (lsplit f (cdr l) f_res))
	)
)

(defun group(l n)
	(cond
		((null l) nil)
		(t (lgroup l n ()))
	)
)

; list num list_of_num
(defun lgroup(l n l_n)
	(cond
		; if(l is empty) return l_n
		((null l) (list l_n))
		; if(l_n full) return(append(l_n to new_list))
		((equal (length l_n) n) (append (list l_n) (lgroup l n nil)))
		; if(l_n not full) return(lgroup(l.rest(), n, l_n + l.first()))
		((< (length l_n) n) (lgroup (cdr l) n (append l_n (list (car l)))))
	)
)

(defun mostn(f l)
	; build list
	(list (lmostn f l (get_s f l)) (get_s f l))
)

(defun get_s(f l)
	(cond
		((null l) 0)
		(t (max (funcall f (car l)) (get_s f (cdr l))))
	)
)

(defun lmostn(f l h_s)
	(cond
		((null l) nil)
		; if(list.first() == h_s) append(list.first(), lmostn(f, l.rest(), h_s))
		((equal h_s (funcall f (car l))) (append (list (car l)) (lmostn f (cdr l) h_s)))
		; simply skip l.first()
		(t (lmostn f (cdr l) h_s))
	)
)

(defun match(la lb)
	(cond
		; if (la == null || lb == null) return(la == lb)   // check number and type of atoms
		((or (null la) (null lb)) (equal la lb))
		; else if (? a) then return (match(la.rest(), lb.rest());
		((equal '? (car la)) (match (cdr la) (cdr lb)))
		; else if (! a) then return (match(la.rest(), lb.rest()) || match(la, lb.rest()));
		((equal '! (car la)) (or (match (cdr la) (cdr lb)) (match la (cdr lb))))
		; else return(la.first()==lb.first() && match(la.rest(), lb.rest()));
		(t (and (equal (car la) (car lb)) (match (cdr la) (cdr lb))))
	)
)

(defun match-var(la lb)
	; initialize var's
	(reset la)
	(match-var-init la lb)
)

; reset var to nil
(defun reset(l)
	(cond
		((null l) nil)
		; if (l.first() is a list && l.first() != null )   {l.first().second() = null;  reset(l.rest())}
		((and (listp (car l)) (not (null (car l)))) (makunbound (cadar l)) (reset (cdr l)))
		; else reset(l,rest());
		(t (reset (cdr l)))
	)
)

; match-var-initialized
(defun match-var-init(la lb)
	(cond
		((or (null la) (null lb)) (equal la lb))
		; if (? a) do single assign
		((and (listp (car la)) (equal '? (caar la))) (assign la lb))
		; else if (! a) do list assign
		((and (listp (car la)) (equal '! (caar la))) (l_assign la lb))
		; else return(la.first()==lb.first() && match-var-init(la.rest(), lb.rest());
		(t (and (equal (car la) (car lb)) (match-var-init (cdr la) (cdr lb))))
	)
)

; atom_assign
(defun assign(la lb)
	; a in (? a), a = lb.first()
	(set (cadar la) (car lb))
	; do rest
	(match-var-init (cdr la) (cdr lb))
)

; list_assign
(defun l_assign(la lb)
	; new list or append
	(cond
        		; if (a in (! a) == null) a=list(lb.first());
      		; else if (a is a list already) a = a+list(lb.first());
        		((not (boundp (cadar la))) (set (cadar la) (list(car lb))))
        		((listp (eval (cadar la))) (set (cadar la) (append (eval (cadar la)) (list(car lb)))))      
      	)
	; same as match()
	(or (match-var-init (cdr la) (cdr  lb)) (match-var-init la (cdr lb)))
)