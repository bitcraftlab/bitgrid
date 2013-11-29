# Simple Makefile for Processing Libraries
# (c) bitcraftlab 2013

NAMESPACE=bitcraftlab
NAME=bitgrid

LIBRARY=library/$(NAME).jar
JARFILE=build/$(NAME).jar
SOURCES=src/$(NAMESPACE)/$(NAME)/*.java
CLASSES=build/$(NAMESPACE)/$(NAME)/*.class
P5_PATH=/Applications/Processing/Processing-1.5.1.app/Contents/Resources/Java
P5_JARS=$(P5_PATH)/core.jar

install:	$(LIBRARY)
build:		$(JARFILE)
compile:	$(CLASSES)

$(LIBRARY):	$(JARFILE)
	mkdir -p library
	cp $(JARFILE) $(LIBRARY)

$(JARFILE):	$(CLASSES)
	jar -cf $(JARFILE) -C build $(NAMESPACE)

$(CLASSES):	$(SOURCES)
	mkdir -p build
	javac -cp "${P5_JARS}" -d build ${SOURCES}

clean:
	rm -r build/*

