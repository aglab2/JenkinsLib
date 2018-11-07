package org.barabuga;

def buildUnix(label, stashName) {
	node(label) {
		checkout scm
		sh """#!/bin/sh
			gcc hello.c -o hello_${label}.out
		"""
		stash name: stashName, includes: "*.out"
		deleteDir()
	}
}

def buildWin(label, stashName) {
	node(label) {
		checkout scm
		String vsvars_bat = 'Microsoft Visual Studio 12.0\\VC\\vcvarsall.bat'
		bat """
			call "%ProgramFiles(X86)%\\${vsvars_bat}" x86
			cl.exe hello.c
		"""
		stash name: stashName, includes: "*.exe"
		deleteDir()
	}
}

return this;