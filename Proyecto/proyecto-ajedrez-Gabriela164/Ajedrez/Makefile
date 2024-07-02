PATH_TO_FX="/home/gabriela/Descargas/openjfx-21_linux-x64_bin-sdk/javafx-sdk-21/lib"
WORKING_DIRECTORY="$(shell pwd)/classes"

compile:
	javac --module-path $(PATH_TO_FX) --add-modules=javafx.controls,javafx.fxml,java.logging -d ./classes --source-path ./src/ajedrez/classes/ src/ajedrez/classes/ia/ajedrez/ApTablero.java
	javac --module-path $(PATH_TO_FX) --add-modules=javafx.controls,javafx.fxml,java.logging -d ./classes --source-path ./src/ajedrez/classes/ src/ajedrez/classes/ia/ajedrez/ControladorTablero.java
	cp -r ./resources ./classes

run:
	java -Duser.dir=$(WORKING_DIRECTORY) --module-path $(PATH_TO_FX) --add-modules=javafx.controls,javafx.fxml,java.logging ia.ajedrez.ApTablero

.PHONY: clean
clean:
	rm -rf classes
