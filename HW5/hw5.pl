/*******************************************/
/**    Your solution goes in this file    **/ 
/*******************************************/


fc_course(Course) :-
	course(Course, _, Unit),
	(Unit is 3; Unit is 4).

prereq_110(Course) :-
	course(Course, Prereq, _),
	member(ecs110, Prereq).

ecs140a_students(Name) :-
	student(Name, Course),
	member(ecs140a, Course).

check_ins(Inscourse) :-
	student(john, Stucourse),
	member(Course, Stucourse),
	member(Course, Inscourse),
	!. /* avoid duplicate */

instructor_names(Name) :-
	instructor(Name, Course),
	check_ins(Course).

check_stu(Stucourse) :-
	instructor(jim, Inscourse),
	member(Course, Inscourse),
	member(Course, Stucourse),
	!.

students(Name) :-
	student(Name, Course),
	check_stu(Course).


allprereq(X, All_Pre) :-
    find_course([X],All_Pre).	/*put bracket around input course*/

find_course([],[]).				/*deal with empty case*/

/*Deals with the case when tail is empty, */
/*checks if H is exist and append list of prereq*/
find_course([H|[]],All_Pre) :-
    (course(H,X,_) -> 			/*if(course of head value exist*/
    find_course(X,L),			/*if true: 	find_course(prereqs,L)*/
    append(L,X,All_Pre);		/*			append(L,prereqs,All_Pre)*/
    find_course([],L)),!.		/*else: 	findcourse([],L),terminate*/
    
/*Deals with the case when tail is not empty, */
/*so we recursively find the tail first, and then back to head*/
find_course([H|T],All_Pre) :-
    find_course(T,L1),			/*find_course(tail,L1)*/
    find_course([H],L2),		/*find_course(head,L2)*/
    append(L2,L1,All_Pre).		/*append(L2,L1,All_Pre)*/


all_length([], 0).

all_length([H|T], Length) :-
	atom(H),
	all_length(T, L_T),
	Length is 1 + L_T.

all_length([[HH|HT]|T], Length) :-
	(atom(HH) ->
		all_length(HT, L_HT),
		all_length(T, L_T),
		L_H is 1 + L_HT;
		
		all_length([HH|HT], L_H),
		all_length(T, L_T)),
	Length is L_H + L_T.

equal_a_b(L) :-
	count(L, 0, 0).

count([], A, B) :-
	A = B.

count([H|T], A, B) :-
		H = a ->
			(Temp is A + 1,
			count(T, Temp, B));
		H = b ->
			(Temp is B + 1,
			count(T, A, Temp));

	count(T, A, B).

pre_k_suf(Pre, K, Suf, L) :-
	append(Pre, L_Suf, L),
	append(K, Suf, L_Suf).

swap_prefix_suffix(K, L, S) :-
	pre_k_suf(Pre, K, Suf, L),
	pre_k_suf(Suf, K, Pre, S).

rev([],A,A).

rev([H|T],A,R) :-
	rev(T,[H|A],R). 

palin(L) :-
	rev(L, [], R),
	L = R.


/* A ::= 0 | 1AA */
good([0]).

good([1|T]) :-
	/* try combinations */
	append(TH, TT, T),
	good(TH),
	good(TT).

/* F:farmer, W:wolf, G:goat, C:cabbage */
/* States: */
/* 0: LLLL */
/* 1: RLRL */
/* 2: LLRL */
/* 3: RRRL */
/* 4: LRLL */
/* 5: RRLR */
/* 6: LRLR */
/* 7: RRRR */

opposite(left, right).
opposite(right, left).

/* SAFE RULES */
unsafe(state(left, _, right, right)).
unsafe(state(left, right, right, _)).
unsafe(state(right, left, left, _)).
unsafe(state(right, _, left, left)).
safe(A) :-
	\+ unsafe(A).

/* ARC TEMPLATES */
arc(take(none,A,B), state(A,W,G,C), state(B,W,G,C)) :-
	opposite(A,B).
arc(take(wolf,A,B), state(A,A,G,C), state(B,B,G,C)) :-
	opposite(A,B).
arc(take(goat,A,B), state(A,W,A,C), state(B,W,B,C)) :-
	opposite(A,B).
arc(take(cabbage,A,B), state(A,W,G,A), state(B,W,G,B)) :-
	opposite(A,B).

solve :-
	move(state(left, left, left, left), []),
	!.

/* FINISH */
move(state(right, right, right, right), _) :- !.

move(X, History) :-
	arc(N, X, Y),
	safe(Y),
	/* check duplicate state */
	\+ member(Y, History),
	write(N), nl,
	move(Y, [Y|History]).