JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=BarrierReusable.class Methane.class Carbon.class Hydrogen.class RunSimulation.class 

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm bin/molecule/*.class
run:
	java -cp bin molecule.RunSimulation 12 3

run1:
	java -cp bin molecule.RunSimulation 20 5

run2:
	java -cp bin molecule.RunSimulation 1000 250

run3:
	java -cp bin molecule.RunSimulation 1500 375

