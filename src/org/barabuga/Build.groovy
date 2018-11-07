package org.barabuga;


class Build {
	def globals
	Build(Map parameters)
	{
		this.globals = parameters.get('globals')
	}
	
	def buildUnix(label, stashName) {
		globals.node(label) {
			globals.checkout globals.scm
			globals.sh """#!/bin/sh
				gcc hello.c -o hello_${label}.out
			"""
			globals.stash name: stashName, includes: "*.out"
			globals.deleteDir()
		}
	}

	def buildWin(label, stashName) {
		globals.node(label) {
			globals.checkout globals.scm
			String vsvars_bat = 'Microsoft Visual Studio 12.0\\VC\\vcvarsall.bat'
			globals.bat """
				call "%ProgramFiles(X86)%\\${vsvars_bat}" x86
				cl.exe hello.c
			"""
			globals.stash name: stashName, includes: "*.exe"
			globals.deleteDir()
		}
	}
}