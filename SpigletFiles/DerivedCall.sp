MAIN
	MOVE TEMP 2 DerivedCall_Vtable
	MOVE TEMP 3 HALLOCATE 4
	HSTORE TEMP 2 0 TEMP 3
	MOVE TEMP 4 DerivedCall_Main
	HSTORE TEMP 3 0 TEMP 4
 
	MOVE TEMP 5 A_Vtable
	MOVE TEMP 6 HALLOCATE 0
	HSTORE TEMP 5 0 TEMP 6
 
	MOVE TEMP 7 B_Vtable
	MOVE TEMP 8 HALLOCATE 0
	HSTORE TEMP 7 0 TEMP 8
 
	MOVE TEMP 9 F_Vtable
	MOVE TEMP 10 HALLOCATE 4
	HSTORE TEMP 9 0 TEMP 10
	MOVE TEMP 11 F_foo
	HSTORE TEMP 10 0 TEMP 11
 
	MOVE TEMP 15 F_Vtable
	MOVE TEMP 16 HALLOCATE 4
	HLOAD TEMP 17 TEMP 15 0
	HSTORE TEMP 16 0 TEMP 17
	MOVE TEMP 18 0
	MOVE TEMP 14 TEMP 16
	MOVE TEMP 20 B_Vtable
	MOVE TEMP 21 HALLOCATE 12
	HLOAD TEMP 22 TEMP 20 0
	HSTORE TEMP 21 0 TEMP 22
	MOVE TEMP 23 0
	HSTORE TEMP 21 4TEMP 23
	HSTORE TEMP 21 8TEMP 23
	MOVE TEMP 13 TEMP 21
	HLOAD TEMP 25 TEMP 14 0
	HLOAD TEMP 26 TEMP 25 0
	MOVE TEMP 27 CALL TEMP 26 (TEMP 14 TEMP 13 )
	MOVE TEMP 12 TEMP 27
	PRINT TEMP 12
END

F_foo [2]
BEGIN
	MOVE TEMP 29 0
RETURN
	TEMP 29
END