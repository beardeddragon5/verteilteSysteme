package main

import (
	"fmt"
	"math/rand"
)

const Processes = 10

type Process struct {
	pid    int
	active bool
}

func (p Process) String() string {
	var out = "inactive"
	if p.active {
		out = "active"
	}
	return fmt.Sprintf("p(%d, %s)", p.pid, out)
}

func (p Process) elect(processes []Process, sender int) bool {
	if !p.active {
		return false
	}
	if sender != -1 {
		fmt.Printf("OK-Nachricht von Prozess %d an Prozess %d gesendet\n", p.pid, sender)
	}

	var receivedOk = false
	for pid := p.pid + 1; pid < len(processes); pid++ {
		//for pid := len(processes) - 1; pid > p.pid; pid-- {
		fmt.Printf("ELECT-Nachricht von Prozess %d an Prozess %d gesendet\n", p.pid, pid)
		if processes[pid].elect(processes, p.pid) {
			receivedOk = true
			//break
		}
	}

	if !receivedOk {
		p.coord(processes)
	}
	return true
}

func (p Process) coord(processes []Process) {
	for pid := 0; pid < len(processes); pid++ {
		if processes[pid].active {
			fmt.Printf("COORD-Nachricht von Prozess %d an Prozess %d gesendet\n", p.pid, pid)
		}
	}
}

func main() {
	var processes = make([]Process, Processes)
	for i := 0; i < Processes; i++ {
		processes[i].pid = i
		processes[i].active = rand.Float32() < 1.5
	}

	fmt.Printf("%v\n", processes)

	var userDefinedProcessToKill int // rand.Int31n(Processes)
	fmt.Print("Process to kill: ")
	fmt.Scanf("%d\n", &userDefinedProcessToKill)
	var userDefinedProcessToStartElect int // rand.Int31n(Processes - 1)
	fmt.Print("Process for election: ")
	fmt.Scanf("%d\n", &userDefinedProcessToStartElect)

	processes[userDefinedProcessToKill].active = false

	fmt.Printf("%v\n", processes)

	// for _, process := range processes {
	// 	if process.active {
	// 		userDefinedProcessToStartElect = process.pid
	// 		break
	// 	}
	// }

	fmt.Printf("kill: %d elect: %d\n", userDefinedProcessToKill, userDefinedProcessToStartElect)

	processes[userDefinedProcessToStartElect].elect(processes, -1)
}
