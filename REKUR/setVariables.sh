#!/bin/csh
#Startscript Tourismusmodell

setenv PATH /System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin:/sw/bin:/sw/sbin:/bin:/sbin:/usr/bin:/usr/sbin:/Library/MySQL/bin:/ant/bin:/usr/X11R6/bin
setenv JAVA_HOME /System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home



ant startClient -Ddanubiaclient.id=node01