MAIN
	MOVE TEMP 2 ArrayTest_Vtable
	MOVE TEMP 3 HALLOCATE 4
	HSTORE TEMP 2 0 TEMP 3
	MOVE TEMP 4 ArrayTest_Main
	HSTORE TEMP 3 0 TEMP 4
 
	MOVE TEMP 5 Test_Vtable
	MOVE TEMP 6 HALLOCATE 4
	HSTORE TEMP 5 0 TEMP 6
	MOVE TEMP 7 Test_start
	HSTORE TEMP 6 0 TEMP 7
 
	MOVE TEMP 9 Test_Vtable
	MOVE TEMP 10 HALLOCATE 4
	HLOAD TEMP 11 TEMP 9 0
	HSTORE TEMP 10 0 TEMP 11
	MOVE TEMP 12 0
	MOVE TEMP 13 10
	HLOAD TEMP 14 TEMP 10 0
	HLOAD TEMP 15 TEMP 14 0
	MOVE TEMP 16 CALL TEMP 15 (TEMP 10 TEMP 13 )
	MOVE TEMP 8 TEMP 16
END

Test_start [2]
BEGIN
	MOVE TEMP 21 LT TEMP 1 0
	CJUMP TEMP 21 L1
	ERROR
L1
	NOOP
	MOVE TEMP 22 PLUS TEMP 1 1
	MOVE TEMP 22 TIMES TEMP 22 4
	MOVE TEMP 23 HALLOCATE TEMP 22
	HSTORE TEMP 23 0 TEMP 1
	MOVE TEMP 24 0 
	MOVE TEMP 21 LT TEMP 24 TEMP 1
	MOVE TEMP 25 PLUS TEMP 23 4
	MOVE TEMP 26 PLUS TEMP 1 TEMP 22
	CJUMP TEMP 21 L2
L3
	NOOP
	HSTORE TEMP 25 0 TEMP 24
	MOVE TEMP 25 PLUS TEMP 25 4
	MOVE TEMP 21 LT TEMP 26 TEMP 25
	CJUMP TEMP 21 L3
L2
	NOOP
	MOVE TEMP 18 TEMP 23
	HLOAD TEMP 28 TEMP 18 0
	MOVE TEMP 19 TEMP 28
	MOVE TEMP 30 0
	MOVE TEMP 20 TEMP 30
L4
	NOOP
	MOVE TEMP 32 LT TEMP 20 TEMP 19
	CJUMP TEMP 32 L5
	HLOAD TEMP 33 TEMP 18 0
	MOVE TEMP 34 LT TEMP 20 0
	CJUMP TEMP 34 L6
	ERROR
L6
	NOOP
	MOVE TEMP 35 PLUS TEMP 20 1
	MOVE TEMP 35 TIMES TEMP 35 4
	MOVE TEMP 35 PLUS TEMP 18 TEMP 35
	HSTORE TEMP 35 0 TEMP 20
	HLOAD TEMP 36 TEMP 18 0
	MOVE TEMP 36 MINUS TEMP 36 1
	MOVE TEMP 37 LT TEMP 36 TEMP 20
	CJUMP TEMP 37 L7
	ERROR
L7
	NOOP
	MOVE TEMP 37 LT TEMP 20 0
	CJUMP TEMP 37 L8
	ERROR
L8
	NOOP
	MOVE TEMP 41 PLUS TEMP 20 1
	MOVE TEMP 38 TIMES TEMP 41 4
	MOVE TEMP 39 PLUS TEMP 18 TEMP 38
	HLOAD TEMP 40 TEMP 39  0
	PRINT TEMP 40
	MOVE TEMP 42 1
	MOVE TEMP 43 PLUS TEMP 20 TEMP 42
	MOVE TEMP 20 TEMP 43
	JUMP L4
L5
	NOOP
	MOVE TEMP 45 1
RETURN
	TEMP 45
END
