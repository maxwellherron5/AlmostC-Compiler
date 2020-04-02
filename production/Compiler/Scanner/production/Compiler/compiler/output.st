*****PRINTING SYMBOL TABLE*****
|Name      |Kind      |
|----------|----------|
|a         |VARIABLE  |
|----------|----------|
|b         |VARIABLE  |
|----------|----------|
|myFunc    |FUNCTION  |
|----------|----------|


*****PRINTING SYNTAX TREE*****
Program:
|-- FunctionDefinitions
|-- --- Function: myFunc returns null
|-- --- --- Compound Statement
|-- --- --- --- Declarations
|-- --- --- --- Return
|-- --- --- --- --- Operation: MINUS
|-- --- --- --- --- --- Name: one
|-- --- --- --- --- --- Name: two
|-- Compound Statement
|-- --- Declarations
|-- --- --- Name: a
|-- --- --- Name: b
|-- --- Assignment
|-- --- --- Name: a
|-- --- --- Operation: PLUS
|-- --- --- --- Value: 1
|-- --- --- --- Value: 3
|-- --- Assignment
|-- --- --- Name: b
|-- --- --- Name: a
|-- --- If
|-- --- --- Operation: NOT_EQUAL
|-- --- --- --- Function: yetAnother returns null
|-- --- --- --- --- Name: a
|-- --- --- --- --- Name: b
|-- --- --- --- Value: 2
|-- --- --- Compound Statement
|-- --- --- --- Declarations
|-- --- --- --- Return
|-- --- --- --- --- Value: 5
|-- --- --- Compound Statement
|-- --- --- --- Declarations
|-- --- --- --- Return
|-- --- --- --- --- Value: 1
