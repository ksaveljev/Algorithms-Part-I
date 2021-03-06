#!/bin/bash

# *************************************************
# config.sh
# -------------------
# This should be sourced in the profile of a sh,
# bash, ksh, or zsh shell.
# Last edited: September 18, 2011
# *************************************************

if [ "$ALGS4_CONFIG_SOURCED" != "true" ]; then

	# Standard libraries
	export CLASSPATH=$CLASSPATH:$HOME/github/Algorithms-Part-I/stdlib.jar:$HOME/github/Algorithms-Part-I/algs4.jar
	
	# Checkstyle and Findbugs
	export PATH=$PATH:$HOME/github/Algorithms-Part-I/bin
	
	# Making sure these variables aren't doubly set
	export ALGS4_CONFIG_SOURCED=true
	
fi                                          
