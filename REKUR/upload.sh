#!/bin/sh
#Startscript Tourismusmodell
echo "Setze Variablen...."

# setenv PATH /System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin:/sw/bin:/sw/sbin:/bin:/sbin:/usr/bin:/usr/sbin:/Library/MySQL/bin:/ant/bin:/usr/X11R6/bin
# setenv JAVA_HOME /System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home


PATH=$PATH:/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin
JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home
export PATH
export JAVA_HOME

echo "Variablen gesetzt!"
echo $PATH
echo $JAVA_HOME

echo "Upload wird erstellt..."
ant -f build_build.xml upload


#ant startClient -Ddanubiaclient.id=node01
