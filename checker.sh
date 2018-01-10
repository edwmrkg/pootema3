#! /bin/bash

TASK1="1"
TASK2="2"
INPUTFOLDER1="./testeOficiale/task1"
INPUTFOLDER2="./testeOficiale/task2"
OUTPUTFILE="Labyrinth.out"
points=0
testnr=0

make clean
make build
echo
echo 

if [ $# -eq 0 ] || [ $1 == "1" ]; then
echo "Testing task1"
for file in $INPUTFOLDER1/*.in; do
#Run task1
	filename=$(echo $file | sed 's/\.in/.ok/g')
	testnr=$((testnr+1))
	make run ARGS="$TASK1 $file $OUTPUTFILE" > /dev/null
	diff "$OUTPUTFILE" "$filename" > /dev/null && echo "Test[$file].....................[PASSED]" && points=$((points+1)) || echo "Test[$file].....................[FAILED]" 
done
fi
echo
if [ $# -eq 0 ] || [ $1 == "2" ]; then
echo "Testing task2"
for file in $INPUTFOLDER2/*.in; do
#Run task2
	filename=$(echo $file | sed 's/\.in/.ok/g')
	testnr=$((testnr+1))
	make run ARGS="$TASK2 $file $OUTPUTFILE" > /dev/null
	diff "$OUTPUTFILE" "$filename" > /dev/null  && echo -e "Test[$file].....................[PASSED]" && points=$((points+1)) || echo "Test[$file].....................[FAILED]" 
done
fi
echo "Points: $points/59"

make clean
