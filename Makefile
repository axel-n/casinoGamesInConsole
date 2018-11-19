compile:
	javac -d target/classes src/main/java/games/Slot.java

run:
	java -cp target/classes games.Slot

clean:
	rm -rf target

compile-run: compile run
