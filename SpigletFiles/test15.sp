MAIN
	MOVE TEMP 2 test15_Vtable
	MOVE TEMP 3 HALLOCATE 4
	HSTORE TEMP 2 0 TEMP 3
	MOVE TEMP 4 test15_Main
	HSTORE TEMP 3 0 TEMP 4
 
	MOVE TEMP 5 Test_Vtable
	MOVE TEMP 6 HALLOCATE 12
	HSTORE TEMP 5 0 TEMP 6
	MOVE TEMP 7 Test_start
	HSTORE TEMP 6 0 TEMP 7
	MOVE TEMP 8 Test_mutual1
	HSTORE TEMP 6 4 TEMP 8
	MOVE TEMP 9 Test_mutual2
	HSTORE TEMP 6 8 TEMP 9
 
	MOVE TEMP 10 Test_Vtable
	MOVE TEMP 11 HALLOCATE 12
	HLOAD TEMP 12 TEMP 10 0
	HSTORE TEMP 11 0 TEMP 12
	MOVE TEMP 13 0
	HSTORE TEMP 11 4TEMP 13
	HSTORE TEMP 11 8TEMP 13
	HLOAD TEMP 14 TEMP 11 0
	HLOAD TEMP 15 TEMP 14 0
	MOVE TEMP 16 CALL TEMP 15 (TEMP 11 )
	PRINT TEMP 16
END

Test_start [1]
BEGIN
	MOVE TEMP 17 PLUS TEMP 0 4
	MOVE TEMP 18 4
	HSTORE TEMP 17 0 TEMP 18
	MOVE TEMP 20 PLUS TEMP 0 8
	MOVE TEMP 21 0
	HSTORE TEMP 20 0 TEMP 21
	HLOAD TEMP 23 TEMP 0 0
	HLOAD TEMP 24 TEMP 23 4
	MOVE TEMP 25 CALL TEMP 24 (TEMP 0 )
RETURN
	TEMP 25
END
Test_mutual1 [1]
BEGIN
	MOVE TEMP 27 PLUS TEMP 0 4
	HLOAD TEMP 28 TEMP 0 4
	MOVE TEMP 29 1
	MOVE TEMP 30 MINUS TEMP 28 TEMP 29
	HSTORE TEMP 27 0 TEMP 30
	HLOAD TEMP 32 TEMP 0 4
	MOVE TEMP 33 0
	MOVE TEMP 34 LT TEMP 32 TEMP 33
	CJUMP TEMP 34 L1
	MOVE TEMP 35 PLUS TEMP 0 8
	MOVE TEMP 36 0
	HSTORE TEMP 35 0 TEMP 36
	JUMP L2
L1
	NOOP
	HLOAD TEMP 38 TEMP 0 8
	PRINT TEMP 38
	MOVE TEMP 39 PLUS TEMP 0 8
	MOVE TEMP 40 1
	HSTORE TEMP 39 0 TEMP 40
	HLOAD TEMP 42 TEMP 0 0
	HLOAD TEMP 43 TEMP 42 8
	MOVE TEMP 44 CALL TEMP 43 (TEMP 0 )
	MOVE TEMP 26 TEMP 44
L2
	NOOP
	HLOAD TEMP 46 TEMP 0 8
RETURN
	TEMP 46
END
Test_mutual2 [1]
BEGIN
	MOVE TEMP 48 PLUS TEMP 0 4
	HLOAD TEMP 49 TEMP 0 4
	MOVE TEMP 50 1
	MOVE TEMP 51 MINUS TEMP 49 TEMP 50
	HSTORE TEMP 48 0 TEMP 51
	HLOAD TEMP 53 TEMP 0 4
	MOVE TEMP 54 0
	MOVE TEMP 55 LT TEMP 53 TEMP 54
	CJUMP TEMP 55 L3
	MOVE TEMP 56 PLUS TEMP 0 8
	MOVE TEMP 57 0
	HSTORE TEMP 56 0 TEMP 57
	JUMP L4
L3
	NOOP
	HLOAD TEMP 59 TEMP 0 8
	PRINT TEMP 59
	MOVE TEMP 60 PLUS TEMP 0 8
	MOVE TEMP 61 0
	HSTORE TEMP 60 0 TEMP 61
	HLOAD TEMP 63 TEMP 0 0
	HLOAD TEMP 64 TEMP 63 4
	MOVE TEMP 65 CALL TEMP 64 (TEMP 0 )
	MOVE TEMP 47 TEMP 65
L4
	NOOP
	HLOAD TEMP 67 TEMP 0 8
RETURN
	TEMP 67
END